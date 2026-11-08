/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controlador.factory.DAOFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.dao.HuecoDAO;
import modelo.dao.PreviewDAO;
import modelo.dao.SesionDAO;
import modelo.dao.UsuarioDAO;
import modelo.vo.Detalle;
import modelo.vo.FechaSesion;
import modelo.vo.Hueco;
import modelo.vo.Preview;
import modelo.vo.Sesion;
import modelo.vo.SolicitudPendiente;
import modelo.vo.Usuario;
import vista.vistaCliente;
import vista.vistaHueco;
import vista.vistaInforme;
import vista.vistaLogin;
import vista.vistaMenuPrincipal;
import vista.vistaPreview;
import vista.vistaSesion;
import vista.vistaSolicitudes;

/**
 *
 * @author Acceso a datos
 */
public class controladorPrincipal {

    public static DAOFactory mySQLFactory;
    //declara los objetos DAO
    public static UsuarioDAO usuDAO;
    public static HuecoDAO hueDAO;
    public static SesionDAO sesDAO;
    public static PreviewDAO prevDAO;

    public static vistaLogin ventanaLogin = new vistaLogin();
    public static vistaMenuPrincipal ventanaSelector = new vistaMenuPrincipal();
    public static vistaCliente ventanaCliente = new vistaCliente();
    public static vistaHueco ventanaHueco = new vistaHueco();
    public static vistaSolicitudes ventanaSolicitudes = new vistaSolicitudes();
    public static vistaSesion ventanaSesiones = new vistaSesion();
    public static vistaPreview ventanaPreview = new vistaPreview();

    //Variable para la busqueda por mail y volcado en tabla (el mail es el segundo campo de la tabla)
    private static final int COL_EMAIL = 2;

    public static void iniciar() {

        iniciaFactory();

        /*
        //Mensaje para comprobar conexion
        try {
            Connection conn = mySQLFactory.getConnection();
            JOptionPane.showMessageDialog(null, "Conectado a shuttware");
            mySQLFactory.releaseConnection(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sin conexion: " + ex.getMessage());
        }
         */
        //Abrir ventanaLogin principal
        ventanaLogin.setVisible(true);
        //Al hacer la ventanaLogin login visible se pierde el foco para que no haya ningun elemento seleccionado
        ventanaLogin.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> ventanaLogin.soltarFoco());
        ventanaLogin.setLocationRelativeTo(null);
    }

    public static void iniciaFactory() {
        mySQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        //inicializa los objetos DAO
        usuDAO = mySQLFactory.getUsuarioDAO();
        hueDAO = mySQLFactory.getHuecoDAO();
        sesDAO = mySQLFactory.getSesionDAO();
        prevDAO = mySQLFactory.getPreviewDAO();
    }

    public static void cerrarFactory() {
        try {
            mySQLFactory.shutdown();
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo de loggueo para la base de datos
    public static void entrar() {
        //Se guardan en variables los datos introducidos en la vista
        String email = ventanaLogin.getEmail();
        String clave = ventanaLogin.getClave();

        //Faltan datos en alguno de los campos
        if (email.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(null, idioma.t("login.faltamail"));
            return;
        }
        //En caso contrario se intenta entrar
        try {
            Connection conn = mySQLFactory.getConnection();
            Usuario u = usuDAO.cargarPorEmail(conn, email);
            mySQLFactory.releaseConnection(conn);

            //Para poder entrar se necesita encontrar mail, contrasenia, tipo fotografo e id=1
            if (u == null || !u.getContrasenia().equals(clave)
                    || !"fotografo".equals(u.getRol()) || u.getIdUsuario() != 1) {
                JOptionPane.showMessageDialog(null, idioma.t("login.accesodenegado"));
                return;
            }

            //Se entra en el menu principal y se sale de la ventana de logueo      
            ventanaSelector.aplicarTextos();
            ventanaSelector.setVisible(true);
            ventanaLogin.setVisible(false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("login.sinconexion") + ex.getMessage());
        }

    }

    //Metodo de carga de tabla en vista cliente
    public static void cargarTablaClientes(vistaCliente vista) {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Id", "Nombre", "Email", "Telefono"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Se establece la conexion con la base de datos
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            //Se recorre la lista de registros
            for (Usuario u : usuDAO.listarClientes(conn)) {
                modelo.addRow(new Object[]{
                    u.getIdUsuario(), u.getNombre(), u.getEmail(), u.getTelefono()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajeerror"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }

        vista.getTblClientes().setModel(modelo);
        vista.getTblClientes().getColumnModel().getColumn(0).setMinWidth(0);
        vista.getTblClientes().getColumnModel().getColumn(0).setMaxWidth(0);
    }

    //Metodo que carga los datos del registro seleccionado en la tabla de la vista
    public static void cargarClienteSeleccionado(vistaCliente ventana) {
        int fila = ventana.getTblClientes().getSelectedRow();
        if (fila == -1) {
            return;
        }
        int id = (int) ventana.getTblClientes().getValueAt(fila, 0);

        //Se lanza la conexion
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            Usuario u = usuDAO.cargarPorId(conn, id);
            if (u == null) {
                return;
            }
            ventana.getTxtModiNombre().setText(u.getNombre());
            ventana.getTxtModiApel().setText(u.getApellidos());
            ventana.getTxtModiMail().setText(u.getEmail());
            ventana.getTxtModiTelf().setText(u.getTelefono() == null ? "" : u.getTelefono().toString());
            ventana.getTxtModiInsta().setText(u.getUrlInstagram());
            ventana.getTxtModiNota().setText(u.getNotaInterna());
            ventana.getRbModiCliente().setSelected("cliente".equals(u.getRol()));
            ventana.getRdModiFotografo().setSelected("fotografo".equals(u.getRol()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajeclienteerror"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Deja en la tabla solo los clientes cuyo email empieza por el texto escrito
    @SuppressWarnings("unchecked")
    public static void filtrarClientesPorEmail(vistaCliente vista) {
        JTable tabla = vista.getTblClientes();

        //Se reutiliza el sorter de la tabla o se crea si no existe (o si se cambio el modelo)
        TableRowSorter<TableModel> sorter;
        if (tabla.getRowSorter() instanceof TableRowSorter) {
            sorter = (TableRowSorter<TableModel>) tabla.getRowSorter();
        } else {
            sorter = new TableRowSorter<>(tabla.getModel());
            tabla.setRowSorter(sorter);
        }

        //Se obtiene el texto escrito en el campo de búsqueda
        final String texto = vista.getTxtBusquedaMail().getText().trim().toLowerCase();

        //En caso de caja sin texto se muestran todos los registros
        if (texto.isEmpty()) {
            sorter.setRowFilter(null);   //sin texto se ven todos
            return;
        }

        //Filtrado por mail
        sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> fila) {
                Object email = fila.getValue(COL_EMAIL);
                return email != null && email.toString().toLowerCase().startsWith(texto);
            }
        });
    }

    //Metodo de baja de cliente por fila de tabla seleccionada
    public static void bajaCliente(vistaCliente vista) {
        //Se guarda la posicion del cliente en tabla
        int fila = vista.getTblClientes().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un cliente");
            return;
        }

        //En caso de que el id fuese 1 en algun caso salta error
        int id = (int) vista.getTblClientes().getValueAt(fila, 0);
        if (id == 1) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajenobaja"));
            return;
        }

        //Aviso de borrado en cascada
        int ok = JOptionPane.showConfirmDialog(null,
                idioma.t("cliente.mensajeborrarsesion"),
                idioma.t("cliente.baja"), JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }

        //Se establece la conexion
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            //Se lanza el borrado del DAO y se resetean los valores de las areas
            usuDAO.borrar(conn, id);
            JOptionPane.showMessageDialog(null, "Cliente borrado");
            conn.commit();
            cargarTablaClientes(vista);
            vista.getTxtModiNombre().setText("");
            vista.getTxtModiApel().setText("");
            vista.getTxtModiMail().setText("");
            vista.getTxtModiTelf().setText("");
            vista.getTxtModiInsta().setText("");
            vista.getTxtModiNota().setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajebajaerror"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Insercion de datos en cliente
    public static void insertarRegistro(vistaCliente vista) {
        //Se recogen los datos de la vista
        String nombre = vista.getTxtNombre().getText().trim();
        String email = vista.getTxtEmail().getText().trim();
        //Al tener que hacer la creacion de usuario por parte del fotografo se genera una contraseña que tendra que cambiar el cliente
        String clave = "1234";
        String rol = vista.getRdClient().isSelected() ? "cliente" : null;
        if (vista.getRdFoto().isSelected()) {
            rol = "fotografo";
        }
        //Es necesario que estos campos esten cubiertos
        if (nombre.isEmpty() || email.isEmpty() || rol == null) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajedatos"));
            return;
        }

        //Se recogen los campos que pueden ser nulos
        String apellidos = vista.getTxtApellidos().getText();
        String insta = vista.getTxtInsta().getText();
        String nota = vista.getTxtNota().getText();
        Integer telefono = null;
        String tel = vista.getTxtTelf().getText().trim();
        if (!tel.isEmpty()) {
            try {
                telefono = Integer.valueOf(tel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajetelf"));
                return;
            }
        }

        //Se realiza conexion
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            usuDAO.insertar(conn, email, clave, rol, nombre, apellidos, telefono, nota, insta);
            conn.commit();

            //Hay que volver a cargar los clientes para la tabla
            cargarTablaClientes(vista);

            JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajeguardado"));

            //Se restablecen los txtFields
            vista.getTxtNombre().setText("");
            vista.getTxtApellidos().setText("");
            vista.getTxtEmail().setText("");
            vista.getTxtTelf().setText("");
            vista.getTxtInsta().setText("");
            vista.getTxtNota().setText("");
            vista.getBtnGroupTipo().clearSelection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.correorepetido"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.errordatos"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo que recoge los campos con los datos de cliente y en caso de cambio en alguno se realiza modificacion
    public static void modificarCiente(vistaCliente vista) {
        //Se selecciona el cliente en la tabla 
        int fila = vista.getTblClientes().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un cliente");
            return;
        }
        int id = (int) vista.getTblClientes().getValueAt(fila, 0);

        //Se recogen sus datos confirmando que no queden vacios los que no pueden ser null
        String nombre = vista.getTxtModiNombre().getText().trim();
        String email = vista.getTxtModiMail().getText().trim();
        String apellidos = vista.getTxtModiApel().getText();
        String insta = vista.getTxtModiInsta().getText();
        String nota = vista.getTxtModiNota().getText();
        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajenovacios"));
            return;
        }
        Integer telefono = null;
        String tel = vista.getTxtModiTelf().getText().trim();
        if (!tel.isEmpty()) {
            try {
                telefono = Integer.valueOf(tel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, idioma.t("cliente.mensajetelf"));
                return;
            }
        }

        //Se establece la conexion
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            //Se carga el usuario para comprobar si existe
            Usuario u = usuDAO.cargarPorId(conn, id);
            if (u == null) {
                JOptionPane.showMessageDialog(null, idioma.t("cliente.noexiste"));
                return;
            }
            //Se comprueba si ha habido cambios en las entradas a la hora de pulsar "modificar"
            boolean igual = nombre.equals(u.getNombre())
                    && email.equals(u.getEmail())
                    && mismo(apellidos, u.getApellidos())
                    && mismo(insta, u.getUrlInstagram())
                    && mismo(nota, u.getNotaInterna())
                    && mismoTel(telefono, u.getTelefono());
            if (igual) {
                JOptionPane.showMessageDialog(null, idioma.t("cliente.nocambios"));
                return;
            }
            //Se lanza el metodo del DAO
            usuDAO.modificar(conn, id, email, nombre, apellidos, telefono, nota, insta);
            JOptionPane.showConfirmDialog(null, "Cliente modificado");
            conn.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.nomodi"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("cliente.errormodi"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo para comprobar si el telefono se repite en la BD (Para la hora de modificar registros)
    private static boolean mismoTel(Integer a, Integer b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    //Metodo de carga de huecos en el calendario
    public static void cargarHuecos(vistaHueco vista) {
        Connection conn = null;
        try {
            //Se lanza la conexion con la BD
            conn = mySQLFactory.getConnection();
            vista.mostrarHuecos(hueDAO.listar(conn));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("hueco.nopubli"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo que gestiona las pulsaciones en el calendario para aniadir dias o darlos de baja
    public static void pulsarDia(vistaHueco vista, LocalDate fecha) {
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            //Se carga el objeto de hueco para confirmar que exista
            Hueco existente = hueDAO.buscarPorFecha(conn, Date.valueOf(fecha));
            //En caso de no existir se lanza la insercion
            if (existente == null) {
                hueDAO.insertar(conn, Date.valueOf(fecha));
                conn.commit();
                //En caso contrario se realiza el borrado pero antes pidiendo confirmacion
            } else {
                int ok = JOptionPane.showConfirmDialog(null,
                        idioma.t("hueco.borrarde") + " " + fecha + "?",
                        idioma.t("hueco.baja"), JOptionPane.YES_NO_OPTION);
                if (ok != JOptionPane.YES_OPTION) {
                    return;
                }
                hueDAO.borrar(conn, existente.getIdHueco());
                conn.commit();
            }
            //Se recarga la vista del calendario
            vista.mostrarHuecos(hueDAO.listar(conn));
        } catch (SQLException ex) {
            ex.printStackTrace(); // o System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Error al borrar: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    ex.getClass().getSimpleName() + ": " + ex.getMessage());
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    public static void cargarSesionesPendientes(vistaSolicitudes vista) {
        //Se carga el objeto de lo que se representa en la tabla 
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Id", "Fecha", "Mail Cliente", "Tipo", "Lugar"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            List<SolicitudPendiente> lista = sesDAO.listarPendientes(conn);
            for (SolicitudPendiente p : lista) {
                modelo.addRow(new Object[]{p.idSesion, p.fecha, p.email, p.tipo, p.lugar});
            }
            vista.getTxtPendientes().setText(lista.size() + idioma.t("solicitud.pendientes"));
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
        vista.getjTable1().setModel(modelo);
        vista.getjTable1().getColumnModel().getColumn(0).setMinWidth(0);
        vista.getjTable1().getColumnModel().getColumn(0).setMaxWidth(0);
    }

    public static void cargarDetalleSesion(vistaSolicitudes vista) {
        //Se carga la informacion de el registro seleccionado en la tabla
        int fila = vista.getjTable1().getSelectedRow();
        if (fila == -1) {
            return;
        }
        int id = (int) vista.getjTable1().getValueAt(fila, 0);
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            //Se carga la informacion de los detalles de la sesion
            Detalle d = sesDAO.detalle(conn, id);
            if (d == null) {
                return;
            }

            //Se cargan los datos obtenidos del DAO
            vista.getLblCliente().setText(d.cliente);
            vista.getLblHueco().setText(d.fecha);
            vista.getLblNota().setText(d.nota == null || d.nota.isEmpty() ? idioma.t("solicitud.nonota") : d.nota);
            vista.getLblTelefono().setText(d.telefono.isEmpty() ? idioma.t("solicitud.sintelefono") : d.telefono);
            vista.getLblTipoSesion().setText(
                    (d.tipo == null ? "" : d.tipo) + " - "
                    + (d.lugar == null || d.lugar.isEmpty() ? idioma.t("solicitud.sinlugar") : d.lugar)
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("solicitud.nodetalle"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Mismo metodo para aceptar o decision peticiones, varia el estado recibido de ella en formato String
    public static void resolverPeticion(vistaSolicitudes vista, String decision) {
        //Se obtiene el valor seleccionado de la tabla de sesiones pendientes
        int fila = vista.getjTable1().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, idioma.t("solicitud.peticion"));
            return;
        }
        //Se gestiona el tipo de accion que se quiere realizar y se lanza
        int id = (int) vista.getjTable1().getValueAt(fila, 0);
        String texto = "confirmada".equals(decision) ? "Confirmar esta sesion?" : idioma.t("solicitud.rechazarsesion");
        int ok = JOptionPane.showConfirmDialog(null, texto, "Sesion", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }
        Connection conn = null;
        try {
            //Se conecta con el DAO para guardar el estado
            conn = mySQLFactory.getConnection();
            sesDAO.resolver(conn, id, decision);
            conn.commit();
            //Se restablecen los valores de la tabla y los labels
            cargarSesionesPendientes(vista);
            vista.getLblCliente().setText("-");
            vista.getLblHueco().setText("-");
            vista.getLblNota().setText("-");
            vista.getLblTelefono().setText("-");
            vista.getLblTipoSesion().setText("-");
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "No se pudo resolver la sesion");
            JOptionPane.showMessageDialog(
                    null,
                    idioma.t("solicitud.noresolver") + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo que carga el combo con las fechas de las sesiones existentes
    public static void cargaCombo(vistaSesion vista) {
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();

            //Al cargar el combo se cambiara tambien el estado de las sesiones que ya hayan pasado como realizada (se entiendo que al pasar el dia sin ningun error se ha realizado la sesion)
            sesDAO.marcarRealizadas(conn);
            List<FechaSesion> lista = sesDAO.listarFechas(conn);
            conn.commit();
            System.out.println("fechas: " + lista.size());
            DefaultComboBoxModel<FechaSesion> modelo = new DefaultComboBoxModel<>();
            for (FechaSesion f : lista) {
                modelo.addElement(f);
            }
            vista.getCmbFechaSesion().setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Se cargan la informacion de la vistaSesion
    public static void cargarFicha(vistaSesion vista) {

        Object item = vista.getCmbFechaSesion().getSelectedItem();
        if (!(item instanceof FechaSesion)) {
            return;
        }
        int id = ((FechaSesion) item).idSesion;
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            Sesion s = sesDAO.cargar(conn, id);
            if (s == null) {
                return;
            }
            Usuario u = usuDAO.cargarPorId(conn, s.getIdUsuario());
            conn.commit();

            //Carga de datos
            String nombre = u == null ? "" : u.getNombre();
            vista.getLblNombreTipo().setText(nombre + "  ·  " + (s.getTipo() == null ? "" : s.getTipo()));

            //El estado tambien repinta el txtField que representa en funcion de su valor
            vista.getTxtEstadoSesion().setText(s.getEstado());
            vista.getTxtEstadoSesion().setText(s.getEstado());
            vista.pintarEstado(s.getEstado());

            vista.getTxtLugar().setText(s.getLugar() == null ? "" : s.getLugar());
            vista.getTxtNotaSesion().setText(s.getNota() == null ? "" : s.getNota());
            vista.getTxtFotosTotales().setText(String.valueOf(s.getTotalFotos()));
            vista.getTxtFotosEditadas().setText(String.valueOf(s.getFotosEditadas()));
            vista.getTxtPrecio().setText(String.valueOf(s.getPrecioEstimado()));
            //Calculos para pocentaje de fotos editadas, y saber cuantas quedan por hacer
            vista.getLblContadorFotos().setText((s.getTotalFotos() - s.getFotosEditadas()) + " fotos por editar");
            vista.getLblPorcentaje().setText((s.getTotalFotos() > 0 ? Math.round((double) s.getFotosEditadas() / s.getTotalFotos() * 100) : 0) + "%");

            //Seleccion del tipo de entrega y desmarcar en caso de nulo
            String tipoEntrega = s.getTipoEntrega();
            vista.getButGroupTipoEntrega().clearSelection();
            if (tipoEntrega != null) {
                vista.getRdLink().setSelected("link_descarga".equals(tipoEntrega));
                vista.getRdUSB().setSelected("USB".equals(tipoEntrega));
                vista.getRdAlbum().setSelected("album".equals(tipoEntrega));
                vista.getRdCaja().setSelected("caja_personalizada".equals(tipoEntrega));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("sesion.noficha"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo que marca la sesion cargada como entregada
    public static void entregarSesion(vistaSesion vista) {
        //Se carga el objeto del combo que sera la sesion seleccionada
        Object item = vista.getCmbFechaSesion().getSelectedItem();
        int id = ((FechaSesion) item).idSesion;

        //Mensaje de confirmacion antes de entregar la sesion
        if (JOptionPane.showConfirmDialog(null, "Marcar esta sesion como entregada?",
                "Entrega", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            sesDAO.entregar(conn, id);
            conn.commit();

            //Se vuelven a cargar los datos para visualizar la sesion como entregada
            cargarFicha(vista);
            JOptionPane.showMessageDialog(null, "Sesion entregada");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("sesion.noentregar"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo que comunica con el DAO para modificar datos de la sesion
    public static void modificarDatosSesion(vistaSesion vista) {
        Object item = vista.getCmbFechaSesion().getSelectedItem();

        int id = ((FechaSesion) item).idSesion;

        //Se cargan los datos de las casillas de la vista para comprobar nuevos datos, comprobando previamente si estos son validos
        String lugar = vacio(vista.getTxtLugar().getText());
        String nota = vacio(vista.getTxtNotaSesion().getText());
        String tipoEntrega = entregaSeleccionada(vista);
        int total = entero(vista.getTxtFotosTotales().getText());
        //El fotografo usara mayormente esta funcion para actualizar el total de fotos que va editando
        int editadas = entero(vista.getTxtFotosEditadas().getText());
        Double precio = decimal(vista.getTxtPrecio().getText());
        if (total < 0 || editadas < 0 || (precio != null && precio < 0)) {
            JOptionPane.showMessageDialog(null, idioma.t("sesion.fotosyprecio"));
            return;
        }

        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            Sesion s = sesDAO.cargar(conn, id);
            if (s == null) {
                return;
            }
            //Se comprueba que hay cambios antes de moficiar, en caso contrario no se recorre el resto del metodo
            boolean igual = mismo(lugar, s.getLugar())
                    && mismo(nota, s.getNota())
                    && mismo(tipoEntrega, s.getTipoEntrega())
                    && total == s.getTotalFotos()
                    && editadas == s.getFotosEditadas()
                    && mismoPrecio(precio, s.getPrecioEstimado());
            if (igual) {
                JOptionPane.showMessageDialog(null, idioma.t("sesion.nocambios"));
                return;
            }

            //Cambio de estado de la sesion en funcion de las fotos editadas que haya
            String estado = s.getEstado();
            if (!"entregada".equals(estado)
                    && !"rechazada".equals(estado)
                    && !"cancelada".equals(estado)) {
                if (total > 0 && editadas >= total) {
                    estado = "lista_entrega";
                } else if (s.getFotosEditadas() == 0 && editadas > 0) {
                    estado = "en_edicion";
                }
            }

            System.out.println("estado: [" + estado + "]");
            System.out.println("tipoEntrega: [" + tipoEntrega + "] precio: " + precio);

            //Se lanza la modificacion en el DAO
            sesDAO.modificarFicha(conn, id, lugar, nota, total, editadas, tipoEntrega, precio, estado);

            conn.commit();
            cargarFicha(vista);
            JOptionPane.showMessageDialog(null, "Sesion modificada");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("sesion.errormodi"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo para la cancelacion de una sesion
    public static void cancelarSesion(vistaSesion vista) {
        //Se carga el objeto del combo que sera la sesion seleccionada
        Object item = vista.getCmbFechaSesion().getSelectedItem();
        int id = ((FechaSesion) item).idSesion;

        //Mensaje de confirmacion antes de entregar la sesion
        if (JOptionPane.showConfirmDialog(null, "Marcar esta sesion como cancelada?",
                "Cancelada", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            sesDAO.cancelar(conn, id);
            conn.commit();

            //Se vuelven a cargar los datos para visualizar la sesion como cancelada
            cargarFicha(vista);
            JOptionPane.showMessageDialog(null, "Sesion cancelada");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("sesion.noentregar"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo que carga para la preview la sesion seleccionada y el cliente que le corresponde
    public static void cargaComboPreview(vistaPreview vista) {
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            sesDAO.marcarRealizadas(conn);
            List<FechaSesion> lista = sesDAO.listarFechas(conn);
            conn.commit();
            DefaultComboBoxModel<FechaSesion> modelo = new DefaultComboBoxModel<>();
            for (FechaSesion f : lista) {
                modelo.addElement(f);
            }
            vista.getCmbSesionCliente().setModel(modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo que carga las previews la sesion seleccionada
    public static void cargaPreviews(vistaPreview vista) {
        Object item = vista.getCmbSesionCliente().getSelectedItem();
        if (!(item instanceof FechaSesion)) {
            return;
        }
        int id = ((FechaSesion) item).idSesion;
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            List<modelo.vo.Preview> lista = prevDAO.listarPorSesion(conn, id);
            conn.commit();
            vista.getPanelLista().removeAll();
            for (Preview p : lista) {
                vista.getPanelLista().add(vista.tarjeta(p));
                //Se aniaden los paneles con las previews
                vista.getPanelLista().add(Box.createVerticalStrut(8));
            }
            vista.getTxtCantidadPreview().setText(lista.size() + " previews");
            vista.getPanelLista().revalidate();
            vista.getPanelLista().repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.nopreviews"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodos para realizar comprobaciones en los campos de entradas de datos
    private static String entregaSeleccionada(vista.vistaSesion ventana) {
        if (ventana.getRdLink().isSelected()) {
            return "link_descarga";
        }
        if (ventana.getRdUSB().isSelected()) {
            return "USB";
        }
        if (ventana.getRdAlbum().isSelected()) {
            return "album";
        }
        if (ventana.getRdCaja().isSelected()) {
            return "caja_personalizada";
        }
        return null;
    }

    //Eliminacion de la preview seleccionada
    public static void eliminarPreview(vistaPreview vista) {
        int id = vista.getPreviewSeleccionada();
        //Se maneja lo que ocurre al no seleccionar preview
        if (id == -1) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.selecpreview"));
            return;
        }
        //Mensaje de confirmacion
        if (JOptionPane.showConfirmDialog(null, "Eliminar esta preview?",
                "Preview", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            prevDAO.borrar(conn, id);
            conn.commit();
            //Se vuelve a cargar la vista desde el principio
            cargaPreviews(vista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.noeliminar"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Modificacion de datos de Preview 
    public static void modificarDatosPreviews(vistaPreview vista) {
        //Es necesario almacenar la preview seleccionada y sus nuevos valores
        int id = vista.getPreviewSeleccionada();
        if (id == -1) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.selecpreview"));
            return;
        }
        Object item = vista.getCmbSesionCliente().getSelectedItem();
        if (!(item instanceof FechaSesion)) {
            return;
        }
        int idSesion = ((FechaSesion) item).idSesion;

        String pie = vista.getTxtPieFoto().getText().trim();
        if (pie.isEmpty()) {
            pie = null;
        }
        //Se guarda el valor de el orden y se reorganizan las previews a las que puede estar afectando
        int orden;
        try {
            orden = Integer.parseInt(vista.getTxtOrden().getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.ordennumero"));
            return;
        }
        if (orden < 1) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.orden1"));
            return;
        }

        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            //Se lanza la reorganizacion en el DAO
            int ordenAnterior = prevDAO.obtenerOrden(conn, id);
            if (orden < ordenAnterior) {
                prevDAO.incrementarOrden(conn, idSesion, orden, ordenAnterior, id);
            } else if (orden > ordenAnterior) {
                prevDAO.decrementarOrden(conn, idSesion, ordenAnterior, orden, id);
            }

            //Se modifican los datos y se carga directamente
            prevDAO.modificar(conn, id, pie, orden);
            conn.commit();

            cargaPreviews(vista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.nomodi"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo para insertar la preview precargada
    public static void insertarPreview(vistaPreview vista) {
        //Se carga el objeto del combo para saber la sesion a la que corresponde
        Object item = vista.getCmbSesionCliente().getSelectedItem();

        //String de la ruta que hemos seleccionado
        String ruta = vista.getRutaNueva();
        if (ruta == null) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.elegirimagen"));
            return;
        }

        //Pie que estamos escribiendo en la ventana que podra ser nulo
        String pie = vista.getTxtPieFoto().getText().trim();
        if (pie.isEmpty()) {
            pie = null;
        }

        int idSesion = ((FechaSesion) item).idSesion;
        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            int orden = prevDAO.siguienteOrden(conn, idSesion);
            prevDAO.insertar(conn, idSesion, ruta, pie, orden);
            conn.commit();
            cargaPreviews(vista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("preview.noaniadir"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }
    }

    //Metodo de carga de tabla de clientes en la vista Informe, aunque o despues de el filtrado
    public static void cargarTablaClientesInforme(vistaInforme vista, String mail, Date desde, Date hasta) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTblSesion().getModel();
        modelo.setRowCount(0);

        //Variables de calculo totales para volcar en labels
        int sesiones = 0;
        int fotos = 0;
        double precio = 0;

        Connection conn = null;
        try {
            conn = mySQLFactory.getConnection();
            //Se vuelca en la tabla los datos obtenidos del DAO
            for (Object[] fila : sesDAO.listarInforme(conn, mail, desde, hasta)) {
                modelo.addRow(fila);

                //Se van calculando los valores para el total
                sesiones++;
                if (fila[4] != null) {
                    fotos += ((Number) fila[4]).intValue();
                }
                if (fila[5] != null) {
                    precio += ((Number) fila[5]).doubleValue();
                }
            }
            conn.commit();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, idioma.t("informe.noinforme"));
        } finally {
            if (conn != null) {
                mySQLFactory.releaseConnection(conn);
            }
        }

        //Se cargan los valores de calculo en los labels inferiores de la vista
        vista.getLblTotalSesion().setText(String.valueOf(sesiones));
        vista.getLblTotalFoto().setText(String.valueOf(fotos));
        vista.getLblTotalPrecio().setText(String.format("%.2f €", precio));
    }

    //Metodo que realiza la descarga del informe pdf con los datos de las sesiones entregadas
    public static void descargaPDF(vistaInforme vista) {
        //Se va recorriendo la tabla de sesiones de la vista
        JTable tabla = vista.getTblSesion();
        if (tabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, idioma.t("informe.nosesioinfo"));
            return;
        }

        //Ventana de selección de ruta para descarga
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("informe_shuttware.pdf"));

        //Se carga la ultima direccion en la que se descargo el informe
        String ultimaCarpeta = Preferencias.cargar("ultima_carpeta_informe", null);
        if (ultimaCarpeta != null) {
            File dir = new File(ultimaCarpeta);
            if (dir.exists() && dir.isDirectory()) {
                chooser.setCurrentDirectory(dir);
            }
        }

        //Se ofrece la posibilidad de cancelar el guardado
        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File destino = chooser.getSelectedFile();
        //Se aniade la extension pdf al archivo descargado
        if (!destino.getName().toLowerCase().endsWith(".pdf")) {
            destino = new java.io.File(destino.getAbsolutePath() + ".pdf");
        }

        //Se guarda la direccion de descarga para la proxima vez que se descargue
        Preferencias.guardar("ultima_carpeta_informe", destino.getParent());

        //Creacion y guardado del pdf
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(destino));
            doc.open();

            //Se incluyen los datos de titulo y de si se han aplicado filtros a la busqueda de sesiones entregadas
            doc.add(new com.itextpdf.text.Paragraph("Shuttware  -  Sesiones entregadas"));

            String mail = vista.getTxtMail().getText().trim();
            String desde = vista.getTxtDesde().getText().trim();
            String hasta = vista.getTxtHasta().getText().trim();
            String filtro = "";
            if (!mail.isEmpty()) {
                filtro += "Mail: " + mail + "    ";
            }
            if (!desde.isEmpty()) {
                filtro += "Desde: " + desde + "    ";
            }
            if (!hasta.isEmpty()) {
                filtro += "Hasta: " + hasta;
            }
            if (!filtro.isEmpty()) {
                doc.add(new com.itextpdf.text.Paragraph(filtro));
            }
            doc.add(new com.itextpdf.text.Paragraph(" "));

            //Se crea la misma tabla que en la vista
            PdfPTable t = new PdfPTable(tabla.getColumnCount());
            t.setWidthPercentage(100);
            //Primero se aniaden todos los nombres de las columnas
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                t.addCell(tabla.getColumnName(c));
            }
            //Para continuacion aniadir los datos en las filas 
            for (int f = 0; f < tabla.getRowCount(); f++) {
                for (int c = 0; c < tabla.getColumnCount(); c++) {
                    Object v = tabla.getValueAt(f, c);
                    t.addCell(v == null ? "" : v.toString());
                }
            }
            //Toca aniadir la tabla al documento
            doc.add(t);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(
                    "Sesiones: " + vista.getLblTotalSesion().getText()
                    + "    Fotos: " + vista.getLblTotalFoto().getText()
                    + "    Total: " + vista.getLblTotalPrecio().getText()));
            doc.close();
            JOptionPane.showMessageDialog(null, "Informe guardado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo crear el PDF");
        }
    }

    private static boolean mismo(String a, String b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    private static String vacio(String t) {
        if (t == null || t.trim().isEmpty()) {
            return null;
        }
        return t.trim();
    }

    private static int entero(String t) {
        if (t == null || t.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(t.trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static Double decimal(String t) {
        if (t == null || t.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.valueOf(t.trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            return -1d;
        }
    }

    private static boolean mismoPrecio(Double a, double b) {
        if (a == null) {
            return b == 0;
        }
        return Double.compare(a, b) == 0;
    }

}

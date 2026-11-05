/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.controladorPrincipal;
import controlador.idioma;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;

/**
 *
 * @author AitorMF
 */
public class vistaCliente extends javax.swing.JFrame {

    //Habra dos opciones de bordes dependiendo de si esta seleccionado
    private static final java.awt.Color BORDE_NORMAL = new java.awt.Color(229, 229, 229);
    private static final java.awt.Color BORDE_ACTIVO = new java.awt.Color(153, 204, 255);

    /**
     * Creates new form vistaCliente
     */
    public vistaCliente() {
        initComponents();

        //Mejora de aspecto de la interfaz
        panelContenido.setOpaque(false);   
        rdClient.setOpaque(false);         
        rdFoto.setOpaque(false);

        //Transparencia para los paneles intermedios
        for (Container c = panelContenido.getParent();
                c != null && !(c instanceof PanelImagen) && !(c instanceof JRootPane);
                c = c.getParent()) {
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
            }
        }

        //Lanzamiento de metodos de mejora de aspecto
        estilizarCampos();
        estilizarTabla();
        
        //Metodo de busqueda por mail
        activarBusquedaMail();

        //Carga de datos seleccionando registro en la tabla
        tblClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblClientes.getSelectedRow() != -1) {
                controladorPrincipal.cargarClienteSeleccionado(this);
            }
        });

        //Cada panel se envuelve en una tarjeta translucida, asi no depende del diseñador
        panelRegistro.setOpaque(false);
        panelGestionDatos.setOpaque(false);

        JPanel tarjetaRegistro = new PanelTransparente();
        tarjetaRegistro.setLayout(new java.awt.BorderLayout());
        tarjetaRegistro.add(panelRegistro, java.awt.BorderLayout.CENTER);

        JPanel tarjetaGestion = new PanelTransparente();
        tarjetaGestion.setLayout(new java.awt.BorderLayout());
        tarjetaGestion.add(panelGestionDatos, java.awt.BorderLayout.CENTER);

        //Se usa Card Layout para elegir entre un panel u el otro CardLayout
        panelContenido.setLayout(new CardLayout());
        panelContenido.add(tarjetaRegistro, "registro");
        panelContenido.add(tarjetaGestion, "modificar");
        CardLayout cardLayout = (CardLayout) panelContenido.getLayout();
        cardLayout.show(panelContenido, "registro");

        //Boton de panel registro empieza como seleccionado
        btnSelecRegistro.setBackground(BORDE_ACTIVO);
        btnSelecModiBor.setBackground(BORDE_NORMAL);

        setTitle("Cliente");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private Border bordeCampo(java.awt.Color color) {
        return BorderFactory.createCompoundBorder(
                new LineBorder(color, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10));
    }

    //Borde gris normal y borde azul cuando esta seleccionado
    private void estilizarCampos() {
        for (JTextField c : new JTextField[]{txtNombre, txtApellidos, txtEmail, txtTelf, txtInsta}) {
            c.setBorder(bordeCampo(BORDE_NORMAL));
            c.addFocusListener(new FocusAdapter() {
                //Borde seleccionado
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    c.setBorder(bordeCampo(BORDE_ACTIVO));
                }

                //Borde no seleccionado
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    c.setBorder(bordeCampo(BORDE_NORMAL));
                }
            });
        }

        //La nota: el foco lo recibe el JTextArea, pero el borde está en el scroll
        jScrollPane1.setBorder(new LineBorder(BORDE_NORMAL, 1, true));
        txtNota.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                jScrollPane1.setBorder(new LineBorder(BORDE_ACTIVO, 1, true));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                jScrollPane1.setBorder(new LineBorder(BORDE_NORMAL, 1, true));
            }
        });
    }

    private void estilizarTabla() {
        tblClientes.setShowGrid(false);
        tblClientes.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblClientes.setRowHeight(32);
        tblClientes.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 13));
        tblClientes.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tblClientes.setSelectionForeground(java.awt.Color.WHITE);
        tblClientes.setFillsViewportHeight(true);

        JTableHeader cabecera = tblClientes.getTableHeader();
        cabecera.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12));
        cabecera.setBackground(new java.awt.Color(245, 245, 245));
        cabecera.setForeground(new java.awt.Color(80, 80, 80));
        cabecera.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, new java.awt.Color(229, 229, 229)));
    }

    //Al ir escribiendo en el field aparecera los registros correspondientes en la tabla
    private void activarBusquedaMail() {
        txtBusquedaMail.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controladorPrincipal.filtrarClientesPorEmail(vistaCliente.this);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controladorPrincipal.filtrarClientesPorEmail(vistaCliente.this);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                controladorPrincipal.filtrarClientesPorEmail(vistaCliente.this);
            }
        });
    }

    //Metodo que aplica el idioma escogido
    void aplicarTextos() {
        lblTituloCliente1.setText(idioma.t("cliente.gestion"));
        jLabel8.setText(idioma.t("cliente.emailcliente"));
        btnSelecRegistro.setText(idioma.t("cliente.registro"));
        lblInsertar.setText(idioma.t("cliente.registrotexto"));
        lblModificarBorrado.setText(idioma.t("cliente.modificaciontexto"));
        lblNombre.setText(idioma.t("cliente.nombre"));
        lblApellidos.setText(idioma.t("cliente.apellidos"));
        btnBaja.setText(idioma.t("cliente.baja"));
        btnGuardar.setText(idioma.t("cliente.guardar"));
        lblNombre1.setText(idioma.t("cliente.nombre"));
        lblApellidos1.setText(idioma.t("cliente.apellidos"));
        lblTituloCliente.setText(idioma.t("cliente.nuevocliente"));
    }

    //Carga de la imagen para panel de fondo
    private static class PanelImagen extends JPanel {

        private java.awt.Image imagen;
        //variable para aplicar zoom a panel y label
        private double zoom = 1.0;

        public PanelImagen(String ruta) {
            java.net.URL url = vistaMenuPrincipal.class.getResource(ruta);
            if (url != null) {
                imagen = new ImageIcon(url).getImage();
            } else {
                System.err.println("No se encuentra la imagen: " + ruta);
            }
        }

        public void setZoom(double zoom) {
            this.zoom = zoom;
            repaint();
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                        java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int anchoImg = imagen.getWidth(this);
                int altoImg = imagen.getHeight(this);

                //Escala la imagen sin deformarla 
                double escala = Math.max((double) getWidth() / anchoImg,
                        (double) getHeight() / altoImg) * zoom;
                int w = (int) (anchoImg * escala);
                int h = (int) (altoImg * escala);

                //Centra la imagen
                int x = (getWidth() - w) / 2;
                int y = (getHeight() - h) / 2;

                g2.drawImage(imagen, x, y, w, h, this);
                g2.dispose();
            }
        }
    }   

    // Hacer los paneles con algo de transparecia y mas esteticos
    private static class PanelTransparente extends JPanel {

        private final int alfa;
        private final int radio;

        public PanelTransparente() {
            this(225, 28);
        }

        public PanelTransparente(int alfa, int radio) {
            this.alfa = alfa;
            this.radio = radio;
            setOpaque(false);  
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new java.awt.Color(255, 255, 255, alfa));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
            g2.dispose();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupTipo = new javax.swing.ButtonGroup();
        btnGroupModiTipo = new javax.swing.ButtonGroup();
        fondoCliente = new PanelImagen("/imagenes/calle.jpg");
        lblNuevoCliente = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();
        panelGestionDatos = new javax.swing.JPanel();
        lblTituloCliente1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtBusquedaMail = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        lblApellidos = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtModiNombre = new javax.swing.JTextField();
        txtModiApel = new javax.swing.JTextField();
        txtModiMail = new javax.swing.JTextField();
        txtModiTelf = new javax.swing.JTextField();
        txtModiInsta = new javax.swing.JTextField();
        rbModiCliente = new javax.swing.JRadioButton();
        rdModiFotografo = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtModiNota = new javax.swing.JTextArea();
        btnModificar = new javax.swing.JButton();
        btnBaja = new javax.swing.JButton();
        panelRegistro = new javax.swing.JPanel();
        lblTituloCliente = new javax.swing.JLabel();
        lblNombre1 = new javax.swing.JLabel();
        lblApellidos1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtTelf = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtInsta = new javax.swing.JTextField();
        rdClient = new javax.swing.JRadioButton();
        rdFoto = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNota = new javax.swing.JTextArea();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        panelBtnRegistro = new javax.swing.JPanel();
        lblInsertar = new javax.swing.JLabel();
        btnSelecRegistro = new javax.swing.JButton();
        panelBtnModiBor = new javax.swing.JPanel();
        lblModificarBorrado = new javax.swing.JLabel();
        btnSelecModiBor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 650));
        setResizable(false);

        fondoCliente.setPreferredSize(new java.awt.Dimension(1000, 650));

        lblNuevoCliente.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 36)); // NOI18N
        lblNuevoCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblNuevoCliente.setText("C l i e n t e");

        lblTituloCliente1.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 24)); // NOI18N
        lblTituloCliente1.setText("G e s t i ó n  c l i e n t e s");

        jLabel8.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel8.setText("Email de cliente");

        txtBusquedaMail.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        lblNombre.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblNombre.setText("Nombre");

        tblClientes.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Email", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        lblApellidos.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblApellidos.setText("Apellidos");

        jLabel11.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel11.setText("Email");

        jLabel12.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel12.setText("Teléfono");

        jLabel13.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel13.setText("Instagram");

        jLabel14.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel14.setText("Rol");

        jLabel15.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel15.setText("Nota");

        txtModiNombre.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        txtModiApel.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        txtModiMail.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        txtModiTelf.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        txtModiInsta.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        btnGroupModiTipo.add(rbModiCliente);
        rbModiCliente.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rbModiCliente.setText("Cliente");

        btnGroupModiTipo.add(rdModiFotografo);
        rdModiFotografo.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rdModiFotografo.setText("Fotografo");

        txtModiNota.setColumns(20);
        txtModiNota.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtModiNota.setRows(5);
        jScrollPane3.setViewportView(txtModiNota);

        btnModificar.setBackground(new java.awt.Color(229, 229, 229));
        btnModificar.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnBaja.setBackground(new java.awt.Color(153, 0, 51));
        btnBaja.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        btnBaja.setForeground(new java.awt.Color(255, 255, 255));
        btnBaja.setText("Baja Cliente");
        btnBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGestionDatosLayout = new javax.swing.GroupLayout(panelGestionDatos);
        panelGestionDatos.setLayout(panelGestionDatosLayout);
        panelGestionDatosLayout.setHorizontalGroup(
            panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombre)
                .addContainerGap(547, Short.MAX_VALUE))
            .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelGestionDatosLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTituloCliente1)
                        .addGroup(panelGestionDatosLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBusquedaMail, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGestionDatosLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(42, 42, 42)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGestionDatosLayout.createSequentialGroup()
                                .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGestionDatosLayout.createSequentialGroup()
                                        .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblApellidos)
                                            .addComponent(jLabel11))
                                        .addGap(14, 14, 14)
                                        .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtModiApel, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtModiMail, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                            .addComponent(txtModiNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGestionDatosLayout.createSequentialGroup()
                                        .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelGestionDatosLayout.createSequentialGroup()
                                                .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel12)
                                                    .addComponent(jLabel13))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGestionDatosLayout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(51, 51, 51)))
                                        .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelGestionDatosLayout.createSequentialGroup()
                                                .addComponent(rbModiCliente)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdModiFotografo))
                                            .addComponent(txtModiInsta)
                                            .addComponent(txtModiTelf, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(143, 143, 143)
                                .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelGestionDatosLayout.setVerticalGroup(
            panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionDatosLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(lblNombre)
                .addContainerGap(254, Short.MAX_VALUE))
            .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelGestionDatosLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblTituloCliente1)
                    .addGap(18, 18, 18)
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtBusquedaMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(txtModiNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblApellidos)
                        .addComponent(txtModiApel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtModiMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnModificar))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtModiTelf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBaja))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtModiInsta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(rbModiCliente)
                        .addComponent(rdModiFotografo))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(panelGestionDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        lblTituloCliente.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 24)); // NOI18N
        lblTituloCliente.setText("N u e v o   C l i e n t e");

        lblNombre1.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblNombre1.setText("Nombre");

        lblApellidos1.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblApellidos1.setText("Apellidos");

        txtNombre.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        txtApellidos.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel3.setText("Email");

        jLabel4.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel4.setText("Teléfono");

        txtEmail.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        txtTelf.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel5.setText("Instagram");

        jLabel6.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel6.setText("Rol");

        txtInsta.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        btnGroupTipo.add(rdClient);
        rdClient.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rdClient.setText("Cliente");

        btnGroupTipo.add(rdFoto);
        rdFoto.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rdFoto.setText("Fotógrafo");

        jLabel7.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel7.setText("Nota Interna");

        txtNota.setColumns(20);
        txtNota.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtNota.setRows(5);
        jScrollPane1.setViewportView(txtNota);

        btnCancelar.setBackground(new java.awt.Color(225, 225, 225));
        btnCancelar.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(153, 204, 255));
        btnGuardar.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar Cliente");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRegistroLayout = new javax.swing.GroupLayout(panelRegistro);
        panelRegistro.setLayout(panelRegistroLayout);
        panelRegistroLayout.setHorizontalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRegistroLayout.createSequentialGroup()
                                        .addGap(292, 292, 292)
                                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtTelf, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)
                                            .addGroup(panelRegistroLayout.createSequentialGroup()
                                                .addComponent(rdClient)
                                                .addGap(53, 53, 53)
                                                .addComponent(rdFoto))))
                                    .addComponent(lblTituloCliente)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGroup(panelRegistroLayout.createSequentialGroup()
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnGuardar))
                                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRegistroLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(292, 292, 292))
                                    .addComponent(jScrollPane1))))
                        .addContainerGap(48, Short.MAX_VALUE))
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRegistroLayout.createSequentialGroup()
                                .addComponent(lblNombre1)
                                .addGap(243, 243, 243)
                                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblApellidos1)))
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInsta, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelRegistroLayout.setVerticalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblTituloCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre1)
                    .addComponent(lblApellidos1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInsta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdClient)
                    .addComponent(rdFoto))
                .addGap(57, 57, 57)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout panelContenidoLayout = new javax.swing.GroupLayout(panelContenido);
        panelContenido.setLayout(panelContenidoLayout);
        panelContenidoLayout.setHorizontalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(panelGestionDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(443, Short.MAX_VALUE))
            .addGroup(panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelContenidoLayout.createSequentialGroup()
                    .addGap(257, 257, 257)
                    .addComponent(panelRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(257, Short.MAX_VALUE)))
        );
        panelContenidoLayout.setVerticalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(panelGestionDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(235, Short.MAX_VALUE))
            .addGroup(panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelContenidoLayout.createSequentialGroup()
                    .addGap(202, 202, 202)
                    .addComponent(panelRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(203, Short.MAX_VALUE)))
        );

        panelBtnRegistro.setOpaque(false);

        lblInsertar.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        lblInsertar.setForeground(new java.awt.Color(255, 255, 255));
        lblInsertar.setText("<html>Registra los datos de<br>contacto para poder<br>almacenar datos de<br>tus clientes</html>");

        btnSelecRegistro.setBackground(new java.awt.Color(229, 229, 229));
        btnSelecRegistro.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        btnSelecRegistro.setText("Registro");
        btnSelecRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecRegistroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBtnRegistroLayout = new javax.swing.GroupLayout(panelBtnRegistro);
        panelBtnRegistro.setLayout(panelBtnRegistroLayout);
        panelBtnRegistroLayout.setHorizontalGroup(
            panelBtnRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBtnRegistroLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBtnRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelecRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBtnRegistroLayout.setVerticalGroup(
            panelBtnRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBtnRegistroLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(btnSelecRegistro)
                .addGap(18, 18, 18)
                .addComponent(lblInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        panelBtnModiBor.setOpaque(false);

        lblModificarBorrado.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        lblModificarBorrado.setForeground(new java.awt.Color(255, 255, 255));
        lblModificarBorrado.setText("<html>Accede a la lista de<br>clientes para modificar<br>registros o dar de baja</html>");

        btnSelecModiBor.setBackground(new java.awt.Color(229, 229, 229));
        btnSelecModiBor.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        btnSelecModiBor.setText("Modificacion / Borrado");
        btnSelecModiBor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecModiBorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBtnModiBorLayout = new javax.swing.GroupLayout(panelBtnModiBor);
        panelBtnModiBor.setLayout(panelBtnModiBorLayout);
        panelBtnModiBorLayout.setHorizontalGroup(
            panelBtnModiBorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBtnModiBorLayout.createSequentialGroup()
                .addGroup(panelBtnModiBorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBtnModiBorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblModificarBorrado, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBtnModiBorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSelecModiBor, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBtnModiBorLayout.setVerticalGroup(
            panelBtnModiBorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBtnModiBorLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(btnSelecModiBor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblModificarBorrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout fondoClienteLayout = new javax.swing.GroupLayout(fondoCliente);
        fondoCliente.setLayout(fondoClienteLayout);
        fondoClienteLayout.setHorizontalGroup(
            fondoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoClienteLayout.createSequentialGroup()
                .addGroup(fondoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoClienteLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(fondoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelBtnModiBor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBtnRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(fondoClienteLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55)
                .addComponent(panelContenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fondoClienteLayout.setVerticalGroup(
            fondoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoClienteLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(fondoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelContenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fondoClienteLayout.createSequentialGroup()
                        .addComponent(lblNuevoCliente)
                        .addGap(118, 118, 118)
                        .addComponent(panelBtnRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(panelBtnModiBor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fondoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Se seleccionado el panel de registro
    private void btnSelecRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecRegistroActionPerformed
        CardLayout cardLayout = (CardLayout) panelContenido.getLayout();
        cardLayout.show(panelContenido, "registro");

        btnSelecRegistro.setBackground(BORDE_ACTIVO);
        btnSelecModiBor.setBackground(BORDE_NORMAL);
    }//GEN-LAST:event_btnSelecRegistroActionPerformed

    //Se seleccioa el panel de modificado y baja
    private void btnSelecModiBorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecModiBorActionPerformed
        CardLayout cardLayout = (CardLayout) panelContenido.getLayout();
        cardLayout.show(panelContenido, "modificar");

        btnSelecRegistro.setBackground(BORDE_NORMAL);
        btnSelecModiBor.setBackground(BORDE_ACTIVO);
        
        //Al seleccionar este panel se carga la lista directamente
        controladorPrincipal.cargarTablaClientes(this);
        
    }//GEN-LAST:event_btnSelecModiBorActionPerformed

    //Pulsar boton de baja cliente
    private void btnBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaActionPerformed
        controladorPrincipal.bajaCliente(this);
        //Se recarga la tabla desde la vista para generar una nueva conexion
        controladorPrincipal.cargarTablaClientes(this);
    }//GEN-LAST:event_btnBajaActionPerformed

    //Pulsar boton cancelar que borra los datos escritos a la hora de insertar cliente
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        getTxtNombre().setText("");
        getTxtApellidos().setText("");
        getTxtTelf().setText("");
        getTxtInsta().setText("");
        getTxtEmail().setText("");
        getTxtNota().setText("");
        btnGroupTipo.clearSelection();
    }//GEN-LAST:event_btnCancelarActionPerformed

    //Pulsar boton de realizar registro
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        controladorPrincipal.insertarRegistro(this);
    }//GEN-LAST:event_btnGuardarActionPerformed

    //Pulsar el boton de modificar registro
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        controladorPrincipal.modificarCiente(this);
        //Se recarga la tabla desde la vista para generar una nueva conexion
        controladorPrincipal.cargarTablaClientes(this);
    }//GEN-LAST:event_btnModificarActionPerformed

    public JTable getTblClientes() {
        return tblClientes;
    }

    public void setTblClientes(JTable tblClientes) {
        this.tblClientes = tblClientes;
    }

    public JRadioButton getRbModiCliente() {
        return rbModiCliente;
    }

    public void setRbModiCliente(JRadioButton rbModiCliente) {
        this.rbModiCliente = rbModiCliente;
    }

    public JRadioButton getRdModiFotografo() {
        return rdModiFotografo;
    }

    public void setRdModiFotografo(JRadioButton rdModiFotografo) {
        this.rdModiFotografo = rdModiFotografo;
    }

    public JTextField getTxtModiApel() {
        return txtModiApel;
    }

    public void setTxtModiApel(JTextField txtModiApel) {
        this.txtModiApel = txtModiApel;
    }

    public JTextField getTxtModiInsta() {
        return txtModiInsta;
    }

    public void setTxtModiInsta(JTextField txtModiInsta) {
        this.txtModiInsta = txtModiInsta;
    }

    public JTextField getTxtModiMail() {
        return txtModiMail;
    }

    public void setTxtModiMail(JTextField txtModiMail) {
        this.txtModiMail = txtModiMail;
    }

    public JTextField getTxtModiNombre() {
        return txtModiNombre;
    }

    public void setTxtModiNombre(JTextField txtModiNombre) {
        this.txtModiNombre = txtModiNombre;
    }

    public JTextArea getTxtModiNota() {
        return txtModiNota;
    }

    public void setTxtModiNota(JTextArea txtModiNota) {
        this.txtModiNota = txtModiNota;
    }

    public JTextField getTxtModiTelf() {
        return txtModiTelf;
    }

    public void setTxtModiTelf(JTextField txtModiTelf) {
        this.txtModiTelf = txtModiTelf;
    }

    public JTextField getTxtBusquedaMail() {
        return txtBusquedaMail;
    }

    public void setTxtBusquedaMail(JTextField txtBusquedaMail) {
        this.txtBusquedaMail = txtBusquedaMail;
    }

    public JRadioButton getRdClient() {
        return rdClient;
    }

    public void setRdClient(JRadioButton rdClient) {
        this.rdClient = rdClient;
    }

    public JRadioButton getRdFoto() {
        return rdFoto;
    }

    public void setRdFoto(JRadioButton rdFoto) {
        this.rdFoto = rdFoto;
    }

    public JTextField getTxtApellidos() {
        return txtApellidos;
    }

    public void setTxtApellidos(JTextField txtApellidos) {
        this.txtApellidos = txtApellidos;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JTextField getTxtInsta() {
        return txtInsta;
    }

    public void setTxtInsta(JTextField txtInsta) {
        this.txtInsta = txtInsta;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextArea getTxtNota() {
        return txtNota;
    }

    public void setTxtNota(JTextArea txtNota) {
        this.txtNota = txtNota;
    }

    public JTextField getTxtTelf() {
        return txtTelf;
    }

    public void setTxtTelf(JTextField txtTelf) {
        this.txtTelf = txtTelf;
    }

    public ButtonGroup getBtnGroupTipo() {
        return btnGroupTipo;
    }
    
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaja;
    private javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup btnGroupModiTipo;
    private javax.swing.ButtonGroup btnGroupTipo;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSelecModiBor;
    private javax.swing.JButton btnSelecRegistro;
    private javax.swing.JPanel fondoCliente;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblApellidos1;
    private javax.swing.JLabel lblInsertar;
    private javax.swing.JLabel lblModificarBorrado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblNuevoCliente;
    private javax.swing.JLabel lblTituloCliente;
    private javax.swing.JLabel lblTituloCliente1;
    private javax.swing.JPanel panelBtnModiBor;
    private javax.swing.JPanel panelBtnRegistro;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelGestionDatos;
    private javax.swing.JPanel panelRegistro;
    private javax.swing.JRadioButton rbModiCliente;
    private javax.swing.JRadioButton rdClient;
    private javax.swing.JRadioButton rdFoto;
    private javax.swing.JRadioButton rdModiFotografo;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBusquedaMail;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtInsta;
    private javax.swing.JTextField txtModiApel;
    private javax.swing.JTextField txtModiInsta;
    private javax.swing.JTextField txtModiMail;
    private javax.swing.JTextField txtModiNombre;
    private javax.swing.JTextArea txtModiNota;
    private javax.swing.JTextField txtModiTelf;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtNota;
    private javax.swing.JTextField txtTelf;
    // End of variables declaration//GEN-END:variables
}

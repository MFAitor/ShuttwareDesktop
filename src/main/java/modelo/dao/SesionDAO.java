/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.vo.Detalle;
import modelo.vo.FechaSesion;
import modelo.vo.Sesion;
import modelo.vo.SolicitudPendiente;

/**
 *
 * @author AitorMF
 */
public class SesionDAO {

    //Metodo que lista las solicitudes pendientes
    public List<SolicitudPendiente> listarPendientes(Connection conn) throws SQLException {
        List<SolicitudPendiente> lista = new ArrayList<>();
        //Consulta para solicitudes pendientes
        String consulta = "SELECT s.id_sesion, h.fecha, u.email, s.tipo, s.lugar "
                + "FROM sesion s "
                + "JOIN hueco h ON s.id_hueco = h.id_hueco "
                + "JOIN usuario u ON s.id_usuario = u.id_usuario "
                + "WHERE s.estado = 'pendiente' "
                + "ORDER BY h.fecha";
        PreparedStatement stat = conn.prepareStatement(consulta);
        ResultSet rs = stat.executeQuery();

        //Se recorren los resultados de la consulta para guardar las peticiones
        while (rs.next()) {
            lista.add(new SolicitudPendiente(
                    rs.getInt("id_sesion"),
                    rs.getString("fecha"),
                    rs.getString("email"),
                    rs.getString("tipo"),
                    rs.getString("lugar")
            ));
        }
        return lista;
    }

    //Obtener detalles de las sesiones para mostrar su informacion
    public Detalle detalle(Connection conn, int idSesion) throws SQLException {
        //Consulta con JOIN par guardar tambien datos de la fecha(hueco) y del cliente
        String consulta = "SELECT u.nombre, u.apellidos, u.email, u.telefono, "
                + "h.fecha, s.tipo, s.lugar, s.nota "
                + "FROM sesion s "
                + "JOIN usuario u ON s.id_usuario = u.id_usuario "
                + "JOIN hueco h ON s.id_hueco = h.id_hueco "
                + "WHERE s.id_sesion = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, idSesion);
        ResultSet rs = stat.executeQuery();
        if (!rs.next()) {
            return null;
        }
        //Se cargan los datos para devolverlos en forma de objeto al controlador
        String apellidos = rs.getString("apellidos");
        String nombre = rs.getString("nombre") + (apellidos == null ? "" : " " + apellidos);
        int tel = rs.getInt("telefono");
        String telefono = rs.wasNull() ? "" : String.valueOf(tel);
        return new Detalle(nombre, rs.getString("email"), telefono,
                rs.getString("fecha"), rs.getString("tipo"),
                rs.getString("lugar"), rs.getString("nota"));
    }

    //Metodo que resuelve el estado de una sesion pendiente
    public void resolver(Connection conn, int id, String decision) throws SQLException {
        String consulta = "CALL resolver_sesion(?, ?)";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        stat.setString(2, decision);
        stat.execute();
    }

    //Metodo que consige las fechas de las sesiones existentes
    public List<FechaSesion> listarFechas(Connection conn) throws SQLException {
        //Se cargan los objetos de las fechas por sesion para cargar el combo de las sesiones
        List<FechaSesion> lista = new ArrayList<>();
        String consulta = "SELECT s.id_sesion, h.fecha, u.email, u.nombre "
                + "FROM sesion s "
                + "JOIN hueco h ON s.id_hueco = h.id_hueco "
                + "JOIN usuario u ON s.id_usuario = u.id_usuario "
                + "ORDER BY h.fecha";
        PreparedStatement stat = conn.prepareStatement(consulta);
        ResultSet rs = stat.executeQuery();
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("dd/MM/yyyy");
        while (rs.next()) {
            Date dia = rs.getDate("fecha");
            lista.add(new FechaSesion(
                    rs.getInt("id_sesion"),
                    dia == null ? "" : fmt.format(dia),
                    rs.getString("email"),
                    rs.getString("nombre")));
        }
        rs.close();
        stat.close();
        return lista;
    }

    //Creo el metodo que devuelve el objeto sesion para cargar sus datos
    public Sesion cargar(Connection conn, int id) throws SQLException {
        String consulta = "SELECT * FROM sesion WHERE id_sesion = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        ResultSet rs = stat.executeQuery();
        //Cargo valores por defecto por los casos de null
        if (!rs.next()) {
            return null;
        }
        int hueco = rs.getInt("id_hueco");
        if (rs.wasNull()) {
            hueco = 0;
        }
        int total = rs.getInt("total_fotos");
        if (rs.wasNull()) {
            total = 0;
        }
        int editadas = rs.getInt("fotos_editadas");
        if (rs.wasNull()) {
            editadas = 0;
        }
        double precio = rs.getDouble("precio_estimado");
        if (rs.wasNull()) {
            precio = 0;
        }
        Sesion s = new Sesion(
                rs.getString("estado"),
                editadas,
                hueco,
                rs.getInt("id_sesion"),
                rs.getInt("id_usuario"),
                rs.getString("lugar"),
                rs.getString("nota"),
                precio,
                rs.getString("tipo"),
                rs.getString("tipo_entrega"),
                total
        );
        rs.close();
        stat.close();
        return s;
    }

    //Metodo para realizar la entrega de la sesion
    public void entregar(Connection conn, int id) throws SQLException {
        //Se entrega la sesion teniendo en cuenta su "id"
        String consulta = "UPDATE sesion SET estado = 'entregada' WHERE id_sesion = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        stat.executeUpdate();
        stat.close();
    }

    //Lanzamiento de metodo para la modificacion de datos de sesion
    public void modificarFicha(Connection conn, int id, String lugar, String nota,
            int total, int editadas, String tipoEntrega,
            Double precio, String estado) throws SQLException {
        String consulta = "UPDATE sesion SET lugar = ?, nota = ?, total_fotos = ?, fotos_editadas = ?, "
                + "tipo_entrega = ?, precio_estimado = ?, estado = ? WHERE id_sesion = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setString(1, lugar);
        stat.setString(2, nota);
        stat.setInt(3, total);
        stat.setInt(4, editadas);

        //Tanto tipo de entrega como precio pueden ser nulos
        if (tipoEntrega == null) {
            stat.setNull(5, java.sql.Types.VARCHAR);
        } else {
            stat.setString(5, tipoEntrega);
        }
        if (precio == null) {
            stat.setNull(6, java.sql.Types.DECIMAL);
        } else {
            stat.setDouble(6, precio);
        }
        stat.setString(7, estado);
        stat.setInt(8, id);
        stat.executeUpdate();
        stat.close();
    }

    public void cancelar(Connection conn, int id) throws SQLException {
        //Se cancela la sesion teniendo en cuenta su "id"
        String consulta = "UPDATE sesion SET estado = 'cancelada' WHERE id_sesion = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        stat.executeUpdate();
        stat.close();
    }

    //Metodo que cambia el estado de las sesiones a realizadas cuando la fecha pasa de la del sistema
    public void marcarRealizadas(Connection conn) throws SQLException {
        String consulta = "UPDATE sesion s "
                + "JOIN hueco h ON s.id_hueco = h.id_hueco "
                + "SET s.estado = 'realizada' "
                + "WHERE h.fecha < CURDATE() AND s.estado = 'confirmada'";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.executeUpdate();
        stat.close();
    }

    //Metodo que obtiene los datos de las sesiones que iran en la ventana informe
    public Iterable<Object[]> listarInforme(Connection conn, String mail, Date desde, Date hasta) throws SQLException {
        List<Object[]> lista = new ArrayList<>();

        //Se necesitan cargar datos tanto de sesion y de cliente, por lo que hay que unir incluso con hueco
        String consulta = "SELECT h.fecha, u.nombre, u.apellidos, s.tipo, "
                + "s.tipo_entrega, s.total_fotos, s.precio_estimado "
                + "FROM sesion s "
                + "JOIN hueco h ON s.id_hueco = h.id_hueco "
                + "JOIN usuario u ON s.id_usuario = u.id_usuario "
                + "WHERE s.estado = 'entregada' "
                //Se plantea la posibilidad de que se hayan aplicado los filtros de fechas y mail
                + "AND (? IS NULL OR u.email LIKE ?) "
                + "AND (? IS NULL OR h.fecha >= ?) "
                + "AND (? IS NULL OR h.fecha <= ?) "
                + "ORDER BY h.fecha";

        PreparedStatement stat = conn.prepareStatement(consulta);

        //Si no se indica un correo, no se aplica el filtro por email
        if (mail == null) {
            stat.setNull(1, java.sql.Types.VARCHAR);
            stat.setNull(2, java.sql.Types.VARCHAR);
        } else {
            stat.setString(1, mail);
            stat.setString(2, mail + "%");
        }

        //Si no se indica una fecha inicial, no se aplica el filtro desde
        if (desde == null) {
            stat.setNull(3, java.sql.Types.DATE);
            stat.setNull(4, java.sql.Types.DATE);
        } else {
            stat.setDate(3, desde);
            stat.setDate(4, desde);
        }

        //Si no se indica una fecha final, no se aplica el filtro hasta
        if (hasta == null) {
            stat.setNull(5, java.sql.Types.DATE);
            stat.setNull(6, java.sql.Types.DATE);
        } else {
            stat.setDate(5, hasta);
            stat.setDate(6, hasta);
        }

        ResultSet rs = stat.executeQuery();

        //Se da el formato que tendra la fecha en la tabla
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

        //Y se recorren los datos encontrados en la BD que se cargaran en el controlador
        while (rs.next()) {
            Date dia = rs.getDate("fecha");
            String ap = rs.getString("apellidos");
            String cliente = rs.getString("nombre") + (ap == null ? "" : " " + ap);

            lista.add(new Object[]{
                dia == null ? "" : fmt.format(dia),
                cliente,
                rs.getString("tipo"),
                rs.getString("tipo_entrega"),
                rs.getObject("total_fotos"),
                rs.getBigDecimal("precio_estimado")
            });
        }

        rs.close();
        stat.close();

        return lista;

    }
}

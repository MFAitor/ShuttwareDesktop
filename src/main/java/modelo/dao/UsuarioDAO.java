/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.INTEGER;
import java.util.ArrayList;
import java.util.List;
import modelo.vo.Usuario;

/**
 *
 * @author AitorMF
 */

public class UsuarioDAO {

    //Metodo que carga el "usuario" como objeto 
    private Usuario leer(ResultSet rs) throws SQLException {
        int tel = rs.getInt("telefono");
        Integer telefono = rs.wasNull() ? null : tel;
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("email"),
                rs.getString("contrasenia"),
                rs.getString("rol"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                telefono,
                rs.getString("nota_interna"),
                rs.getString("url_instagram"),
                rs.getString("fecha_alta")
        );
    }

    //Busqueda de usuario por ID
    public Usuario cargarPorId(Connection conn, int id) throws SQLException {
        String consulta = "SELECT * FROM usuario WHERE id_usuario = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
            return leer(rs);
        }
        return null;
    }

    //Busqueda de usuario por mail (principalmente usado para poder realizar el login)
    public Usuario cargarPorEmail(Connection conn, String email) throws SQLException {
        String consulta = "SELECT * FROM usuario WHERE email = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setString(1, email);
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
            return leer(rs);
        }
        return null;
    }

    //Insercion de nuevo usuario (la creacion de nuevo fotografo se piensa como una funcionalidad futura)
    public void insertar(Connection conn, String email, String contrasenia, String rol,
                         String nombre, String apellidos, Integer telefono,
                         String notaInterna, String urlInstagram) throws SQLException {
        String consulta = "INSERT INTO usuario "
                + "(email, contrasenia, rol, nombre, apellidos, telefono, nota_interna, url_instagram, fecha_alta) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setString(1, email);
        stat.setString(2, contrasenia);
        stat.setString(3, rol);
        stat.setString(4, nombre);
        stat.setString(5, apellidos);
        if (telefono == null) {
            stat.setNull(6, java.sql.Types.INTEGER);
        } else {
            stat.setInt(6, telefono);
        }
        stat.setString(7, notaInterna);
        stat.setString(8, urlInstagram);
        stat.executeUpdate();
    }

    //Baja de usuario
    public void borrar(Connection conn, int id) throws SQLException {
        String consulta = "DELETE FROM usuario WHERE id_usuario = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        stat.executeUpdate();
    }

    //Modificacion de datos de un usuario (sobretodo corrección de datos de cliente)
    public void modificar(Connection conn, int id, String email, String nombre, String apellidos,
            Integer telefono, String notaInterna, String urlInstagram) throws SQLException {
        //Se actualizan los datos del cliente con lo que se recibe de la vista 
        String consulta = "UPDATE usuario SET email = ?, nombre = ?, apellidos = ?, telefono = ?, "
                + "nota_interna = ?, url_instagram = ? WHERE id_usuario = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setString(1, email);
        stat.setString(2, nombre);
        stat.setString(3, apellidos);
        if (telefono == null) {
            stat.setNull(4, INTEGER);
        } else {
            stat.setInt(4, telefono);
        }
        stat.setString(5, notaInterna);
        stat.setString(6, urlInstagram);
        stat.setInt(7, id);
        stat.executeUpdate();
    }

    //Funcion que devuelve todos los clientes y los devuelve en forma de lista
    public List<Usuario> listarClientes(Connection conn) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        //Se seleccionan todos los valores ordenados teniendo en cuenta su id
        String consulta = "SELECT * FROM usuario WHERE rol = 'cliente' ORDER BY id_usuario";
        PreparedStatement stat = conn.prepareStatement(consulta);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            lista.add(leer(rs));
        }
        return lista;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.vo.Preview;

/**
 *
 * @author ThinkPad
 */
public class PreviewDAO {

    //Carga de las previes de la sesion seleccionada
    public List<modelo.vo.Preview> listarPorSesion(Connection conn, int idSesion) throws SQLException {
        List<Preview> lista = new ArrayList<>();
        String consulta = "SELECT * FROM preview WHERE id_sesion = ? ORDER BY orden";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, idSesion);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            lista.add(new modelo.vo.Preview(
                    rs.getInt("id_preview"),
                    rs.getInt("id_sesion"),
                    rs.getString("ruta"),
                    rs.getString("pie"),
                    rs.getInt("orden")));
        }
        rs.close();
        stat.close();
        return lista;
    }

    //Metodo para realizar el borrado de la preview seleccionada
    public void borrar(Connection conn, int id) throws SQLException {
        String consulta = "DELETE FROM preview WHERE id_preview = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        stat.executeUpdate();
        stat.close();
    }

    //Metodo que modifica los datos de la preview
    public void modificar(Connection conn, int id, String pie, int orden) throws SQLException {
        String consulta = "UPDATE preview SET pie = ?, orden = ? WHERE id_preview = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setString(1, pie);
        stat.setInt(2, orden);
        stat.setInt(3, id);
        stat.executeUpdate();
        stat.close();
    }

    //Genero los metodos para reorganizar el orden de las previews en el momento de querer moverlas
    public int obtenerOrden(Connection conn, int id) throws SQLException {
        String consulta = "SELECT orden FROM preview WHERE id_preview = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        ResultSet rs = stat.executeQuery();
        int orden = 0;
        if (rs.next()) {
            orden = rs.getInt("orden");
        }
        rs.close();
        stat.close();
        return orden;
    }

    public void incrementarOrden(Connection conn, int idSesion, int desde, int hasta, int id) throws SQLException {
        String consulta = "UPDATE preview SET orden = orden + 1 "
                + "WHERE id_sesion = ? AND orden >= ? AND orden < ? AND id_preview <> ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, idSesion);
        stat.setInt(2, desde);
        stat.setInt(3, hasta);
        stat.setInt(4, id);
        stat.executeUpdate();
        stat.close();
    }

    public void decrementarOrden(Connection conn, int idSesion, int desde, int hasta, int id) throws SQLException {
        String consulta = "UPDATE preview SET orden = orden - 1 "
                + "WHERE id_sesion = ? AND orden > ? AND orden <= ? AND id_preview <> ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, idSesion);
        stat.setInt(2, desde);
        stat.setInt(3, hasta);
        stat.setInt(4, id);
        stat.executeUpdate();
        stat.close();
    }

    //Metodo que insrta preview en la BD
    public void insertar(Connection conn, int idSesion, String ruta, String pie, int orden) throws SQLException {
        String consulta = "INSERT INTO preview (id_sesion, ruta, pie, orden) VALUES (?, ?, ?, ?)";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, idSesion);
        stat.setString(2, ruta);
        stat.setString(3, pie);
        stat.setInt(4, orden);
        stat.executeUpdate();
        stat.close();
    }

    //Metodo que calcula el orden para insertar la preview y reorganizar
    public int siguienteOrden(Connection conn, int idSesion) throws SQLException {
        String consulta = "SELECT COALESCE(MAX(orden), 0) + 1 FROM preview WHERE id_sesion = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, idSesion);
        ResultSet rs = stat.executeQuery();
        int n = 1;
        if (rs.next()) {
            n = rs.getInt(1);
        }
        rs.close();
        stat.close();
        return n;
    }

}

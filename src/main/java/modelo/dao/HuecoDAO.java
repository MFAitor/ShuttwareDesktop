/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

/**
 *
 * @author AitorMF
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.vo.Hueco;

//DAO con el que se comunica con la tabla Hueco de la BD
public class HuecoDAO {

    //Pasa la fila a un Hueco
    private Hueco leer(ResultSet rs) throws SQLException {
        return new Hueco(
                rs.getInt("id_hueco"),
                rs.getDate("fecha"),
                rs.getString("estado")
        );
    }

    //Busca un hueco por id
    public Hueco cargarPorId(Connection conn, int id) throws SQLException {
        String consulta = "SELECT * FROM hueco WHERE id_hueco = ?";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
            return leer(rs);
        }
        return null;
    }

    //Lista todos los huecos por orden de fecha
    public List<Hueco> listar(Connection conn) throws SQLException {
        List<Hueco> lista = new ArrayList<>();
        String consulta = "SELECT * FROM hueco ORDER BY fecha";
        PreparedStatement stat = conn.prepareStatement(consulta);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            lista.add(leer(rs));
        }
        return lista;
    }

    //Publica un dia libre
    public void insertar(Connection conn, Date fecha) throws SQLException {
        String consulta = "INSERT INTO hueco (fecha, estado) VALUES (?, 'libre')";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setDate(1, fecha);
        stat.executeUpdate();
    }

    //Borrado de hueco, solo se podran borrar huecos libres
    public void borrar(Connection conn, int id) throws SQLException {
        Hueco h = cargarPorId(conn, id);
        if (h == null) {
            throw new SQLException("El hueco no existe");
        }
        //Condicion para borrar hueco que solo conste como libre
        if (!"libre".equals(h.getEstado())) {
            throw new SQLException("Solo se puede borrar un hueco libre");
        }
        
        String consulta = "DELETE FROM hueco WHERE id_hueco = ? AND estado = 'libre'";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setInt(1, id);
        stat.executeUpdate();

    }

    //Metodo que busca hueco por la fecha que se introduce
    public Hueco buscarPorFecha(Connection conn, Date fecha) throws SQLException {
        String consulta = "SELECT * FROM hueco WHERE fecha = ? LIMIT 1";
        PreparedStatement stat = conn.prepareStatement(consulta);
        stat.setDate(1, fecha);
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
            return leer(rs);
        }
        return null;

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.*;

/**
 *
 * @author AitorMF
 */
//Creo la clase para guardar las preferencias del usuario en SQLite
public class Preferencias {

    private static final String DB_URL = "jdbc:sqlite:shuttware_prefs.db";

    //Creacion de la tabla de preferencias
    static {
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement st = conn.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS preferencias (
                    clave TEXT PRIMARY KEY,
                    valor TEXT
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para guardar las preferencias despues de seleccionadas
    public static void guardar(String clave, String valor) {
        System.out.println("GUARDANDO → " + clave + " = " + valor);
        String sql = "INSERT OR REPLACE INTO preferencias (clave, valor) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clave);
            ps.setString(2, valor);
            ps.executeUpdate();
            System.out.println("Guardado correctamente en: " + new java.io.File("shuttware_prefs.db").getAbsolutePath());
        } catch (SQLException e) {
            System.out.println("ERROR al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Metodo de carga de las preferencias que se han cargado anteriormente
    public static String cargar(String clave, String valorPorDefecto) {
        System.out.println("CARGANDO → " + clave);
        String sql = "SELECT valor FROM preferencias WHERE clave = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clave);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String valor = rs.getString("valor");
                    System.out.println("Encontrado: " + valor);
                    return valor;
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR al cargar: " + e.getMessage());
        }
        System.out.println("No encontrado, usando por defecto: " + valorPorDefecto);
        return valorPorDefecto;
    }

}

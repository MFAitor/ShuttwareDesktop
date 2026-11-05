/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author AitorMF
 */
//Clase necesaria cargar el comboBox con las fechas de las sesiones
public class FechaSesion {
    public int idSesion;
    public String fecha;
    public String email;
    public String nombre;

    public FechaSesion(int idSesion, String fecha, String email, String nombre) {
        this.idSesion = idSesion;
        this.fecha = fecha;
        this.email = email;
        this.nombre = nombre;
    }

    public String toString() {
    return fecha + "  ·  " + nombre;
}
}

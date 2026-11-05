/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author AitorMF
 */
//Clase para guardar las solicitudes de sesiones pendientes
public class SolicitudPendiente {

    public int idSesion;
    public String fecha;
    public String email;
    public String tipo;
    public String lugar;

    public SolicitudPendiente(int idSesion, String fecha, String email, String tipo, String lugar) {
        this.idSesion = idSesion;
        this.fecha = fecha;
        this.email = email;
        this.tipo = tipo;
        this.lugar = lugar;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    
    
}

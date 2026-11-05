/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author AitorMF
 */

//Creacion de la clase "Usuario" con sus respectivos datos de la BD
public class Usuario {

    //Variables existentes de la base de datos
    private int idUsuario;
    private String email;
    private String contrasenia;
    private String rol;
    private String nombre;
    private String apellidos;
    private Integer telefono;
    private String notaInterna;
    private String urlInstagram;
    private String fechaAlta;

    //Constructor de la clase
    public Usuario(int idUsuario, String email, String contrasenia, String rol,
            String nombre, String apellidos, Integer telefono,
            String notaInterna, String urlInstagram, String fechaAlta) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.notaInterna = notaInterna;
        this.urlInstagram = urlInstagram;
        this.fechaAlta = fechaAlta;
    }

    //Getters y setters 
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNotaInterna() {
        return notaInterna;
    }

    public void setNotaInterna(String notaInterna) {
        this.notaInterna = notaInterna;
    }

    public String getUrlInstagram() {
        return urlInstagram;
    }

    public void setUrlInstagram(String urlInstagram) {
        this.urlInstagram = urlInstagram;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    
}

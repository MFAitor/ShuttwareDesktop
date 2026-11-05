/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author AitorMF
 */
public class Sesion {

    //Datos de la sesion en la BD
    private String estado;
    private int fotosEditadas;
    private int idHueco;
    private int idSesion;
    private int idUsuario;
    private String lugar;
    private String nota;
    private double precioEstimado;
    private String tipo;
    private String tipoEntrega;
    private int totalFotos;

    //Constructor de la sesion
    public Sesion(String estado, int fotosEditadas, int idHueco, int idSesion,
            int idUsuario, String lugar, String nota, double precioEstimado,
            String tipo, String tipoEntrega, int totalFotos) {
        this.estado = estado;
        this.fotosEditadas = fotosEditadas;
        this.idHueco = idHueco;
        this.idSesion = idSesion;
        this.idUsuario = idUsuario;
        this.lugar = lugar;
        this.nota = nota;
        this.precioEstimado = precioEstimado;
        this.tipo = tipo;
        this.tipoEntrega = tipoEntrega;
        this.totalFotos = totalFotos;
    }

    //Getters y Setters 
    public String getEstado() {
        return estado;
    }

    public int getFotosEditadas() {
        return fotosEditadas;
    }

    public int getIdHueco() {
        return idHueco;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getLugar() {
        return lugar;
    }

    public String getNota() {
        return nota;
    }

    public double getPrecioEstimado() {
        return precioEstimado;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public int getTotalFotos() {
        return totalFotos;
    }

    //Metodos para modificar los datos de la sesion
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFotosEditadas(int fotosEditadas) {
        this.fotosEditadas = fotosEditadas;
    }

    public void setIdHueco(int idHueco) {
        this.idHueco = idHueco;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setPrecioEstimado(double precioEstimado) {
        this.precioEstimado = precioEstimado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public void setTotalFotos(int totalFotos) {
        this.totalFotos = totalFotos;
    }

}

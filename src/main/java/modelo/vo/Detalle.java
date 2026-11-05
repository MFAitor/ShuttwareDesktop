/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author AitorMF
 */
//Creacion de clase para cargar los detalles de una sesion
public class Detalle {

    public String cliente;
    public String email;
    public String telefono;
    public String fecha;
    public String tipo;
    public String lugar;
    public String nota;

    public Detalle(String cliente, String email, String telefono,
            String fecha, String tipo, String lugar, String nota) {
        this.cliente = cliente;
        this.email = email;
        this.telefono = telefono;
        this.fecha = fecha;
        this.tipo = tipo;
        this.lugar = lugar;
        this.nota = nota;
    }
}

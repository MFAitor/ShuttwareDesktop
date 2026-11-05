/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.sql.Date;

/**
 *
 * @author AitorMF
 */
//Clase Hueco con los datos de la BD
public class Hueco {

    private int idHueco;
    private Date fecha;
    private String estado;

    public Hueco(int idHueco, Date fecha, String estado) {
        this.idHueco = idHueco;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdHueco() {
        return idHueco;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }
}

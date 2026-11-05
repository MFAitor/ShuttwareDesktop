/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

/**
 *
 * @author ThinkPad
 */
public class Preview {
    
    private int idPreview;
    private int idSesion;
    private String ruta;
    private String pie;
    private int orden;

    public Preview(int idPreview, int idSesion, String ruta, String pie, int orden) {
        this.idPreview = idPreview;
        this.idSesion = idSesion;
        this.ruta = ruta;
        this.pie = pie;
        this.orden = orden;
    }

    public int getIdPreview() {
        return idPreview;
    }

    public void setIdPreview(int idPreview) {
        this.idPreview = idPreview;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}

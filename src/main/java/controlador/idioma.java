/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author AitorMF
 */
//Clase idioma para cargar las cadenas de texto desde las distintas parte del proyecto 
public class idioma {

    private static String codigo = "es";
    private static ResourceBundle textos = ResourceBundle.getBundle("mensajes", new Locale("es"));

    public static void set(String c) {
        codigo = c;
        textos = ResourceBundle.getBundle("mensajes", new Locale(c));
    }

    public static String codigo() {
        return codigo;
    }

    public static String t(String clave) {
        return textos.getString(clave);
    }
}

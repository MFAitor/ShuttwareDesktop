/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.File;

/**
 *
 * @author AitorMF
 */

//Con esta clase al llevar el programa a otro equipo haremos que se trabaje con rutas relativas para que la direccion de las imagenes no influya
public class RutasUtil {

    private static final String CARPETA_BASE = "imagenes";

    //Convierte una ruta relativa en un File absoluto. Si la ruta ya es absoluta, la devuelve tal cual.
    public static File resolver(String rutaRelativa) {
        if (rutaRelativa == null || rutaRelativa.isBlank()) {
            return null;
        }
        File f = new File(rutaRelativa);
        if (f.isAbsolute()) {
            return f;
        }
        return new File(CARPETA_BASE, rutaRelativa);
    }

    //Devuelve una ruta relativa simple a partir del archivo seleccionado.
    public static String hacerRelativa(File archivoSeleccionado) {
        return "previews/" + archivoSeleccionado.getName();
    }
}

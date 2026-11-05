/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author AitorMF
 */
//Clase para crear el calendario que se usara en la vista de huecos
public class panelCalendario extends javax.swing.JPanel {

    private Image fondo;

    //La vista solo avisa de que se ha pulsado un dia; lo que pasa despues lo decide el controlador
    public interface Listener {

        void diaPulsado(LocalDate fecha);
    }

    //Se guarda el mes y anio mostrados en el calendario
    private YearMonth mes = YearMonth.now();
    //Se gurdan los estados de los huecos
    private Map<LocalDate, String> estados = new HashMap<>();
    //Listener que recibira los dias pulsados
    private Listener listener;

    //Fuente de nuestra aplicacion para aplicar al calendario
    private final Font fuente = new Font("HomepageBaukasten", Font.PLAIN, 14);

    //Creacion de etiquetas y rejilla
    private final JLabel lblMes = new JLabel("", SwingConstants.CENTER);
    private final JPanel rejilla = new JPanel(new GridLayout(0, 7, 6, 6));

    //Inicializacion del calendario y cargado de la imagen de fondo
    public panelCalendario() {

        //Colocar la imagen de fondo
        URL url = panelCalendario.class.getResource("/imagenes/escultura_v2.jpg");
        if (url != null) {
            fondo = new ImageIcon(url).getImage();
        }
        setOpaque(false);

        //Redistribucion con separacion en forma vertical 
        setLayout(new BorderLayout(0, 10));
        setOpaque(false);
        rejilla.setOpaque(false);
        lblMes.setFont(fuente.deriveFont(Font.BOLD, 16f));
        lblMes.setForeground(Color.WHITE);

        //Botones de cambio de mes
        JButton anterior = new JButton("<");
        JButton siguiente = new JButton(">");
        anterior.addActionListener(e -> {
            mes = mes.minusMonths(1);
            pintar();
        });
        siguiente.addActionListener(e -> {
            mes = mes.plusMonths(1);
            pintar();
        });

        //Cabecera con los botones y el nombre del mes
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        cabecera.add(anterior, BorderLayout.WEST);
        cabecera.add(lblMes, BorderLayout.CENTER);
        cabecera.add(siguiente, BorderLayout.EAST);

        add(cabecera, BorderLayout.NORTH);
        add(rejilla, BorderLayout.CENTER);
        add(crearLeyenda(), BorderLayout.SOUTH);
        pintar();
    }

    //Listener para los dias pulsados
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    //El controlador entrega el estado de cada dia con hueco y el calendario se repinta
    public void setEstados(Map<LocalDate, String> estados) {
        this.estados = estados;
        pintar();
    }

    //Mostrar el mes y anio actuales
    void pintar() {
        //Se obtiene el nombre del mes
        String nombre = mes.getMonth().getDisplayName(
                TextStyle.FULL,
                new Locale(controlador.idioma.codigo()));
        lblMes.setText(Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1) + " " + mes.getYear());

        //Se escribe la primera letra en mayuscula y se aniade el anio
        lblMes.setText(Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1) + " " + mes.getYear());
        //Se limpia la rejilla antes de dibujar el calendario
        rejilla.removeAll();

        //Se aniaden los dias de la semana centrados
        for (String d : new String[]{"L", "M", "X", "J", "V", "S", "D"}) {
            JLabel l = new JLabel(d, SwingConstants.CENTER);
            l.setFont(fuente.deriveFont(Font.BOLD, 14f));
            l.setForeground(Color.WHITE);
            rejilla.add(l);
        }

        //Formateo del mes con dias vacios
        int vacias = mes.atDay(1).getDayOfWeek().getValue() - 1;
        for (int i = 0; i < vacias; i++) {
            rejilla.add(new JLabel());
        }

        //Se recorre cada dia del mes para mostrarlo en el calendario
        for (int dia = 1; dia <= mes.lengthOfMonth(); dia++) {
            final LocalDate fecha = mes.atDay(dia);
            String estado = estados.get(fecha);
            final Color tinta = colorDe(estado);
            //Se crea el boton de cada dia
            JButton b = new JButton(String.valueOf(dia)) {
                //Mejora de estetica de las casillas
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(tinta);
                    g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 12, 12);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };

            b.setContentAreaFilled(false);
            b.setOpaque(false);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setForeground(new Color(40, 40, 40));
            b.setToolTipText(estado == null ? "Sin hueco" : estado);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setFont(fuente.deriveFont(Font.BOLD, 14f));
            //Se escribe la accion de pulsar el boton para marcar los huecos como libres o borrarlos
            b.addActionListener(e -> {
                if (listener != null) {
                    listener.diaPulsado(fecha);
                }
            });
            //Se aniade el boton del dia a la rejilla del calendario
            rejilla.add(b);
        }

        //Se actualiza la redistribucion
        rejilla.revalidate();
        rejilla.repaint();
    }

    // Se devuelve el color correspondiente al tipo de hueco
    private Color colorDe(String estado) {
        // Si no hay estado, se devuelve un color transparente
        if (estado == null) {
            return new Color(255, 255, 255, 70);
        }

        // Se convierte el estado a minusculas para comprobarlo
        switch (estado.toLowerCase(Locale.ROOT)) {
            case "libre":
                return new Color(198, 239, 206, 230);
            case "solicitado":
                return new Color(255, 235, 156, 230);
            case "reservado":
                return new Color(155, 194, 230, 230);
            case "bloqueado":
                return new Color(210, 210, 210, 230);
            default:
                return new Color(255, 255, 255, 70);
        }
    }

    //Leyenda para saber que color corresponde a cada tipo de hueco
    private JPanel crearLeyenda() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        p.setOpaque(false);
        //Se recorren los estados para mostrarlos
        for (String e : new String[]{"Libre", "Solicitado", "Reservado", "Bloqueado"}) {
            //Se crea la etiqueta y se le aplica un color distito a cada una
            String texto = e.substring(0, 1).toUpperCase() + e.substring(1);
            JLabel chip = new JLabel("  " + texto + "  ");
            chip.setOpaque(true);
            chip.setBackground(colorDe(e));

            // Se cambia la fuente, el estilo y el tamaño del texto
            chip.setFont(fuente);

            p.add(chip);
        }
        return p;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        // Se dibuja la imagen de fondo sin aplicar transparencia
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

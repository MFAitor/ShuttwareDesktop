/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.controladorPrincipal;
import controlador.idioma;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author AitorMF
 */
public class vistaInforme extends javax.swing.JFrame {

    /**
     * Creates new form AitorMF
     */
    public vistaInforme() {
        initComponents();
        setTitle("Informe");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo (para que se vea la imagen de fondo si la tienes)
        panelFondo.setOpaque(false);

        // Paneles semi-transparentes (igual que en Preview)
        panelFiltro.setOpaque(true);
        panelFiltro.setBackground(new java.awt.Color(255, 255, 255, 210));

        panelSesiones.setOpaque(true);
        panelSesiones.setBackground(new java.awt.Color(255, 255, 255, 210));

        // Tarjetas de totales (más sólidas o igual de transparentes, según prefieras)
        jPanel3.setOpaque(true);
        jPanel3.setBackground(new java.awt.Color(255, 255, 255, 230));
        jPanel4.setOpaque(true);
        jPanel4.setBackground(new java.awt.Color(255, 255, 255, 230));
        jPanel5.setOpaque(true);
        jPanel5.setBackground(new java.awt.Color(255, 255, 255, 230));
        
        redondearPanel(panelSesiones, 210, 25);
        redondearPanel(panelFiltro, 210, 25);

        estilizarCampos();
        estilizarBotones();
        estilizarTabla();
    }

    //Getters y setters
    public JTable getTblSesion() {
        return tblSesion;
    }

    public void setTblSesion(JTable tblSesion) {
        this.tblSesion = tblSesion;
    }

    public JLabel getLblTotalFoto() {
        return lblTotalFoto;
    }

    public void setLblTotalFoto(JLabel lblTotalFoto) {
        this.lblTotalFoto = lblTotalFoto;
    }

    public JLabel getLblTotalPrecio() {
        return lblTotalPrecio;
    }

    public void setLblTotalPrecio(JLabel lblTotalPrecio) {
        this.lblTotalPrecio = lblTotalPrecio;
    }

    public JLabel getLblTotalSesion() {
        return lblTotalSesion;
    }

    public void setLblTotalSesion(JLabel lblTotalSesion) {
        this.lblTotalSesion = lblTotalSesion;
    }

    public JTextField getTxtDesde() {
        return txtDesde;
    }

    public void setTxtDesde(JTextField txtDesde) {
        this.txtDesde = txtDesde;
    }

    public JTextField getTxtHasta() {
        return txtHasta;
    }

    public void setTxtHasta(JTextField txtHasta) {
        this.txtHasta = txtHasta;
    }

    public JTextField getTxtMail() {
        return txtMail;
    }

    public void setTxtMail(JTextField txtMail) {
        this.txtMail = txtMail;
    }

    //Metodos para la estetica de la vista
    private void estilizarCampos() {
        java.awt.Font fuente = new java.awt.Font("HomepageBaukasten Book", 0, 12);
        javax.swing.border.Border borde = javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)),
                javax.swing.BorderFactory.createEmptyBorder(4, 6, 4, 6));

        for (javax.swing.JTextField campo : new javax.swing.JTextField[]{txtDesde, txtHasta, txtMail}) {
            campo.setFont(fuente);
            campo.setBorder(borde);
            campo.setOpaque(true);
            campo.setBackground(java.awt.Color.WHITE);
        }
    }

    private void estilizarBotones() {
        java.awt.Font fuente = new java.awt.Font("HomepageBaukasten Book", 0, 12);

        btnAplicarFiltros.setFont(fuente);
        btnAplicarFiltros.setFocusPainted(false);
        btnAplicarFiltros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAplicarFiltros.setBackground(new java.awt.Color(59, 130, 246));
        btnAplicarFiltros.setForeground(java.awt.Color.WHITE);
        btnAplicarFiltros.setBorderPainted(false);

        btnDescargar.setFont(fuente);
        btnDescargar.setFocusPainted(false);
        btnDescargar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescargar.setBackground(new java.awt.Color(16, 185, 129));
        btnDescargar.setForeground(java.awt.Color.WHITE);
        btnDescargar.setBorderPainted(false);
    }

    void aplicarTextos() {
        lblDende.setText(idioma.t("informe.desde"));
        lblHata.setText(idioma.t("informe.hasta"));
        lblPrezo.setText(idioma.t("informe.total"));
        lblSesion.setText(idioma.t("informe.sesion"));
        lblEntregadas.setText(idioma.t("informe.sesiones"));
    }

    private static class PanelImagen extends JPanel {

        private Image imagen;

        public PanelImagen(String ruta) {
            URL url = vistaPreview.class.getResource(ruta);
            if (url != null) {
                imagen = new ImageIcon(url).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int aw = imagen.getWidth(this);
                int ah = imagen.getHeight(this);
                double escala = Math.max((double) getWidth() / aw, (double) getHeight() / ah);
                int w = (int) (aw * escala);
                int h = (int) (ah * escala);
                g2.drawImage(imagen, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, this);
                g2.dispose();
            }
        }
    }

    private void redondearPanel(javax.swing.JPanel panel, int alfa, int radio) {
        panel.setOpaque(false);

        panel.setUI(new javax.swing.plaf.basic.BasicPanelUI() {
            @Override
            public void update(Graphics g, javax.swing.JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(255, 255, 255, alfa));
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radio, radio);

                g2.dispose();
                paint(g, c);
            }
        });
    }

    //Metodo para estilizar la tabla y darle un enfoque mas moderno
    private void estilizarTabla() {
        //Quitar las cuadrículas
        tblSesion.setShowGrid(false);
        tblSesion.setIntercellSpacing(new Dimension(0, 0));

        //Modificaciones en las filas y columnas
        tblSesion.setBackground(Color.WHITE);
        tblSesion.setForeground(new Color(50, 50, 50));
        tblSesion.setRowHeight(35);

        //Seleccion de filas
        tblSesion.setSelectionBackground(new Color(220, 235, 252));
        tblSesion.setSelectionForeground(new Color(30, 30, 30));
        //Evitar enfoque
        tblSesion.setFocusable(false);

        // Fondo de la zona que queda vacía debajo de las filas
        tblSesion.setFillsViewportHeight(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new PanelImagen("/imagenes/ramo.jpg");
        panelFiltro = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblDende = new javax.swing.JLabel();
        txtDesde = new javax.swing.JTextField();
        lblHata = new javax.swing.JLabel();
        txtHasta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        btnAplicarFiltros = new javax.swing.JButton();
        btnDescargar = new javax.swing.JButton();
        panelSesiones = new javax.swing.JPanel();
        lblEntregadas = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblSesion = new javax.swing.JLabel();
        lblTotalSesion = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblTotalFoto = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblPrezo = new javax.swing.JLabel();
        lblTotalPrecio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSesion = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 18)); // NOI18N
        jLabel2.setText("I N F O R M E");

        lblDende.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblDende.setText("Desde");

        txtDesde.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtDesde.setText("09/10/2026");

        lblHata.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblHata.setText("Hasta");

        txtHasta.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtHasta.setText("12/12/2026");

        jLabel5.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel5.setText("Cliente (email)");

        txtMail.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        btnAplicarFiltros.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnAplicarFiltros.setText("Aplicar Filtros");
        btnAplicarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarFiltrosActionPerformed(evt);
            }
        });

        btnDescargar.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnDescargar.setText("Descargar Informe");
        btnDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFiltroLayout = new javax.swing.GroupLayout(panelFiltro);
        panelFiltro.setLayout(panelFiltroLayout);
        panelFiltroLayout.setHorizontalGroup(
            panelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltroLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(lblHata)
                    .addComponent(lblDende)
                    .addComponent(jLabel2)
                    .addComponent(txtDesde)
                    .addComponent(txtHasta)
                    .addComponent(txtMail)
                    .addComponent(btnDescargar, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(btnAplicarFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        panelFiltroLayout.setVerticalGroup(
            panelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltroLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addComponent(lblDende)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblHata)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAplicarFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDescargar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        lblEntregadas.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 18)); // NOI18N
        lblEntregadas.setText("S E S I O N E S   E N T R E G A D A S");

        lblSesion.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblSesion.setForeground(new java.awt.Color(153, 153, 153));
        lblSesion.setText("Sesiones");

        lblTotalSesion.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 14)); // NOI18N
        lblTotalSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalSesion.setText("-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSesion)
                .addContainerGap(130, Short.MAX_VALUE))
            .addComponent(lblTotalSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalSesion)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Fotos");

        lblTotalFoto.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 14)); // NOI18N
        lblTotalFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalFoto.setText("-");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(152, Short.MAX_VALUE))
            .addComponent(lblTotalFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalFoto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblPrezo.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblPrezo.setForeground(new java.awt.Color(153, 153, 153));
        lblPrezo.setText("Total Precio");

        lblTotalPrecio.setFont(new java.awt.Font("Microsoft JhengHei", 1, 14)); // NOI18N
        lblTotalPrecio.setForeground(new java.awt.Color(0, 0, 255));
        lblTotalPrecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPrecio.setText("-");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPrezo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblTotalPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPrezo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalPrecio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblSesion.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        tblSesion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Cliente", "Tipo", "Entrega", "Fotos", "Precio"
            }
        ));
        jScrollPane1.setViewportView(tblSesion);

        javax.swing.GroupLayout panelSesionesLayout = new javax.swing.GroupLayout(panelSesiones);
        panelSesiones.setLayout(panelSesionesLayout);
        panelSesionesLayout.setHorizontalGroup(
            panelSesionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSesionesLayout.createSequentialGroup()
                .addGroup(panelSesionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelSesionesLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblEntregadas))
                    .addGroup(panelSesionesLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panelSesionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelSesionesLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelSesionesLayout.setVerticalGroup(
            panelSesionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSesionesLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblEntregadas)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(panelSesionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(panelFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelSesiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSesiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Boton para aplicar filtros
    private void btnAplicarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarFiltrosActionPerformed
        String mail = getTxtMail().getText().trim();
        if (mail.isEmpty()) {
            mail = null;
        }

        Date desde = fecha(getTxtDesde().getText());
        Date hasta = fecha(getTxtHasta().getText());
        if (getTxtDesde().getText().trim().length() > 0 && desde == null
                || getTxtHasta().getText().trim().length() > 0 && hasta == null) {
            JOptionPane.showMessageDialog(this, "Fecha en formato dd/MM/yyyy");
            return;
        }
        //Se llama al metodo del contolador pasando los datos de la vista
        controlador.controladorPrincipal.cargarTablaClientesInforme(this, mail, desde, hasta);
    }//GEN-LAST:event_btnAplicarFiltrosActionPerformed

    //Se llama al controlador para gestionar la logica de la descarga del pdf
    private void btnDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarActionPerformed
        controladorPrincipal.descargaPDF(this);
    }//GEN-LAST:event_btnDescargarActionPerformed

    //Transformacion de la fecha recogida a tipo de datos usable para la BD
    private Date fecha(String texto) {
        texto = texto.trim();

        if (texto.isEmpty()) {
            return null;
        }

        try {
            java.util.Date d = new SimpleDateFormat("dd/MM/yyyy").parse(texto);
            return new Date(d.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAplicarFiltros;
    private javax.swing.JButton btnDescargar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDende;
    private javax.swing.JLabel lblEntregadas;
    private javax.swing.JLabel lblHata;
    private javax.swing.JLabel lblPrezo;
    private javax.swing.JLabel lblSesion;
    private javax.swing.JLabel lblTotalFoto;
    private javax.swing.JLabel lblTotalPrecio;
    private javax.swing.JLabel lblTotalSesion;
    private javax.swing.JPanel panelFiltro;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelSesiones;
    private javax.swing.JTable tblSesion;
    private javax.swing.JTextField txtDesde;
    private javax.swing.JTextField txtHasta;
    private javax.swing.JTextField txtMail;
    // End of variables declaration//GEN-END:variables
}

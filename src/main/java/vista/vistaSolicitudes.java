/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.controladorPrincipal;
import controlador.idioma;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author AitorMF
 */
public class vistaSolicitudes extends javax.swing.JFrame {

    /**
     * Creates new form vistaSesion
     */
    public vistaSolicitudes() {
        initComponents();
        setTitle("Solicitudes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Carga de imagen y lanzamiento de metodos para la estetica de la vista
        PanelImagen fondo = new PanelImagen("/imagenes/calle_asia.jpg");
        fondo.setLayout(new java.awt.BorderLayout());
        setContentPane(fondo);

        panelFondo.setOpaque(false);
        fondo.add(panelFondo, java.awt.BorderLayout.CENTER);

        //Tarjeta translucida sin tocar el GroupLayout
        panelSubFondo.setOpaque(true);
        panelSubFondo.setBackground(new java.awt.Color(255, 255, 255, 210));
        redondearPanel(panelSubFondo, 210, 25);

        panelInfo.setOpaque(true);
        panelInfo.setBackground(java.awt.Color.WHITE);

        jLabel12.setForeground(java.awt.Color.WHITE);
        lblTextolargo.setForeground(java.awt.Color.WHITE);

        estilizarTabla();
        fijarDetalle();
        estilizarBotones();

        //Se cargan los datos de la sesion seleccionada en los labels inferiores
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                controladorPrincipal.cargarDetalleSesion(this);
            }
        });

    }

    //Getters y setters de los elementos necesarios en operaciones
    public JTable getjTable1() {
        return jTable1;
    }

    public void setjTable1(JTable jTable1) {
        this.jTable1 = jTable1;
    }

    public JTextField getTxtPendientes() {
        return txtPendientes;
    }

    public void setTxtPendientes(JTextField txtPendientes) {
        this.txtPendientes = txtPendientes;
    }

    public JLabel getLblCliente() {
        return lblCliente;
    }

    public void setLblCliente(JLabel lblCliente) {
        this.lblCliente = lblCliente;
    }

    public JLabel getLblHueco() {
        return lblHueco;
    }

    public void setLblHueco(JLabel lblHueco) {
        this.lblHueco = lblHueco;
    }

    public JLabel getLblNota() {
        return lblNota;
    }

    public void setLblNota(JLabel lblNota) {
        this.lblNota = lblNota;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    public void setLblTelefono(JLabel lblTelefono) {
        this.lblTelefono = lblTelefono;
    }

    public JLabel getLblTipoSesion() {
        return lblTipoSesion;
    }

    public void setLblTipoSesion(JLabel lblTipoSesion) {
        this.lblTipoSesion = lblTipoSesion;
    }

    //Metodo para no mover los paneles en funcion del tamanio de los labesl
    private void fijarDetalle() {
        Dimension caja = new Dimension(200, 22);
        for (JLabel l : new JLabel[]{
            lblCliente, lblHueco, lblNota, lblTelefono, lblTipoSesion}) {
            l.setPreferredSize(caja);
            l.setMinimumSize(caja);
            l.setMaximumSize(caja);
        }
    }

    //Metodos para la mejora estetica de la vista 
    private void estilizarTabla() {
        jTable1.setShowGrid(false);
        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable1.setRowHeight(32);
        jTable1.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 13));
        jTable1.setSelectionBackground(new java.awt.Color(153, 204, 255));
        jTable1.setSelectionForeground(java.awt.Color.WHITE);
        jTable1.setOpaque(false);
        jTable1.setFillsViewportHeight(true);
        javax.swing.table.JTableHeader cabecera = jTable1.getTableHeader();
        cabecera.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12));
        cabecera.setBackground(new java.awt.Color(245, 245, 245));
        cabecera.setForeground(new java.awt.Color(80, 80, 80));
        cabecera.setBorder(javax.swing.BorderFactory.createMatteBorder(
                0, 0, 1, 0, new java.awt.Color(229, 229, 229)));
    }

    private void estilizarBotones() {
        btnAceptar.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar.setForeground(java.awt.Color.WHITE);
        btnAceptar.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12));
        btnAceptar.setBorderPainted(false);
        btnAceptar.setFocusPainted(false);

        btnRechazar.setBackground(new java.awt.Color(153, 0, 51));
        btnRechazar.setForeground(java.awt.Color.WHITE);
        btnRechazar.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12));
        btnRechazar.setBorderPainted(false);
        btnRechazar.setFocusPainted(false);

        txtPendientes.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));
        txtPendientes.setEditable(false);
    }

    void aplicarTextos() {
        btnRechazar.setText(idioma.t("solicitud.rechazar"));
        lblTiLug.setText(idioma.t("solicitud.tipoylugar"));
        lblHuecosel.setText(idioma.t("solicitud.hueco"));
        lblTextolargo.setText(idioma.t("solicitud.explicacion"));
    }

    private static class PanelImagen extends javax.swing.JPanel {

        private java.awt.Image imagen;

        public PanelImagen(String ruta) {
            java.net.URL url = vistaSolicitudes.class.getResource(ruta);
            if (url != null) {
                imagen = new javax.swing.ImageIcon(url).getImage();
            }
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                        java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
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

    private static class PanelTransparente extends javax.swing.JPanel {

        private final int alfa;
        private final int radio;

        public PanelTransparente(int alfa, int radio) {
            this.alfa = alfa;
            this.radio = radio;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            // Mejora la calidad de los bordes
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibuja el fondo con transparencia y bordes redondeados
            g2.setColor(new Color(255, 255, 255, alfa));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

            g2.dispose();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelFondo = new javax.swing.JPanel();
        panelSubFondo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panelInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        lblHuecosel = new javax.swing.JLabel();
        lblHueco = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblNota = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblTiLug = new javax.swing.JLabel();
        lblTipoSesion = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnRechazar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtPendientes = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lblTextolargo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 650));
        setResizable(false);

        jTable1.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Mail Cliente", "Tipo", "Lugar"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        panelInfo.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 14)); // NOI18N
        jLabel1.setText("Solicitud Seleccionada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 17, 0, 0);
        panelInfo.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(165, 165, 165));
        jLabel2.setText("Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 17, 0, 0);
        panelInfo.add(jLabel2, gridBagConstraints);

        lblCliente.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblCliente.setText("-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 17, 0, 0);
        panelInfo.add(lblCliente, gridBagConstraints);

        lblHuecosel.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 10)); // NOI18N
        lblHuecosel.setForeground(new java.awt.Color(165, 165, 165));
        lblHuecosel.setText("Hueco seleccionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 17, 0, 0);
        panelInfo.add(lblHuecosel, gridBagConstraints);

        lblHueco.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblHueco.setText("-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 17, 0, 0);
        panelInfo.add(lblHueco, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(165, 165, 165));
        jLabel6.setText("Nota del cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 17, 0, 0);
        panelInfo.add(jLabel6, gridBagConstraints);

        lblNota.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblNota.setText("-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 17, 12, 0);
        panelInfo.add(lblNota, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(165, 165, 165));
        jLabel8.setText("Teléfono de contacto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 243, 0, 6);
        panelInfo.add(jLabel8, gridBagConstraints);

        lblTelefono.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblTelefono.setText("-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 243, 0, 0);
        panelInfo.add(lblTelefono, gridBagConstraints);

        lblTiLug.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 10)); // NOI18N
        lblTiLug.setForeground(new java.awt.Color(165, 165, 165));
        lblTiLug.setText("Tipo y lugar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 243, 0, 0);
        panelInfo.add(lblTiLug, gridBagConstraints);

        lblTipoSesion.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblTipoSesion.setText("-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 243, 0, 0);
        panelInfo.add(lblTipoSesion, gridBagConstraints);

        btnAceptar.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnAceptar.setText("Confirmar Sesion");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnRechazar.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnRechazar.setText("Rechazar");
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 18)); // NOI18N
        jLabel14.setText("S O L I C I T U D E S");

        txtPendientes.setBackground(new java.awt.Color(255, 255, 204));
        txtPendientes.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtPendientes.setText("jTextField1");

        javax.swing.GroupLayout panelSubFondoLayout = new javax.swing.GroupLayout(panelSubFondo);
        panelSubFondo.setLayout(panelSubFondoLayout);
        panelSubFondoLayout.setHorizontalGroup(
            panelSubFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubFondoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelSubFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelSubFondoLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSubFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelSubFondoLayout.createSequentialGroup()
                            .addComponent(btnRechazar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panelSubFondoLayout.setVerticalGroup(
            panelSubFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubFondoLayout.createSequentialGroup()
                .addGroup(panelSubFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSubFondoLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelSubFondoLayout.createSequentialGroup()
                        .addContainerGap(47, Short.MAX_VALUE)
                        .addComponent(txtPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSubFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRechazar)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 18)); // NOI18N
        jLabel12.setText("Solicitudes");

        lblTextolargo.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblTextolargo.setText("<html>Revisa las peticiones<br>de tus cliente y<br>decide si las confirmas<br>o las rechazas </html>");

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(lblTextolargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSubFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(481, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTextolargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(panelSubFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(132, Short.MAX_VALUE))
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

    //Accion para aceptar la sesion seleccionada
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        controladorPrincipal.resolverPeticion(this,"confirmada");
    }//GEN-LAST:event_btnAceptarActionPerformed

    //Accion para rechazar la sesion seleccionada
    private void btnRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarActionPerformed
        controladorPrincipal.resolverPeticion(this,"rechazada");
    }//GEN-LAST:event_btnRechazarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnRechazar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblHueco;
    private javax.swing.JLabel lblHuecosel;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTextolargo;
    private javax.swing.JLabel lblTiLug;
    private javax.swing.JLabel lblTipoSesion;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelSubFondo;
    private javax.swing.JTextField txtPendientes;
    // End of variables declaration//GEN-END:variables
}

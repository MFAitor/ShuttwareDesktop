/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.controladorPrincipal;
import controlador.idioma;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.vo.FechaSesion;

/**
 *
 * @author AitorMF
 */
public class vistaSesion extends javax.swing.JFrame {

    /**
     * Creates new form vistaFicha
     */
    public vistaSesion() {
        initComponents();
        setTitle("Sesiones");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelFondo.setOpaque(false);

        panelBase.setOpaque(true);
        panelBase.setBackground(new java.awt.Color(255, 255, 255, 210));

        panelDetalles.setOpaque(true);
        panelDetalles.setBackground(java.awt.Color.WHITE);
        panelPorcentaje.setOpaque(true);
        panelPorcentaje.setBackground(java.awt.Color.WHITE);

        redondearPanel(panelBase, 210, 25);
        redondearPanel(panelDetalles, 210, 25);
        redondearPanel(panelPorcentaje, 210, 25);

    }

    //Getters y setters
    public JComboBox<FechaSesion> getCmbFechaSesion() {
        return cmbFechaSesion;
    }

    public void setCmbFechaSesion(JComboBox<FechaSesion> cmbFechaSesion) {
        this.cmbFechaSesion = cmbFechaSesion;
    }

    public ButtonGroup getButGroupTipoEntrega() {
        return butGroupTipoEntrega;
    }

    public void setButGroupTipoEntrega(ButtonGroup butGroupTipoEntrega) {
        this.butGroupTipoEntrega = butGroupTipoEntrega;
    }

    public JLabel getLblNombreTipo() {
        return lblNombreTipo;
    }

    public void setLblNombreTipo(JLabel lblNombreTipo) {
        this.lblNombreTipo = lblNombreTipo;
    }

    public JRadioButton getRdAlbum() {
        return rdAlbum;
    }

    public void setRdAlbum(JRadioButton rdAlbum) {
        this.rdAlbum = rdAlbum;
    }

    public JRadioButton getRdCaja() {
        return rdCaja;
    }

    public void setRdCaja(JRadioButton rdCaja) {
        this.rdCaja = rdCaja;
    }

    public JRadioButton getRdLink() {
        return rdLink;
    }

    public void setRdLink(JRadioButton rdLink) {
        this.rdLink = rdLink;
    }

    public JRadioButton getRdUSB() {
        return rdUSB;
    }

    public void setRdUSB(JRadioButton rdUSB) {
        this.rdUSB = rdUSB;
    }

    public JTextField getTxtEstadoSesion() {
        return txtEstadoSesion;
    }

    public void setTxtEstadoSesion(JTextField txtEstadoSesion) {
        this.txtEstadoSesion = txtEstadoSesion;
    }

    public JTextField getTxtFotosEditadas() {
        return txtFotosEditadas;
    }

    public void setTxtFotosEditadas(JTextField txtFotosEditadas) {
        this.txtFotosEditadas = txtFotosEditadas;
    }

    public JTextField getTxtFotosTotales() {
        return txtFotosTotales;
    }

    public void setTxtFotosTotales(JTextField txtFotosTotales) {
        this.txtFotosTotales = txtFotosTotales;
    }

    public JTextField getTxtLugar() {
        return txtLugar;
    }

    public void setTxtLugar(JTextField txtLugar) {
        this.txtLugar = txtLugar;
    }

    public JTextArea getTxtNotaSesion() {
        return txtNotaSesion;
    }

    public void setTxtNotaSesion(JTextArea txtNotaSesion) {
        this.txtNotaSesion = txtNotaSesion;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JLabel getLblContadorFotos() {
        return lblContadorFotos;
    }

    public void setLblContadorFotos(JLabel lblContadorFotos) {
        this.lblContadorFotos = lblContadorFotos;
    }

    public JLabel getLblPorcentaje() {
        return lblPorcentaje;
    }

    public void setLblPorcentaje(JLabel lblPorcentaje) {
        this.lblPorcentaje = lblPorcentaje;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    private void estilizarBordes() {
        java.awt.Color normal = new java.awt.Color(229, 229, 229);
        javax.swing.border.Border borde = javax.swing.BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(normal, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(6, 10, 6, 10));
        // Cambia estos nombres por los campos de tu diseñador
        for (javax.swing.JTextField c : new javax.swing.JTextField[]{}) {
            c.setBorder(borde);
        }
    }

    private void estilizarBotones() {
        // Mismo criterio: azul para guardar, rojo para una baja si la hay
        for (javax.swing.JButton b : new javax.swing.JButton[]{}) {
            b.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12));
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }
    }

    void aplicarTextos() {
        lblPrezo.setText(idioma.t("sesion.precioestimado"));
        lblNota.setText(idioma.t("sesion.nota"));
        btnGuardar.setText(idioma.t("sesion.guardar"));
        lblTotal.setText(idioma.t("sesion.totales"));
        lblDetalles.setText(idioma.t("sesion.detalles"));
    }

    private static class PanelImagen extends javax.swing.JPanel {

        private java.awt.Image imagen;

        public PanelImagen(String ruta) {
            java.net.URL url = vistaSesion.class.getResource(ruta);
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

    //Metodo para pintar el txtEstado en un funcion de su valor
    public void pintarEstado(String estado) {
        java.awt.Color fondo;
        if (estado == null) {
            fondo = new java.awt.Color(229, 229, 229);
        } else {
            switch (estado) {
                case "pendiente":
                    fondo = new java.awt.Color(230, 168, 0);
                    break;
                case "confirmada":
                    fondo = new java.awt.Color(70, 130, 200);
                    break;
                case "realizada":
                    fondo = new java.awt.Color(90, 90, 170);
                    break;
                case "en_edicion":
                    fondo = new java.awt.Color(210, 120, 40);
                    break;
                case "lista_entrega":
                    fondo = new java.awt.Color(40, 160, 140);
                    break;
                case "entregada":
                    fondo = new java.awt.Color(60, 150, 80);
                    break;
                case "rechazada":
                    fondo = new java.awt.Color(180, 50, 60);
                    break;
                case "cancelada":
                    fondo = new java.awt.Color(140, 140, 140);
                    break;
                default:
                    fondo = new java.awt.Color(229, 229, 229);
            }
        }
        txtEstadoSesion.setOpaque(true);
        txtEstadoSesion.setBackground(fondo);
        txtEstadoSesion.setForeground(java.awt.Color.WHITE);
        txtEstadoSesion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstadoSesion.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butGroupTipoEntrega = new javax.swing.ButtonGroup();
        panelFondo = new PanelImagen("/imagenes/oceano.jpg");
        panelBase = new javax.swing.JPanel();
        panelPorcentaje = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblPorcentaje = new javax.swing.JLabel();
        lblContadorFotos = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFotosTotales = new javax.swing.JTextField();
        txtFotosEditadas = new javax.swing.JTextField();
        panelDetalles = new javax.swing.JPanel();
        lblDetalles = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblPrezo = new javax.swing.JLabel();
        txtLugar = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblNota = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotaSesion = new javax.swing.JTextArea();
        rdAlbum = new javax.swing.JRadioButton();
        rdUSB = new javax.swing.JRadioButton();
        rdLink = new javax.swing.JRadioButton();
        rdCaja = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnEntrega = new javax.swing.JButton();
        txtEstadoSesion = new javax.swing.JTextField();
        cmbFechaSesion = new javax.swing.JComboBox<>();
        lblNombreTipo = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 650));
        setResizable(false);

        jLabel3.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 18)); // NOI18N
        jLabel3.setText("Progreso de Edición");

        lblPorcentaje.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 48)); // NOI18N
        lblPorcentaje.setText("25 %");

        lblContadorFotos.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblContadorFotos.setForeground(new java.awt.Color(153, 153, 153));
        lblContadorFotos.setText("40 fotos por editar");

        lblTotal.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblTotal.setText("Fotos totales");

        jLabel7.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel7.setText("Fotos editadas");

        txtFotosTotales.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtFotosTotales.setText("jTextField1");

        txtFotosEditadas.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtFotosEditadas.setText("jTextField2");

        javax.swing.GroupLayout panelPorcentajeLayout = new javax.swing.GroupLayout(panelPorcentaje);
        panelPorcentaje.setLayout(panelPorcentajeLayout);
        panelPorcentajeLayout.setHorizontalGroup(
            panelPorcentajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPorcentajeLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(panelPorcentajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(panelPorcentajeLayout.createSequentialGroup()
                        .addComponent(lblTotal)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel7))
                    .addGroup(panelPorcentajeLayout.createSequentialGroup()
                        .addComponent(txtFotosTotales, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFotosEditadas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblContadorFotos)
                    .addComponent(lblPorcentaje))
                .addGap(33, 33, 33))
        );
        panelPorcentajeLayout.setVerticalGroup(
            panelPorcentajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPorcentajeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblPorcentaje)
                .addGap(47, 47, 47)
                .addComponent(lblContadorFotos)
                .addGap(50, 50, 50)
                .addGroup(panelPorcentajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPorcentajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFotosTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFotosEditadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblDetalles.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 18)); // NOI18N
        lblDetalles.setText("Detalles de Entrega");

        jLabel8.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel8.setText("Lugar");

        lblPrezo.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblPrezo.setText("Precio Estimado");

        txtLugar.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtLugar.setText("Puerto de Esteiro");

        txtPrecio.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtPrecio.setText("180.00 €");

        jLabel10.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        jLabel10.setText("Tipo de Entrega");

        lblNota.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblNota.setText("Nota de la sesión");

        txtNotaSesion.setColumns(20);
        txtNotaSesion.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtNotaSesion.setRows(5);
        jScrollPane1.setViewportView(txtNotaSesion);

        butGroupTipoEntrega.add(rdAlbum);
        rdAlbum.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rdAlbum.setText("Album");

        butGroupTipoEntrega.add(rdUSB);
        rdUSB.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rdUSB.setText("USB");

        butGroupTipoEntrega.add(rdLink);
        rdLink.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rdLink.setText("Link de descarga");

        butGroupTipoEntrega.add(rdCaja);
        rdCaja.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        rdCaja.setText("Caja personalizada");

        javax.swing.GroupLayout panelDetallesLayout = new javax.swing.GroupLayout(panelDetalles);
        panelDetalles.setLayout(panelDetallesLayout);
        panelDetallesLayout.setHorizontalGroup(
            panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetallesLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelDetallesLayout.createSequentialGroup()
                        .addComponent(rdAlbum)
                        .addGap(42, 42, 42)
                        .addComponent(rdUSB)
                        .addGap(46, 46, 46)
                        .addComponent(rdLink)
                        .addGap(27, 27, 27)
                        .addComponent(rdCaja))
                    .addComponent(lblNota)
                    .addComponent(jLabel10)
                    .addComponent(lblDetalles)
                    .addGroup(panelDetallesLayout.createSequentialGroup()
                        .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLugar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrezo)))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDetallesLayout.setVerticalGroup(
            panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetallesLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblDetalles)
                .addGap(37, 37, 37)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblPrezo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdAlbum)
                    .addComponent(rdUSB)
                    .addComponent(rdLink)
                    .addComponent(rdCaja))
                .addGap(81, 81, 81)
                .addComponent(lblNota)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 24)); // NOI18N
        jLabel1.setText("S E S I O N");

        btnGuardar.setBackground(new java.awt.Color(204, 255, 255));
        btnGuardar.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 255));
        btnGuardar.setText("Guardar Cambios");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEntrega.setBackground(new java.awt.Color(51, 0, 204));
        btnEntrega.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnEntrega.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrega.setText("Realizar Entrega");
        btnEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntregaActionPerformed(evt);
            }
        });

        txtEstadoSesion.setBackground(new java.awt.Color(204, 255, 255));
        txtEstadoSesion.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtEstadoSesion.setForeground(new java.awt.Color(51, 0, 204));
        txtEstadoSesion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstadoSesion.setText("EN EDICIÓN");

        cmbFechaSesion.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        cmbFechaSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFechaSesionActionPerformed(evt);
            }
        });

        lblNombreTipo.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 12)); // NOI18N
        lblNombreTipo.setText("- / - ");

        btnCancelar.setBackground(new java.awt.Color(255, 204, 204));
        btnCancelar.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(204, 0, 0));
        btnCancelar.setText("Cancelar Sesión");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBaseLayout = new javax.swing.GroupLayout(panelBase);
        panelBase.setLayout(panelBaseLayout);
        panelBaseLayout.setHorizontalGroup(
            panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBaseLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panelBaseLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(347, 347, 347)
                            .addComponent(cmbFechaSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addComponent(txtEstadoSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33))
                        .addGroup(panelBaseLayout.createSequentialGroup()
                            .addComponent(panelPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(panelDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(23, 23, 23))
                        .addGroup(panelBaseLayout.createSequentialGroup()
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(btnEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(72, 72, 72)))
                    .addGroup(panelBaseLayout.createSequentialGroup()
                        .addComponent(lblNombreTipo)
                        .addContainerGap())))
        );
        panelBaseLayout.setVerticalGroup(
            panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbFechaSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstadoSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombreTipo)
                .addGap(18, 18, 18)
                .addGroup(panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelPorcentaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDetalles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(panelBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panelBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Al cambiar el valor del combo se cargan los datos de la sesion que muestra
    private void cmbFechaSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFechaSesionActionPerformed
        controladorPrincipal.cargarFicha(this);
    }//GEN-LAST:event_cmbFechaSesionActionPerformed

    //Pulsacion de sesion entregada para saltar notificacion al cliente
    private void btnEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregaActionPerformed
        controladorPrincipal.entregarSesion(this);
    }//GEN-LAST:event_btnEntregaActionPerformed

    //Modificacion de datos de la sesion
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        controladorPrincipal.modificarDatosSesion(this);
    }//GEN-LAST:event_btnGuardarActionPerformed

    //Cancelar sesion
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        controladorPrincipal.cancelarSesion(this);
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEntrega;
    private javax.swing.JButton btnGuardar;
    private javax.swing.ButtonGroup butGroupTipoEntrega;
    private javax.swing.JComboBox<FechaSesion> cmbFechaSesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContadorFotos;
    private javax.swing.JLabel lblDetalles;
    private javax.swing.JLabel lblNombreTipo;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblPorcentaje;
    private javax.swing.JLabel lblPrezo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelBase;
    private javax.swing.JPanel panelDetalles;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelPorcentaje;
    private javax.swing.JRadioButton rdAlbum;
    private javax.swing.JRadioButton rdCaja;
    private javax.swing.JRadioButton rdLink;
    private javax.swing.JRadioButton rdUSB;
    private javax.swing.JTextField txtEstadoSesion;
    private javax.swing.JTextField txtFotosEditadas;
    private javax.swing.JTextField txtFotosTotales;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextArea txtNotaSesion;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}

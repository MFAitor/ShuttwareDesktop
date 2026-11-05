/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.controladorPrincipal;
import controlador.idioma;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.vo.FechaSesion;
import modelo.vo.Preview;

/**
 *
 * @author AitorMF
 */
public class vistaPreview extends javax.swing.JFrame {

    /**
     * Creates new form AitorMF
     */
    //Se generan variables para guardar las previews pulsadas
    private int previewSeleccionada = -1;
    private JPanel tarjetaMarcada;

    //Variable para cargar la ruta de una nueva preview en el dispositivo
    private String rutaNueva;

    public vistaPreview() {
        initComponents();
        setTitle("Preview");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelFondo.setOpaque(false);

        panelSeleccionada.setOpaque(true);
        panelSeleccionada.setBackground(new Color(255, 255, 255, 210));

        panelSelector.setOpaque(true);
        panelSelector.setBackground(new Color(255, 255, 255, 210));

        panelNueva.setOpaque(true);
        panelNueva.setBackground(Color.WHITE);

        estilizarCampos();
        estilizarBotones();

        redondearPanel(panelSelector, 210, 25);
        redondearPanel(panelSeleccionada, 210, 25);

        //El panel lista apila en vertical
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
    }

    //Getters y setters 
    public JComboBox<FechaSesion> getCmbSesionCliente() {
        return cmbSesionCliente;
    }

    public void setCmbSesionCliente(JComboBox<FechaSesion> cmbSesionCliente) {
        this.cmbSesionCliente = cmbSesionCliente;
    }

    public JPanel getPanelLista() {
        return jPanel1;
    }

    public void setPanelLista(JPanel panelLista) {
        this.jPanel1 = panelLista;
    }

    public JTextField getTxtCantidadPreview() {
        return txtCantidadPreview;
    }

    public void setTxtCantidadPreview(JTextField txtCantidadPreview) {
        this.txtCantidadPreview = txtCantidadPreview;
    }

    public int getPreviewSeleccionada() {
        return previewSeleccionada;
    }

    public JTextField getTxtOrden() {
        return txtOrden;
    }

    public void setTxtOrden(JTextField txtOrden) {
        this.txtOrden = txtOrden;
    }

    public JTextField getTxtPieFoto() {
        return txtPieFoto;
    }

    public void setTxtPieFoto(JTextField txtPieFoto) {
        this.txtPieFoto = txtPieFoto;
    }

    public String getRutaNueva() {
        return rutaNueva;
    }

    //Metodos para aplicar estilo a la vista
    private void estilizarCampos() {
        Color normal = new Color(229, 229, 229);
        Border borde = BorderFactory.createCompoundBorder(
                new LineBorder(normal, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10));
        for (JTextField c : new JTextField[]{txtPieFoto, txtOrden}) {
            c.setBorder(borde);
            c.setFont(new Font("HomepageBaukasten Book", 0, 12));
        }
        txtCantidadPreview.setEditable(false);
        txtCantidadPreview.setHorizontalAlignment(JTextField.CENTER);
        txtCantidadPreview.setBorder(BorderFactory.createEmptyBorder());
    }

    private void estilizarBotones() {
        estilo(btnAniadir, new Color(51, 0, 204), Color.WHITE);
        estilo(btnElegirNuevaPreview, new Color(204, 204, 255), new Color(0, 51, 255));
        estilo(btnEliminarPreview, new Color(255, 204, 204), new Color(204, 0, 0));
        estilo(btnMenos, new Color(204, 204, 204), Color.DARK_GRAY);
        btnMas.setBackground(new Color(204, 204, 204));
        btnMas.setFocusPainted(false);
        btnMas.setBorderPainted(false);
    }

    private void estilo(JButton b, Color fondo, Color texto) {
        b.setBackground(fondo);
        b.setForeground(texto);
        b.setFont(new Font("HomepageBaukasten Bold", 0, 12));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }

    void aplicarTextos() {
        lblPie.setText(idioma.t("preview.pie"));
        lblNova.setText(idioma.t("preview.nueva"));
        lblInstrucc.setText(idioma.t("preview.descripcion"));
        btnElegirNuevaPreview.setText(idioma.t("preview.elegir"));
        btnAniadir.setText(idioma.t("preview.aniadir"));
    }

    //Metodo para dibujar la imagen ajustandola al panel disponible 
    private static class PanelImagen extends JPanel {

        private Image imagen;

        //Se carga la imagen en caso de existir el archivo 
        public PanelImagen(String ruta) {
            File f = new File(ruta);
            if (f.exists()) {
                try (InputStream is = new FileInputStream(f)) {
                    imagen = ImageIO.read(is);
                } catch (IOException e) {
                    imagen = null;
                }
            }
        }

        //Se dibuja la imagen en el panel respetando la mayor calidad posible y ajustando el tamanio 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int aw = imagen.getWidth(this);
                int ah = imagen.getHeight(this);
                if (aw > 0 && ah > 0) {
                    double escala = Math.max((double) getWidth() / aw, (double) getHeight() / ah);
                    int w = (int) (aw * escala);
                    int h = (int) (ah * escala);
                    g2.drawImage(imagen, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, this);
                }
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

    public JPanel tarjeta(Preview p) {
        JPanel t = new JPanel(new BorderLayout(10, 0));
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(229, 229, 229), 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        t.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        t.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel foto = new JLabel();
        foto.setPreferredSize(new Dimension(64, 64));
        File f = new File(p.getRuta());
        System.out.println(p.getRuta() + "  existe: " + f.exists());
        //Se hace la lectura de las imagenes por Streams
        if (f.exists()) {
            try (InputStream is = new FileInputStream(f)) {
                BufferedImage orig = ImageIO.read(is);
                if (orig != null) {
                    Image img = orig.getScaledInstance(64, 64, Image.SCALE_FAST);
                    foto.setIcon(new ImageIcon(img));
                } else {
                    foto.setText("?");
                }
            } catch (IOException ex) {
                foto.setText("?");
            }
        } else {
            foto.setText("?");
        }
        t.add(foto, BorderLayout.WEST);

        //Se cargan tambien los datos de la preview seleccionada
        JLabel lbl = new JLabel(p.getOrden() + "   " + (p.getPie() == null ? "" : p.getPie()));
        lbl.setFont(new Font("HomepageBaukasten Book", 0, 12));
        t.add(lbl, BorderLayout.CENTER);

        final Preview sel = p;
        t.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                marcar(t, sel);
            }
        });

        return t;
    }

    //Seleccion de casilla con preview
    private void marcar(JPanel t, Preview p) {
        if (tarjetaMarcada != null) {
            tarjetaMarcada.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(229, 229, 229), 1, true),
                    BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        }
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(51, 0, 204), 2, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        tarjetaMarcada = t;
        previewSeleccionada = p.getIdPreview();
        txtPieFoto.setText(p.getPie() == null ? "" : p.getPie());
        txtOrden.setText(String.valueOf(p.getOrden()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new PanelImagen("/imagenes/gaviota.jpg");
        panelSeleccionada = new javax.swing.JPanel();
        panelNueva = new javax.swing.JPanel();
        btnAniadir = new javax.swing.JButton();
        lblNova = new javax.swing.JLabel();
        lblInstrucc = new javax.swing.JLabel();
        cmbSesionCliente = new javax.swing.JComboBox<>();
        btnElegirNuevaPreview = new javax.swing.JButton();
        lblPie = new javax.swing.JLabel();
        txtPieFoto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtOrden = new javax.swing.JTextField();
        btnMenos = new javax.swing.JButton();
        btnMas = new javax.swing.JToggleButton();
        btnModificarPreview = new javax.swing.JButton();
        panelSelector = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCantidadPreview = new javax.swing.JTextField();
        btnEliminarPreview = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout panelNuevaLayout = new javax.swing.GroupLayout(panelNueva);
        panelNueva.setLayout(panelNuevaLayout);
        panelNuevaLayout.setHorizontalGroup(
            panelNuevaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelNuevaLayout.setVerticalGroup(
            panelNuevaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        btnAniadir.setBackground(new java.awt.Color(51, 0, 204));
        btnAniadir.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnAniadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAniadir.setText("Añadir Preview");
        btnAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniadirActionPerformed(evt);
            }
        });

        lblNova.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 18)); // NOI18N
        lblNova.setText("N U E V A");

        lblInstrucc.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblInstrucc.setText("Añade una preview a la sesión");

        cmbSesionCliente.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        cmbSesionCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSesionClienteActionPerformed(evt);
            }
        });

        btnElegirNuevaPreview.setBackground(new java.awt.Color(204, 204, 255));
        btnElegirNuevaPreview.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnElegirNuevaPreview.setForeground(new java.awt.Color(0, 51, 255));
        btnElegirNuevaPreview.setText("Elegir imagen");
        btnElegirNuevaPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElegirNuevaPreviewActionPerformed(evt);
            }
        });

        lblPie.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        lblPie.setText("Pie de foto");

        txtPieFoto.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N

        jLabel5.setText("Orden");

        txtOrden.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOrden.setText("-");

        btnMenos.setBackground(new java.awt.Color(204, 204, 204));
        btnMenos.setText("-");
        btnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosActionPerformed(evt);
            }
        });

        btnMas.setBackground(new java.awt.Color(204, 204, 204));
        btnMas.setText("+");
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });

        btnModificarPreview.setBackground(new java.awt.Color(204, 204, 204));
        btnModificarPreview.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnModificarPreview.setText("Modificar datos Preview");
        btnModificarPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPreviewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSeleccionadaLayout = new javax.swing.GroupLayout(panelSeleccionada);
        panelSeleccionada.setLayout(panelSeleccionadaLayout);
        panelSeleccionadaLayout.setHorizontalGroup(
            panelSeleccionadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionadaLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(panelSeleccionadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelSeleccionadaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addGroup(panelSeleccionadaLayout.createSequentialGroup()
                        .addComponent(btnMenos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMas))
                    .addComponent(lblPie)
                    .addComponent(lblInstrucc)
                    .addComponent(lblNova)
                    .addComponent(panelNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAniadir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbSesionCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnElegirNuevaPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPieFoto)
                    .addComponent(btnModificarPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelSeleccionadaLayout.setVerticalGroup(
            panelSeleccionadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionadaLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblNova)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInstrucc)
                .addGap(18, 18, 18)
                .addComponent(cmbSesionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelNueva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnElegirNuevaPreview)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAniadir)
                .addGap(72, 72, 72)
                .addComponent(lblPie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPieFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSeleccionadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenos)
                    .addComponent(btnMas))
                .addGap(18, 18, 18)
                .addComponent(btnModificarPreview)
                .addGap(22, 22, 22))
        );

        panelSelector.setPreferredSize(new java.awt.Dimension(296, 250));

        jLabel2.setFont(new java.awt.Font("HomepageBaukasten Bold", 0, 18)); // NOI18N
        jLabel2.setText("P R E V I E W S");

        txtCantidadPreview.setBackground(new java.awt.Color(204, 204, 255));
        txtCantidadPreview.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        txtCantidadPreview.setForeground(new java.awt.Color(0, 0, 255));
        txtCantidadPreview.setText("5 Previews");

        btnEliminarPreview.setBackground(new java.awt.Color(255, 204, 204));
        btnEliminarPreview.setFont(new java.awt.Font("HomepageBaukasten Book", 0, 12)); // NOI18N
        btnEliminarPreview.setForeground(new java.awt.Color(204, 0, 0));
        btnEliminarPreview.setText("Eliminar");
        btnEliminarPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPreviewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout panelSelectorLayout = new javax.swing.GroupLayout(panelSelector);
        panelSelector.setLayout(panelSelectorLayout);
        panelSelectorLayout.setHorizontalGroup(
            panelSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSelectorLayout.createSequentialGroup()
                .addGroup(panelSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelSelectorLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelSelectorLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(panelSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                            .addGroup(panelSelectorLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCantidadPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20))
        );
        panelSelectorLayout.setVerticalGroup(
            panelSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectorLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidadPreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarPreview)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(panelSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelSeleccionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                .addContainerGap(52, Short.MAX_VALUE))
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

    private void cmbSesionClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSesionClienteActionPerformed
        controladorPrincipal.cargaPreviews(this);
    }//GEN-LAST:event_cmbSesionClienteActionPerformed

    private void btnEliminarPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPreviewActionPerformed
        controladorPrincipal.eliminarPreview(this);
    }//GEN-LAST:event_btnEliminarPreviewActionPerformed

    private void btnModificarPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPreviewActionPerformed
        controladorPrincipal.modificarDatosPreviews(this);
    }//GEN-LAST:event_btnModificarPreviewActionPerformed

    //Boton para sumar o restar en el orden
    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
        int orden = Integer.parseInt(txtOrden.getText());
        orden++;
        txtOrden.setText(String.valueOf(orden));
    }//GEN-LAST:event_btnMasActionPerformed

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
        int orden = Integer.parseInt(txtOrden.getText());
        orden--;
        txtOrden.setText(String.valueOf(orden));
    }//GEN-LAST:event_btnMenosActionPerformed

    //Al pulsar el boton de elegir nueva ruta se abre un selector de archivos para escogerla en el dispositivo
    private void btnElegirNuevaPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElegirNuevaPreviewActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Imagenes", "jpg", "jpeg", "png"));

        //Se carga la ultima carpeta a la que se ha viajado para cargar una preview
        String ultimaCarpeta = controlador.Preferencias.cargar("ultima_carpeta_preview", null);
        if (ultimaCarpeta != null) {
            File dir = new File(ultimaCarpeta);
            if (dir.exists() && dir.isDirectory()) {
                chooser.setCurrentDirectory(dir);
            }
        }

        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File seleccionado = chooser.getSelectedFile();
        rutaNueva = seleccionado.getAbsolutePath();

        //Se guarda en la BD de preferencias la ruta de la carpeta escogida para la siguiente vez
        controlador.Preferencias.guardar("ultima_carpeta_preview",
                seleccionado.getParent());

        //Carga la imagen y la ajusta al panel seleccionado
        try {
            BufferedImage orig = ImageIO.read(seleccionado);
            Image img = orig.getScaledInstance(
                    panelNueva.getWidth(), panelNueva.getHeight(), Image.SCALE_FAST);
            JLabel lbl = new JLabel(new ImageIcon(img));
            panelNueva.removeAll();
            panelNueva.setLayout(new BorderLayout());
            panelNueva.add(lbl, BorderLayout.CENTER);
            panelNueva.revalidate();
            panelNueva.repaint();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo leer la imagen");
        }
    }//GEN-LAST:event_btnElegirNuevaPreviewActionPerformed

    //Al pulsar el boton de aniadir se empuja esta imagen a la base de datos
    private void btnAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniadirActionPerformed
        controladorPrincipal.insertarPreview(this);
    }//GEN-LAST:event_btnAniadirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAniadir;
    private javax.swing.JButton btnElegirNuevaPreview;
    private javax.swing.JButton btnEliminarPreview;
    private javax.swing.JToggleButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnModificarPreview;
    private javax.swing.JComboBox<FechaSesion> cmbSesionCliente;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInstrucc;
    private javax.swing.JLabel lblNova;
    private javax.swing.JLabel lblPie;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelNueva;
    private javax.swing.JPanel panelSeleccionada;
    private javax.swing.JPanel panelSelector;
    private javax.swing.JTextField txtCantidadPreview;
    private javax.swing.JTextField txtOrden;
    private javax.swing.JTextField txtPieFoto;
    // End of variables declaration//GEN-END:variables
}

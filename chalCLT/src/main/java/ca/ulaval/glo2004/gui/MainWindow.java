/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.domain.AccessoireDTO;
import ca.ulaval.glo2004.domain.AccessoireType;
import ca.ulaval.glo2004.domain.ChaletControleur;
import ca.ulaval.glo2004.domain.ChaletDTO;
import ca.ulaval.glo2004.domain.Face;
import ca.ulaval.glo2004.domain.GestionnaireSauvegarde;
import java.util.List;
import java.util.UUID;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;




public class MainWindow extends javax.swing.JPanel {

    public ChaletControleur chaletControleur;
    public GestionnaireSauvegarde gestionnaireSauvegarde;
    public Vue vueActuelle;
    public boolean afficherToit = true;
    public boolean afficherGrille = false;
    
    private DefaultListModel dListPorte = new DefaultListModel();
    private DefaultListModel dListFenetre = new DefaultListModel();
    private boolean modeModification = false;
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        
        //setDarkMode();
        
        chaletControleur = new ChaletControleur();
        vueActuelle = Vue.DESSUS;
        initComponents();
        jListPortes.setModel(dListPorte);
        jListFenetres.setModel(dListFenetre);
        getData();
    }
    
    public void getData(){
        /* Data pour les murs/chalet */
        ChaletDTO chaletDTO = chaletControleur.getChaletDTO();
        double grosseurCellule = chaletControleur.getGrosseurCellule();
        
        jFormattedTextFieldMurHauteur.setValue(chaletDTO.hauteur);
        jFormattedTextFieldMurLargeur.setValue(chaletDTO.largeur);
        jFormattedTextFieldMurProfondeur.setValue(chaletDTO.profondeur);
        jSliderAngle.setValue((int)chaletDTO.angleToit);
        jFormattedTextFieldDistanceAccess.setValue(chaletDTO.distanceMinAccessoire);
        jFormattedTextFieldEpaisseurMur.setValue(chaletDTO.epaisseur);
        jFormattedTextFieldSuppRain.setValue((int)chaletDTO.distanceRainure);
        jFormattedTextFieldSuppRainFraction.setText(convertDecimalEnFraction(chaletDTO.distanceRainure));
        jFormattedTextFieldGrosseurCellule.setValue(grosseurCellule);
        
        // Remove the action listener temporarily
        ActionListener[] listeners = jComboBoxOrientation.getActionListeners();
        for (ActionListener listener : listeners) {
        jComboBoxOrientation.removeActionListener(listener);
}

        
        jComboBoxOrientation.setSelectedItem(chaletDTO.toitDTO.orientation.toString());
        
        // Add back the action listeners
        for (ActionListener listener : listeners) {
        jComboBoxOrientation.addActionListener(listener);
}
    }
    
    public void setdListeAccessoire(){
        dListPorte.clear();
        dListFenetre.clear();
        if(vueActuelle.toString()!="DESSUS"){
            List<AccessoireDTO> accessoiresList = chaletControleur.getAccessoiresParMur(Face.valueOf(vueActuelle.toString()));
            int i = 1;
            for(AccessoireDTO a: accessoiresList)
            {
                if(a.accType == AccessoireType.PORTE)
                    dListPorte.addElement("ID:(" +a.id+")  Porte " + i + ":  X:" + a.positionX + ",  Y:" + a.positionY);
                else
                    dListFenetre.addElement("ID:(" +a.id+")  Fenetre " + i + ":  X:" + a.positionX + ",  Y:" + a.positionY);
                i++;
            }
        }
    }
    
    ///Si on veut implementer un Darkmode ???    
    private void setDarkMode(){

        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Panel.foreground", Color.LIGHT_GRAY);

        UIManager.put("Label.foreground", Color.LIGHT_GRAY);
        UIManager.put("TextField.background", Color.DARK_GRAY);
        UIManager.put("TextField.foreground", Color.LIGHT_GRAY);
        
        UIManager.put("TextArea.background", Color.DARK_GRAY);
        UIManager.put("TextArea.foreground", Color.LIGHT_GRAY);
        
        UIManager.put("JMenuBar.background", Color.DARK_GRAY);
        UIManager.put("JMenuBar.background", Color.LIGHT_GRAY);
        
        UIManager.put("FormattedTextField.background", Color.DARK_GRAY);
        UIManager.put("FormattedTextField.foreground", Color.LIGHT_GRAY);
        
        UIManager.put("Slider.background", Color.DARK_GRAY);
        UIManager.put("Slider.foreground", Color.LIGHT_GRAY);
        
        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.LIGHT_GRAY);
        UIManager.put("Button.select", Color.LIGHT_GRAY); // Color when the button is selected
    
        UIManager.put("TabbedPane.background", Color.DARK_GRAY);
        UIManager.put("TabbedPane.foreground", Color.LIGHT_GRAY);
        UIManager.put("TabbedPane.selected", Color.LIGHT_GRAY); // Color of the selected tab
        UIManager.put("TabbedPane.unselected", Color.DARK_GRAY); // Color of unselected tabs
        
        
    }
    
   static private String convertDecimalEnFraction(double x)
    {
        if (x < 0){
        return "-" + convertDecimalEnFraction(-x);
    }
    double tolerance = 1.0E-6;
    double h1=1; double h2=0;
    double k1=0; double k2=1;
    double b = x;
    do {
        double a = Math.floor(b);
        double aux = h1; h1 = a*h1+h2; h2 = aux;
        aux = k1; k1 = a*k1+k2; k2 = aux;
        b = 1/(b-a);
    } while (Math.abs(x-h1/k1) > x*tolerance);

    return (int)h1+"/"+(int)k1;
        
    }
    
    //Getter pour MainFrame
    public JFrame getMainFrame(){
        return MainFrame;
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainFrame = new javax.swing.JFrame();
        jMainPanel = new javax.swing.JPanel();
        jPanelGraphicsParent = new GestionTooltip();
        jPanelGraphics = new ca.ulaval.glo2004.gui.DrawingPanel(this);
        jPanelFormulaires = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMurs = new javax.swing.JPanel();
        jLabelMurLargeur = new javax.swing.JLabel();
        jFormattedTextFieldMurLargeur = new javax.swing.JFormattedTextField();
        jFormattedTextFieldMurHauteur = new javax.swing.JFormattedTextField();
        jFormattedTextFieldMurProfondeur = new javax.swing.JFormattedTextField();
        jLabelMurHauteurFraction = new javax.swing.JLabel();
        jLabelMurProfondeur = new javax.swing.JLabel();
        jButtonSauvegarderMurs = new javax.swing.JButton();
        jLabelMurLargeurFraction = new javax.swing.JLabel();
        jLabelMurHauteur = new javax.swing.JLabel();
        jLabelMurProfondeurFraction = new javax.swing.JLabel();
        jFormattedTextFieldMurLargeurFraction = new javax.swing.JTextField();
        jFormattedTextFieldMurHauteurFraction = new javax.swing.JTextField();
        jFormattedTextFieldMurProfondeurFraction = new javax.swing.JTextField();
        jPanelAccessoires = new javax.swing.JPanel();
        jLabelPortes = new javax.swing.JLabel();
        jLabelFenetres = new javax.swing.JLabel();
        jButtonAddPorte = new javax.swing.JButton();
        jButtonSupPorte = new javax.swing.JButton();
        jButtonAddFen = new javax.swing.JButton();
        jButtonSupFen = new javax.swing.JButton();
        jButtonModPorte = new javax.swing.JButton();
        jScrollPaneFenetres = new javax.swing.JScrollPane();
        jListFenetres = new javax.swing.JList<>();
        jScrollPanePortes = new javax.swing.JScrollPane();
        jListPortes = new javax.swing.JList<>();
        jButtonModFenetre = new javax.swing.JButton();
        jPanelDimensionsFenetres = new javax.swing.JPanel();
        jButtonEntrerFen = new javax.swing.JButton();
        jLabelHauteurFenetre = new javax.swing.JLabel();
        jLabelLargeurFenetre = new javax.swing.JLabel();
        jLabelPositionXFenetre = new javax.swing.JLabel();
        jLabelPositionYFenetre = new javax.swing.JLabel();
        jTextFieldLargeurFenetrePouce = new javax.swing.JTextField();
        jTextFieldHauteurFenetrePouce = new javax.swing.JTextField();
        jTextFieldPositionXFenetrePouce = new javax.swing.JTextField();
        jTextFieldPositionYFenetrePouce = new javax.swing.JTextField();
        jLabelHauteurFenetre1 = new javax.swing.JLabel();
        jLabelHauteurFenetre2 = new javax.swing.JLabel();
        jLabelHauteurFenetre3 = new javax.swing.JLabel();
        jLabelHauteurFenetre4 = new javax.swing.JLabel();
        jTextFieldHauteurFenetreFraction = new javax.swing.JTextField();
        jTextFieldLargeurFenetreFraction = new javax.swing.JTextField();
        jTextFieldPositionXFenetreFraction = new javax.swing.JTextField();
        jTextFieldPositionYFenetreFraction = new javax.swing.JTextField();
        jPanelDimensionsPortes = new javax.swing.JPanel();
        jButtonEntrerPorte = new javax.swing.JButton();
        jTextFieldLargeurPorteFraction = new javax.swing.JTextField();
        jTextFieldHauteurPortePouce = new javax.swing.JTextField();
        jLabelHauteurPorte = new javax.swing.JLabel();
        jLabelLargeurPorte = new javax.swing.JLabel();
        jTextFieldPositionXPortePouce = new javax.swing.JTextField();
        jLabelPositionXPorte = new javax.swing.JLabel();
        jTextFieldHauteurPorteFraction = new javax.swing.JTextField();
        jTextFieldLargeurPortePouce = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldPositionXPorteFraction = new javax.swing.JTextField();
        jPanelToit = new javax.swing.JPanel();
        jLabelOrientation = new javax.swing.JLabel();
        jLabelAngle = new javax.swing.JLabel();
        jComboBoxOrientation = new javax.swing.JComboBox<>();
        jSliderAngle = new javax.swing.JSlider();
        jFormattedTextFieldAngle = new javax.swing.JFormattedTextField();
        jPanelConfiguration = new javax.swing.JPanel();
        jLabelDistanceAccess = new javax.swing.JLabel();
        jLabelDistanceSuppRain = new javax.swing.JLabel();
        jLabelEpaisseurMur = new javax.swing.JLabel();
        jFormattedTextFieldDistanceAccess = new javax.swing.JFormattedTextField();
        jFormattedTextFieldSuppRain = new javax.swing.JFormattedTextField();
        jFormattedTextFieldGrosseurCellule = new javax.swing.JFormattedTextField();
        jButtonSauvegarderConfig = new javax.swing.JButton();
        jButtonResetConfig = new javax.swing.JButton();
        jFormattedTextFieldDistanceAccessFraction = new javax.swing.JTextField();
        jLabelDistanceAccess1 = new javax.swing.JLabel();
        jLabelDistanceAccess2 = new javax.swing.JLabel();
        jFormattedTextFieldSuppRainFraction = new javax.swing.JTextField();
        jLabelDistanceAccess3 = new javax.swing.JLabel();
        jFormattedTextFieldEpaisseurMurFraction = new javax.swing.JTextField();
        jLabelGrosseurCellules = new javax.swing.JLabel();
        jFormattedTextFieldEpaisseurMur = new javax.swing.JFormattedTextField();
        jLabelDistanceAccess4 = new javax.swing.JLabel();
        jFormattedTextFieldGrosseurCelluleFraction = new javax.swing.JTextField();
        jMenuBarFile = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExport = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSauvegarderChalet = new javax.swing.JMenuItem();
        jMenuItemChargerChalet = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItemTooltip = new javax.swing.JMenuItem();
        Edit_jMenu = new javax.swing.JMenu();
        Undo_jMenuItem = new javax.swing.JMenuItem();
        Redo_jMenuItem = new javax.swing.JMenuItem();
        jMenuVue = new javax.swing.JMenu();
        jMenuItemVue1 = new javax.swing.JMenuItem();
        jMenuItemVue2 = new javax.swing.JMenuItem();
        jMenuItemVue3 = new javax.swing.JMenuItem();
        jMenuItemVue4 = new javax.swing.JMenuItem();
        jMenuItemVue5 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItemAfficherToit = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemAfficherGrille = new javax.swing.JCheckBoxMenuItem();

        MainFrame.setTitle("ChalCLT");
        MainFrame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MainFrameKeyPressed(evt);
            }
        });

        jMainPanel.setForeground(new java.awt.Color(60, 63, 65));
        jMainPanel.setLayout(new java.awt.BorderLayout());

        jPanelGraphicsParent.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelGraphicsParent.setLayout(new java.awt.BorderLayout());

        jPanelGraphics.setMinimumSize(new java.awt.Dimension(500, 500));
        jPanelGraphics.setName(""); // NOI18N
        jPanelGraphics.setOpaque(false);
        jPanelGraphics.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanelGraphics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelGraphicsMouseEntered(evt);
            }
        });
        jPanelGraphics.setLayout(new java.awt.BorderLayout());
        jPanelGraphicsParent.add(jPanelGraphics, java.awt.BorderLayout.CENTER);

        jMainPanel.add(jPanelGraphicsParent, java.awt.BorderLayout.CENTER);

        jPanelFormulaires.setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(98, 90));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(450, 799));

        jPanelMurs.setPreferredSize(new java.awt.Dimension(189, 560));

        jLabelMurLargeur.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelMurLargeur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMurLargeur.setText("Largeur");

        jFormattedTextFieldMurLargeur.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        jFormattedTextFieldMurLargeur.setName(""); // NOI18N

        jFormattedTextFieldMurHauteur.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        jFormattedTextFieldMurHauteur.setName(""); // NOI18N

        jFormattedTextFieldMurProfondeur.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        jFormattedTextFieldMurProfondeur.setName(""); // NOI18N

        jLabelMurHauteurFraction.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelMurHauteurFraction.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMurHauteurFraction.setText("Fraction:");

        jLabelMurProfondeur.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelMurProfondeur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMurProfondeur.setText("Profondeur");

        jButtonSauvegarderMurs.setText("Sauvegarder");
        jButtonSauvegarderMurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSauvegarderMursActionPerformed(evt);
            }
        });

        jLabelMurLargeurFraction.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelMurLargeurFraction.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMurLargeurFraction.setText("Fraction:");

        jLabelMurHauteur.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelMurHauteur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMurHauteur.setText("Hauteur");

        jLabelMurProfondeurFraction.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelMurProfondeurFraction.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMurProfondeurFraction.setText("Fraction:");

        jFormattedTextFieldMurLargeurFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldMurLargeurFraction.setText("(Opt.)");

        jFormattedTextFieldMurHauteurFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldMurHauteurFraction.setText("(Opt.)");

        jFormattedTextFieldMurProfondeurFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldMurProfondeurFraction.setText("(Opt.)");

        javax.swing.GroupLayout jPanelMursLayout = new javax.swing.GroupLayout(jPanelMurs);
        jPanelMurs.setLayout(jPanelMursLayout);
        jPanelMursLayout.setHorizontalGroup(
            jPanelMursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMursLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanelMursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSauvegarderMurs, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelMursLayout.createSequentialGroup()
                            .addComponent(jFormattedTextFieldMurLargeur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelMurLargeurFraction)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextFieldMurLargeurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(70, 70, 70))
                        .addComponent(jLabelMurLargeur, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelMurHauteur, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelMursLayout.createSequentialGroup()
                            .addComponent(jFormattedTextFieldMurProfondeur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelMurProfondeurFraction)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFormattedTextFieldMurProfondeurFraction))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelMursLayout.createSequentialGroup()
                            .addComponent(jFormattedTextFieldMurHauteur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelMurHauteurFraction)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jFormattedTextFieldMurHauteurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelMurProfondeur, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanelMursLayout.setVerticalGroup(
            jPanelMursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMursLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabelMurLargeur)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldMurLargeur, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMurLargeurFraction)
                    .addComponent(jFormattedTextFieldMurLargeurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelMurHauteur)
                .addGap(7, 7, 7)
                .addGroup(jPanelMursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldMurHauteur, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMurHauteurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldMurHauteurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelMurProfondeur)
                .addGap(4, 4, 4)
                .addGroup(jPanelMursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldMurProfondeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMurProfondeurFraction)
                    .addComponent(jFormattedTextFieldMurProfondeurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonSauvegarderMurs)
                .addContainerGap(560, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Murs", jPanelMurs);

        jLabelPortes.setText("Portes");

        jLabelFenetres.setText("Fenêtres");

        jButtonAddPorte.setText("Ajouter ");
        jButtonAddPorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddPorteActionPerformed(evt);
            }
        });

        jButtonSupPorte.setText("Supprimer");
        jButtonSupPorte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSupPorteMouseClicked(evt);
            }
        });
        jButtonSupPorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupPorteActionPerformed(evt);
            }
        });

        jButtonAddFen.setText("Ajouter");
        jButtonAddFen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddFenActionPerformed(evt);
            }
        });

        jButtonSupFen.setText("Supprimer");
        jButtonSupFen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSupFenMouseClicked(evt);
            }
        });

        jButtonModPorte.setText("Modifier");
        jButtonModPorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModPorteActionPerformed(evt);
            }
        });

        jListFenetres.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPaneFenetres.setViewportView(jListFenetres);

        jListPortes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPanePortes.setViewportView(jListPortes);

        jButtonModFenetre.setText("Modifier");
        jButtonModFenetre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModFenetreActionPerformed(evt);
            }
        });

        jPanelDimensionsFenetres.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonEntrerFen.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButtonEntrerFen.setText("Entrer");
        jButtonEntrerFen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntrerFenActionPerformed(evt);
            }
        });

        jLabelHauteurFenetre.setText("Hauteur:");

        jLabelLargeurFenetre.setText("Largeur:");

        jLabelPositionXFenetre.setText("Position X:");

        jLabelPositionYFenetre.setText("Position Y:");

        jTextFieldLargeurFenetrePouce.setToolTipText("Largeur");
        jTextFieldLargeurFenetrePouce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLargeurFenetrePouceActionPerformed(evt);
            }
        });

        jTextFieldHauteurFenetrePouce.setToolTipText("Hauteur");
        jTextFieldHauteurFenetrePouce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHauteurFenetrePouceActionPerformed(evt);
            }
        });

        jTextFieldPositionXFenetrePouce.setToolTipText("Position X");

        jTextFieldPositionYFenetrePouce.setToolTipText("Position Y");

        jLabelHauteurFenetre1.setText("Fraction:");

        jLabelHauteurFenetre2.setText("Fraction:");

        jLabelHauteurFenetre3.setText("Fraction:");

        jLabelHauteurFenetre4.setText("Fraction:");

        jTextFieldHauteurFenetreFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldHauteurFenetreFraction.setText("(Opt.)");
        jTextFieldHauteurFenetreFraction.setToolTipText("Hauteur");

        jTextFieldLargeurFenetreFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldLargeurFenetreFraction.setText("(Opt.)");
        jTextFieldLargeurFenetreFraction.setToolTipText("Largeur");

        jTextFieldPositionXFenetreFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldPositionXFenetreFraction.setText("(Opt.)");
        jTextFieldPositionXFenetreFraction.setToolTipText("Position X");

        jTextFieldPositionYFenetreFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldPositionYFenetreFraction.setText("(Opt.)");
        jTextFieldPositionYFenetreFraction.setToolTipText("Position Y");

        javax.swing.GroupLayout jPanelDimensionsFenetresLayout = new javax.swing.GroupLayout(jPanelDimensionsFenetres);
        jPanelDimensionsFenetres.setLayout(jPanelDimensionsFenetresLayout);
        jPanelDimensionsFenetresLayout.setHorizontalGroup(
            jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDimensionsFenetresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPositionYFenetre)
                    .addComponent(jLabelHauteurFenetre)
                    .addComponent(jLabelLargeurFenetre)
                    .addComponent(jLabelPositionXFenetre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDimensionsFenetresLayout.createSequentialGroup()
                        .addComponent(jTextFieldPositionYFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelHauteurFenetre3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPositionYFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEntrerFen, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDimensionsFenetresLayout.createSequentialGroup()
                        .addComponent(jTextFieldLargeurFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelHauteurFenetre2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldLargeurFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDimensionsFenetresLayout.createSequentialGroup()
                        .addComponent(jTextFieldPositionXFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelHauteurFenetre4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPositionXFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDimensionsFenetresLayout.createSequentialGroup()
                        .addComponent(jTextFieldHauteurFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelHauteurFenetre1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldHauteurFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanelDimensionsFenetresLayout.setVerticalGroup(
            jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDimensionsFenetresLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldHauteurFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHauteurFenetre)
                    .addComponent(jLabelHauteurFenetre1)
                    .addComponent(jTextFieldHauteurFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLargeurFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLargeurFenetre)
                    .addComponent(jLabelHauteurFenetre2)
                    .addComponent(jTextFieldLargeurFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPositionXFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPositionXFenetre)
                    .addComponent(jLabelHauteurFenetre4)
                    .addComponent(jTextFieldPositionXFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDimensionsFenetresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPositionYFenetrePouce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPositionYFenetre)
                    .addComponent(jLabelHauteurFenetre3)
                    .addComponent(jTextFieldPositionYFenetreFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEntrerFen))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelDimensionsPortes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonEntrerPorte.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButtonEntrerPorte.setText("Entrer");
        jButtonEntrerPorte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonEntrerPorteMouseClicked(evt);
            }
        });
        jButtonEntrerPorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntrerPorteActionPerformed(evt);
            }
        });

        jTextFieldLargeurPorteFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldLargeurPorteFraction.setText("(Opt.)");
        jTextFieldLargeurPorteFraction.setToolTipText("Largeur");

        jTextFieldHauteurPortePouce.setToolTipText("Hauteur");

        jLabelHauteurPorte.setText("Hauteur:");

        jLabelLargeurPorte.setText("Largeur:");

        jTextFieldPositionXPortePouce.setToolTipText("PositionX");

        jLabelPositionXPorte.setText("Position X:");

        jTextFieldHauteurPorteFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldHauteurPorteFraction.setText("(Opt.)");
        jTextFieldHauteurPorteFraction.setToolTipText("Hauteur");

        jTextFieldLargeurPortePouce.setToolTipText("Largeur");
        jTextFieldLargeurPortePouce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLargeurPortePouceActionPerformed(evt);
            }
        });

        jLabel1.setText("Fraction:");

        jLabel2.setText("Fraction:");

        jLabel3.setText("Fraction:");

        jTextFieldPositionXPorteFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldPositionXPorteFraction.setText("(Opt.)");
        jTextFieldPositionXPorteFraction.setToolTipText("PositionX");

        javax.swing.GroupLayout jPanelDimensionsPortesLayout = new javax.swing.GroupLayout(jPanelDimensionsPortes);
        jPanelDimensionsPortes.setLayout(jPanelDimensionsPortesLayout);
        jPanelDimensionsPortesLayout.setHorizontalGroup(
            jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDimensionsPortesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLargeurPorte)
                    .addComponent(jLabelHauteurPorte)
                    .addComponent(jLabelPositionXPorte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextFieldHauteurPortePouce, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPositionXPortePouce, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(jTextFieldLargeurPortePouce, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldPositionXPorteFraction)
                    .addComponent(jTextFieldHauteurPorteFraction, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(jTextFieldLargeurPorteFraction))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEntrerPorte, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDimensionsPortesLayout.setVerticalGroup(
            jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDimensionsPortesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHauteurPorte)
                    .addComponent(jTextFieldHauteurPortePouce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHauteurPorteFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(3, 3, 3)
                .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLargeurPorte)
                    .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldLargeurPorteFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldLargeurPortePouce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(22, 22, 22)
                .addGroup(jPanelDimensionsPortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPositionXPortePouce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPositionXPorte)
                    .addComponent(jButtonEntrerPorte)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldPositionXPorteFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanelAccessoiresLayout = new javax.swing.GroupLayout(jPanelAccessoires);
        jPanelAccessoires.setLayout(jPanelAccessoiresLayout);
        jPanelAccessoiresLayout.setHorizontalGroup(
            jPanelAccessoiresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAccessoiresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAccessoiresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelDimensionsPortes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneFenetres)
                    .addGroup(jPanelAccessoiresLayout.createSequentialGroup()
                        .addComponent(jLabelFenetres)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAddFen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonModFenetre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSupFen))
                    .addGroup(jPanelAccessoiresLayout.createSequentialGroup()
                        .addComponent(jLabelPortes)
                        .addGap(24, 24, 24)
                        .addComponent(jButtonAddPorte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonModPorte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSupPorte))
                    .addComponent(jPanelDimensionsFenetres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanelAccessoiresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelAccessoiresLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPanePortes)
                    .addContainerGap()))
        );
        jPanelAccessoiresLayout.setVerticalGroup(
            jPanelAccessoiresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAccessoiresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAccessoiresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPortes)
                    .addComponent(jButtonAddPorte)
                    .addComponent(jButtonSupPorte)
                    .addComponent(jButtonModPorte))
                .addGap(124, 124, 124)
                .addComponent(jPanelDimensionsPortes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAccessoiresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFenetres)
                    .addComponent(jButtonAddFen)
                    .addComponent(jButtonModFenetre)
                    .addComponent(jButtonSupFen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneFenetres, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelDimensionsFenetres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
            .addGroup(jPanelAccessoiresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelAccessoiresLayout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addComponent(jScrollPanePortes, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(620, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Accessoires", jPanelAccessoires);

        jLabelOrientation.setText("Orientation");

        jLabelAngle.setText("Angle");

        jComboBoxOrientation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FACADE", "DROITE", "GAUCHE", "ARRIERE" }));
        jComboBoxOrientation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxOrientationActionPerformed(evt);
            }
        });

        jSliderAngle.setMajorTickSpacing(1);
        jSliderAngle.setMaximum(75);
        jSliderAngle.setMinimum(5);
        jSliderAngle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSliderAngle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderAngleStateChanged(evt);
            }
        });
        jSliderAngle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jSliderAngleMouseReleased(evt);
            }
        });

        jFormattedTextFieldAngle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldAngleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelToitLayout = new javax.swing.GroupLayout(jPanelToit);
        jPanelToit.setLayout(jPanelToitLayout);
        jPanelToitLayout.setHorizontalGroup(
            jPanelToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelToitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxOrientation, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelToitLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelOrientation)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelToitLayout.createSequentialGroup()
                        .addComponent(jSliderAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextFieldAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelToitLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabelAngle)))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanelToitLayout.setVerticalGroup(
            jPanelToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelToitLayout.createSequentialGroup()
                .addGroup(jPanelToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelToitLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabelOrientation))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelToitLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelAngle)))
                .addGroup(jPanelToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jComboBoxOrientation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSliderAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelToitLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jFormattedTextFieldAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(691, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Toit", jPanelToit);

        jLabelDistanceAccess.setText("Distance entre les accessoires");

        jLabelDistanceSuppRain.setText("Distance supplémentaire rainure");

        jLabelEpaisseurMur.setText("Épaisseur des murs");

        jButtonSauvegarderConfig.setText("Sauvegarder");
        jButtonSauvegarderConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSauvegarderConfigActionPerformed(evt);
            }
        });

        jButtonResetConfig.setText("Reset");
        jButtonResetConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetConfigActionPerformed(evt);
            }
        });

        jFormattedTextFieldDistanceAccessFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldDistanceAccessFraction.setText("(Opt.)");

        jLabelDistanceAccess1.setText("Fraction:");

        jLabelDistanceAccess2.setText("Fraction:");

        jFormattedTextFieldSuppRainFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldSuppRainFraction.setText("(Opt.)");

        jLabelDistanceAccess3.setText("Fraction:");

        jFormattedTextFieldEpaisseurMurFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldEpaisseurMurFraction.setText("(Opt.)");

        jLabelGrosseurCellules.setText("Grosseur des cellules");

        jFormattedTextFieldEpaisseurMur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldEpaisseurMurActionPerformed(evt);
            }
        });

        jLabelDistanceAccess4.setText("Fraction:");

        jFormattedTextFieldGrosseurCelluleFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldGrosseurCelluleFraction.setText("(Opt.)");

        javax.swing.GroupLayout jPanelConfigurationLayout = new javax.swing.GroupLayout(jPanelConfiguration);
        jPanelConfiguration.setLayout(jPanelConfigurationLayout);
        jPanelConfigurationLayout.setHorizontalGroup(
            jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfigurationLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelConfigurationLayout.createSequentialGroup()
                        .addComponent(jFormattedTextFieldGrosseurCellule, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDistanceAccess4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldGrosseurCelluleFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelConfigurationLayout.createSequentialGroup()
                        .addGroup(jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFormattedTextFieldSuppRain, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(jFormattedTextFieldEpaisseurMur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelConfigurationLayout.createSequentialGroup()
                                .addComponent(jLabelDistanceAccess1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldSuppRainFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelConfigurationLayout.createSequentialGroup()
                                .addComponent(jLabelDistanceAccess3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldEpaisseurMurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabelGrosseurCellules)
                    .addComponent(jButtonResetConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSauvegarderConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDistanceSuppRain)
                    .addComponent(jLabelDistanceAccess)
                    .addComponent(jLabelEpaisseurMur)
                    .addGroup(jPanelConfigurationLayout.createSequentialGroup()
                        .addComponent(jFormattedTextFieldDistanceAccess, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDistanceAccess2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDistanceAccessFraction, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanelConfigurationLayout.setVerticalGroup(
            jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfigurationLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabelDistanceAccess)
                .addGap(18, 18, 18)
                .addGroup(jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldDistanceAccess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldDistanceAccessFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDistanceAccess2))
                .addGap(22, 22, 22)
                .addComponent(jLabelDistanceSuppRain)
                .addGap(18, 18, 18)
                .addGroup(jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldSuppRain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDistanceAccess1)
                    .addComponent(jFormattedTextFieldSuppRainFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelEpaisseurMur)
                .addGap(18, 18, 18)
                .addGroup(jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDistanceAccess3)
                    .addComponent(jFormattedTextFieldEpaisseurMurFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldEpaisseurMur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelGrosseurCellules)
                .addGap(18, 18, 18)
                .addGroup(jPanelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldGrosseurCellule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDistanceAccess4)
                    .addComponent(jFormattedTextFieldGrosseurCelluleFraction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonSauvegarderConfig)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonResetConfig)
                .addContainerGap(385, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Configuration", jPanelConfiguration);

        jPanelFormulaires.add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        jMainPanel.add(jPanelFormulaires, java.awt.BorderLayout.EAST);

        jMenuFile.setText("File");

        jMenuItemExport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemExport.setText("Exporter STL");
        jMenuItemExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExport);
        jMenuFile.add(jSeparator2);

        jMenuItemSauvegarderChalet.setText("Sauvegarder Chalet");
        jMenuItemSauvegarderChalet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSauvegarderChaletActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSauvegarderChalet);

        jMenuItemChargerChalet.setText("Charger Chalet");
        jMenuItemChargerChalet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemChargerChaletActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemChargerChalet);
        jMenuFile.add(jSeparator3);

        jMenuItemTooltip.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemTooltip.setText("Tooltip");
        jMenuItemTooltip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTooltipActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemTooltip);

        jMenuBarFile.add(jMenuFile);

        Edit_jMenu.setText("Edit");

        Undo_jMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Undo_jMenuItem.setText("Undo");
        Undo_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Undo_jMenuItemActionPerformed(evt);
            }
        });
        Edit_jMenu.add(Undo_jMenuItem);

        Redo_jMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Redo_jMenuItem.setText("Redo");
        Redo_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Redo_jMenuItemActionPerformed(evt);
            }
        });
        Edit_jMenu.add(Redo_jMenuItem);

        jMenuBarFile.add(Edit_jMenu);

        jMenuVue.setText("Vues");
        jMenuVue.setToolTipText("");
        jMenuVue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuVueMouseEntered(evt);
            }
        });

        jMenuItemVue1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemVue1.setText("Vue du dessus");
        jMenuItemVue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVue1ActionPerformed(evt);
            }
        });
        jMenuVue.add(jMenuItemVue1);

        jMenuItemVue2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemVue2.setText("Vue de façade");
        jMenuItemVue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVue2ActionPerformed(evt);
            }
        });
        jMenuVue.add(jMenuItemVue2);

        jMenuItemVue3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemVue3.setText("Vue de arrière");
        jMenuItemVue3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVue3ActionPerformed(evt);
            }
        });
        jMenuVue.add(jMenuItemVue3);

        jMenuItemVue4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemVue4.setText("Vue de droite");
        jMenuItemVue4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVue4ActionPerformed(evt);
            }
        });
        jMenuVue.add(jMenuItemVue4);

        jMenuItemVue5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemVue5.setText("Vue de gauche");
        jMenuItemVue5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVue5ActionPerformed(evt);
            }
        });
        jMenuVue.add(jMenuItemVue5);
        jMenuVue.add(jSeparator1);

        jCheckBoxMenuItemAfficherToit.setSelected(true);
        jCheckBoxMenuItemAfficherToit.setText("Afficher le toit");
        jCheckBoxMenuItemAfficherToit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemAfficherToitActionPerformed(evt);
            }
        });
        jMenuVue.add(jCheckBoxMenuItemAfficherToit);

        jCheckBoxMenuItemAfficherGrille.setText("Afficher grille");
        jCheckBoxMenuItemAfficherGrille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemAfficherGrilleActionPerformed(evt);
            }
        });
        jMenuVue.add(jCheckBoxMenuItemAfficherGrille);

        jMenuBarFile.add(jMenuVue);

        MainFrame.setJMenuBar(jMenuBarFile);

        javax.swing.GroupLayout MainFrameLayout = new javax.swing.GroupLayout(MainFrame.getContentPane());
        MainFrame.getContentPane().setLayout(MainFrameLayout);
        MainFrameLayout.setHorizontalGroup(
            MainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                .addContainerGap())
        );
        MainFrameLayout.setVerticalGroup(
            MainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 704, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuVueMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuVueMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuVueMouseEntered

    private void jMenuItemVue4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVue4ActionPerformed
        vueActuelle = Vue.DROITE;
        setdListeAccessoire();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jMenuItemVue4ActionPerformed

    private void jMenuItemVue5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVue5ActionPerformed
        vueActuelle = Vue.GAUCHE;
        setdListeAccessoire();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jMenuItemVue5ActionPerformed

    private void jComboBoxOrientationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxOrientationActionPerformed
        try{
            Face orientation = Face.valueOf(jComboBoxOrientation.getSelectedItem().toString());
            chaletControleur.changerOrientationToit(orientation);
            
            chaletControleur.ajouterEtapeHistorique();
            jPanelGraphics.repaint(); 
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_jComboBoxOrientationActionPerformed

    private void jButtonModPorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModPorteActionPerformed
        modeModification = true;
    }//GEN-LAST:event_jButtonModPorteActionPerformed

    private void jButtonEntrerPorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntrerPorteActionPerformed
        Face murFace = Face.valueOf(vueActuelle.toString());
        double largeur;
        double hauteur;
        
        try{
            if(jTextFieldPositionXPortePouce.getText().contains("-") || jTextFieldPositionXPorteFraction.getText().contains("-") )
            {
                throw new Exception();
            }
            else if(jTextFieldHauteurPortePouce.getText().isEmpty() && jTextFieldHauteurPorteFraction.getText().equals("(Opt.)") && jTextFieldLargeurPortePouce.getText().isEmpty()&& jTextFieldLargeurPorteFraction.getText().equals("(Opt.)")){
                largeur = 38;
                hauteur = 88;
            }
            else{
                largeur = Double.parseDouble(jTextFieldLargeurPortePouce.getText());
                hauteur = Double.parseDouble(jTextFieldHauteurPortePouce.getText());
            }
            double positionX = Double.parseDouble(jTextFieldPositionXPortePouce.getText());
            
            if(!jTextFieldLargeurPorteFraction.getText().isEmpty() && !jTextFieldLargeurPorteFraction.getText().equals("(Opt.)"))
            {
                int largeurFractIndex = jTextFieldLargeurPorteFraction.getText().indexOf("/");
                largeur += Double.parseDouble(jTextFieldLargeurPorteFraction.getText().substring(0,largeurFractIndex)) / Double.parseDouble(jTextFieldLargeurPorteFraction.getText().substring(largeurFractIndex + 1));
            }
            if(!jTextFieldHauteurPorteFraction.getText().isEmpty() && !jTextFieldHauteurPorteFraction.getText().equals("(Opt.)"))
            {
                int hauteurFractIndex = jTextFieldHauteurPorteFraction.getText().indexOf("/");
                hauteur += Double.parseDouble(jTextFieldHauteurPorteFraction.getText().substring(0,hauteurFractIndex)) / Double.parseDouble(jTextFieldHauteurPorteFraction.getText().substring(hauteurFractIndex + 1));
            }
            if(!jTextFieldPositionXPorteFraction.getText().isEmpty() && !jTextFieldPositionXPorteFraction.getText().equals("(Opt.)"))
            {
                int positionXFractIndex = jTextFieldPositionXPorteFraction.getText().indexOf("/");
                positionX += Double.parseDouble(jTextFieldPositionXPorteFraction.getText().substring(0,positionXFractIndex)) / Double.parseDouble(jTextFieldPositionXPorteFraction.getText().substring(positionXFractIndex + 1));
            }

            
            if(modeModification)
            {
                int startIndex = jListPortes.getSelectedValue().indexOf("(");
                int endIndex = jListPortes.getSelectedValue().indexOf(")");
                UUID id = UUID.fromString(jListPortes.getSelectedValue().substring(startIndex+1,endIndex));
                chaletControleur.modifierAccessoire(id, positionX,0, hauteur, largeur, murFace);
                setdListeAccessoire();
            }
            else
            {
                AccessoireDTO porte = chaletControleur.creerPorte(positionX, largeur , hauteur, murFace);
                dListPorte.addElement("ID:(" +porte.id+")  Porte " + chaletControleur.getAccessoiresParMur(murFace).size() + ":  X:" + porte.positionX + ",  Y:" + porte.positionY);
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
        chaletControleur.ajouterEtapeHistorique();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jButtonEntrerPorteActionPerformed

    private void jButtonSupPorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupPorteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSupPorteActionPerformed

    private void jButtonAddPorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPorteActionPerformed
        modeModification = false;
    }//GEN-LAST:event_jButtonAddPorteActionPerformed

    private void jButtonModFenetreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModFenetreActionPerformed
        modeModification = true;
    }//GEN-LAST:event_jButtonModFenetreActionPerformed

    private void jMenuItemVue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVue1ActionPerformed
        vueActuelle = Vue.DESSUS;
        dListPorte.clear();
        dListFenetre.clear();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jMenuItemVue1ActionPerformed

    private void jMenuItemVue3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVue3ActionPerformed
        vueActuelle = Vue.ARRIERE;
        setdListeAccessoire();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jMenuItemVue3ActionPerformed

    private void jMenuItemVue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVue2ActionPerformed
        vueActuelle = Vue.FACADE;
        setdListeAccessoire();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jMenuItemVue2ActionPerformed

    private void jButtonEntrerPorteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEntrerPorteMouseClicked

    }//GEN-LAST:event_jButtonEntrerPorteMouseClicked

    private void jButtonSupPorteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSupPorteMouseClicked
        Face murFace = Face.valueOf(vueActuelle.toString());
        int startIndex = jListPortes.getSelectedValue().indexOf("(");
        int endIndex = jListPortes.getSelectedValue().indexOf(")");
        UUID id = UUID.fromString(jListPortes.getSelectedValue().substring(startIndex+1,endIndex));
        dListPorte.remove(jListPortes.getSelectedIndex());
        chaletControleur.supprimerAccessoire(id, murFace);
        
        chaletControleur.ajouterEtapeHistorique();
        jPanelGraphics.repaint();        
    }//GEN-LAST:event_jButtonSupPorteMouseClicked

    private void jButtonSupFenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSupFenMouseClicked
        Face murFace = Face.valueOf(vueActuelle.toString());
        int startIndex = jListFenetres.getSelectedValue().indexOf("(");
        int endIndex = jListFenetres.getSelectedValue().indexOf(")");
        UUID id = UUID.fromString(jListFenetres.getSelectedValue().substring(startIndex+1,endIndex));

        chaletControleur.supprimerAccessoire(id, murFace);
        dListFenetre.remove(jListFenetres.getSelectedIndex());
        
        chaletControleur.ajouterEtapeHistorique();
        jPanelGraphics.repaint();   
    }//GEN-LAST:event_jButtonSupFenMouseClicked

    private void jButtonAddFenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddFenActionPerformed
        modeModification = false;
    }//GEN-LAST:event_jButtonAddFenActionPerformed

    private void jTextFieldHauteurFenetrePouceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHauteurFenetrePouceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHauteurFenetrePouceActionPerformed

    private void jTextFieldLargeurFenetrePouceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLargeurFenetrePouceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLargeurFenetrePouceActionPerformed

    private void jButtonEntrerFenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntrerFenActionPerformed
        Face murFace = Face.valueOf(vueActuelle.toString());
        try {   
            if(jTextFieldPositionXFenetrePouce.getText().contains("-") || jTextFieldPositionXFenetreFraction.getText().contains("-") 
                || jTextFieldPositionYFenetrePouce.getText().contains("-")|| jTextFieldPositionYFenetreFraction.getText().contains("-") 
                || jTextFieldHauteurFenetrePouce.getText().contains("-") || jTextFieldHauteurFenetreFraction.getText().contains("-")
                || jTextFieldLargeurFenetrePouce.getText().contains("-") || jTextFieldLargeurFenetreFraction.getText().contains("-"))
            {
                throw new Exception();
            }
            
            double largeur = Double.parseDouble(jTextFieldLargeurFenetrePouce.getText());
            double hauteur = Double.parseDouble(jTextFieldHauteurFenetrePouce.getText());
            double positionX = Double.parseDouble(jTextFieldPositionXFenetrePouce.getText());
            double positionY = Double.parseDouble(jTextFieldPositionYFenetrePouce.getText());
            
            if(!jTextFieldLargeurFenetreFraction.getText().isEmpty() && !jTextFieldLargeurFenetreFraction.getText().equals("(Opt.)"))
            {
                int largeurFractIndex = jTextFieldLargeurFenetreFraction.getText().indexOf("/");
                largeur += Double.parseDouble(jTextFieldLargeurFenetreFraction.getText().substring(0,largeurFractIndex)) / Double.parseDouble(jTextFieldLargeurFenetreFraction.getText().substring(largeurFractIndex + 1));
            }
            if(!jTextFieldHauteurFenetreFraction.getText().isEmpty() && !jTextFieldHauteurFenetreFraction.getText().equals("(Opt.)"))
            {
                int hauteurFractIndex = jTextFieldHauteurFenetreFraction.getText().indexOf("/");
                hauteur += Double.parseDouble(jTextFieldHauteurFenetreFraction.getText().substring(0,hauteurFractIndex)) / Double.parseDouble(jTextFieldHauteurFenetreFraction.getText().substring(hauteurFractIndex + 1));
            }
            if(!jTextFieldPositionXFenetreFraction.getText().isEmpty() && !jTextFieldPositionXFenetreFraction.getText().equals("(Opt.)"))
            {
                int positionXFractIndex = jTextFieldPositionXFenetreFraction.getText().indexOf("/");
                positionX += Double.parseDouble(jTextFieldPositionXFenetreFraction.getText().substring(0,positionXFractIndex)) / Double.parseDouble(jTextFieldPositionXFenetreFraction.getText().substring(positionXFractIndex + 1));
            }
            if(!jTextFieldPositionYFenetreFraction.getText().isEmpty() && !jTextFieldPositionYFenetreFraction.getText().equals("(Opt.)"))
            {
                int positionYFractIndex = jTextFieldPositionYFenetreFraction.getText().indexOf("/");
                positionY += Double.parseDouble(jTextFieldPositionYFenetreFraction.getText().substring(0,positionYFractIndex)) / Double.parseDouble(jTextFieldPositionYFenetreFraction.getText().substring(positionYFractIndex + 1));
            }
            
            if(modeModification)
            {
                int startIndex = jListFenetres.getSelectedValue().indexOf("(");
                int endIndex = jListFenetres.getSelectedValue().indexOf(")");
                UUID id = UUID.fromString(jListFenetres.getSelectedValue().substring(startIndex+1,endIndex));
                chaletControleur.modifierAccessoire(id,positionX,positionY, hauteur, largeur, murFace);
                setdListeAccessoire();
            }
            else
            {
                AccessoireDTO fenetre = chaletControleur.creerFenetre(positionX,positionY, largeur, hauteur, murFace);
                dListFenetre.addElement("ID:(" +fenetre.id+")  Fenêtre " + chaletControleur.getAccessoiresParMur(murFace).size() + ":  X:" + fenetre.positionX + ",  Y:" + fenetre.positionY);
            }
        } catch(Exception e){
            System.out.println(e.toString());
        }
        
        chaletControleur.ajouterEtapeHistorique();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jButtonEntrerFenActionPerformed

    private void jSliderAngleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderAngleStateChanged
        try{
            chaletControleur.changerAngleToit(jSliderAngle.getValue());
            jFormattedTextFieldAngle.setValue(jSliderAngle.getValue());
            
            jPanelGraphics.repaint(); 
        }catch (Exception e){
            System.out.println(e.toString());
        }  
    }//GEN-LAST:event_jSliderAngleStateChanged

    private void jButtonSauvegarderConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSauvegarderConfigActionPerformed
        try{
            if(jFormattedTextFieldDistanceAccess.getText().contains("-") || jFormattedTextFieldDistanceAccessFraction.getText().contains("-") 
                || jFormattedTextFieldSuppRain.getText().contains("-")|| jFormattedTextFieldSuppRainFraction.getText().contains("-") 
                || jFormattedTextFieldGrosseurCellule.getText().contains("-") || jFormattedTextFieldEpaisseurMurFraction.getText().contains("-"))
            {
                throw new Exception();
            }
            double newDistanceAccessoire = Double.parseDouble(jFormattedTextFieldDistanceAccess.getValue().toString());
            double newDistanceRainure = Double.parseDouble(jFormattedTextFieldSuppRain.getValue().toString());
            double newEpaisseur = Double.parseDouble(jFormattedTextFieldEpaisseurMur.getValue().toString());
            double newGrosseurCellule = Double.parseDouble(jFormattedTextFieldGrosseurCellule.getValue().toString());
            
            if(!jFormattedTextFieldDistanceAccessFraction.getText().isEmpty() && !jFormattedTextFieldDistanceAccessFraction.getText().equals("(Opt.)"))
            {
                int DistanceAccessoireIndex = jFormattedTextFieldDistanceAccessFraction.getText().indexOf("/");
                newDistanceAccessoire += Double.parseDouble(jFormattedTextFieldDistanceAccessFraction.getText().substring(0,DistanceAccessoireIndex)) / Double.parseDouble(jFormattedTextFieldDistanceAccessFraction.getText().substring(DistanceAccessoireIndex + 1));
            }
            if(!jFormattedTextFieldSuppRainFraction.getText().isEmpty() && !jFormattedTextFieldSuppRainFraction.getText().equals("(Opt.)"))
            {
                int DistanceRainureIndex = jFormattedTextFieldSuppRainFraction.getText().indexOf("/");
                newDistanceRainure += Double.parseDouble(jFormattedTextFieldSuppRainFraction.getText().substring(0,DistanceRainureIndex)) / Double.parseDouble(jFormattedTextFieldSuppRainFraction.getText().substring(DistanceRainureIndex + 1));
            }
            if(!jFormattedTextFieldEpaisseurMurFraction.getText().isEmpty() && !jFormattedTextFieldEpaisseurMurFraction.getText().equals("(Opt.)"))
            {
                int EpaisseurIndex = jFormattedTextFieldEpaisseurMurFraction.getText().indexOf("/");
                newEpaisseur += Double.parseDouble(jFormattedTextFieldEpaisseurMurFraction.getText().substring(0,EpaisseurIndex)) / Double.parseDouble(jFormattedTextFieldEpaisseurMurFraction.getText().substring(EpaisseurIndex + 1));
            }
            
            if(!jFormattedTextFieldGrosseurCelluleFraction.getText().isEmpty() && !jFormattedTextFieldGrosseurCelluleFraction.getText().equals("(Opt.)"))
            {
                int GrosseurCelluleIndex = jFormattedTextFieldGrosseurCelluleFraction.getText().indexOf("/");
                newGrosseurCellule += Double.parseDouble(jFormattedTextFieldGrosseurCelluleFraction.getText().substring(0,GrosseurCelluleIndex)) / Double.parseDouble(jFormattedTextFieldGrosseurCelluleFraction.getText().substring(GrosseurCelluleIndex + 1));
            }
            
            chaletControleur.modifierConfiguration(newDistanceAccessoire, newDistanceRainure, newEpaisseur, newGrosseurCellule);
            ChaletDTO c = chaletControleur.getChaletDTO();
            chaletControleur.modifierDimensionChalet(c.hauteur, c.largeur, c.profondeur);
            
            chaletControleur.ajouterEtapeHistorique();
            jPanelGraphics.repaint(); 
        } catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_jButtonSauvegarderConfigActionPerformed

    private void jButtonSauvegarderMursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSauvegarderMursActionPerformed
        try{

            if(jFormattedTextFieldMurLargeur.getText().contains("-") || jFormattedTextFieldMurLargeurFraction.getText().contains("-") 
                || jFormattedTextFieldMurHauteur.getText().contains("-")|| jFormattedTextFieldMurHauteurFraction.getText().contains("-") 
                || jFormattedTextFieldMurProfondeur.getText().contains("-") || jFormattedTextFieldMurProfondeurFraction.getText().contains("-"))
            {
                throw new Exception();
            }

            double newLargeur = Double.parseDouble(jFormattedTextFieldMurLargeur.getValue().toString());
            double newHauteur = Double.parseDouble(jFormattedTextFieldMurHauteur.getValue().toString());
            double newProfondeur = Double.parseDouble(jFormattedTextFieldMurProfondeur.getValue().toString());     

            
            if(!jFormattedTextFieldMurLargeurFraction.getText().isEmpty() && !jFormattedTextFieldMurLargeurFraction.getText().equals("(Opt.)"))
            {
                int largeurFractIndex = jFormattedTextFieldMurLargeurFraction.getText().indexOf("/");
                newLargeur += Double.parseDouble(jFormattedTextFieldMurLargeurFraction.getText().substring(0,largeurFractIndex)) / Double.parseDouble(jFormattedTextFieldMurLargeurFraction.getText().substring(largeurFractIndex + 1));
            }
            if(!jFormattedTextFieldMurHauteurFraction.getText().isEmpty() && !jFormattedTextFieldMurHauteurFraction.getText().equals("(Opt.)"))
            {
                int hauteurFractIndex = jFormattedTextFieldMurHauteurFraction.getText().indexOf("/");
                newHauteur += Double.parseDouble(jFormattedTextFieldMurHauteurFraction.getText().substring(0,hauteurFractIndex)) / Double.parseDouble(jFormattedTextFieldMurHauteurFraction.getText().substring(hauteurFractIndex + 1));
            }
            if(!jFormattedTextFieldMurProfondeurFraction.getText().isEmpty() && !jFormattedTextFieldMurProfondeurFraction.getText().equals("(Opt.)"))
            {
                int positionXFractIndex = jFormattedTextFieldMurProfondeurFraction.getText().indexOf("/");
                newProfondeur += Double.parseDouble(jFormattedTextFieldMurProfondeurFraction.getText().substring(0,positionXFractIndex)) / Double.parseDouble(jFormattedTextFieldMurProfondeurFraction.getText().substring(positionXFractIndex + 1));
            }
  
            chaletControleur.modifierDimensionChalet(newHauteur, newLargeur, newProfondeur);
            chaletControleur.ajouterEtapeHistorique();
            jPanelGraphics.repaint();   

        } catch(Exception e){
            System.out.println(e.toString());
        }      
    }//GEN-LAST:event_jButtonSauvegarderMursActionPerformed

    private void jTextFieldLargeurPortePouceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLargeurPortePouceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLargeurPortePouceActionPerformed

    private void jCheckBoxMenuItemAfficherToitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemAfficherToitActionPerformed

        afficherToit =jCheckBoxMenuItemAfficherToit.isSelected();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jCheckBoxMenuItemAfficherToitActionPerformed

    private void jMenuItemExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportActionPerformed
        try{
            chaletControleur.exporterSTL();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_jMenuItemExportActionPerformed

    private void jMenuItemTooltipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTooltipActionPerformed

        ((GestionTooltip)jPanelGraphicsParent).toggleTooltip();
    }//GEN-LAST:event_jMenuItemTooltipActionPerformed

    private void jButtonResetConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetConfigActionPerformed
        chaletControleur.resetConfiguration();
        getData();
        jFormattedTextFieldDistanceAccessFraction.setText("(Opt.)");
        jFormattedTextFieldEpaisseurMurFraction.setText("(Opt.)");
        jFormattedTextFieldGrosseurCelluleFraction.setText("(Opt.)");
        jFormattedTextFieldSuppRainFraction.setText("1/2");
        

        ChaletDTO c = chaletControleur.getChaletDTO();
        chaletControleur.modifierDimensionChalet(c.hauteur, c.largeur, c.profondeur);
        
        chaletControleur.ajouterEtapeHistorique();
        jPanelGraphics.repaint(); 
        
    }//GEN-LAST:event_jButtonResetConfigActionPerformed

    private void MainFrameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MainFrameKeyPressed

    }//GEN-LAST:event_MainFrameKeyPressed

    private void jFormattedTextFieldEpaisseurMurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldEpaisseurMurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldEpaisseurMurActionPerformed

    private void jFormattedTextFieldAngleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldAngleActionPerformed
        try{
            chaletControleur.changerAngleToit(Double.parseDouble(jFormattedTextFieldAngle.getValue().toString()));
            jSliderAngle.setValue((int) Double.parseDouble(jFormattedTextFieldAngle.getValue().toString()));
            
            chaletControleur.ajouterEtapeHistorique();
            jPanelGraphics.repaint(); 
        }catch (Exception e){
            System.out.println(e.toString());
        } 
    }//GEN-LAST:event_jFormattedTextFieldAngleActionPerformed

    private void jPanelGraphicsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelGraphicsMouseEntered
    
    }//GEN-LAST:event_jPanelGraphicsMouseEntered

    private void jCheckBoxMenuItemAfficherGrilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemAfficherGrilleActionPerformed
        afficherGrille = jCheckBoxMenuItemAfficherGrille.isSelected();
        jPanelGraphics.repaint();
    }//GEN-LAST:event_jCheckBoxMenuItemAfficherGrilleActionPerformed

    private void Undo_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Undo_jMenuItemActionPerformed
        chaletControleur.annulerModification();
        getData();
        setdListeAccessoire();
        jPanelGraphics.repaint();   
    }//GEN-LAST:event_Undo_jMenuItemActionPerformed

    private void Redo_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Redo_jMenuItemActionPerformed
        chaletControleur.repeterModification();
        getData();
        jPanelGraphics.repaint();   
    }//GEN-LAST:event_Redo_jMenuItemActionPerformed

    private void jMenuItemSauvegarderChaletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSauvegarderChaletActionPerformed
        try{
            chaletControleur.sauvegarderChalet();
        } catch(Exception e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_jMenuItemSauvegarderChaletActionPerformed

    private void jMenuItemChargerChaletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemChargerChaletActionPerformed
        try{
            chaletControleur.chargerChalet();
        } catch(Exception e){
            System.out.println(e.toString());
        }
        getData();
        jPanelGraphics.repaint();
        
    }//GEN-LAST:event_jMenuItemChargerChaletActionPerformed

    private void jSliderAngleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderAngleMouseReleased
        chaletControleur.ajouterEtapeHistorique();
    }//GEN-LAST:event_jSliderAngleMouseReleased

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit_jMenu;
    private javax.swing.JFrame MainFrame;
    private javax.swing.JMenuItem Redo_jMenuItem;
    private javax.swing.JMenuItem Undo_jMenuItem;
    private javax.swing.JButton jButtonAddFen;
    private javax.swing.JButton jButtonAddPorte;
    private javax.swing.JButton jButtonEntrerFen;
    private javax.swing.JButton jButtonEntrerPorte;
    private javax.swing.JButton jButtonModFenetre;
    private javax.swing.JButton jButtonModPorte;
    private javax.swing.JButton jButtonResetConfig;
    private javax.swing.JButton jButtonSauvegarderConfig;
    private javax.swing.JButton jButtonSauvegarderMurs;
    private javax.swing.JButton jButtonSupFen;
    private javax.swing.JButton jButtonSupPorte;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAfficherGrille;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAfficherToit;
    private javax.swing.JComboBox<String> jComboBoxOrientation;
    private javax.swing.JFormattedTextField jFormattedTextFieldAngle;
    private javax.swing.JFormattedTextField jFormattedTextFieldDistanceAccess;
    private javax.swing.JTextField jFormattedTextFieldDistanceAccessFraction;
    private javax.swing.JFormattedTextField jFormattedTextFieldEpaisseurMur;
    private javax.swing.JTextField jFormattedTextFieldEpaisseurMurFraction;
    private javax.swing.JFormattedTextField jFormattedTextFieldGrosseurCellule;
    private javax.swing.JTextField jFormattedTextFieldGrosseurCelluleFraction;
    private javax.swing.JFormattedTextField jFormattedTextFieldMurHauteur;
    private javax.swing.JTextField jFormattedTextFieldMurHauteurFraction;
    private javax.swing.JFormattedTextField jFormattedTextFieldMurLargeur;
    private javax.swing.JTextField jFormattedTextFieldMurLargeurFraction;
    private javax.swing.JFormattedTextField jFormattedTextFieldMurProfondeur;
    private javax.swing.JTextField jFormattedTextFieldMurProfondeurFraction;
    private javax.swing.JFormattedTextField jFormattedTextFieldSuppRain;
    private javax.swing.JTextField jFormattedTextFieldSuppRainFraction;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelAngle;
    private javax.swing.JLabel jLabelDistanceAccess;
    private javax.swing.JLabel jLabelDistanceAccess1;
    private javax.swing.JLabel jLabelDistanceAccess2;
    private javax.swing.JLabel jLabelDistanceAccess3;
    private javax.swing.JLabel jLabelDistanceAccess4;
    private javax.swing.JLabel jLabelDistanceSuppRain;
    private javax.swing.JLabel jLabelEpaisseurMur;
    private javax.swing.JLabel jLabelFenetres;
    private javax.swing.JLabel jLabelGrosseurCellules;
    private javax.swing.JLabel jLabelHauteurFenetre;
    private javax.swing.JLabel jLabelHauteurFenetre1;
    private javax.swing.JLabel jLabelHauteurFenetre2;
    private javax.swing.JLabel jLabelHauteurFenetre3;
    private javax.swing.JLabel jLabelHauteurFenetre4;
    private javax.swing.JLabel jLabelHauteurPorte;
    private javax.swing.JLabel jLabelLargeurFenetre;
    private javax.swing.JLabel jLabelLargeurPorte;
    private javax.swing.JLabel jLabelMurHauteur;
    private javax.swing.JLabel jLabelMurHauteurFraction;
    private javax.swing.JLabel jLabelMurLargeur;
    private javax.swing.JLabel jLabelMurLargeurFraction;
    private javax.swing.JLabel jLabelMurProfondeur;
    private javax.swing.JLabel jLabelMurProfondeurFraction;
    private javax.swing.JLabel jLabelOrientation;
    private javax.swing.JLabel jLabelPortes;
    private javax.swing.JLabel jLabelPositionXFenetre;
    private javax.swing.JLabel jLabelPositionXPorte;
    private javax.swing.JLabel jLabelPositionYFenetre;
    private javax.swing.JList<String> jListFenetres;
    private javax.swing.JList<String> jListPortes;
    private javax.swing.JPanel jMainPanel;
    private javax.swing.JMenuBar jMenuBarFile;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemChargerChalet;
    private javax.swing.JMenuItem jMenuItemExport;
    private javax.swing.JMenuItem jMenuItemSauvegarderChalet;
    private javax.swing.JMenuItem jMenuItemTooltip;
    private javax.swing.JMenuItem jMenuItemVue1;
    private javax.swing.JMenuItem jMenuItemVue2;
    private javax.swing.JMenuItem jMenuItemVue3;
    private javax.swing.JMenuItem jMenuItemVue4;
    private javax.swing.JMenuItem jMenuItemVue5;
    private javax.swing.JMenu jMenuVue;
    private javax.swing.JPanel jPanelAccessoires;
    private javax.swing.JPanel jPanelConfiguration;
    private javax.swing.JPanel jPanelDimensionsFenetres;
    private javax.swing.JPanel jPanelDimensionsPortes;
    private javax.swing.JPanel jPanelFormulaires;
    private javax.swing.JPanel jPanelGraphics;
    private javax.swing.JPanel jPanelGraphicsParent;
    private javax.swing.JPanel jPanelMurs;
    private javax.swing.JPanel jPanelToit;
    private javax.swing.JScrollPane jScrollPaneFenetres;
    private javax.swing.JScrollPane jScrollPanePortes;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSlider jSliderAngle;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldHauteurFenetreFraction;
    private javax.swing.JTextField jTextFieldHauteurFenetrePouce;
    private javax.swing.JTextField jTextFieldHauteurPorteFraction;
    private javax.swing.JTextField jTextFieldHauteurPortePouce;
    private javax.swing.JTextField jTextFieldLargeurFenetreFraction;
    private javax.swing.JTextField jTextFieldLargeurFenetrePouce;
    private javax.swing.JTextField jTextFieldLargeurPorteFraction;
    private javax.swing.JTextField jTextFieldLargeurPortePouce;
    private javax.swing.JTextField jTextFieldPositionXFenetreFraction;
    private javax.swing.JTextField jTextFieldPositionXFenetrePouce;
    private javax.swing.JTextField jTextFieldPositionXPorteFraction;
    private javax.swing.JTextField jTextFieldPositionXPortePouce;
    private javax.swing.JTextField jTextFieldPositionYFenetreFraction;
    private javax.swing.JTextField jTextFieldPositionYFenetrePouce;
    // End of variables declaration//GEN-END:variables
}

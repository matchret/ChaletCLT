
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.domain.AccessoireDTO;
import ca.ulaval.glo2004.domain.Face;
import ca.ulaval.glo2004.domain.drawing.Afficheur;
import ca.ulaval.glo2004.domain.utils.ConvertisseurImperial;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JToolTip;

/**
 *
 * @author Thomas Ouellet
 */
public class DrawingPanel extends JPanel {
    private static final double FACTEUR_ZOOM = 1.05;
    
    private MainWindow mainWindow;
    private Dimension initialDimension;
    private AffineTransform transform;
    private Polygon selectedPoly;
    private UUID selectedID;
    private boolean isSourisDansPoly;
    
    private int decalageX = 100;
    private int decalageY = 100;
    
    // Variable pour la gestion du tooltip
    private Point positionSouris = new Point();
    
    private Afficheur afficheurPrincipal;
 
    public DrawingPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.transform = new AffineTransform();
        this.selectedPoly = new Polygon();
        
        // On indique la largeur et la hauteur du panel
        int largeur = (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/2);
        int hauteur = (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
        setPreferredSize(new Dimension(largeur,hauteur));
        initialDimension = new Dimension(largeur, hauteur);
        afficheurPrincipal = new Afficheur(mainWindow.chaletControleur);
        
        setVisible(true);
        
        initEventListeners();
    }
    
    
    private void initEventListeners() {    
        // Mouse Adapter utilisé pour les eventListener
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point positionDebutDecalage;
            private double resteDeltaX = 0, resteDeltaY = 0;
            private int resteConvertX = 0, resteConvertY = 0;
            @Override
            public void mousePressed(MouseEvent e) {
                positionDebutDecalage = e.getLocationOnScreen();           
                isSourisDansPoly = ((GestionTooltip)getParent()).isSourisDansPoly(selectedPoly, positionSouris, transform);
                if(selectedID == null)
                {
                    isSourisDansPoly = false;
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                positionDebutDecalage = null;
                if(isSourisDansPoly){
                    mainWindow.chaletControleur.ajouterEtapeHistorique();
                    mainWindow.setdListeAccessoire();
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (positionDebutDecalage != null && !isSourisDansPoly) {
                    int deltaX = e.getXOnScreen() - positionDebutDecalage.x;
                    int deltaY = e.getYOnScreen() - positionDebutDecalage.y;
                    
                    decalageX += deltaX;
                    decalageY += deltaY;
                    
                    double zoom = ConvertisseurImperial.zoom;
                    double min = -1000 * zoom;
                    double max = 1000 * zoom;
                    decalageX = Math.max((int) min, Math.min((int) max, decalageX));
                    decalageY = Math.max((int) min, Math.min((int) max, decalageY));
                    
                    positionDebutDecalage = e.getLocationOnScreen();
                  
                    repaint();
                }
                else if(isSourisDansPoly)
                {
                    double deltaX = ((e.getXOnScreen() - positionDebutDecalage.x) / ConvertisseurImperial.zoom) + resteDeltaX;
                    double deltaY = ((e.getYOnScreen() - positionDebutDecalage.y) / ConvertisseurImperial.zoom) + resteDeltaY;
                    resteDeltaX = deltaX - Math.floor(deltaX);
                    resteDeltaY = deltaY - Math.floor(deltaY);
                    int positionX = (int)Math.floor(deltaX) + resteConvertX;
                    int positionY = (int)Math.floor(deltaY) + resteConvertY;
                    
                    resteConvertX = positionX % ConvertisseurImperial.DPI;
                    resteConvertY = positionY % ConvertisseurImperial.DPI;
                    
                    for(AccessoireDTO acc : mainWindow.chaletControleur.getAccessoiresParMur(Face.valueOf(mainWindow.vueActuelle.toString())))
                    {
                        if(acc.id.equals(selectedID))
                        {
                            if(acc.accType == acc.accType.FENETRE)
                            {
                                mainWindow.chaletControleur.modifierAccessoire(selectedID, acc.positionX + ConvertisseurImperial.pixelsEnPouces(positionX), acc.positionY - ConvertisseurImperial.pixelsEnPouces(positionY), acc.hauteur,
                                                                                acc.largeur, Face.valueOf(mainWindow.vueActuelle.toString()));

                                for(int i = 0; i < selectedPoly.npoints; i++) {
                                    Point delta = new Point((int)Math.floor(deltaX), (int)Math.floor(deltaY));
                                    selectedPoly.xpoints[i] = selectedPoly.xpoints[i] + delta.x;
                                    selectedPoly.ypoints[i] = selectedPoly.ypoints[i] + delta.y;                                  
                                }
                            }
                            else
                            {
                                mainWindow.chaletControleur.modifierAccessoire(selectedID, acc.positionX + ConvertisseurImperial.pixelsEnPouces(positionX), 0, acc.hauteur,
                                                                            acc.largeur, Face.valueOf(mainWindow.vueActuelle.toString()));
                                for(int i = 0; i < selectedPoly.npoints; i++) {
                                    selectedPoly.xpoints[i] = selectedPoly.xpoints[i] + (int)Math.floor(deltaX);
                                }
                            }
                        }
                    }
                    positionDebutDecalage = e.getLocationOnScreen();
                    repaint();               
                }              
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {  
                positionSouris = e.getPoint();
                repaint();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                //tooltipAffiche = true;
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                //tooltipAffiche = false;
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(positionSouris != null) {
                    ArrayList<Polygon> pList = afficheurPrincipal.GetPolygonList();
                    ArrayList<String> nomList = afficheurPrincipal.GetNomPolygonList();
                    selectedPoly = ((GestionTooltip)getParent()).selectPolygon(positionSouris, pList, nomList, transform); 
                    selectedID = ((GestionTooltip)getParent()).getPolygonID(selectedPoly, pList, nomList);
                }
                    
            }
        };
        
        // On ajoute un mouse listener et un mouse motion listener pour capturer les événements de la souris
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
        // Mouse Wheel Listener utilisé pour les eventListener
        MouseWheelListener mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int coches = e.getWheelRotation();
                double zoom = ConvertisseurImperial.zoom;
                if (coches < 0 && zoom < 3) {
                    zoom(e.getPoint(), FACTEUR_ZOOM);
                } else if(coches > 0 && zoom > 0.7) {
                    zoom(e.getPoint(), 1.0 / FACTEUR_ZOOM);
                }
                repaint();

            }
        };
        
        addMouseWheelListener(mouseWheelListener);
    }
    
    private void zoom(Point pivot, double facteur) {
        decalageX = (int) (facteur * (decalageX - pivot.x) + pivot.x);
        decalageY = (int) (facteur * (decalageY - pivot.y) + pivot.y);
        ConvertisseurImperial.zoom *= facteur;
    }
    
    private void afficherTooltip(AffineTransform transform)
    {
        if(positionSouris != null) {
            ArrayList<Polygon> pList = afficheurPrincipal.GetPolygonList();
            ArrayList<String> nomList = afficheurPrincipal.GetNomPolygonList();
            ((GestionTooltip)getParent()).updateTooltipInfo(positionSouris, pList, nomList, transform);                       
        }
    }  

    
    @Override
    protected void paintComponent(Graphics g)
    {
        if (mainWindow != null)
        {
            super.paintComponent(g);            

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(0.5F));

            // Appliquer les transformations
            AffineTransform newTransform = new AffineTransform();
            newTransform.translate(decalageX-18, decalageY-64);//int hardcoded pour coordonner avec la position du tooltip DO NOT TOUCH
            newTransform.scale(ConvertisseurImperial.zoom, ConvertisseurImperial.zoom);//scale ne semble pas concorder avec la détection du tooltip
            transform = newTransform;
            g2d.setTransform(transform);
            // Dessiner la grille
            afficheurPrincipal.dessineGrille(g, decalageX, decalageY, mainWindow.afficherGrille);
            
            Vue vue = mainWindow.vueActuelle;
            if(vue == Vue.DESSUS){
                // Dessiner la vue du dessus
                afficheurPrincipal.dessineDessus(g);
            }
            else{
                // Dessiner la vue du mur
                afficheurPrincipal.dessineMur(g, Face.valueOf(vue.name()),mainWindow.afficherToit);
            }
            if(selectedPoly != null)
            {
                g.setColor(Color.red);
                g2d.setStroke(new BasicStroke(3));
                g.drawPolygon(selectedPoly);
            }
            g2d.setStroke(new BasicStroke(1));
            afficherTooltip(transform);
        }
    }
}

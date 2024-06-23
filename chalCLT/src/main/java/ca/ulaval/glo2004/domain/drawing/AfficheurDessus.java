package ca.ulaval.glo2004.domain.drawing;

import ca.ulaval.glo2004.domain.Configuration;
import ca.ulaval.glo2004.domain.Face;
import ca.ulaval.glo2004.domain.drawing.Afficheur.CouleurMur;
import ca.ulaval.glo2004.domain.utils.ConvertisseurImperial;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Thomas Ouellet
 */
public class AfficheurDessus {
    
    private ArrayList<Polygon> polyList;
    private ArrayList<String> nomList;
    
    public AfficheurDessus() {
    }
    
    public void dessineDessus(Graphics g, int decalageX, int decalageY, double largeurChalet, double profondeurChalet, Face orientationToit) {
        double epaisseur = Configuration.getInstance().getEpaisseur();
        int epaisseurPixel = ConvertisseurImperial.poucesEnPixels(epaisseur);
        
        int largeurPixel = ConvertisseurImperial.poucesEnPixels(largeurChalet);
        int profondeurPixel = ConvertisseurImperial.poucesEnPixels(profondeurChalet);
        
        // Dessine mur facade
        dessineMurFacade(g, decalageX, decalageY, largeurPixel, epaisseurPixel, orientationToit);
        
        // Dessine mur de droite
        dessineMurDroite(g, decalageX, decalageY, largeurPixel, profondeurPixel, epaisseurPixel, orientationToit);
        
        // Dessine mur de arrière
        dessineMurArriere(g, decalageX, decalageY, largeurPixel, profondeurPixel, epaisseurPixel, orientationToit);
        
        // Dessine mur de gauche
        dessineMurGauche(g, decalageX, decalageY, profondeurPixel, epaisseurPixel, orientationToit);
    }
    
    private void dessineMurFacade(Graphics g, int positionX, int positionY, int largeur, int epaisseur, Face orientationToit) {
        Color couleurMur = CouleurMur.FACADE.getCouleur();
        g.setColor(couleurMur);
        int epaisseurRainure = epaisseur / 2;
        
        int distanceRainure = ConvertisseurImperial.poucesEnPixels(Configuration.getInstance().getDistanceRainure())/ 2;
        positionY = positionY - distanceRainure;
        boolean estLong = calculerEstLong(orientationToit, Face.FACADE);
        int longueurMur = estLong ? largeur - (epaisseurRainure * 2) : largeur - (epaisseurRainure * 4);
        int positionMurX = estLong ? positionX + epaisseurRainure : positionX + (epaisseurRainure * 2);
        // On dessine le mur en se basant sur la position et l'orientation du mur
        
        
        // mur principal
        int[] xlist = new int[]{positionMurX, positionMurX + longueurMur, positionMurX + longueurMur,positionMurX + longueurMur + epaisseurRainure,
            positionMurX + longueurMur + epaisseurRainure, positionMurX - epaisseurRainure, positionMurX - epaisseurRainure, positionMurX};       
        int[] ylist = new int[]{positionY + epaisseur, positionY + epaisseur, positionY + epaisseurRainure,positionY + epaisseurRainure,positionY, positionY ,
            positionY + epaisseurRainure, positionY + epaisseurRainure};
 
        Polygon p = new Polygon(xlist, ylist, xlist.length);
        g.fillPolygon(p);
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
        
        polyList.add(p);
        
        boolean largeurCouper = orientationToit==Face.FACADE || orientationToit==Face.ARRIERE;
        int largeurPouce = largeurCouper ? (int) ConvertisseurImperial.pixelsEnPouces(largeur): (int) (ConvertisseurImperial.pixelsEnPouces(largeur) - ConvertisseurImperial.pixelsEnPouces(epaisseur));
        nomList.add("<html>Mur de Façade" + "<br><br>Épaisseur : " + (int) ConvertisseurImperial.pixelsEnPouces(epaisseur) + " \"<br>Longueur : " + largeurPouce + " \"</html>");
      
    }
    
    private void dessineMurDroite(Graphics g, int positionX, int positionY, int largeur, int profondeur, int epaisseur, Face orientationToit) {
        Color couleurMur = CouleurMur.DROITE.getCouleur();
        g.setColor(couleurMur);
        int epaisseurRainure = epaisseur / 2;
        
        int distanceRainure = ConvertisseurImperial.poucesEnPixels(Configuration.getInstance().getDistanceRainure())/ 2;
        positionX = positionX + distanceRainure;
        boolean estLong = calculerEstLong(orientationToit, Face.DROITE);
        int longueurMur = estLong ? profondeur - (epaisseurRainure * 2) : profondeur - (epaisseurRainure * 4);
        int positionYMur = estLong ? positionY + epaisseurRainure : positionY + epaisseur;
        
        // mur principal
        int[] xlist = new int[]{positionX + largeur - epaisseur, positionX + largeur - epaisseurRainure, positionX + largeur - epaisseurRainure,
            positionX + largeur,positionX + largeur,positionX + largeur - epaisseurRainure,positionX + largeur - epaisseurRainure, positionX + largeur - epaisseur};       
        int[] ylist = new int[]{positionYMur, positionYMur,positionYMur-epaisseurRainure, positionYMur-epaisseurRainure ,
            positionYMur + longueurMur + epaisseurRainure,positionYMur + longueurMur + epaisseurRainure, positionYMur + longueurMur, positionYMur + longueurMur};
 
        Polygon p = new Polygon(xlist, ylist, xlist.length);
        g.fillPolygon(p);
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
        
        polyList.add(p);
        boolean largeurCouper = orientationToit==Face.DROITE || orientationToit==Face.GAUCHE;
        int longueurPouce = largeurCouper ? (int) ConvertisseurImperial.pixelsEnPouces(profondeur): (int) (ConvertisseurImperial.pixelsEnPouces(profondeur) - ConvertisseurImperial.pixelsEnPouces(epaisseur));
        nomList.add("<html>Mur droite" + "<br><br>Épaisseur : " + (int) ConvertisseurImperial.pixelsEnPouces(epaisseur) + " \"<br>Longueur : " +  longueurPouce + " \"</html>");

    }
    
    private void dessineMurArriere(Graphics g, int positionX, int positionY, int largeur, int profondeur, int epaisseur, Face orientationToit) {
        Color couleurMur = CouleurMur.ARRIERE.getCouleur();
        g.setColor(couleurMur);
        int epaisseurRainure = epaisseur / 2;
        
        int distanceRainure = ConvertisseurImperial.poucesEnPixels(Configuration.getInstance().getDistanceRainure())/ 2;
        positionY = positionY + distanceRainure;
        boolean estLong = calculerEstLong(orientationToit, Face.ARRIERE);
        int longueurMur = estLong ? largeur - (epaisseurRainure * 2) : largeur - (epaisseurRainure * 4);
        int positionMurX = estLong ? positionX + epaisseurRainure : positionX + (epaisseurRainure * 2);
        // On dessine le mur en se basant sur la position et l'orientation du mur
        
        // mur principal
        int[] xlist = new int[]{positionMurX, positionMurX + longueurMur, positionMurX + longueurMur,positionMurX + longueurMur + epaisseurRainure,
            positionMurX + longueurMur + epaisseurRainure, positionMurX - epaisseurRainure, positionMurX - epaisseurRainure, positionMurX};       
        int[] ylist = new int[]{positionY + profondeur - epaisseur, positionY + profondeur - epaisseur, positionY + profondeur - epaisseurRainure,
            positionY + profondeur - epaisseurRainure,positionY + profondeur, positionY + profondeur, positionY + profondeur - epaisseurRainure,
            positionY + profondeur - epaisseurRainure};
 
        Polygon p = new Polygon(xlist, ylist, xlist.length);
        g.fillPolygon(p);
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
        
        polyList.add(p);
        
        boolean largeurCouper = orientationToit==Face.FACADE || orientationToit==Face.ARRIERE;
        int largeurPouce = largeurCouper ? (int) ConvertisseurImperial.pixelsEnPouces(largeur): (int) (ConvertisseurImperial.pixelsEnPouces(largeur) - ConvertisseurImperial.pixelsEnPouces(epaisseur));
        nomList.add("<html>Mur arrière" + "<br><br>Épaisseur : " + (int) ConvertisseurImperial.pixelsEnPouces(epaisseur) + " \"<br>Longueur : " + largeurPouce + " \"</html>");
    }
    
    private void dessineMurGauche(Graphics g, int positionX, int positionY, int profondeur, int epaisseur, Face orientationToit) {
        Color couleurMur = CouleurMur.GAUCHE.getCouleur();
        g.setColor(couleurMur);
        int epaisseurRainure = epaisseur / 2;
        
        int distanceRainure = ConvertisseurImperial.poucesEnPixels(Configuration.getInstance().getDistanceRainure())/ 2;
        positionX = positionX - distanceRainure;
        boolean estLong = calculerEstLong(orientationToit, Face.GAUCHE);
        int positionYMur = estLong ? positionY + epaisseurRainure : positionY + epaisseur;
        int longueurMur = estLong ? profondeur - (epaisseurRainure * 2) : profondeur - (epaisseurRainure * 4);
        // On dessine le mur en se basant sur la position et l'orientation du mur
        
        // mur principal
        int[] xlist = new int[]{positionX + epaisseur, positionX + epaisseurRainure, positionX + epaisseurRainure,
            positionX,positionX ,positionX + epaisseurRainure,positionX + epaisseurRainure, positionX + epaisseur};       
        int[] ylist = new int[]{positionYMur, positionYMur,positionYMur-epaisseurRainure, positionYMur-epaisseurRainure ,
            positionYMur + longueurMur + epaisseurRainure,positionYMur + longueurMur + epaisseurRainure, positionYMur + longueurMur, positionYMur + longueurMur};
 
        Polygon p = new Polygon(xlist, ylist, xlist.length);
        g.fillPolygon(p);
        polyList.add(p);
        g.setColor(Color.BLACK);
        
        g.drawPolygon(p);
        
        boolean largeurCouper = orientationToit==Face.DROITE || orientationToit==Face.GAUCHE;
        int longueurPouce = largeurCouper ? (int) ConvertisseurImperial.pixelsEnPouces(profondeur): (int) (ConvertisseurImperial.pixelsEnPouces(profondeur) - ConvertisseurImperial.pixelsEnPouces(epaisseur));
        nomList.add("<html>Mur gauche" + "<br><br>Épaisseur : " + (int)ConvertisseurImperial.pixelsEnPouces(epaisseur) + " \"<br>Longueur : " + longueurPouce + " \"</html>");
    }
    
    private boolean calculerEstLong(Face orientationToit, Face orientationMur) {
        return  ((orientationToit == Face.FACADE || orientationToit == Face.ARRIERE) && (orientationMur == Face.FACADE || orientationMur == Face.ARRIERE)) // le mur est long si l'orientation du toit est FACADE ou ARRIERE et que le mur est FACADE ou ARRIERE 
                ||
                ((orientationToit == Face.DROITE || orientationToit == Face.GAUCHE) && (orientationMur == Face.DROITE || orientationMur == Face.GAUCHE)); // le mur est long si l'orientation du toit est DROITE ou GAUCHE et que le mur est DROITE ou GAUCHE
    }
    
    public ArrayList<Polygon> GetPolygonList()
    {
        return polyList;
    }
    
    public ArrayList<String> GetNomPolygonList()
    {
        return nomList;
    }
    
    public void resetPolyList(){
        polyList = new ArrayList<Polygon>();
        nomList = new ArrayList<String>();
    }
    
    
}



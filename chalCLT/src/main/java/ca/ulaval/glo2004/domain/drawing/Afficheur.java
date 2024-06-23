
package ca.ulaval.glo2004.domain.drawing;

import java.awt.Graphics;
import ca.ulaval.glo2004.domain.ChaletControleur;
import ca.ulaval.glo2004.domain.ChaletDTO;
import ca.ulaval.glo2004.domain.Face;
import ca.ulaval.glo2004.domain.utils.ConvertisseurImperial;
import java.awt.Color;
import java.awt.Polygon;
import java.lang.Math;
import java.util.ArrayList;


/**
 *
 * @author Thomas Ouellet 
 */
public class Afficheur {
   private final ChaletControleur chaletControleur;
   private AfficheurMur afficheurMur;
   private AfficheurDessus afficheurDessus;
   private AfficheurGrille afficheurGrille;
   
   private ArrayList<Polygon> polyList;
   private ArrayList<String> nomList;
   
   private int decalageX = 0;
   private int decalageY = 0;
   
   public Afficheur(ChaletControleur chaletControleur) {
       this.chaletControleur = chaletControleur;
       this.afficheurMur = new AfficheurMur();
       this.afficheurDessus = new AfficheurDessus();
       this.afficheurGrille = new AfficheurGrille(); 
       this.polyList = new ArrayList();
       this.nomList = new ArrayList();
   }
   
   public AfficheurMur GetAfficheurMur()
   {
       return this.afficheurMur;
   }
       
    public void dessineGrille(Graphics g, int decalageX, int decalageY, boolean afficherGrille) {
        if(afficherGrille){
            afficheurGrille.afficheGrille(g, decalageX, decalageY);  
        }
    }
   
    public void dessineDessus(Graphics g) {  
        ChaletDTO chalet = chaletControleur.getChaletDTO();
        afficheurDessus.resetPolyList();
        Face orientationToit = chaletControleur.getOrientationToit();
        afficheurDessus.dessineDessus(g, decalageX, decalageY, chalet.largeur, chalet.profondeur, orientationToit);
        polyList = afficheurDessus.GetPolygonList();
        nomList = afficheurDessus.GetNomPolygonList();
    }
   
    public void dessineMur(Graphics g, Face face, boolean afficherToit) {
        ChaletDTO chalet =chaletControleur.getChaletDTO();
        afficheurMur.setChalet(chalet);
        afficheurMur.resetPolyList();
        int topVersant = (int) Math.round(ConvertisseurImperial.poucesEnPixels(chaletControleur.getEpaisseur())/2/Math.cos(Math.toRadians(chalet.toitDTO.angle)));
        
        int y = 0; //decalage pour toit et mur
        if(afficherToit){
            afficheurMur.dessineToit(g, face);
            y += ConvertisseurImperial.poucesEnPixels(chalet.toitDTO.rallongeDTO.hauteur)+ topVersant +ConvertisseurImperial.poucesEnPixels(chalet.distanceRainure);
        }
        
        afficheurMur.dessineMur(g,face,y);
        polyList = afficheurMur.GetPolygonList();
        nomList = afficheurMur.GetNomPolygonList();
    }

    public ArrayList<Polygon> GetPolygonList()
    {
        return polyList;
    }
    
    public ArrayList<String> GetNomPolygonList()
    {
        return nomList;
    }
    
    public enum CouleurMur {
        FACADE(Color.GRAY),
        ARRIERE(Color.GREEN),
        DROITE(Color.WHITE),
        GAUCHE(Color.PINK);
        
        private final Color couleur;

        CouleurMur(Color couleur) {
            this.couleur = couleur;
        }

        public Color getCouleur() {
            return couleur;
        }
    }
}


package ca.ulaval.glo2004.domain.drawing;

import java.awt.Graphics;
import java.awt.Color;
import ca.ulaval.glo2004.domain.ChaletDTO;
import ca.ulaval.glo2004.domain.MurDTO;
import ca.ulaval.glo2004.domain.Face;
import ca.ulaval.glo2004.domain.AccessoireDTO;
import ca.ulaval.glo2004.domain.AccessoireType;
import ca.ulaval.glo2004.domain.utils.ConvertisseurImperial;
import ca.ulaval.glo2004.domain.drawing.Afficheur.CouleurMur;
import java.awt.Polygon;
import java.util.List;
import java.lang.Math;
import java.util.ArrayList;

/**
 *
 * @author Thomas Ouellet
 */
public class AfficheurMur {

    private ChaletDTO chaletDTO;
    private ArrayList<Polygon> polyList;
    private ArrayList<String> nomList;
    
    public AfficheurMur() {
        polyList = new ArrayList<Polygon>();
        nomList = new ArrayList<String>();     
    }
    
/********setter**********/
    public void setChalet(ChaletDTO chaletDTO) {
        this.chaletDTO = chaletDTO;
    }
    public void resetPolyList(){
        polyList = new ArrayList<Polygon>();
        nomList = new ArrayList<String>();
    }
/*******************************************/ 
    
    public void dessineMur(Graphics g,Face face,int decalageY) {
            List<MurDTO> listeMur = chaletDTO.murListDTO;
            int demiEpaiseur = ConvertisseurImperial.poucesEnPixels(chaletDTO.epaisseur)/2;
            Face orientation = chaletDTO.toitDTO.orientation;
            
            boolean MurCoteAfficher =(((orientation == Face.FACADE || orientation == Face.ARRIERE) && (face == Face.DROITE || face == Face.GAUCHE)) || 
            ((orientation == Face.DROITE || orientation == Face.GAUCHE) && (face == Face.FACADE || face == Face.ARRIERE)) );
            
             for(MurDTO mur: listeMur){
                if(mur.typeMur == face){//mur de face
                    int x = 0; 
                    int y = decalageY;
                    Color couleurMur = CouleurMur.valueOf(face.name()).getCouleur();
                    
                    if(MurCoteAfficher){
                        //Mur cote
                        g.setColor(CouleurMurGauche(face)); 
                        int xPoly1[] = {x, x+demiEpaiseur, x+demiEpaiseur, x};
                        int yPoly1[] = {y, y, y+ConvertisseurImperial.poucesEnPixels(mur.hauteur), y+ConvertisseurImperial.poucesEnPixels(mur.hauteur)};
                        Polygon p1 = new Polygon(xPoly1, yPoly1, xPoly1.length);
                        polyList.add(p1);
                        nomList.add("<html>Mur " + NomMurGauche(face) + "<br><br>Hauteur : " + (int)mur.hauteur + " \"<br>Largeur : " + (int)mur.largeur + " \"</html>");
                        g.fillPolygon(p1);
                        g.setColor(Color.BLACK); 
                        g.drawRect(x, y, demiEpaiseur, ConvertisseurImperial.poucesEnPixels(mur.hauteur));
                        x += demiEpaiseur + ConvertisseurImperial.poucesEnPixels(chaletDTO.distanceRainure);
                    }
                
                    g.setColor(couleurMur);
                    int xPoly2[] = {x, x+ConvertisseurImperial.poucesEnPixels(mur.largeur), x+ConvertisseurImperial.poucesEnPixels(mur.largeur), x};
                    int yPoly2[] = {y, y, y+ConvertisseurImperial.poucesEnPixels(mur.hauteur), y+ConvertisseurImperial.poucesEnPixels(mur.hauteur)};
                    Polygon p2 = new Polygon(xPoly2, yPoly2, xPoly2.length);
                    //g.fillRect(x, y, ConvertisseurImperial.poucesEnPixels(mur.largeur), ConvertisseurImperial.poucesEnPixels(mur.hauteur));
                    g.fillPolygon(p2);
                    polyList.add(p2);
                    nomList.add("<html>Mur "+ NomMur(face) + "<br><br>Hauteur : " + (int)mur.hauteur + " \"<br>Largeur : " + (int)mur.largeur + " \"</html>");
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, ConvertisseurImperial.poucesEnPixels(mur.largeur), ConvertisseurImperial.poucesEnPixels(mur.hauteur));
                    
                    if(MurCoteAfficher){
                        //Distance Rainure
                        x = x + ConvertisseurImperial.poucesEnPixels(mur.largeur)+ ConvertisseurImperial.poucesEnPixels(chaletDTO.distanceRainure);
                        //Mur cote
                        g.setColor(CouleurMurDroite(face)); 
                        int xPoly3[] = {x, x+demiEpaiseur, x+demiEpaiseur, x};
                        int yPoly3[] = {y, y, y+ConvertisseurImperial.poucesEnPixels(mur.hauteur), y+ConvertisseurImperial.poucesEnPixels(mur.hauteur)};
                        Polygon p3 = new Polygon(xPoly3, yPoly3, xPoly3.length);
                        //g.fillRect(x, y, demiEpaiseur, ConvertisseurImperial.poucesEnPixels(mur.hauteur));
                        g.fillPolygon(p3);
                        polyList.add(p3);
                        nomList.add("<html>Mur " + NomMurDroit(face) + "<br><br>Hauteur : " + (int)mur.hauteur + " \"<br>Largeur : " + (int)mur.largeur + " \"</html>");
                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, demiEpaiseur, ConvertisseurImperial.poucesEnPixels(mur.hauteur));
                    }
                    
                    if(MurCoteAfficher){x = x - ConvertisseurImperial.poucesEnPixels(mur.largeur);}
                    dessineAccessoire(g,face,x,y);
                    
                }
             }
    }

    
    public void dessineToit(Graphics g,Face face) {
        int xlist[];//For Polygon
        int ylist[];//For Polygon
        Polygon p;
        Face orientation = chaletDTO.toitDTO.orientation;
        int demiEpaisseur = ConvertisseurImperial.poucesEnPixels(chaletDTO.epaisseur)/2;
        int distanceRainure = ConvertisseurImperial.poucesEnPixels(chaletDTO.distanceRainure);
        int x = 0;
        int y = 0;
       
        boolean PignonDroite = CouleurMur.valueOf(face.name()).getCouleur() == CouleurMurDroite(orientation); //Use color map for logic
        boolean PignonGauche = CouleurMur.valueOf(face.name()).getCouleur() == CouleurMurGauche(orientation);
        
        if(face==orientation){          
            int hauteurRallonge = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.rallongeDTO.hauteur);
            int largeurRallonge = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.rallongeDTO.largeur);
            
            int topVersant = (int)(demiEpaisseur/Math.cos(Math.toRadians(chaletDTO.toitDTO.angle)));
            //Versant
            g.setColor(Color.BLUE);
            
            xlist = new int[]{x, x + largeurRallonge, x + largeurRallonge, x};
            ylist = new int[]{y, y, y + topVersant, y + topVersant};
            
            //g.fillRect(x, y, largeurRallonge, topVersant);     
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Versant" + "<br><br>Largeur : " + (int)chaletDTO.toitDTO.versantDTO.largeur + " \"<br>Longueur : " + (int)chaletDTO.toitDTO.versantDTO.longueur + " \"</html>");
            g.setColor(Color.BLACK);           
            g.drawRect(x, y, largeurRallonge, topVersant);
            
            y += topVersant + distanceRainure; 
            
            //Rallonge
            xlist = new int[]{x, largeurRallonge, largeurRallonge, x};       
            ylist = new int[]{y,y,hauteurRallonge + y, hauteurRallonge + y};
            
            g.setColor(Color.YELLOW); 
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Rallonge" + "<br><br>Hauteur : " + (int)chaletDTO.toitDTO.rallongeDTO.hauteur + " \"<br>Largeur : " + (int)chaletDTO.toitDTO.rallongeDTO.largeur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawPolygon(xlist, ylist, 4);
        }
        else if(PignonDroite){
            int hauteurPignon = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.pignonDTO.hauteur);
            int largeurPignon = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.pignonDTO.largeur);
            int hauteurRallonge = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.rallongeDTO.hauteur);
            int longueurVersant = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.profondeur);
            x += distanceRainure;
            
            //Versant
            int topVersant = (int) Math.round(demiEpaisseur / Math.cos(Math.toRadians(chaletDTO.toitDTO.angle)));
            int coinVersant = (int) Math.round(demiEpaisseur/2 * Math.sin(Math.toRadians(chaletDTO.toitDTO.angle)));

            xlist = new int[]{x, longueurVersant, longueurVersant,longueurVersant - demiEpaisseur,x};       
            ylist = new int[]{y, hauteurRallonge + coinVersant + y, topVersant + hauteurRallonge + y, topVersant + hauteurRallonge + y, topVersant + y};
            
            g.setColor(Color.BLUE);  
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Versant" + "<br><br>Largeur : " + (int)chaletDTO.toitDTO.versantDTO.largeur + " \"<br>Longueur : " + (int)chaletDTO.toitDTO.versantDTO.longueur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawPolygon(xlist, ylist, 5);
            
            x -= distanceRainure;
            y += topVersant + distanceRainure;
            
            //Rallonge
            int coinRallonge = (int)(demiEpaisseur *  Math.tan(Math.toRadians(chaletDTO.toitDTO.angle)));
            xlist = new int[]{x, demiEpaisseur , demiEpaisseur , x};       
            ylist = new int[]{y,y + coinRallonge, hauteurRallonge + y, hauteurRallonge + y};
            g.setColor(Color.YELLOW);           
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Rallonge" + "<br><br>Hauteur : " + (int)chaletDTO.toitDTO.rallongeDTO.hauteur + " \"<br>Largeur : " + (int)chaletDTO.toitDTO.rallongeDTO.largeur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawPolygon(xlist, ylist, 4);
            
            x += demiEpaisseur + distanceRainure;
            y += coinRallonge;
            
            //Pignon
            xlist = new int[]{x, largeurPignon + x, x};       
            ylist = new int[]{y,hauteurPignon + y, hauteurPignon + y};
            
            g.setColor(Color.RED); 
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Pignon Droite" + "<br><br>Hauteur : " + (int)chaletDTO.toitDTO.pignonDTO.hauteur + " \"<br>Largeur : " + (int)chaletDTO.toitDTO.pignonDTO.largeur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawPolygon(xlist, ylist, 3);
        }
        else if(PignonGauche){
           int hauteurPignon = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.pignonDTO.hauteur);
           int largeurPignon = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.pignonDTO.largeur);
           int hauteurRallonge = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.rallongeDTO.hauteur);
           int longueurVersant = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.profondeur);

           // Versant
            x += distanceRainure;
            int topVersant = (int) Math.round(demiEpaisseur / Math.cos(Math.toRadians(chaletDTO.toitDTO.angle)));
            int coinVersant = (int) Math.round(demiEpaisseur/2 * Math.sin(Math.toRadians(chaletDTO.toitDTO.angle)));

            xlist = new int[]{x, x, x + demiEpaisseur , x + longueurVersant , x + longueurVersant };
            ylist = new int[]{hauteurRallonge + coinVersant, topVersant + hauteurRallonge,topVersant + hauteurRallonge ,topVersant , y};

            g.setColor(Color.BLUE);
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Versant" + "<br><br>Largeur : " + (int)chaletDTO.toitDTO.versantDTO.largeur + " \"<br>Longueur : " + (int)chaletDTO.toitDTO.versantDTO.longueur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawPolygon(xlist, ylist, 5);
           
            x += distanceRainure;
            y += topVersant + distanceRainure;

            // Rallonge
            int coinRallonge = (int) (demiEpaisseur * Math.tan(Math.toRadians(chaletDTO.toitDTO.angle)));
            xlist = new int[]{x + longueurVersant - demiEpaisseur, x + longueurVersant , x + longueurVersant, x + longueurVersant - demiEpaisseur};
            ylist = new int[]{y + coinRallonge,y, hauteurRallonge + y, hauteurRallonge + y};
            g.setColor(Color.YELLOW);
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Rallonge" + "<br><br>Hauteur : " + (int)chaletDTO.toitDTO.rallongeDTO.hauteur + " \"<br>Largeur : " + (int)chaletDTO.toitDTO.rallongeDTO.largeur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawPolygon(xlist, ylist, 4);
            
            

            x += demiEpaisseur - distanceRainure;
            y += coinRallonge;

            // Pignon
            xlist = new int[]{largeurPignon + x, largeurPignon + x, x};       
            ylist = new int[]{y,hauteurPignon + y, hauteurPignon + y};
            
            g.setColor(Color.RED);
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Pignon Gauche" + "<br><br>Hauteur : " + (int)chaletDTO.toitDTO.pignonDTO.hauteur + " \"<br>Largeur : " + (int)chaletDTO.toitDTO.pignonDTO.largeur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawPolygon(xlist, ylist, 3);
        }
        else{
         int hauteurRallonge = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.rallongeDTO.hauteur);
         int largeurRallonge = ConvertisseurImperial.poucesEnPixels(chaletDTO.toitDTO.rallongeDTO.largeur);
         int topVersant = (int) Math.round(demiEpaisseur / Math.cos(Math.toRadians(chaletDTO.toitDTO.angle)));
         
         int coinVersant = (int) Math.round(demiEpaisseur/2 * Math.sin(Math.toRadians(chaletDTO.toitDTO.angle)));
         
            //Versant
            xlist = new int[]{x, x + largeurRallonge, x + largeurRallonge, x};
            ylist = new int[]{y, y, y + topVersant + hauteurRallonge, y + topVersant + hauteurRallonge};
            
            g.setColor(Color.BLUE);
            p = new Polygon(xlist, ylist, xlist.length);
            g.fillPolygon(p);
            polyList.add(p);
            nomList.add("<html>Versant" + "<br><br>Largeur : " + (int)chaletDTO.toitDTO.versantDTO.largeur + " \"<br>Longueur : " + (int)chaletDTO.toitDTO.versantDTO.longueur + " \"</html>");
            g.setColor(Color.BLACK);
            g.drawRect(x, y, largeurRallonge, topVersant+ hauteurRallonge);  
            g.drawLine(x, hauteurRallonge + coinVersant, largeurRallonge,  hauteurRallonge + coinVersant);
        }
    }
    
    private void dessineAccessoire(Graphics g,Face face,int decalageX,int decalageY) {
        List<MurDTO> listeMur = chaletDTO.murListDTO;
        for(MurDTO mur: listeMur)
        {
            if(mur.typeMur == face)
            {
                for(int i = 0; i < mur.accessoireDTOListe.size(); i++)
                {
                    AccessoireDTO acc = mur.accessoireDTOListe.get(i);
                    int hauteur = ConvertisseurImperial.poucesEnPixels(mur.hauteur);
                                      
                    int x = ConvertisseurImperial.poucesEnPixels(acc.positionX) + decalageX;
                    int y = decalageY + (hauteur-ConvertisseurImperial.poucesEnPixels(acc.hauteur)) - ConvertisseurImperial.poucesEnPixels((int)acc.positionY);
                    
                    if(acc.accType == AccessoireType.PORTE)
                    {    
                        int x1 = ConvertisseurImperial.poucesEnPixels(acc.hauteur);
                        int y1 = y + ConvertisseurImperial.poucesEnPixels(acc.largeur);
                        Color color = acc.estValide ? CouleurPorte.VALIDE.getCouleur() : CouleurPorte.INVALIDE.getCouleur();
                        g.setColor(color);
                        int xPoly1[] = {x, x+ConvertisseurImperial.poucesEnPixels(acc.largeur), x+ConvertisseurImperial.poucesEnPixels(acc.largeur), x};
                        int yPoly1[] = {y, y, y+ConvertisseurImperial.poucesEnPixels(acc.hauteur), y+ConvertisseurImperial.poucesEnPixels(acc.hauteur)};
                        Polygon p1 = new Polygon(xPoly1, yPoly1, xPoly1.length);
                        polyList.add(p1);
                        nomList.add("<html>Porte "+ (i+1) + "<br><br>Hauteur : " + (int)acc.hauteur + " \"<br>Largeur : " + (int)acc.largeur + " \"</html>" + "(" + acc.id +")");
                        g.fillPolygon(p1);
                        
                        //Outline
                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, ConvertisseurImperial.poucesEnPixels(acc.largeur), ConvertisseurImperial.poucesEnPixels(acc.hauteur));
                        
                        //Poignee de porte
                         drawPoignee(g, x + ConvertisseurImperial.poucesEnPixels(acc.largeur) /5, y + ConvertisseurImperial.poucesEnPixels(acc.hauteur) / 2);

                    }
                    else
                    {      
                        Color color = acc.estValide ? CouleurFenetre.VALIDE.getCouleur() : CouleurFenetre.INVALIDE.getCouleur();
                        g.setColor(color);
                        int xPoly1[] = {x, x+ConvertisseurImperial.poucesEnPixels(acc.largeur), x+ConvertisseurImperial.poucesEnPixels(acc.largeur), x};
                        int yPoly1[] = {y, y, y+ConvertisseurImperial.poucesEnPixels(acc.hauteur), y+ConvertisseurImperial.poucesEnPixels(acc.hauteur)};
                        Polygon p1 = new Polygon(xPoly1, yPoly1, xPoly1.length);
                        polyList.add(p1);
                        nomList.add("<html>Fenêtre "+ (i+1) + "<br><br>Hauteur : " + (int)acc.hauteur + " \"<br>Largeur : " + (int)acc.largeur + " \"</html>"+ "(" + acc.id +")");
                        g.fillPolygon(p1);
                        
                        //Outline
                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, ConvertisseurImperial.poucesEnPixels(acc.largeur), ConvertisseurImperial.poucesEnPixels(acc.hauteur));
                        
                        //Carreaux de fenetre
                        drawCross(g, x, y, ConvertisseurImperial.poucesEnPixels(acc.largeur), ConvertisseurImperial.poucesEnPixels(acc.hauteur) );
                    }
                }
            }             
        }    
    }
    
    
    /**********Methode pour gerer les couleurs**************/
    private Color CouleurMurDroite(Face faceType) {
    Color couleurMur;
    
    // Check the wall's type and assign the appropriate color
    if (faceType == Face.FACADE) {
        couleurMur = CouleurMur.DROITE.getCouleur();
    } else if (faceType == Face.ARRIERE) {
        couleurMur = CouleurMur.GAUCHE.getCouleur();
    } else if (faceType == Face.DROITE) {
        couleurMur = CouleurMur.ARRIERE.getCouleur();
    } else if (faceType == Face.GAUCHE) {
        couleurMur = CouleurMur.FACADE.getCouleur();
    } else {
        // too compile
        couleurMur = Color.BLACK;
    }
    
    return couleurMur;
    }
    
    private String NomMur(Face faceType)
    {
        String nom;
        if (faceType == Face.FACADE) {
            nom = "de Façade";
        } else if (faceType == Face.ARRIERE) {
            nom = "Arrière";
        } else if (faceType == Face.DROITE) {
            nom = "Droit";
        } else if (faceType == Face.GAUCHE) {
            nom = "Gauche";
        } else {
            // too compile
            nom = "Invalide";
        }
        return nom;
    }
    
    private String NomMurDroit(Face faceType)
    {
        String nom;
        if (faceType == Face.FACADE) {
            nom = "Droit";
        } else if (faceType == Face.ARRIERE) {
            nom = "Gauche";
        } else if (faceType == Face.DROITE) {
            nom = "Arrière";
        } else if (faceType == Face.GAUCHE) {
            nom = "de Façade";
        } else {
            // too compile
            nom = "Invalide";
        }
        return nom;
    }
    
    private String NomMurGauche(Face faceType)
    {
        String nom;
        if (faceType == Face.FACADE) {
            nom = "Gauche";
        } else if (faceType == Face.ARRIERE) {
            nom = "Droit";
        } else if (faceType == Face.DROITE) {
            nom = "de Façade";
        } else if (faceType == Face.GAUCHE) {
            nom = "Arrière";
        } else {
            // too compile
            nom = "Invalide";
        }
        return nom;
    }
    
    private Color CouleurMurGauche(Face faceType) {
    Color couleurMur;
    
    // Check the wall's type and assign the appropriate color
    if (faceType == Face.FACADE) {
        couleurMur = CouleurMur.GAUCHE.getCouleur();
    } else if (faceType == Face.ARRIERE) {
        couleurMur = CouleurMur.DROITE.getCouleur();
    } else if (faceType == Face.DROITE) {
        couleurMur = CouleurMur.FACADE.getCouleur();
    } else if (faceType == Face.GAUCHE) {
        couleurMur = CouleurMur.ARRIERE.getCouleur();
    } else {
        // too compile
        couleurMur = Color.BLACK;
    }
    
    return couleurMur;
    }
    
    private void drawCross(Graphics g, int x, int y, int width, int height){
        g.setColor(Color.BLACK);
        g.drawLine(x, y + height/2, x + width, y + height/2);
        g.drawLine(x + width/2, y, x + width/2, y + height);
    }
 
    private void drawPoignee(Graphics g, int centreX, int centreY) {
    int diametrePogne = 6;
    int handleX = centreX - diametrePogne;
    int handleY = centreY - diametrePogne;

    g.setColor(Color.BLACK);
    g.drawOval(handleX, handleY, 2 * diametrePogne, 2 * diametrePogne);
    g.setColor(Color.DARK_GRAY);
    g.fillOval(handleX, handleY, 2 * diametrePogne, 2 * diametrePogne);
    }
    
    private enum CouleurFenetre{
        VALIDE(Color.lightGray),
        INVALIDE(Color.RED);
        
        private final Color couleur;

        CouleurFenetre(Color couleur) {
            this.couleur = couleur;
        }

        public Color getCouleur() {
            return couleur;
        }
    }
    
        private enum CouleurPorte{
        VALIDE(Color.YELLOW),
        INVALIDE(Color.RED);
        
        private final Color couleur;

        CouleurPorte(Color couleur) {
            this.couleur = couleur;
        }

        public Color getCouleur() {
            return couleur;
        }
    }
        
    public ArrayList<Polygon> GetPolygonList()
    {
        return polyList;
    }
    
    public ArrayList<String> GetNomPolygonList()
    {
        return nomList;
    }
    
}

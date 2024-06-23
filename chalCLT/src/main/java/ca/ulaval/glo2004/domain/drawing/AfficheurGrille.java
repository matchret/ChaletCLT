
package ca.ulaval.glo2004.domain.drawing;

import ca.ulaval.glo2004.domain.Configuration;
import ca.ulaval.glo2004.domain.utils.ConvertisseurImperial;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author Thomas Ouellet
 */
public class AfficheurGrille {
    private int grosseurCellule;
    
    public AfficheurGrille() {
        grosseurCellule = ConvertisseurImperial.DPI * 2;
    }
     
    public void afficheGrille(Graphics g, int decalageX, int decalageY) {
        grosseurCellule = ConvertisseurImperial.poucesEnPixels(Configuration.getInstance().getGrosseurCellule());
        int largeurFenetre = 3000;
        int hauteurFenetre = 3000;

        // Calculer le point de départ pour la grille en fonction du décalage
        int debutX = grosseurCellule - largeurFenetre;
        int debutY = grosseurCellule - hauteurFenetre;

        // Dessiner la grille horizontale
        for (int y = debutY; y < hauteurFenetre; y += grosseurCellule) {
            CouleurCellule couleur = determinerCouleurCellule(y);
            
            g.setColor(couleur.getCouleur());
            g.drawLine(-largeurFenetre, y, largeurFenetre, y);
        }

        // Dessiner la grille verticale
        for (int x = debutX; x < largeurFenetre; x += grosseurCellule) {
            CouleurCellule couleur = determinerCouleurCellule(x);

            g.setColor(couleur.getCouleur());
            g.drawLine(x, -hauteurFenetre, x, hauteurFenetre);
        }
    }
    
    private CouleurCellule determinerCouleurCellule(int position) {
        if (position % (grosseurCellule * 9) == 0) {
            return CouleurCellule.LARGE;
        } else if (position % (grosseurCellule * 3) == 0) {
            return CouleurCellule.MEDIUM;
        } else {
            return CouleurCellule.SMALL;
        }
    }
            
    private enum CouleurCellule {
        LARGE(new Color(130, 130, 130)),
        MEDIUM(new Color(170,170, 170)),
        SMALL(new Color(200, 200, 200));
        
        private final Color couleur;

        CouleurCellule(Color couleur) {
            this.couleur = couleur;
        }

        public Color getCouleur() {
            return couleur;
        }
    }
}

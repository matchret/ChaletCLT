
package ca.ulaval.glo2004.domain;

import java.io.Serializable;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class Pignon implements Serializable {
    
    private double hauteur;
    private double largeur;

    public Pignon(double hauteur, double largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
    }
    
    public Pignon(Pignon original) {
        this.hauteur = original.hauteur;
        this.largeur = original.largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }
    
    
}

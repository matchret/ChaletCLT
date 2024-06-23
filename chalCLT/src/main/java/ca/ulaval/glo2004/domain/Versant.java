
package ca.ulaval.glo2004.domain;

import java.io.Serializable;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class Versant implements Serializable {
    
    private double largeur;
    private double longueur;
    
    public Versant(double largeur, double longueur) {
        this.largeur = largeur;
        this.longueur = longueur;   
    }
    
    public Versant(Versant original) {
        this.largeur = original.largeur;
        this.longueur = original.longueur;   
    }
    
    public double getLargeur() {
        return largeur;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setLongeur(double longeur) {
        this.longueur = longeur;
    } 
    
}

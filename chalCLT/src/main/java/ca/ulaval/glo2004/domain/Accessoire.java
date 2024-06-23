
package ca.ulaval.glo2004.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author William Harvey, Jonathan Dion
 */
public abstract class Accessoire implements Serializable {
    protected UUID uuid;
    protected double hauteur;
    protected double largeur;
    protected double positionX;
    protected double positionY;
    protected boolean estValide;
    
    public Accessoire()
    {
        this.uuid = UUID.randomUUID();
        this.hauteur = 1;
        this.largeur = 1;
        this.positionX = 0;
        this.positionY = 0;
        this.estValide = false;
    }

    public Accessoire(double hauteur, double largeur, double positionX, double positionY, boolean estValide) {
        this.uuid = UUID.randomUUID();
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.positionX = positionX;
        this.positionY = positionY;
        this.estValide = estValide;
    }
    
    public Accessoire(UUID uuid, double hauteur, double largeur, double positionX, double positionY,
            boolean estValide)
    {
        this.uuid = uuid;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.positionX = positionX;
        this.positionY = positionY;
        this.estValide = estValide;
    }
    
    // Constructeur de copie
    public Accessoire(Accessoire original) {
        this.uuid = original.uuid;
        this.hauteur = original.hauteur;
        this.largeur = original.largeur;
        this.positionX = original.positionX;
        this.positionY = original.positionY;
        this.estValide = original.estValide;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }

    public double getHauteur() {
        return this.hauteur;
    }

    public double getLargeur() {
        return this.largeur;
    }

    public double getPositionX() {
        return this.positionX;
    }

    public double getPositionY() {
        return this.positionY;
    }

    public boolean isEstValide() {
        return estValide;
    }

    public void setEstValide(boolean estValide) {
        this.estValide = estValide;
    }
    
    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
    
}

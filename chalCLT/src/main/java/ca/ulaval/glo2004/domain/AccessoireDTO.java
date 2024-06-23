
package ca.ulaval.glo2004.domain;

import java.util.UUID;

/**
 *
 * @author William Harvey
 */
public class AccessoireDTO {
    public UUID id;
    public double hauteur;
    public double largeur;
    public double positionX;
    public double positionY;
    public boolean estValide;
    public AccessoireType accType; 
    
    public AccessoireDTO(Accessoire a)
    {
        this.id = a.getUuid();
        this.hauteur = a.getHauteur();
        this.largeur = a.getLargeur();
        this.positionX = a.getPositionX();
        this.positionY = a.getPositionY();
        if(a.isEstValide()){
            this.estValide = true;
        }
        else{
            this.estValide = false;
        }
        
        if(a instanceof Porte){
           this.accType = AccessoireType.PORTE; 
        }
        else{
            this.accType = AccessoireType.FENETRE; 
        }
    }
    
    public AccessoireDTO(double hauteur, double largeur, double positionX, double positionY)
    {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}

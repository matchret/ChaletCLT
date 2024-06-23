
package ca.ulaval.glo2004.domain;
import java.util.UUID;

/**
 *
 * @author William Harvey, Jonathan Dion
 */
public class Porte extends Accessoire{
    
    public Porte()
    {
        super();
        this.largeur = Configuration.getInstance().getLargeurPorte();
        this.hauteur = Configuration.getInstance().getHauteurPorte();
    }
    
    public Porte(double hauteur, double largeur, double positionX, double positionY)
    {
        super(hauteur,largeur,positionX, positionY += Configuration.getInstance().getBasePorte(),false);
    }
    
    public Porte(UUID uuid, double hauteur, double largeur, double positionX, double positionY)
    {
        super(uuid,hauteur,largeur,positionX, positionY += Configuration.getInstance().getBasePorte(),false);
    }
    /***Copie****/
    public Porte(Porte original) {
        super(original);
    }
}

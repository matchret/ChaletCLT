
package ca.ulaval.glo2004.domain;
import java.util.UUID;

/**
 *
 * @author William Harvey, Jonathan Dion
 */
public class Fenetre extends Accessoire{
    
    public Fenetre()
    {
        super();
    }
    
    public Fenetre(double hauteur, double largeur, double positionX, double positionY)
    {
        super(hauteur,largeur,positionX, positionY,false);
    }
    
    public Fenetre(UUID uuid, double hauteur, double largeur, double positionX, double positionY)
    {
        super(uuid,hauteur,largeur,positionX, positionY, false);    
    }
    
    /***Copie****/
    public Fenetre(Fenetre original) {
        super(original);
    }
    
}

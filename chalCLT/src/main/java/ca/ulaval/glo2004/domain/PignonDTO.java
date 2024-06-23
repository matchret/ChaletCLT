
package ca.ulaval.glo2004.domain;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class PignonDTO {
    public double hauteur;
    public double largeur;
    
    public PignonDTO(Pignon pignon){
        hauteur = pignon.getHauteur();
        largeur = pignon.getLargeur();
    }    
}

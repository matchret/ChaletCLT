
package ca.ulaval.glo2004.domain;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class RallongeDTO {
    
    public double hauteur;
    public double largeur;
    
    public RallongeDTO(Rallonge rallonge){
        hauteur = rallonge.getHauteur();
        largeur = rallonge.getLargeur();
    }
    
}

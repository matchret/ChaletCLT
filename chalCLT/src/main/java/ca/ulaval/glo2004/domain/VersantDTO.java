
package ca.ulaval.glo2004.domain;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class VersantDTO {
    public double largeur;
    public double longueur;
    
    public VersantDTO(Versant versant){
      largeur = versant.getLargeur();
      longueur = versant.getLongueur();
    }
}
    



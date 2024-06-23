
package ca.ulaval.glo2004.domain;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Mathieu Chretien
 */
public class ChaletDTO {
    public List<MurDTO> murListDTO;
    public ToitDTO toitDTO;
    public double hauteur;
    public double largeur;
    public double profondeur;
    public double distanceRainure;
    public double distanceMinAccessoire;
    public double epaisseur;
    public double angleToit;
    
    public ChaletDTO(Chalet c){
        murListDTO = new ArrayList<>();
        for (Mur mur : c.getMurList()) {
            murListDTO.add(new MurDTO(mur));
        }
        toitDTO = new ToitDTO(c.getToit());
        angleToit = c.getToit().getAngle();
        hauteur = c.getHauteur();
        largeur = c.getLargeur();
        profondeur = c.getProfondeur();
        distanceRainure = c.getConfiguration().getDistanceRainure();
        epaisseur = c.getConfiguration().getEpaisseur();
        distanceMinAccessoire = c.getConfiguration().getDistanceMinAccessoire();
        
        
    }
    
}

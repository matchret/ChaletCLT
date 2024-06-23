
package ca.ulaval.glo2004.domain;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Jonathan Dion
 */
public class MurDTO {
    public double hauteur;
    public double largeur;
    public Face typeMur;
    public List<AccessoireDTO> accessoireDTOListe;
    public UUID uuid;

    public MurDTO(Mur mur) {
        hauteur = mur.getHauteur();
        largeur = mur.getLargeur();
        typeMur = mur.getTypeMur();
        accessoireDTOListe = new ArrayList<>();
        for(Accessoire a: mur.getAccessoireListe()){
            accessoireDTOListe.add(new AccessoireDTO(a));
        }
        uuid = mur.getUuid();
    }
    
    
}

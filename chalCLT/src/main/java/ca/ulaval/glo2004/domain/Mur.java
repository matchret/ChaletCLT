
package ca.ulaval.glo2004.domain;
import java.io.Serializable;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Jonathan Dion
 */
public class Mur implements Serializable {
    private double hauteur;
    private double largeur;
    private Face typeMur;
    private List<Accessoire> accessoireListe;
    private UUID uuid;

    public Mur(double hauteur, double largeur, Face typeMur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.typeMur = typeMur;
        this.accessoireListe = new ArrayList();
        this.uuid = UUID.randomUUID();
    }

    public Mur(double hauteur, double largeur, Face typeMur, List<Accessoire> accessoireListe) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.typeMur = typeMur;
        this.accessoireListe = accessoireListe;
        this.uuid = UUID.randomUUID();
    }
    
    // Constructeur de copie
    public Mur(Mur original) {
        this.hauteur = original.hauteur;
        this.largeur = original.largeur;
        this.typeMur = original.typeMur;
        this.accessoireListe = new ArrayList<>(original.accessoireListe.size());
        for (Accessoire accessoire : original.accessoireListe) {
            if (accessoire instanceof Porte) {
                this.accessoireListe.add(new Porte((Porte) accessoire));
            } else if (accessoire instanceof Fenetre) {
                this.accessoireListe.add(new Fenetre((Fenetre) accessoire));
            }
        }
        this.uuid = original.uuid;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public Face getTypeMur() {
        return typeMur;
    }

    public List<Accessoire> getAccessoireListe() {
        return accessoireListe;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setTypeMur(Face typeMur) {
        this.typeMur = typeMur;
    }

    public void setAccessoireListe(List<Accessoire> accessoireListe) {
        this.accessoireListe = accessoireListe;
    }
    
    public boolean ajouterPorte(double hauteur, double largeur, double positionX, double positionY){
        Porte porte = new Porte(hauteur, largeur, positionX, positionY);
        if (this.validerPorte(porte)){
            porte.setEstValide(true);
        }
        else{
            porte.setEstValide(false);
        }
        accessoireListe.add(porte);
        return porte.isEstValide();
    }
    
    public boolean ajouterFenetre(double hauteur, double largeur, double positionX, 
            double positionY){
        Fenetre fenetre = new Fenetre(hauteur, largeur, positionX, positionY);
        if (this.validerFenetre(fenetre)){
            fenetre.setEstValide(true);
        }
        else{
            fenetre.setEstValide(false);
        }
        accessoireListe.add(fenetre);
        return fenetre.isEstValide();
    }
    
    public boolean modifierAccessoire(UUID uuid, double positionX, 
            double positionY, double hauteur, double largeur){
        for (int i = 0; i < accessoireListe.size(); i++){
            if(accessoireListe.get(i).getUuid().equals(uuid)){
                if(accessoireListe.get(i) instanceof Porte){
                    this.supprimerAccessoire(uuid);
                    Porte porte = new Porte(uuid, hauteur, largeur, positionX, positionY);
                    if (this.validerPorte(porte)){
                        porte.setEstValide(true);
                    }
                    else{
                        porte.setEstValide(false);
                    }
                    accessoireListe.add(porte);
                    return porte.isEstValide();
                }
                else{
                    this.supprimerAccessoire(uuid);
                    Fenetre fenetre = new Fenetre(uuid, hauteur, largeur, positionX, positionY);
                    if (this.validerFenetre(fenetre)){
                        fenetre.setEstValide(true);
                    }
                    else{
                        fenetre.setEstValide(false);
                    }
                    accessoireListe.add(fenetre);
                    return fenetre.isEstValide();
                }
            }
        }
        return true;
    }
    
    public boolean supprimerAccessoire(UUID uuid){
        for (int i = 0; i < accessoireListe.size(); i++){
            if(accessoireListe.get(i).getUuid().equals(uuid)) {
                accessoireListe.remove(i);
                revaliderInvalide();
                return true;
            }
        }
        return false;
    }
    
    private void revaliderInvalide(){
       for(int i = 0; i < accessoireListe.size(); i++){
           
           if(accessoireListe.get(i) instanceof Porte && !accessoireListe.get(i).estValide){
               
               accessoireListe.get(i).setEstValide(validerPorte((Porte) accessoireListe.get(i)));
           }
           
           if(accessoireListe.get(i) instanceof Fenetre && !accessoireListe.get(i).estValide){
               
               accessoireListe.get(i).setEstValide(validerFenetre((Fenetre) accessoireListe.get(i)));
           }
        }
    }

    protected boolean validerPorte(Porte porte){
        Configuration config = Configuration.getInstance();
        if (porte.getPositionX() <= config.getDistanceMinAccessoire() + config.getEpaisseur() ){return false;}
        if (porte.getPositionX() + porte.getLargeur() >= this.largeur-(config.getDistanceMinAccessoire() + config.getEpaisseur())){return false;}
        if (porte.getHauteur() >= this.hauteur-config.getDistanceMinAccessoire()){return false;}
        
        for (int i = 0; i < accessoireListe.size(); i++){
            if(porte.uuid.equals(accessoireListe.get(i).uuid) ) continue;
            
            if(accessoireListe.get(i) instanceof Porte){
                
                if (porte.getPositionX()- accessoireListe.get(i).getPositionX() < 0){
                    
                    if(porte.getPositionX()+porte.getLargeur()>= 
                            accessoireListe.get(i).getPositionX()-config.getDistanceMinAccessoire()){return false;}
                    }
                
                else if(porte.getPositionX()- accessoireListe.get(i).getPositionX() == 0)
                    {return false;}
                
                else{
                    if(accessoireListe.get(i).getPositionX()+
                         accessoireListe.get(i).getLargeur()>= porte.getPositionX()-config.getDistanceMinAccessoire() )
                      {return false;}
                }
            }
            else{
                if (porte.getHauteur() + config.getDistanceMinAccessoire() >=
                        accessoireListe.get(i).getPositionY()){
                    
                    if (porte.getPositionX()- accessoireListe.get(i).getPositionX() < 0){
                        
                        if(porte.getPositionX()+porte.getLargeur()>= 
                            accessoireListe.get(i).getPositionX()-config.getDistanceMinAccessoire()){return false;}
                    }
                    
                    else if(porte.getPositionX()- accessoireListe.get(i).getPositionX() == 0)
                        {return false;}
                    
                    else{if(accessoireListe.get(i).getPositionX()+
                         accessoireListe.get(i).getLargeur()>= porte.getPositionX()-config.getDistanceMinAccessoire() )
                      {return false;}
                    }
                }
            }
        }
        return true;
    }
    
    protected boolean validerFenetre (Fenetre fenetre){
        Configuration config = Configuration.getInstance();
        if(fenetre.getPositionX() <= config.getDistanceMinAccessoire() + config.getEpaisseur()){return false;}
        if(fenetre.getPositionX() + fenetre.getLargeur()>= this.largeur-(config.getDistanceMinAccessoire() + config.getEpaisseur())){return false;}
        if(fenetre.getPositionY() <= config.getDistanceMinAccessoire()){return false;}
        if(fenetre.getPositionY() + fenetre.getHauteur() >= this.hauteur-config.getDistanceMinAccessoire()){return false;}
        
        for (int i = 0; i < accessoireListe.size(); i++){
            if(fenetre.uuid.equals(accessoireListe.get(i).uuid) ) continue;
            
            if(accessoireListe.get(i) instanceof Fenetre){
                
                if (accessoireListe.get(i).getHauteur() +
                        config.getDistanceMinAccessoire() >= fenetre.getPositionY()){
                    
                    if (accessoireListe.get(i).getPositionX()- fenetre.getPositionX() < 0){
                        
                        if(accessoireListe.get(i).getPositionX()+
                                accessoireListe.get(i).getLargeur()>= 
                            fenetre.getPositionX()-config.getDistanceMinAccessoire()){return false;}
                    }
                    
                    else if(accessoireListe.get(i).getPositionX()- fenetre.getPositionX() == 0)
                        {return false;}
                    
                    else{if(fenetre.getPositionX()+
                         fenetre.getLargeur()>= accessoireListe.get(i).getPositionX()-config.getDistanceMinAccessoire() )
                      {return false;}
                    }
                }

            }
            else{
                if(fenetre.getPositionY() - config.getDistanceMinAccessoire() <=
                        accessoireListe.get(i).getPositionY() +
                        accessoireListe.get(i).getHauteur() && 
                        fenetre.getPositionY() + fenetre.getHauteur() + config.getDistanceMinAccessoire() >= 
                        accessoireListe.get(i).getPositionY()){
                    
                    if(fenetre.getPositionX() - accessoireListe.get(i).getPositionX() < 0){
                        if(fenetre.getPositionX() + fenetre.getLargeur() >=
                                accessoireListe.get(i).getPositionX() - config.getDistanceMinAccessoire()){
                            return false;
                        }
                    }
                    else if(fenetre.getPositionX() - accessoireListe.get(i).getPositionX() == 0){
                        return false;
                    }
                    else{
                        if(accessoireListe.get(i).getPositionX() +
                                accessoireListe.get(i).getLargeur() >=
                                fenetre.getPositionX() - config.getDistanceMinAccessoire()){return false;}
                    }
                }
            }
        }
        return true;
    }
}

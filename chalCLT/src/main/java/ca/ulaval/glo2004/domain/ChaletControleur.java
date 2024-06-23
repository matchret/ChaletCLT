
package ca.ulaval.glo2004.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Pierre-Olivier Tremblay, Thomas Ouellet, Jonathan Dion
 */
public class ChaletControleur {
    private Chalet chalet;
    private Historique historique;

    public ChaletControleur() {
        this.chalet = new Chalet();
        this.historique = new Historique();
        this.historique.ajouterEtape(chalet.copier());
    }
    /******Historique*******/
    public void ajouterEtapeHistorique() {
        historique.ajouterEtape(chalet);
    }
    public void annulerModification() {
        historique.annuler();
        this.chalet.setChalet( historique.getChaletActuel());
    }
    public void repeterModification() {
        historique.repeter();
        this.chalet.setChalet(historique.getChaletActuel());        
    }

    /****************/
    
    public void sauvegarderChalet(){
        GestionnaireSauvegarde gestionnaireSauvegarde = new GestionnaireSauvegarde();
        
        String filePath = gestionnaireSauvegarde.getPath();
        
        if(filePath != null){
            gestionnaireSauvegarde.sauvegarderChalet(chalet, filePath);
            
        }else{
            System.out.println("La sauvegarde a été annulée par l'utilisateur.");
        }
        
    }
    
    public void chargerChalet(){
        GestionnaireSauvegarde gestionnaireSauvegarde = new GestionnaireSauvegarde();
        
        String filePath = gestionnaireSauvegarde.getPath();
        if(filePath != null){
            Chalet loadedChalet = gestionnaireSauvegarde.chargerChalet(filePath);
        
            if(loadedChalet != null){
                chalet = loadedChalet;
                Configuration.setConfig(loadedChalet.getConfiguration());
                System.out.println("Test: " + loadedChalet.getConfiguration());
                
             }else{
            System.out.println("Le chargement a été annulé par l'utilisateur.");
            }
        }
    }
    
    public ChaletDTO getChaletDTO() {
        return new ChaletDTO(chalet);
    }
    
    public double getDistanceMinAccessoire(){
        return Configuration.getInstance().getDistanceMinAccessoire();
    }
    
    public double getDistanceRainure(){
        return Configuration.getInstance().getDistanceRainure();
    }
    
    public double getEpaisseur(){
        return Configuration.getInstance().getEpaisseur();
    }
    
    public double getGrosseurCellule(){
        return Configuration.getInstance().getGrosseurCellule();
    }
    
    public Face getOrientationToit() {
        return chalet.toit.getOrientation();
    }
    
    public double getAngleToit(){
        return chalet.getToit().getAngle();
    }
    
    public void modifierDimensionChalet(double hauteur, double largeur, double profondeur){
        chalet.setHauteur(hauteur);
        chalet.setLargeur(largeur);
        chalet.setProfondeur(profondeur);
    }
    
    public void modifierConfiguration(double distanceAccessoire, double distanceRainureSupplementaire, double epaisseurMur, double grosseurCellule){
        Configuration.getInstance().setDistanceMinAccessoire(distanceAccessoire);
        Configuration.getInstance().setDistanceRainure(distanceRainureSupplementaire);
        Configuration.getInstance().setEpaisseur(epaisseurMur);
        Configuration.getInstance().setGrosseurCellule((int) grosseurCellule);
    }
    
    public void resetConfiguration(){
        Configuration.getInstance().resetConfiguration();
    }
    
    public List<AccessoireDTO> getAccessoiresParMur(Face typeMur){
        List<AccessoireDTO> accessoiresParMur = new ArrayList<>();
        
        for(Mur mur : chalet.murList){
            if(mur.getTypeMur() == typeMur){
                List<Accessoire> accessoireDuDomaine = new ArrayList<>();
                accessoireDuDomaine.addAll(mur.getAccessoireListe());
                for(Accessoire accessoire : accessoireDuDomaine){ 
                    AccessoireDTO accessoireDTO = new AccessoireDTO(accessoire);
                    accessoiresParMur.add(accessoireDTO);
                }
            }
        }
        return accessoiresParMur;
    }
    
    public void modifierAccessoire(UUID uuid, double positionX, 
            double positionY, double hauteur, double largeur, Face typeMur){
        
        for(Mur mur : chalet.murList){
            if(mur.getTypeMur() == typeMur){
                
                mur.modifierAccessoire(uuid, positionX, 
                    positionY, hauteur, largeur);
            }
        }
    }
    
    public boolean supprimerAccessoire(UUID uuid, Face typeMur){
        for(Mur mur : chalet.murList) {
            if(mur.getTypeMur() == typeMur){
               return mur.supprimerAccessoire(uuid);

            } 
        }
        return false;    
    }
    
    public AccessoireDTO creerPorte(double positionX, double largeur, double hauteur, Face typeMur)
    {
        AccessoireDTO accessoire = null;
        for (Mur mur : chalet.getMurList())
        {
            if(mur.getTypeMur() == typeMur)
            {
                accessoire = new AccessoireDTO(hauteur, largeur, positionX, 0);
                accessoire.estValide = mur.ajouterPorte(hauteur, largeur, positionX, 0);
                accessoire.id = mur.getAccessoireListe().get(mur.getAccessoireListe().size()-1).getUuid();                   
            }
        }
        return accessoire;
    }
    public AccessoireDTO creerFenetre(double positionX, double positionY, double largeur, double hauteur, Face typeMur)
    {
        AccessoireDTO accessoire = null;
        for (Mur mur : chalet.getMurList())
        {
            if(mur.getTypeMur() == typeMur)
            {
                accessoire = new AccessoireDTO(hauteur, largeur, positionX, positionY);
                accessoire.estValide = mur.ajouterFenetre(hauteur, largeur, positionX, positionY);
                accessoire.id = mur.getAccessoireListe().get(mur.getAccessoireListe().size()-1).getUuid();
            }
        }
        return accessoire;
    }
    
    public void changerAngleToit(double angle){
        chalet.getToit().setAngle(angle);
    }
    
    public void changerOrientationToit(Face orientation){
        chalet.getToit().setOrientation(orientation);
        chalet.setLargeur(chalet.largeur);//Pour changer les mur selon orientation
        chalet.setProfondeur(chalet.profondeur);
        
    }
    
    public void exporterSTL() {
        GestionnaireSTL ges = new GestionnaireSTL(chalet);
        
        if(ges.getPath()!= null){
        ges.exportBrutSTL();
        ges.exporterProjetFini();
        //exportFiniSTL();
        ges.exportRetraitSTL();
        }
                
    }
}


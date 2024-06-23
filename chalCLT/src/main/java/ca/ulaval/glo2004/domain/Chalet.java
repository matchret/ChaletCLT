
package ca.ulaval.glo2004.domain;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
/**
 * 
 * @author Mathieu Chretien
 */
public class Chalet implements Serializable {
    protected List<Mur> murList;
    protected Toit toit;
    protected double hauteur;
    protected double largeur;
    protected double profondeur;
    
    protected double distanceRainure;
    protected double distanceMinAccessoire;
    protected double epaisseur;
    protected double hauteurPorte;
    protected double largeurPorte;
    protected double basePorte;
    protected int grosseurCellule;
    private Configuration configuration;
    
    /**
     * Constructeur par défaut de la classe Chalet
     */
    public Chalet() {
        this.hauteur = 96;     //Valeur doir etre en pouce 8 pieds = 96 pouce
        this.largeur = 120;    //10 pieds = 120 pouce
        this.profondeur = 150;
        toit = new Toit(largeur, profondeur);
        this.configuration= Configuration.getInstance();
        
        this.murList = new ArrayList<>();
        
        this.murList.add(calculerDimensionMur(Face.FACADE));
        this.murList.add(calculerDimensionMur(Face.ARRIERE));
        this.murList.add(calculerDimensionMur(Face.GAUCHE));
        this.murList.add(calculerDimensionMur(Face.DROITE));
    }
    
    // Constructeur de copie
    public Chalet(Chalet original) {
        this.murList = new ArrayList<>(original.murList.size());
        for (Mur mur : original.murList) {
            this.murList.add(new Mur(mur)); //constructeur de copie
        }
        this.toit = new Toit(original.toit); //constructeur de copie
        this.hauteur = original.hauteur;
        this.largeur = original.largeur;
        this.profondeur = original.profondeur;
        this.configuration = new Configuration(original.configuration); //constructeur de copie
    }

    // Méthode pour obtenir une copie du chalet
    public Chalet copier() {
        return new Chalet(this);
    }
    
    public void setChalet(Chalet nouveauChalet){
        this.hauteur = nouveauChalet.hauteur;
        this.largeur = nouveauChalet.largeur;
        this.profondeur = nouveauChalet.profondeur;
        this.murList = nouveauChalet.murList;
        this.toit = nouveauChalet.toit;
           
        getConfiguration().setDistanceMinAccessoire( nouveauChalet.configuration.getDistanceMinAccessoire());
        getConfiguration().setDistanceRainure(nouveauChalet.configuration.getDistanceRainure());
        getConfiguration().setEpaisseur(nouveauChalet.configuration.getEpaisseur());
        getConfiguration().setGrosseurCellule(nouveauChalet.configuration.getGrosseurCellule()); 
        }
    
    /**
     * Accesseurs de la classe Chalet.
     */
    public List<Mur> getMurList() {
        return murList;
    }

    public Toit getToit() {
        return toit;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getProfondeur() {
        return profondeur;
    }
    
    /**
     * Mutateurs de la classe Chalet.
     */
    public void setMurList(List<Mur> murList) {
        this.murList = murList;
    }
    
    public void setToit(Toit toit) {
        this.toit = toit;
    }

     public void setHauteur(double hauteur) {//changer 4 mur
        double oldHauteur = this.hauteur;
        this.hauteur = hauteur;
        
        // Mettre à jour les dimensions des murs
        for (Mur mur : murList) {
                mur.setHauteur(hauteur);
                for(Accessoire a : mur.getAccessoireListe())
                {
                    if(a instanceof Fenetre)
                    {
                        double newY = a.getPositionY() * hauteur / oldHauteur;
                        a.setPositionY(newY);
                    }
                }
                
                for(Accessoire a : mur.getAccessoireListe())
                {
                    if(a instanceof Fenetre)
                    {
                        a.setEstValide(mur.validerFenetre((Fenetre)a));
                    }
                    else if(a instanceof Porte)
                    {
                        a.setEstValide(mur.validerPorte((Porte)a));
                    }
                }
            }
    }

    public void setLargeur(double largeur) {/*changer toit et mur(face arriere)*/
        double epaisseur=configuration.getEpaisseur();
        double oldLargeur = this.largeur;
        this.largeur = largeur;  

          
        // Mettre à jour les dimensions des murs
        for (Mur mur : murList) {
            if(mur.getTypeMur()==Face.FACADE || mur.getTypeMur()==Face.ARRIERE){
                             
                if(toit.getOrientation()==Face.FACADE||toit.getOrientation()==Face.ARRIERE){
                    mur.setLargeur(largeur);
                }
                else{
                    mur.setLargeur(largeur-epaisseur);
                }   
                for(Accessoire a : mur.getAccessoireListe())
                {
                    double newX = a.getPositionX() * largeur / oldLargeur;
                    a.setPositionX(newX);
                }                              
                for(Accessoire a : mur.getAccessoireListe())
                {
                    if(a instanceof Fenetre)
                    {
                        a.setEstValide(mur.validerFenetre((Fenetre)a));
                    }
                    else if(a instanceof Porte)
                    {
                        a.setEstValide(mur.validerPorte((Porte)a));
                    }
                }
            }   
        }
        
        // Mettre à jour les dimensions du toit
        toit.calculerDimensionToit(largeur,profondeur);
        toit.getPignon().setLargeur(toit.calculerLargeurPignon());
        toit.getPignon().setHauteur(toit.calculerHauteurPignon());
        toit.getVersant().setLongeur(toit.calculerLongueurVersant());
           
    }

    public void setProfondeur(double profondeur) {/*changer toit et mur(gauche droite)*/
        double epaisseur=getConfiguration().getEpaisseur();
        double oldProfondeur = this.profondeur;
        this.profondeur = profondeur;
    
        // Mettre à jour les dimensions des murs
        for (Mur mur : murList) {
            if(mur.getTypeMur()==Face.DROITE || mur.getTypeMur()==Face.GAUCHE){
                if(toit.getOrientation()==Face.DROITE||toit.getOrientation()==Face.GAUCHE){
                    mur.setLargeur(profondeur); 
                }
                else{
                    mur.setLargeur(profondeur-epaisseur); 
                }
                for(Accessoire a : mur.getAccessoireListe())
                {
                    double newX = a.getPositionX() * profondeur / oldProfondeur;
                    a.setPositionX(newX);
                }
                for(Accessoire a : mur.getAccessoireListe())
                {
                    if(a instanceof Fenetre )
                    {
                        a.setEstValide(!mur.validerFenetre((Fenetre)a));
                    }
                    else if(a instanceof Porte)
                    {
                        a.setEstValide(mur.validerPorte((Porte)a));
                    }
                }
            }           
        }
        
        // Mettre à jour les dimensions du toit
        toit.calculerDimensionToit(largeur,profondeur);
        toit.getPignon().setLargeur(toit.calculerLargeurPignon());
        toit.getPignon().setHauteur(toit.calculerHauteurPignon());
        toit.getVersant().setLongeur(toit.calculerLongueurVersant());
            
    }
    /**/
    private Mur calculerDimensionMur(Face typeMur){
        
    Mur murFinal;
    Face orientation = toit.getOrientation();
    double epaisseur=getConfiguration().getEpaisseur();
    
    if(typeMur==Face.FACADE||typeMur==Face.ARRIERE){   
        if(orientation==Face.FACADE||orientation==Face.ARRIERE){
            murFinal=new Mur(this.hauteur,this.largeur,typeMur);
        }
        else{
            murFinal=new Mur(this.hauteur,(this.largeur-epaisseur),typeMur);
        }
    }
    
    else{
        if(orientation==Face.FACADE||orientation==Face.ARRIERE){
            murFinal=new Mur(this.hauteur,(this.profondeur-epaisseur),typeMur);
        }
        else{
            murFinal=new Mur(this.hauteur,this.profondeur,typeMur);
        }
    }
    return murFinal;
    }
    
    public Configuration getConfiguration() {//Pour le DTO
        return Configuration.getInstance();
    }

}

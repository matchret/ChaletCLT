
package ca.ulaval.glo2004.domain;

import static ca.ulaval.glo2004.domain.Face.*;
import java.io.Serializable;

import java.lang.Math;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class Toit implements Serializable {
    
    private Pignon pignon;
    private Rallonge rallonge;
    private Versant versant;
    private Face orientation; //attribuer par facade
    private double angle;
    private double largeur;
    private double profondeur;
  
    public Toit(double largeur, double profondeur) {      

        this.rallonge = new Rallonge(0, 0);
        this.pignon = new Pignon(0,0 );
        this.versant = new Versant(0, 0);
        this.angle = 15;
        this.orientation = Face.FACADE;
        calculerDimensionToit(largeur,profondeur);
        this.rallonge.setHauteur(calculerHauteurRallonge());
        this.pignon.setHauteur(calculerHauteurPignon());
    }
    
    // Constructeur de copie
    public Toit(Toit original) {
        this.pignon = new Pignon(original.pignon);
        this.rallonge = new Rallonge(original.rallonge); 
        this.versant = new Versant(original.versant);
        this.orientation = original.orientation;
        this.angle = original.angle;
        this.largeur = original.largeur;
        this.profondeur = original.profondeur;
    }

    public Face getOrientation() {
        return orientation;
    }
    
    public Pignon getPignon() {
        return pignon;
    }

    public Rallonge getRallonge() {
        return rallonge;
    }

    public Versant getVersant() {
        return versant;
    }

    public double getAngle() {
        return angle;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getProfondeur() {
        return profondeur;
    }

    public void setPignon(Pignon pignon) {
        this.pignon = pignon;
    }

    public void setRallonge(Rallonge Rallonge) {
        this.rallonge = Rallonge;
    }

    public void setVersant(Versant Versant) {
        this.versant = Versant;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        
        this.pignon.setHauteur(calculerHauteurPignon());
        this.rallonge.setHauteur(calculerHauteurRallonge());
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;     
        
        this.rallonge.setLargeur(largeur);
        this.versant.setLargeur(largeur);
    }

    public void setProfondeur(double profondeur) {
        double epaisseur = Configuration.getInstance().getEpaisseur();
        
        this.profondeur = profondeur;
        
        this.pignon.setLargeur(profondeur-epaisseur);
        this.versant.setLongeur(calculerLongueurVersant());
    }
    
    public void setOrientation (Face orientation){
        this.orientation = orientation;           
    }
        
    protected double calculerLargeurPignon(){ 
        double epaisseur = Configuration.getInstance().getEpaisseur();
        return profondeur-epaisseur;

    }
    
    private double calculerHauteurRallonge(){
        return largeur * Math.tan(Math.toRadians(angle));
    }
    
    protected double calculerHauteurPignon(){
        double demiEpaisseur = Configuration.getInstance().getEpaisseur()/2;
        return rallonge.getHauteur()- demiEpaisseur * Math.tan(Math.toRadians(angle));
    }
    
    protected double calculerLongueurVersant(){
        double demiEpaisseur = Configuration.getInstance().getEpaisseur()/2;
        double topVersant = demiEpaisseur/Math.cos(Math.toRadians(angle));
        
        double longueurVersant = Math.sqrt(Math.pow(rallonge.getHauteur()+topVersant, 2) + Math.pow(profondeur, 2));
        return longueurVersant;
    }
    
    protected void calculerDimensionToit(double largeurChalet, double profondeurChalet){    
        switch(orientation){
            case FACADE:
            case ARRIERE:       
                setLargeur(largeurChalet);
                setProfondeur(profondeurChalet);
            break; 
            default:
                setLargeur(profondeurChalet);
                setProfondeur(largeurChalet);
            break; 
        }
    }
}

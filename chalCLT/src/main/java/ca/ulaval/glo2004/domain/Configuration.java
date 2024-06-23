
package ca.ulaval.glo2004.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 *
 * @author Mathieu Chretien, Pierre-Olivier Tremblay
 */
public class Configuration implements Serializable {
    private double distanceRainure;
    private double distanceMinAccessoire;
    private double epaisseur;
    private double hauteurPorte;
    private double largeurPorte;
    private double basePorte;
    private int grosseurCellule;
    
    private static Configuration instance; /*Modele Singleton:Lazy Initialization
    *Exemple d'appel:Configuration config = Configuration.getInstance();*/
    
    public Configuration() {
        this.distanceMinAccessoire = 3;
        this.epaisseur = 6;
        this.distanceRainure = 0.5; // Distance rainure est la distance supplémentaire lors de l'exportation d'un STL brut
        this.grosseurCellule = 2;
        this.basePorte = 3;
    }
    
    // Constructeur de copie
    public Configuration(Configuration original) {
        this.distanceRainure = original.distanceRainure;
        this.distanceMinAccessoire = original.distanceMinAccessoire;
        this.epaisseur = original.epaisseur;
        this.hauteurPorte = original.hauteurPorte;
        this.largeurPorte = original.largeurPorte;
        this.basePorte = original.basePorte;
        this.grosseurCellule = original.grosseurCellule;
    }
    
    public static Configuration getInstance() {/*Modele Singleton: Methode pour recevoir l'instance config*/
        if (instance == null) {          
                instance = new Configuration();
        }
        return instance;
    }
     
    // Accesseurs
    public double getDistanceRainure() {
        return distanceRainure;
    }
    
    public double getDistanceMinAccessoire() {
        return distanceMinAccessoire;
    }

    public double getEpaisseur() {
        return epaisseur;
    }
    
    public double getHauteurPorte(){
        return hauteurPorte;
    }
    
    public double getLargeurPorte(){
        return largeurPorte;
    }
    
    public int getGrosseurCellule(){
        return grosseurCellule;
    }
    
    public double getBasePorte(){
        return basePorte;
    }
    

    // Mutateurs
    public void setDistanceMinAccessoire(double distanceMinAccessoire) {
        this.distanceMinAccessoire = distanceMinAccessoire;
    }

    public void setEpaisseur(double epaisseur) {
        this.epaisseur = epaisseur;
    }
    
    public void setDistanceRainure(double distanceRainure){
        this.distanceRainure = distanceRainure;
    }
    
    public void setGrosseurCellule(int grosseurCellule){
        this.grosseurCellule = grosseurCellule;
    }
    
    public void resetConfiguration(){
        instance = null;     
    }
    
    static public void setConfig(Configuration configuration){
        instance = configuration; 
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        instance = this;
    }
}

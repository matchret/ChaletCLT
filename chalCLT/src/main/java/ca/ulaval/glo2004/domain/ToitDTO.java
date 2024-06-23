
package ca.ulaval.glo2004.domain;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class ToitDTO {
    public double largeur;
    public double profondeur;
    public Face orientation; //attribuer par facade
    public double angle;
    public PignonDTO pignonDTO;
    public RallongeDTO rallongeDTO;
    public VersantDTO versantDTO;
    
    public ToitDTO(Toit toit) {
    angle = toit.getAngle();
    largeur = toit.getLargeur();
    profondeur = toit.getProfondeur();
    orientation = toit.getOrientation();
    pignonDTO = new PignonDTO(toit.getPignon());
    rallongeDTO = new RallongeDTO(toit.getRallonge());
    versantDTO = new VersantDTO(toit.getVersant());
    
    }
}


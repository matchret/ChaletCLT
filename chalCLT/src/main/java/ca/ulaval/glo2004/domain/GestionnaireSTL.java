
package ca.ulaval.glo2004.domain;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import ca.ulaval.glo2004.gui.FileChooser;
import java.lang.Math;

/**
 *
 * @author Jonathan Dion, Thomas Ouellet
 */
public class GestionnaireSTL {
    private Chalet chalet;
    private String Path;

    public GestionnaireSTL(Chalet chalet) {
        this.chalet = chalet;
        Path = FileChooser.getFilePath();
    }
    
    protected String getPath(){
        return Path;
    }
    
    public void exportBrutSTL(){
        try{
            Configuration config = Configuration.getInstance();

            for(Mur mur: chalet.getMurList()){
                String lettre = mur.getTypeMur().toString().substring(0, 1);
                FileWriter ecrireFichier = new FileWriter(Path + "/Projet_Brut_" + lettre + ".stl");
                PrintWriter imprimerFichier = new PrintWriter(ecrireFichier);
                double[][] sommet = {
                            {0, 0, 0},
                            {0, config.getEpaisseur(), 0},
                            {mur.getLargeur(), config.getEpaisseur(), 0},
                            {mur.getLargeur(), 0, 0},   // 4 sommets du bas

                            {0, 0, mur.getHauteur()},
                            {0, config.getEpaisseur(), mur.getHauteur()},
                            {mur.getLargeur(), config.getEpaisseur(), mur.getHauteur()},
                            {mur.getLargeur(), 0, mur.getHauteur()}    // 4 sommets du haut
                    };

                int[][] faces = {
                            {0, 1, 2}, {3, 0, 2},   // Bas
                            {4, 5, 6}, {4, 6, 7},   // Haut
                            {0, 1, 5}, {0, 5, 4},   // Côté
                            {2, 3, 7}, {2, 7, 6},
                            {0, 3, 7}, {0, 7, 4},
                            {1, 2, 6}, {1, 6, 5}
                    };

                // Écrire dans le fichier

                // Écrire le header
                imprimerFichier.println("solid murBrut_" + lettre);
                //Écrire les faces et sommets
                for (int[] face : faces) {
                    imprimerFichier.println("facet normal 0 0 0");
                    imprimerFichier.println("outer loop");
                    for (int i : face) {
                        double[] vertex = sommet[i];
                        imprimerFichier.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                    }
                imprimerFichier.println("endloop");
                imprimerFichier.println("endfacet");

                }
                // Écrire le footer
                imprimerFichier.printf("endsolid murBrut_%s", lettre);
                // Fermer le fichier
                imprimerFichier.close();
            }
            
            for(int i = 0;i<2;i++){
                String lettres;
                if(i==0){
                    lettres = "PD";
                }
                else{
                    lettres = "PG";
                }
                FileWriter ecrireFichier = new FileWriter(Path + "/Projet_Brut_" + lettres + ".stl");
                PrintWriter imprimerFichier = new PrintWriter(ecrireFichier);
                
                Pignon pignon = chalet.getToit().getPignon();
                // Define triangular prism vertices
                double[][] sommet = {
                        {0, 0, 0}, {pignon.getLargeur(), 0, 0}, {pignon.getLargeur(), pignon.getHauteur(), 0},  // Sommet bas
                        {0, 0, config.getEpaisseur()}, {pignon.getLargeur(), 0, config.getEpaisseur()}, {pignon.getLargeur(), pignon.getHauteur(), config.getEpaisseur()}   // Sommet haut
                };

                // Define triangular prism faces
                int[][] faces = {
                        {0, 1, 2}, {3, 4, 5},   // Faces haut et bas
                        {0, 1, 4}, {0, 4, 3},   // Faces côté
                        {1, 2, 5}, {1, 5, 4},
                        {2, 0, 3}, {2, 3, 5}
                };
                
                // Write STL header
                imprimerFichier.println("solid pignon");
                
                for (int[] face : faces) {
                    imprimerFichier.println("facet normal 0 0 0");
                    imprimerFichier.println("outer loop");
                    for (int j : face) {
                        double[] vertex = sommet[j];
                        imprimerFichier.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                    }
                imprimerFichier.println("endloop");
                imprimerFichier.println("endfacet");

                }
                // Write STL footer
                imprimerFichier.println("endsolid pignon");
                // Fermer le fichier
                imprimerFichier.close();
            }
            
            FileWriter ecrireFichier = new FileWriter(Path + "/Projet_Brut_R.stl");
            PrintWriter imprimerFichier = new PrintWriter(ecrireFichier);
            Rallonge rallonge = chalet.getToit().getRallonge();
            double[][] sommet = {
                        {0, 0, 0},
                        {0, 0, config.getEpaisseur()},
                        {rallonge.getLargeur(), 0, config.getEpaisseur()},
                        {rallonge.getLargeur(), 0, 0},   // 4 sommets du bas

                        {0, rallonge.getHauteur(), 0},
                        {0, rallonge.getHauteur(), config.getEpaisseur()},
                        {rallonge.getLargeur(), rallonge.getHauteur(), config.getEpaisseur()},
                        {rallonge.getLargeur(), rallonge.getHauteur(), 0}    // 4 sommets du haut
                };

            int[][] faces = {
                        {0, 1, 2}, {3, 0, 2},   // Bas
                        {4, 5, 6}, {4, 6, 7},   // Haut
                        {0, 1, 5}, {0, 5, 4},   // Côté
                        {2, 3, 7}, {2, 7, 6},
                        {0, 3, 7}, {0, 7, 4},
                        {1, 2, 6}, {1, 6, 5}
                };

            // Écrire dans le fichier

            // Écrire le header
            imprimerFichier.println("solid rallonge");
            //Écrire les faces et sommets
            for (int[] face : faces) {
                imprimerFichier.println("facet normal 0 0 0");
                imprimerFichier.println("outer loop");
                for (int i : face) {
                    double[] vertex = sommet[i];
                    imprimerFichier.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                }
            imprimerFichier.println("endloop");
            imprimerFichier.println("endfacet");

            }
            // Écrire le footer
            imprimerFichier.printf("endsolid rallonge");
            // Fermer le fichier
            imprimerFichier.close();
            
            FileWriter ecrireFichier1 = new FileWriter(Path + "/Projet_Brut_T.stl");
            PrintWriter imprimerFichier1 = new PrintWriter(ecrireFichier1);
            Versant versant = chalet.getToit().getVersant();
            double[][] sommet1 = {
                        {0, 0, 0},
                        {0, 0, config.getEpaisseur()},
                        {versant.getLargeur(), 0, config.getEpaisseur()},
                        {versant.getLargeur(), 0, 0},   // 4 sommets du bas

                        {0, versant.getLongueur(), rallonge.getHauteur()},
                        {0, versant.getLongueur(), config.getEpaisseur()+rallonge.getHauteur()},
                        {versant.getLargeur(), versant.getLongueur(), config.getEpaisseur()+rallonge.getHauteur()},
                        {versant.getLargeur(), versant.getLongueur(), rallonge.getHauteur()}    // 4 sommets du haut
                };

            int[][] faces1 = {
                        {0, 1, 2}, {3, 0, 2},   // Bas
                        {4, 5, 6}, {4, 6, 7},   // Haut
                        {0, 1, 5}, {0, 5, 4},   // Côté
                        {2, 3, 7}, {2, 7, 6},
                        {0, 3, 7}, {0, 7, 4},
                        {1, 2, 6}, {1, 6, 5}
                };

            // Écrire dans le fichier

            // Écrire le header
            imprimerFichier1.println("solid versant");
            //Écrire les faces et sommets
            for (int[] face1 : faces1) {
                imprimerFichier1.println("facet normal 0 0 0");
                imprimerFichier1.println("outer loop");
                for (int i : face1) {
                    double[] vertex1 = sommet1[i];
                    imprimerFichier1.println(String.format("  vertex %f %f %f", vertex1[0], vertex1[1], vertex1[2]));
                }
            imprimerFichier1.println("endloop");
            imprimerFichier1.println("endfacet");

            }
            // Écrire le footer
            imprimerFichier1.printf("endsolid versant");
            // Fermer le fichier
            imprimerFichier1.close();
            
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
   
    public void exporterProjetFini() {
        try {
            genererMursFinisSTL();
            genererToitFiniSTL();
        }
        catch(Exception e) {
            System.out.println(e.toString()); 
        }
    }
    
    private void genererToitFiniSTL() throws IOException {
        // Versant
        ArrayList<double[][]> trianglesVersant = genererTrianglesVersant(chalet.toit.getVersant());
        String lettre = "T";
        FileWriter ecrireVersant = new FileWriter(Path + "/Projet_Fini_" + lettre + ".stl");
        PrintWriter imprimerVersant = new PrintWriter(ecrireVersant);
        imprimerVersant.println("solid versant");
        for(double[][] triangle: trianglesVersant) {
            this.ajouterTriangleSTL(triangle[0], triangle[1], triangle[2], imprimerVersant);
        }             
        imprimerVersant.println("endsolid versant");
        imprimerVersant.close();
        
        // Pignon
        for (int i = 0; i < 2; i++) {
            lettre = "PG";
            boolean inverse = false;
            
            if(i == 1) {
                lettre = "PD";
                inverse = true;
            }
            ArrayList<double[][]> trianglesPignon = genererTrianglesPignon(chalet.toit.getPignon(), inverse);


            FileWriter ecrirePignon = new FileWriter(Path + "/Projet_Fini_" + lettre + ".stl");
            PrintWriter imprimerPigon = new PrintWriter(ecrirePignon);
            imprimerPigon.println("solid pignon");
            for(double[][] triangle: trianglesPignon) {
                this.ajouterTriangleSTL(triangle[0], triangle[1], triangle[2], imprimerPigon);
            }             
            imprimerPigon.println("endsolid pignon");
            imprimerPigon.close();
        }

        // Rallonge
        ArrayList<double[][]> trianglesRallonge = genererTrianglesRallonge(chalet.toit.getRallonge());
        String lettreRallonge = "R";
        FileWriter ecrireRallonge = new FileWriter(Path + "/Projet_Fini_" + lettreRallonge + ".stl");
        PrintWriter imprimerRallonge = new PrintWriter(ecrireRallonge);
        imprimerRallonge.println("solid rallonge");
        for(double[][] triangle: trianglesRallonge) {
            this.ajouterTriangleSTL(triangle[0], triangle[1], triangle[2], imprimerRallonge);
        }
        imprimerRallonge.println("endsolid rallonge");
        imprimerRallonge.close();
    }
    
    private ArrayList<double[][]> genererTrianglesVersant(Versant versant) {
        ArrayList<double[][]> trianglesVersant = new ArrayList<double[][]>();
        
        double epaisseur = chalet.getConfiguration().getEpaisseur();
        double distSup = chalet.getConfiguration().getDistanceRainure();
        double angle = chalet.toit.getAngle();
        Face orientation = chalet.toit.getOrientation();
        double largeur = orientation == Face.ARRIERE || orientation == Face.FACADE ? chalet.getLargeur() : chalet.getProfondeur();
        double profondeur = orientation == Face.ARRIERE || orientation == Face.FACADE ? chalet.getProfondeur() : chalet.getLargeur();
        
        double hauteurBasVersant = (epaisseur/2)/Math.sin(Math.toRadians(90-angle));
        double hauteurVersant = hauteurBasVersant + (profondeur * Math.tan(Math.toRadians(angle)));
        double angleRainureY = ((epaisseur/2) + distSup) * Math.tan(Math.toRadians(angle));
       
        double[] versantBasGaucheNear = { 0, 0, -distSup };
        double[] versantBasMidNear = { epaisseur/2, 0, -distSup }; 
        double[] versantBasMidInNear = { epaisseur/2, epaisseur/2 - distSup, -distSup }; 
        double[] versantBasDroiteNear = { epaisseur, epaisseur/2 - distSup, -distSup };
        double[] versantBasTopNear = { 0, 0, hauteurBasVersant };
        double[] versantHautTopNear = { profondeur, 0, hauteurVersant };
        double[] versantHautDroiteNear = { profondeur, 0, hauteurVersant - hauteurBasVersant - distSup };
        double[] versantHautGaucheNear = { profondeur - epaisseur/2 + distSup, epaisseur/2 - distSup, hauteurVersant - hauteurBasVersant - distSup - angleRainureY };
        double[] versantRainureDroiteNear = { profondeur - epaisseur/2 + distSup, epaisseur/2 - distSup, hauteurVersant - hauteurBasVersant - distSup - angleRainureY - hauteurBasVersant - distSup };
            
        double[] versantBasGaucheFar = { 0, largeur, -distSup };
        double[] versantBasMidFar = { epaisseur/2, largeur, -distSup }; 
        double[] versantBasMidInFar = { epaisseur/2, largeur + distSup - epaisseur/2, -distSup }; 
        double[] versantBasDroiteFar = { epaisseur, largeur + distSup - epaisseur/2, -distSup };
        double[] versantBasTopFar = { 0, largeur, hauteurBasVersant };
        double[] versantHautTopFar = { profondeur, largeur, hauteurVersant };
        double[] versantHautDroiteFar = { profondeur, largeur, hauteurVersant - hauteurBasVersant - distSup };
        double[] versantHautGaucheFar = { profondeur - epaisseur/2 + distSup, largeur + distSup - epaisseur/2, hauteurVersant - hauteurBasVersant - distSup - angleRainureY };
        double[] versantRainureDroiteFar = { profondeur - epaisseur/2 + distSup, largeur + distSup - epaisseur/2, hauteurVersant - hauteurBasVersant - distSup - angleRainureY - hauteurBasVersant - distSup };
        
        
        trianglesVersant.add(new double[][]{versantBasGaucheNear, versantBasMidNear, versantBasGaucheFar});
        trianglesVersant.add(new double[][]{versantBasMidNear, versantBasMidFar, versantBasGaucheFar});
        
        trianglesVersant.add(new double[][]{versantBasGaucheNear, versantBasGaucheFar, versantBasTopNear});
        trianglesVersant.add(new double[][]{versantBasGaucheFar, versantBasTopNear, versantBasTopFar});
        
        trianglesVersant.add(new double[][]{versantBasMidInNear, versantBasDroiteNear, versantBasDroiteFar});
        trianglesVersant.add(new double[][]{versantBasMidInNear, versantBasMidInFar, versantBasDroiteFar});
        
        trianglesVersant.add(new double[][]{versantBasTopNear, versantBasTopFar, versantHautTopNear});
        trianglesVersant.add(new double[][]{versantBasTopFar,  versantHautTopFar, versantHautTopNear});
        
        trianglesVersant.add(new double[][]{versantHautTopNear, versantHautTopFar, versantHautDroiteFar});
        trianglesVersant.add(new double[][]{versantHautTopNear,  versantHautDroiteNear, versantHautDroiteFar});
        
        trianglesVersant.add(new double[][]{versantHautDroiteNear, versantHautGaucheFar, versantHautGaucheNear});
        trianglesVersant.add(new double[][]{versantHautDroiteNear, versantHautDroiteFar, versantHautGaucheFar});
        
        trianglesVersant.add(new double[][]{versantHautGaucheNear, versantRainureDroiteNear, versantRainureDroiteFar});
        trianglesVersant.add(new double[][]{versantHautGaucheNear, versantHautGaucheFar, versantRainureDroiteFar});
        
        trianglesVersant.add(new double[][]{versantRainureDroiteNear, versantBasDroiteFar, versantBasDroiteNear});
        trianglesVersant.add(new double[][]{versantRainureDroiteNear, versantRainureDroiteFar, versantBasDroiteFar});
        
        trianglesVersant.add(new double[][]{versantHautDroiteNear, versantHautGaucheNear, versantBasMidNear});
        trianglesVersant.add(new double[][]{versantBasMidNear, versantBasMidInNear, versantHautGaucheNear});
        trianglesVersant.add(new double[][]{versantHautDroiteFar, versantHautGaucheFar, versantBasMidFar});
        trianglesVersant.add(new double[][]{versantBasMidFar, versantBasMidInFar, versantHautGaucheFar});
        
        trianglesVersant.add(new double[][]{versantBasMidInNear, versantBasDroiteNear, versantHautGaucheNear});
        trianglesVersant.add(new double[][]{versantHautGaucheNear, versantRainureDroiteNear, versantBasDroiteNear});
        
        trianglesVersant.add(new double[][]{versantBasMidInFar, versantBasDroiteFar, versantHautGaucheFar});
        trianglesVersant.add(new double[][]{versantHautGaucheFar, versantRainureDroiteFar, versantBasDroiteFar});
        
        trianglesVersant.add(new double[][]{versantBasGaucheNear, versantBasMidNear, versantHautDroiteNear});
        trianglesVersant.add(new double[][]{versantBasGaucheNear, versantHautDroiteNear, versantHautTopNear});
        trianglesVersant.add(new double[][]{versantBasTopNear, versantBasGaucheNear, versantHautTopNear});
        
        trianglesVersant.add(new double[][]{versantBasGaucheFar, versantBasMidFar, versantHautDroiteFar});
        trianglesVersant.add(new double[][]{versantBasGaucheFar, versantHautDroiteFar, versantHautTopFar});
        trianglesVersant.add(new double[][]{versantBasTopFar, versantBasGaucheFar, versantHautTopFar});
        
        trianglesVersant = this.deplacerTriangles(trianglesVersant, new double[]{0,-chalet.getLargeur(),chalet.getHauteur()});
        trianglesVersant = this.faireTournerTriangles(trianglesVersant, 90);
        
        return trianglesVersant;
    }
    
    private ArrayList<double[][]> genererTrianglesPignon(Pignon pignon, boolean inverse) {
        ArrayList<double[][]> trianglesPignon = new ArrayList<double[][]>();      
        double epaisseur = chalet.getConfiguration().getEpaisseur();
        double distSup = chalet.getConfiguration().getDistanceRainure();
        double angle = chalet.toit.getAngle();
        
        double maxProf = epaisseur;
        double minProf = 0;
        
        if(inverse) {
            maxProf = 0;
            minProf = epaisseur;
        }
        
        double longueurPignon = pignon.getLargeur();
        double hauteurPignon = Math.tan(Math.toRadians(angle)) * longueurPignon;
        double longueurRIGaucheDroite = pignon.getLargeur() - 2 * epaisseur + distSup;
        double hauteurRI = Math.tan(Math.toRadians(angle)) * longueurRIGaucheDroite;
        
        double[] pointeGaucheNear = {0, minProf, 0};
        double[] pointeDroiteNear = {pignon.getLargeur(), minProf, 0};
        double[] pointeHautNear = {pignon.getLargeur(), minProf, hauteurPignon};
        
        double[] pointeGaucheRO = {0, epaisseur/2 + distSup, 0};
        double[] pointeDroiteRO = {pignon.getLargeur(), epaisseur/2 + distSup, 0};
        double[] pointeHautRO = {pignon.getLargeur(), epaisseur/2 + distSup, hauteurPignon};
        
        double[] pointeGaucheRI = {epaisseur-distSup, epaisseur/2 + distSup, 0};
        double[] pointeDroiteRI = {pignon.getLargeur()- epaisseur + distSup, epaisseur/2 + distSup, 0};
        double[] pointeHautRI = {pignon.getLargeur() - epaisseur + distSup, epaisseur/2 + distSup, hauteurRI};
        
        double[] pointeGaucheFar = {epaisseur-distSup, maxProf, 0};
        double[] pointeDroiteFar = {pignon.getLargeur() - epaisseur + distSup, maxProf, 0};
        double[] pointeHautFar = {pignon.getLargeur() - epaisseur + distSup, maxProf, hauteurRI};
        
        // Face avant
        trianglesPignon.add(new double[][] {pointeGaucheNear, pointeDroiteNear, pointeHautNear});
        
        // Face dessous
        trianglesPignon.add(new double[][] {pointeGaucheNear, pointeDroiteNear, pointeDroiteRO});
        trianglesPignon.add(new double[][] {pointeGaucheNear, pointeDroiteRO, pointeGaucheRO});
        
        // Face hypothenuse
        trianglesPignon.add(new double[][] {pointeGaucheNear, pointeHautNear, pointeHautRO});
        trianglesPignon.add(new double[][] {pointeHautRO, pointeGaucheNear, pointeGaucheRO});
        
        // Face droite
        trianglesPignon.add(new double[][] {pointeDroiteNear, pointeHautNear, pointeHautRO});
        trianglesPignon.add(new double[][] {pointeDroiteNear, pointeHautRO, pointeDroiteRO});
        
        // Faces Rainure
        trianglesPignon.add(new double[][] {pointeGaucheRO, pointeGaucheRI, pointeHautRI});
        trianglesPignon.add(new double[][] {pointeGaucheRO, pointeHautRO, pointeHautRI});
        trianglesPignon.add(new double[][] {pointeHautRO, pointeHautRI, pointeDroiteRO});
        trianglesPignon.add(new double[][] {pointeHautRI, pointeDroiteRO, pointeDroiteRI});
        
        trianglesPignon.add(new double[][] {pointeGaucheRI, pointeGaucheFar, pointeHautFar});
        trianglesPignon.add(new double[][] {pointeGaucheRI, pointeHautFar, pointeHautRI});
        trianglesPignon.add(new double[][] {pointeHautRI, pointeHautFar, pointeDroiteRI});
        trianglesPignon.add(new double[][] {pointeDroiteRI, pointeDroiteFar, pointeHautFar});
        trianglesPignon.add(new double[][] {pointeGaucheRI, pointeGaucheFar, pointeDroiteFar});
        trianglesPignon.add(new double[][] {pointeDroiteFar, pointeGaucheRI, pointeDroiteRI});
        
        trianglesPignon.add(new double[][] {pointeDroiteFar, pointeGaucheFar, pointeHautFar});
        
        double rotateAngle = 90;
       
        trianglesPignon = this.faireTournerTriangles(trianglesPignon, rotateAngle);
        if(inverse) {
            trianglesPignon = this.deplacerTriangles(trianglesPignon, new double[] {epaisseur, 0, chalet.getHauteur()});
        }
        else {
            trianglesPignon = this.deplacerTriangles(trianglesPignon, new double[] {chalet.getLargeur(), 0, chalet.getHauteur()});
        }

        return trianglesPignon;
    }
    
    private ArrayList<double[][]> genererTrianglesRallonge(Rallonge rallonge) {
        ArrayList<double[][]> trianglesRallonge = new ArrayList<double[][]>();
        
        double epaisseur = chalet.getConfiguration().getEpaisseur();
        double distSup = chalet.getConfiguration().getDistanceRainure();
        double angle = chalet.toit.getAngle();
        Face orientation = chalet.toit.getOrientation();
        double profondeur = orientation == Face.ARRIERE || orientation == Face.FACADE ? chalet.getProfondeur() : chalet.getLargeur();
        double largeur = orientation == Face.ARRIERE || orientation == Face.FACADE ? chalet.getLargeur() : chalet.getProfondeur();
        
        double hauteurBasVersant = (epaisseur/2)/Math.sin(Math.toRadians(90-angle));
        double hauteurVersant = (profondeur * Math.tan(Math.toRadians(angle)));
        double angleRainureY = ((epaisseur/2) + distSup) * Math.tan(Math.toRadians(angle));

        double[] gaucheBasNear = {0, 0, 0};
        double[] gaucheMidNear = {0, 0, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant };
        double[] gaucheHautNear = {0, 0, hauteurVersant - hauteurBasVersant + distSup};
        
        double[] gaucheBasFar = {0, largeur, 0};
        double[] gaucheMidFar = {0, largeur, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant };
        double[] gaucheHautFar = {0, largeur, hauteurVersant - hauteurBasVersant + distSup};
        
        double[] basRainureNear = {epaisseur/2 + distSup, 0, 0};
        double[] midRainureNear = {epaisseur/2 + distSup, 0, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant };
        double[] hautRainureNear = {epaisseur/2 + distSup, 0, hauteurVersant - hauteurBasVersant + distSup - angleRainureY};
        
        double[] basRainureFar = {epaisseur/2 + distSup, largeur, 0};
        double[] midRainureFar = {epaisseur/2 + distSup, largeur, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant };
        double[] hautRainureFar = {epaisseur/2 + distSup, largeur, hauteurVersant - hauteurBasVersant + distSup - angleRainureY};
        
        double[] basRainureInNear = {epaisseur/2 + distSup, epaisseur/2 - distSup, 0};
        double[] midRainureInNear = {epaisseur/2 + distSup, epaisseur/2 - distSup, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant };
        double[] hautRainureInNear = {epaisseur/2 + distSup, epaisseur/2 - distSup, hauteurVersant - hauteurBasVersant + distSup - angleRainureY};
        
        double[] basRainureInFar = {epaisseur/2 + distSup, chalet.getLargeur() - epaisseur/2 + distSup, 0};
        double[] midRainureInFar = {epaisseur/2 + distSup, chalet.getLargeur() - epaisseur/2 + distSup, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant };
        double[] hautRainureInFar = {epaisseur/2 + distSup, chalet.getLargeur() - epaisseur/2 + distSup, hauteurVersant - hauteurBasVersant + distSup - angleRainureY};
        
        double[] droiteBasNear = {epaisseur, epaisseur/2 - distSup, 0};
        double[] droiteHautNear = {epaisseur, epaisseur/2 - distSup, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant - angleRainureY};
        
        double[] droiteBasFar = {epaisseur, chalet.getLargeur() - epaisseur/2 + distSup, 0};
        double[] droiteHautFar = {epaisseur, chalet.getLargeur() - epaisseur/2 + distSup, hauteurVersant - hauteurBasVersant + distSup - angleRainureY - hauteurBasVersant - angleRainureY};

        // Côté extérieur
        trianglesRallonge.add(new double[][] {gaucheBasNear, gaucheHautNear, gaucheBasFar});
        trianglesRallonge.add(new double[][] {gaucheBasFar, gaucheHautFar, gaucheHautNear});
        trianglesRallonge.add(new double[][] {midRainureNear, hautRainureNear, gaucheHautNear});
        trianglesRallonge.add(new double[][] {midRainureNear, gaucheHautNear, gaucheMidNear});
        
        // Côté proche 
        trianglesRallonge.add(new double[][] {gaucheBasNear, gaucheMidNear, basRainureNear});
        trianglesRallonge.add(new double[][] {basRainureNear, gaucheMidNear, midRainureNear});
        trianglesRallonge.add(new double[][] {midRainureNear, hautRainureNear, gaucheHautNear});
        trianglesRallonge.add(new double[][] {midRainureNear, gaucheHautNear, gaucheMidNear});
        
        // Côté loin 
        trianglesRallonge.add(new double[][] {gaucheBasFar, gaucheMidFar, basRainureFar});
        trianglesRallonge.add(new double[][] {basRainureFar, gaucheMidFar, midRainureFar});
        trianglesRallonge.add(new double[][] {midRainureFar, hautRainureFar, gaucheHautFar});
        trianglesRallonge.add(new double[][] {midRainureFar, gaucheHautFar, gaucheMidFar});
        
        // Face top
        trianglesRallonge.add(new double[][] {gaucheHautNear, gaucheHautFar, hautRainureFar});
        trianglesRallonge.add(new double[][] {gaucheHautNear, hautRainureFar, hautRainureNear});
               
        // Face bottom
        trianglesRallonge.add(new double[][] {gaucheBasNear, gaucheBasFar, basRainureNear});
        trianglesRallonge.add(new double[][] {basRainureNear, gaucheBasFar, basRainureFar});
        
        trianglesRallonge.add(new double[][] {droiteBasNear, basRainureInNear, droiteBasFar});
        trianglesRallonge.add(new double[][] {basRainureInNear, droiteBasFar, basRainureInFar});
        
        // Rainure in
        trianglesRallonge.add(new double[][] {hautRainureNear, midRainureInNear, midRainureNear});
        trianglesRallonge.add(new double[][] {midRainureNear, basRainureNear, midRainureInNear});
        trianglesRallonge.add(new double[][] {midRainureInNear, basRainureNear, basRainureInNear});
        
        trianglesRallonge.add(new double[][] {hautRainureNear, hautRainureFar, midRainureInNear});
        trianglesRallonge.add(new double[][] {midRainureInNear, hautRainureFar, midRainureInFar});
        
        trianglesRallonge.add(new double[][] {hautRainureFar, midRainureInFar, midRainureFar});
        trianglesRallonge.add(new double[][] {midRainureFar, basRainureFar, midRainureInFar});
        trianglesRallonge.add(new double[][] {midRainureInFar, basRainureFar, basRainureInFar});
        
        trianglesRallonge.add(new double[][] {midRainureInNear, droiteHautNear, droiteBasNear});
        trianglesRallonge.add(new double[][] {midRainureInNear, basRainureInNear, droiteBasNear});
        
        trianglesRallonge.add(new double[][] {midRainureInFar, droiteHautFar, droiteBasFar});
        trianglesRallonge.add(new double[][] {midRainureInFar, basRainureInFar, droiteBasFar});
        
        // Rainure top 
        trianglesRallonge.add(new double[][] {gaucheHautNear, gaucheHautFar, hautRainureFar});
        trianglesRallonge.add(new double[][] {gaucheHautNear, hautRainureFar, hautRainureNear});
        
        trianglesRallonge.add(new double[][] {droiteHautNear, droiteHautFar, midRainureInFar});
        trianglesRallonge.add(new double[][] {droiteHautNear, midRainureInFar, midRainureInNear});
        
        // coté intérieur
        trianglesRallonge.add(new double[][] {droiteHautNear, droiteHautFar, droiteBasNear});
        trianglesRallonge.add(new double[][] {droiteBasNear, droiteHautFar, droiteBasFar});
        
        trianglesRallonge = this.faireTournerTriangles(trianglesRallonge, -90);
        trianglesRallonge = this.deplacerTriangles(trianglesRallonge, new double[]{0, chalet.getProfondeur() + distSup, chalet.getHauteur()});
        
        return trianglesRallonge;
    }
    
    private void genererMursFinisSTL() throws IOException {
        for(Mur mur : chalet.getMurList()) {
            ArrayList<double[][]> trianglesMur = genererTrianglesMur(mur);
            
            String lettre = mur.getTypeMur().toString().substring(0, 1);
            FileWriter ecrireFichier = new FileWriter(Path + "/Projet_Fini_" + lettre + ".stl");
            PrintWriter imprimerFichier = new PrintWriter(ecrireFichier);
            imprimerFichier.println("solid murFini_" + lettre);
            
            for(double[][] triangle: trianglesMur) {
                ajouterTriangleSTL(triangle[0], triangle[1], triangle[2], imprimerFichier);
            }
            
            // Écrire le footer
            imprimerFichier.printf("endsolid murBrut_%s", lettre);
            // Fermer le fichier
            imprimerFichier.close();
        }
    }
    
    private ArrayList<double[][]> genererTrianglesMur(Mur mur) {
        ArrayList<double[][]> trianglesMur = new ArrayList<double[][]>();

        double epaisseur = chalet.getConfiguration().getEpaisseur();
        double hauteur = mur.getHauteur();
        double largeur = mur.getLargeur();
        Face orientationChalet = chalet.toit.getOrientation();
        boolean estLong = true;
        
        // À refactor probablement...
        // Détermine si le mur est long ou non
        if((orientationChalet == Face.DROITE || orientationChalet == Face.GAUCHE && 
           mur.getTypeMur() == Face.FACADE || mur.getTypeMur() == Face.ARRIERE) ||
           (orientationChalet == Face.FACADE || orientationChalet == Face.ARRIERE && 
           mur.getTypeMur() == Face.DROITE || mur.getTypeMur() == Face.GAUCHE)) {
            
            largeur -= epaisseur;
            estLong = false;
        }

        trianglesMur.addAll(genererMurTemplate(largeur, hauteur, epaisseur));
        
        ArrayList<Double>[] sommetsPotentiels = calculerSommetsPotentielMurFini(mur, largeur, epaisseur);
        ArrayList<Double> listeX = sommetsPotentiels[0];
        ArrayList<Double> listeY = sommetsPotentiels[1];
        
        for(int i = 0; i < listeY.size(); i++) {
            for(int j = 0; j < listeX.size(); j++) {
                double x = listeX.get(j);
                double y = listeY.get(i);
                if(i < listeX.size() - 1 && j < listeY.size() - 1) {
                    double prochainX = listeX.get(j + 1);
                    double prochainY = listeY.get(i + 1);
                    double[] sommetBL = {x, 0, y};
                    double[] sommetBR = {prochainX, 0, y};
                    double[] sommetTL = {x, 0, prochainY};
                    double[] sommetTR = {prochainX, 0, prochainY};

                    // On valide si le prochain rectangle est dans un accessoire
                    Accessoire estDansAccessoire = rectangleEstDansAccessoire(sommetBL, sommetBR, sommetTL, sommetTR, mur);

                    // Si n'est pas dans accessoires, on créé le rectangle normalement
                    if(estDansAccessoire == null) {
                        trianglesMur.add(new double[][]{sommetBL, sommetTR, sommetTL});
                        trianglesMur.add(new double[][]{sommetBL, sommetBR, sommetTR});
                        
                        double[] sommetFBL = {sommetBL[0], epaisseur, sommetBL[2]};
                        double[] sommetFBR = {sommetBR[0], epaisseur, sommetBR[2]};
                        double[] sommetFTL = {sommetTL[0], epaisseur, sommetTL[2]};
                        double[] sommetFTR = {sommetTR[0], epaisseur, sommetTR[2]};

                        trianglesMur.add(new double[][]{sommetFBL, sommetFTR, sommetFTL});
                        trianglesMur.add(new double[][]{sommetFBL, sommetFBR, sommetFTR});


                        if(sommetBL[1] == 0) {
                            trianglesMur.add(new double[][]{sommetBL, sommetFBR, sommetFBL});
                            trianglesMur.add(new double[][]{sommetBL, sommetBR, sommetFBR});
                        }
                    }
                    // sinon, on fait un trou
                    else {
                        trianglesMur.addAll(genererTrouMurFini(sommetBL, sommetBR, sommetTL, sommetTR, estDansAccessoire, epaisseur));
                    }
                }
            }
        }
        
        double[] position;
        switch(mur.getTypeMur()) {
            case ARRIERE: 
                trianglesMur = faireTournerTriangles(trianglesMur, 180);
                position = new double[]{chalet.largeur-epaisseur/2, chalet.profondeur, 0};
                
                if(!estLong) {
                    position[0] -= epaisseur/2;
                }
                
                trianglesMur = deplacerTriangles(trianglesMur, position);
                break;
            case DROITE:
                trianglesMur = faireTournerTriangles(trianglesMur, 90);
                position = new double[]{chalet.largeur - epaisseur, epaisseur/2 , 0};
                
                trianglesMur = deplacerTriangles(trianglesMur, position);
                break;
            case GAUCHE:
                trianglesMur = faireTournerTriangles(trianglesMur, 270);
                position = new double[]{0, chalet.profondeur - 3*(epaisseur/2), 0};
                
                trianglesMur = deplacerTriangles(trianglesMur, position);
                break;
            default: 
                break;
        }
        
        return trianglesMur;
    }
    
    public ArrayList<double[][]> faireTournerTriangles(ArrayList<double[][]> triangles, double degres) {
        double radians = Math.toRadians(degres);
        double cosTheta = Math.cos(radians);
        double sinTheta = Math.sin(radians);

        ArrayList<double[][]> trianglesTournes = new ArrayList<>();

        for(double[][] triangle : triangles) {
            double[][] triangleTourne = new double[triangle.length][3];

            for (int i = 0; i < triangle.length; i++) {
                double x = triangle[i][0];
                double y = triangle[i][1];

                // Rotation autour de l'axe z
                triangleTourne[i][0] = x * cosTheta - y * sinTheta;
                triangleTourne[i][1] = x * sinTheta + y * cosTheta;
                triangleTourne[i][2] = triangle[i][2];
            }

            trianglesTournes.add(triangleTourne);
        }

        return trianglesTournes;
    }
    
    public ArrayList<double[][]> deplacerTriangles(ArrayList<double[][]> triangles, double[] position) {
        ArrayList<double[][]> trianglesDeplaces = new ArrayList<>();

        for (double[][] triangle : triangles) {
            double[][] triangleDeplace = new double[triangle.length][3];

            for (int i = 0; i < triangle.length; i++) {
                triangleDeplace[i][0] = triangle[i][0] + position[0];
                triangleDeplace[i][1] = triangle[i][1] + position[1];
                triangleDeplace[i][2] = triangle[i][2] + position[2];
            }

            trianglesDeplaces.add(triangleDeplace);
        }

        return trianglesDeplaces;
    }
  
    private ArrayList<double[][]> genererMurTemplate(double largeur, double hauteur, double epaisseur) {
        ArrayList<double[][]> trianglesMur = new ArrayList<double[][]>();
        // Sommets du bas
        double[] nlb = {0, 0, 0}; // Near Left Bottom
        double[] nlbi = {epaisseur/2, 0, 0}; // Near Bottom Left In
        double[] nrb = {largeur, 0, 0}; // Near Right Bottom
        double[] nrbi = {largeur - epaisseur/2, 0, 0}; // Near Bottom Left In
        double[] flb = {epaisseur/2, epaisseur, 0}; // Far Left Bottom
        double[] frb = {largeur - epaisseur/2, epaisseur, 0}; // Far Right Bottom
        double[] rainurelbo = {0, epaisseur/2, 0}; // Rainure Left Bottom Out
        double[] rainurelbi = {epaisseur/2, epaisseur/2, 0}; // Rainure Left Bottom In
        double[] rainurerbo = {largeur, epaisseur/2, 0}; // Rainure Right Bottom Out
        double[] rainurerbi = {largeur - epaisseur/2, epaisseur/2, 0}; // Rainure Right Bottom In

        double[] nlt = {0, 0, hauteur}; // Near Left Top
        double[] nlti = {epaisseur/2, 0, hauteur}; // Near Top Left In
        double[] nrt = {largeur, 0, hauteur}; // Near Right Top
        double[] nrti = {largeur - epaisseur/2, 0, hauteur}; // Near Bottom Left In
        double[] flt = {epaisseur/2, epaisseur, hauteur}; // Far Left Top
        double[] frt = {largeur - epaisseur/2, epaisseur, hauteur}; // Far Right Top
        double[] rainurelto = {0, epaisseur/2, hauteur }; // Rainure Left Top Out
        double[] rainurelti = {epaisseur/2, epaisseur/2, hauteur}; // Rainure Left Top In
        double[] rainurerto = {largeur, epaisseur/2, hauteur}; // Rainure Right Top Out
        double[] rainurerti = {largeur - epaisseur/2, epaisseur/2, hauteur}; // Rainure Right Top In

        // Face dessus
        trianglesMur.add(new double[][]{nlt, rainurerto, rainurelto});
        trianglesMur.add(new double[][]{nlt, nrt, rainurerto});
        trianglesMur.add(new double[][]{rainurerti, flt, rainurelti});
        trianglesMur.add(new double[][]{flt, rainurerti, frt});

        // Face dessous
        trianglesMur.add(new double[][]{nlb, nlbi, rainurelbi});
        trianglesMur.add(new double[][]{nlb, rainurelbi,rainurelbo});

        trianglesMur.add(new double[][]{nrbi, nrb, rainurerbi});
        trianglesMur.add(new double[][]{nrb, rainurerbo, rainurerbi});

        // Faces Rainure Gauche
        trianglesMur.add(new double[][]{nlb, rainurelto, rainurelbo});
        trianglesMur.add(new double[][]{nlb, nlt, rainurelto});
        trianglesMur.add(new double[][]{rainurelbo, rainurelto, rainurelbi});
        trianglesMur.add(new double[][]{rainurelbi, rainurelti, rainurelto});
        trianglesMur.add(new double[][]{flt, flb, rainurelbi});
        trianglesMur.add(new double[][]{flt, rainurelti, rainurelbi});

        trianglesMur.add(new double[][]{nrb, rainurerto, rainurerbo});
        trianglesMur.add(new double[][]{nrb, nrt, rainurerto});
        trianglesMur.add(new double[][]{rainurerbo, rainurerto, rainurerbi});
        trianglesMur.add(new double[][]{rainurerbi, rainurerti, rainurerto});
        trianglesMur.add(new double[][]{frt, frb, rainurerbi});
        trianglesMur.add(new double[][]{frt, rainurerti, rainurerbi});

        // Face avant sur les rainure
        trianglesMur.add(new double[][]{nlbi, nlt, nlti});
        trianglesMur.add(new double[][]{nlbi, nlt, nlb});

        trianglesMur.add(new double[][]{nrbi, nrt, nrti});
        trianglesMur.add(new double[][]{nrbi, nrt, nrb});
        
        return trianglesMur;
    }
        
    private ArrayList<Double>[] calculerSommetsPotentielMurFini(Mur mur, double largeur, double epaisseur) {
        ArrayList<Double> listeX = new ArrayList<Double>();
        ArrayList<Double> listeY = new ArrayList<Double>();
        
        // On ajoute les valeurs à zéro
        listeX.add(epaisseur/2);
        listeY.add(0.0);

        for(Accessoire accessoire: mur.getAccessoireListe()) {
            if(!accessoire.isEstValide()) continue; // On saute la logique d'ajout si l'accessoire n'est pas valide

            listeX.add(accessoire.getPositionX());
            listeX.add(accessoire.getPositionX() + accessoire.getLargeur());
            listeY.add(accessoire.getPositionY());
            listeY.add(accessoire.getPositionY()+ accessoire.getHauteur());
        }
            
        // On ajoute les valeurs de x et y max du mur
        listeX.add(largeur - epaisseur/2);
        listeY.add(mur.getHauteur());
        
        // On trie les x et les y en ordre croissant
        Collections.sort(listeX);
        Collections.sort(listeY);
        
        ArrayList<Double>[] resultat = new ArrayList[]{listeX, listeY};
        return resultat;
    }
    
    private PrintWriter ajouterTriangleSTL(double[] sommetX1, double[] sommetX2, double[] sommetY, PrintWriter imprimerFichier) {
        imprimerFichier.println("facet normal 0 0 0");
        imprimerFichier.println("outer loop");
        imprimerFichier.println(String.format("  vertex %f %f %f", sommetX1[0], sommetX1[1], sommetX1[2]));
        imprimerFichier.println(String.format("  vertex %f %f %f", sommetX2[0], sommetX2[1], sommetX2[2]));
        imprimerFichier.println(String.format("  vertex %f %f %f", sommetY[0], sommetY[1], sommetY[2]));
        imprimerFichier.println("endloop");
        imprimerFichier.println("endfacet");  
        return imprimerFichier;
    }
    
    private Accessoire rectangleEstDansAccessoire(double[] sommetBL, double[] sommetBR, double[] sommetTL, double[] sommetTR, Mur mur) {
        for (Accessoire accessoire : mur.getAccessoireListe()) {
            if(!accessoire.estValide) continue;
            
            boolean sommetsDansAccessoire = sommetDansAccessoire(sommetBL, accessoire) &&
                                         sommetDansAccessoire(sommetBR, accessoire) &&
                                         sommetDansAccessoire(sommetTL, accessoire) &&
                                         sommetDansAccessoire(sommetTR, accessoire);

            if (sommetsDansAccessoire) {
                return accessoire;
            }
        }
        // Si aucun accessoire ne contient les sommets
        return null;
    }
    
    private boolean sommetDansAccessoire(double[] sommet, Accessoire accessoire) {
        double sommetX = sommet[0];
        double sommetY = sommet[2];
        
        double accessoireX = accessoire.getPositionX();
        double accessoireY = accessoire.getPositionY();
        double accessoireLargeur = accessoire.getLargeur();
        double accessoireHauteur = accessoire.getHauteur();

        return sommetX >= accessoireX && sommetX <= accessoireX + accessoireLargeur &&
               sommetY >= accessoireY && sommetY <= accessoireY + accessoireHauteur;
    }

    private ArrayList<double[][]> genererTrouMurFini(double[] sommetBL, double[] sommetBR, double[] sommetTL, double[] sommetTR, Accessoire accessoire, double epaisseur) {
        ArrayList<double[][]> trianglesTrou = new ArrayList<double[][]>();
        
        double[] sommetFBL = {sommetBL[0], epaisseur, sommetBL[2]};
        double[] sommetFBR = {sommetBR[0], epaisseur, sommetBR[2]};
        double[] sommetFTL = {sommetTL[0], epaisseur, sommetTL[2]};
        double[] sommetFTR = {sommetTR[0], epaisseur, sommetTR[2]};
        
        double positionX = accessoire.getPositionX();
        double positionY = accessoire.getPositionY();
        double largeurAccessoire = accessoire.getLargeur();
        double hauteurAccessoire = accessoire.getHauteur();
        
        if (sommetBL[2] == positionY && sommetBR[2] == positionY && accessoire instanceof Fenetre) {
            trianglesTrou.add(new double[][]{sommetBL, sommetFBR, sommetFBL});
            trianglesTrou.add(new double[][]{sommetBL, sommetBR, sommetFBR});
        }

        if (sommetTL[2] == positionY + hauteurAccessoire && sommetTR[2] == positionY + hauteurAccessoire) {
            trianglesTrou.add(new double[][]{sommetTL, sommetFTR, sommetFTL});
            trianglesTrou.add(new double[][]{sommetTL, sommetTR, sommetFTR});
        }

        if (sommetBL[0] == positionX && sommetTL[0] == positionX) {
            trianglesTrou.add(new double[][]{sommetBL, sommetFTL, sommetTL});
            trianglesTrou.add(new double[][]{sommetBL, sommetFBL, sommetFTL});
        }

        if (sommetBR[0] == positionX + largeurAccessoire && sommetTR[0] == positionX + largeurAccessoire) {
            trianglesTrou.add(new double[][]{sommetBR, sommetFTR, sommetTR});
            trianglesTrou.add(new double[][]{sommetBR, sommetFBR, sommetFTR});
        }
        
        return trianglesTrou;
    }
    
    public void exportRetraitSTL(){
        try{
            Configuration config = Configuration.getInstance();
            
            for(Mur mur: chalet.getMurList()){
                
                int[][] faces = {
                                {0, 1, 2}, {3, 0, 2},   // Bas
                                {4, 5, 6}, {4, 6, 7},   // Haut
                                {0, 1, 5}, {0, 5, 4},   // Côté
                                {2, 3, 7}, {2, 7, 6},
                                {0, 3, 7}, {0, 7, 4},
                                {1, 2, 6}, {1, 6, 5}
                        };
                String lettre = mur.getTypeMur().toString().substring(0, 1);
                int y = 0;
                
                for(Accessoire acc: mur.getAccessoireListe()){
                    if(acc.estValide){
                        FileWriter ecrireFichier = new FileWriter(Path + "/Projet_Retrait_" + lettre + "_" + y + ".stl");
                        PrintWriter imprimerFichier = new PrintWriter(ecrireFichier);

                        double[][] sommet = {
                                {0, 0, 0},
                                {0, 0, config.getEpaisseur()},
                                {acc.getLargeur(), 0, config.getEpaisseur()},
                                {acc.getLargeur(), 0, 0},   // 4 sommets du bas

                                {0, acc.getHauteur(), 0},
                                {0, acc.getHauteur(), config.getEpaisseur()},
                                {acc.getLargeur(), acc.getHauteur(), config.getEpaisseur()},
                                {acc.getLargeur(), acc.getHauteur(), 0}    // 4 sommets du haut
                            };

                        // Écrire dans le fichier

                        // Écrire le header
                        imprimerFichier.println("solid accessoire");
                        //Écrire les faces et sommets
                        for (int[] face : faces) {
                            imprimerFichier.println("facet normal 0 0 0");
                            imprimerFichier.println("outer loop");
                            for (int i : face) {
                                double[] vertex = sommet[i];
                                imprimerFichier.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                            }
                        imprimerFichier.println("endloop");
                        imprimerFichier.println("endfacet");

                        }
                        // Écrire le footer
                        imprimerFichier.println("endsolid accessoire");
                        // Fermer le fichier
                        imprimerFichier.close();
                        y += 1;
                    }
                }
                
                for(int j = 0; j<2;j++){
                    FileWriter ecrireFichier = new FileWriter(Path + "/Projet_Retrait_" + lettre + "_" + y + ".stl");
                    PrintWriter imprimerFichier = new PrintWriter(ecrireFichier);
                    double dis = (config.getEpaisseur()/2)-config.getDistanceRainure();
                    double[][] sommet = {
                            {0, 0, 0},
                            {0, 0, dis},
                            {dis, 0, dis},
                            {dis, 0, 0},   // 4 sommets du bas

                            {0, mur.getHauteur(), 0},
                            {0, mur.getHauteur(), dis},
                            {dis, mur.getHauteur(), dis},
                            {dis, mur.getHauteur(), 0}    // 4 sommets du haut
                    };

                    // Écrire dans le fichier

                    // Écrire le header
                    imprimerFichier.println("solid rainure");
                    //Écrire les faces et sommets
                    for (int[] face : faces) {
                        imprimerFichier.println("facet normal 0 0 0");
                        imprimerFichier.println("outer loop");
                        for (int i : face) {
                            double[] vertex = sommet[i];
                            imprimerFichier.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                        }
                    imprimerFichier.println("endloop");
                    imprimerFichier.println("endfacet");

                    }
                    // Écrire le footer
                    imprimerFichier.println("endsolid rainure");


                    // Fermer le fichier
                    imprimerFichier.close();
                    y=y+1;
                }
            }
            
            for(int i = 0; i<2; i++){
                String lettres;
                if(i==0){
                    lettres = "PD";
                }
                else{
                    lettres = "PG";
                }
                FileWriter ecrireFichier = new FileWriter(Path + "/Projet_Retrait_" + lettres + "_1.stl");
                PrintWriter imprimerFichier = new PrintWriter(ecrireFichier);
                double dis = (config.getEpaisseur()/2)-config.getDistanceRainure();
                Pignon pignon = chalet.getToit().getPignon();
                double prop = pignon.getLargeur()/(pignon.getLargeur()-dis);
                double[][] sommet = {
                            {0, 0, 0}, //0
                            {pignon.getLargeur(), 0, 0},//1
                            {pignon.getLargeur(), pignon.getHauteur(), 0},//2
                            
                            {dis, 0, 0},//3
                            {pignon.getLargeur()-dis, 0, 0},//4
                            {pignon.getLargeur()-dis, (pignon.getHauteur()/prop)-dis, 0},//5
                            
                            {dis, 0, dis},//6
                            {pignon.getLargeur()-dis, 0, dis},//7
                            {pignon.getLargeur()-dis, (pignon.getHauteur()/prop)-dis, dis},//8
                            
                            {0, 0, dis},//9
                            {pignon.getLargeur(), 0, dis},//10
                            {pignon.getLargeur(), pignon.getHauteur(), dis}//11
                    };
                int[][] faces = {
                    {0, 3, 6}, {0, 9, 6},
                    {0, 9 , 11}, {0, 2, 11},
                    {0, 2, 5}, {0, 3, 5},
                    {3, 5, 8}, {3, 6, 8},
                    {9, 6, 8}, {9, 11, 8},
                    
                    {1, 4, 7}, {1, 10, 7},
                    {1, 2, 11}, {1, 10, 11},
                    {1, 2, 5}, {1, 4, 5},
                    {4, 5, 8}, {4, 7, 8},
                    {7, 10, 11}, {7, 8, 11}
                };
                
                // Écrire le header
                imprimerFichier.println("solid rainure");
                //Écrire les faces et sommets
                for (int[] face : faces) {
                    imprimerFichier.println("facet normal 0 0 0");
                    imprimerFichier.println("outer loop");
                    for (int j : face) {
                        double[] vertex = sommet[j];
                        imprimerFichier.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                    }
                imprimerFichier.println("endloop");
                imprimerFichier.println("endfacet");

                }
                // Écrire le footer
                imprimerFichier.println("endsolid rainure");


                // Fermer le fichier
                imprimerFichier.close();
            }
            FileWriter ecrireFichierr = new FileWriter(Path + "/Projet_Retrait_R_1.stl");
            PrintWriter imprimerFichierr = new PrintWriter(ecrireFichierr);
            double dis = (config.getEpaisseur()/2)-config.getDistanceRainure();
            Rallonge rallonge = chalet.getToit().getRallonge();
            Toit toit = chalet.getToit();
            double opp = (Math.tan(Math.toRadians(toit.getAngle()))*dis);
            double hyp =(dis/Math.cos(Math.toRadians(toit.getAngle())));
            
            double[][] sommetr = {
                            {0, 0, 0}, //0
                            {dis, 0, 0},//1
                            {rallonge.getLargeur()-dis, 0, 0},//2
                            {rallonge.getLargeur(), 0, 0},//3
                            
                            {0, 0, dis},//4
                            {dis, 0, dis},//5
                            {rallonge.getLargeur()-dis, 0, dis},//6
                            {rallonge.getLargeur(), 0, dis},//7
                            
                            {dis, rallonge.getHauteur() - opp - opp - hyp, 0},//8
                            {rallonge.getLargeur()-dis, rallonge.getHauteur()-opp - opp -hyp, 0},//9
                            {dis, rallonge.getHauteur()- opp - opp - opp - hyp, dis},//10
                            {rallonge.getLargeur()-dis, rallonge.getHauteur()- opp - opp - opp - hyp, dis},//11
                            
                            {0, rallonge.getHauteur(), -dis},//12
                            {0, rallonge.getHauteur()-opp, 0},//13
                            {rallonge.getLargeur(), rallonge.getHauteur(), -dis},//14
                            {rallonge.getLargeur(), rallonge.getHauteur()-opp, 0},//15
                            
                            {0, rallonge.getHauteur(), dis},//16
                            {rallonge.getLargeur(), rallonge.getHauteur(), dis}//17
                    };
            
            int[][] facesr = {
                {0, 1, 5},{0, 4, 5},
                {1, 5, 10},{1, 8, 10},
                {0, 4, 16},{0, 13, 16},{12, 13, 16},
                {4, 5, 10},{4, 16, 10},
                {0, 1, 8},{0, 13, 8},
                
                {8, 9, 11},{8, 10, 11},
                {12, 14, 15},{12, 13, 15},
                {8, 9, 15},{8, 13, 15},
                {10, 11, 17},{10, 16, 17},
                {12, 14, 17},{12, 16, 17},
                
                {2, 3, 7},{2, 6 ,7},
                {2, 6, 11},{2, 9, 11},
                {3, 7, 17},{3, 15, 17},{14, 15, 17},
                {2, 3, 15},{2, 9, 15},
                {6, 7, 17},{6, 11, 17}
            };
            
            // Écrire le header
            imprimerFichierr.println("solid rainure");
            //Écrire les faces et sommets
            for (int[] face : facesr) {
                imprimerFichierr.println("facet normal 0 0 0");
                imprimerFichierr.println("outer loop");
                for (int j : face) {
                    double[] vertex = sommetr[j];
                    imprimerFichierr.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                }
            imprimerFichierr.println("endloop");
            imprimerFichierr.println("endfacet");

            }
            // Écrire le footer
            imprimerFichierr.println("endsolid rainure");


            // Fermer le fichier
            imprimerFichierr.close();
            
            
            FileWriter ecrireFichierv = new FileWriter(Path + "/Projet_Retrait_T_1.stl");
            PrintWriter imprimerFichierv = new PrintWriter(ecrireFichierv);
            
            Versant v = toit.getVersant();
            double dise = config.getEpaisseur()-config.getDistanceRainure();
            double adj = (dis/Math.cos(Math.toRadians(toit.getAngle())));
            double adja = (Math.cos(Math.toRadians(toit.getAngle()))*dise);
            double oppos = (Math.tan(Math.toRadians(toit.getAngle()))*dis);
            double oppose = (Math.sin(Math.toRadians(toit.getAngle()))*dise);
            double qqc = (adj/Math.tan(Math.toRadians(toit.getAngle())));
            
            double pour = (Math.sin(Math.toRadians(toit.getAngle()))*oppos);
            
            
            double[][] sommetv = {
                {0, 0, 0}, //0
                {v.getLargeur(), 0, 0}, //1

                {0, -oppos, adj}, //2
                {v.getLargeur(), -oppos, adj}, //3

                {0, -oppose, adja}, //4
                {v.getLargeur(), -oppose, adja}, //5

                {0, qqc, adj}, //6
                {v.getLargeur(), qqc, adj}, //7

                {config.getEpaisseur(), qqc, adj}, //8
                {v.getLargeur()-config.getEpaisseur(), qqc, adj}, //9

                {dis, v.getLongueur()-dis, rallonge.getHauteur()-opp-opp-hyp}, //10
                {v.getLargeur()-dis, v.getLongueur()-dis, rallonge.getHauteur()-opp-opp-hyp}, //11

                {dis, v.getLongueur()-dis, rallonge.getHauteur()-opp}, //12
                {v.getLargeur()-dis, v.getLongueur()-dis, rallonge.getHauteur()-opp}, //13

                {dis, v.getLongueur()-oppos, rallonge.getHauteur()}, //14
                {v.getLargeur()-dis, v.getLongueur()-oppos, rallonge.getHauteur()}, //15

                {0, v.getLongueur()-oppos, rallonge.getHauteur()}, //16
                {v.getLargeur(), v.getLongueur()-oppos, rallonge.getHauteur()}, //17

                {0, v.getLongueur()+oppose, rallonge.getHauteur()-adj}, //18
                {v.getLargeur(), v.getLongueur()+oppose, rallonge.getHauteur()-adj}, //19

                {0, v.getLongueur(), rallonge.getHauteur()+opp+opp}, //20
                {v.getLargeur(), v.getLongueur(), rallonge.getHauteur()+opp+opp}, //21

                {0, -oppos, adja + pour}, //22
                {v.getLargeur(), -oppos, adja +pour}, //23

                {dis, qqc, adj}, //24
                {v.getLargeur()-dis, qqc, adj} //25

            };

            int[][] facev = {
                {0, 1, 5}, {0, 4, 5},
                {4, 5, 23}, {4, 22, 23},
                {2, 3, 23}, {2, 22, 23},
                {2, 4, 22}, 
                {3, 5, 23},
                {0, 2, 4},
                {1, 3, 5},

                {2, 3, 7}, {2, 6, 7},
                {0, 1, 7}, {0, 6, 7},
                {0, 2, 6},
                {1, 3, 7},

                {6, 24, 10}, {6, 18, 10},
                {6, 16, 18},
                {18, 16, 20}, 
                {24, 10, 14}, 
                {6, 16, 14}, {6, 24, 14},

                {7, 25, 11}, {7, 19, 11},
                {7, 17, 19}, 
                {19, 17, 21},
                {25, 11, 15},
                {7, 17, 15}, {7, 25, 15},

                {18, 19, 21}, {18, 20, 21},
                {16, 17, 21}, {16, 20, 21},
                {12, 13, 15}, {12, 14, 15},
                {10, 11, 13}, {10, 12, 13},
                {10, 11, 19}, {10, 18, 19}
                };
            
            // Écrire le header
            imprimerFichierv.println("solid rainure");
            //Écrire les faces et sommets
            for (int[] face : facev) {
                imprimerFichierv.println("facet normal 0 0 0");
                imprimerFichierv.println("outer loop");
                for (int i : face) {
                    double[] vertex = sommetv[i];
                    imprimerFichierv.println(String.format("  vertex %f %f %f", vertex[0], vertex[1], vertex[2]));
                }
            imprimerFichierv.println("endloop");
            imprimerFichierv.println("endfacet");

            }
            // Écrire le footer
            imprimerFichierv.println("endsolid rainure");


            // Fermer le fichier
            imprimerFichierv.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}

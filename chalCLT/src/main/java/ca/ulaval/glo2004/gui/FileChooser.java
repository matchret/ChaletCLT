
package ca.ulaval.glo2004.gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Mathieu Chretien
 */
public class FileChooser {

    public static String getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Veuillez choisir l'emplacement de sauvegarde des fichiers STL.");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showDialog(null, "Choose");

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }
    
    public static String getFilePathCharger() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Veuillez choisir l'emplacement de sauvegarde de votre chalet.");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Serial Files (*.ser)", "ser");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showDialog(null, "Choose");

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }
    
    public static String getFilePathSauvegarde() {
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.setDialogTitle("Veuillez choisir l'emplacement et le nom du fichier de sauvegarde de votre chalet.");
       
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Serial Files (*.ser)", "ser");
       fileChooser.setFileFilter(filter);

       int userSelection = fileChooser.showDialog(null, "Choose");
       
       if (userSelection == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!selectedFilePath.toLowerCase().endsWith(".ser")) {
                    selectedFilePath += ".ser";
                }
                return selectedFilePath;
                
       } else {
           return null;
       }
   }
    
}

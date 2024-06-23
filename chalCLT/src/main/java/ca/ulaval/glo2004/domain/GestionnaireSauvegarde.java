
package ca.ulaval.glo2004.domain;

import ca.ulaval.glo2004.gui.FileChooser;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Pierre-Olivier Tremblay
 */
public class GestionnaireSauvegarde {
    
    private Chalet chalet;
    private String path;
    
    protected String getPath(){
        return path;
    }
    
    public GestionnaireSauvegarde() {
        this.chalet = chalet;
        path = FileChooser.getFilePathSauvegarde();
    }

    public void sauvegarderChalet(Chalet chalet, String path) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(chalet);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Before serialization: " + chalet);
    }

    public Chalet chargerChalet(String path) {
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (Chalet) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

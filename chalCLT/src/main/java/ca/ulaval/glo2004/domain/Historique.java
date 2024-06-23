
package ca.ulaval.glo2004.domain;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Mathieu Chretien
 */

// Classe représentant l'historique des modifications
class Historique {
    private List<Chalet> etapes = new ArrayList<>();
    private int indiceActuel = -1;
    
    public int getIndiceActuel(){
        return indiceActuel;
    }

    // Méthode pour ajouter une nouvelle étape à l'historique
    public void ajouterEtape(Chalet etape) {
        // Supprimer toutes les étapes futures si des modifications ont été apportées après une annulation
        etapes.subList(indiceActuel + 1, etapes.size()).clear();
        etapes.add(etape.copier());
        indiceActuel++;
    }

    // Méthode pour annuler une étape
    public void annuler() {
        if (indiceActuel > 0) {
            indiceActuel--;
        }
    }

    // Méthode pour répéter une étape
    public void repeter() {
        if (indiceActuel < etapes.size() - 1) {
            indiceActuel++;
        }
    }

    // Méthode pour obtenir le cabanon à l'étape actuelle
    public Chalet getChaletActuel() {
        Chalet chal =etapes.get(indiceActuel).copier();
        return chal;
    }
}



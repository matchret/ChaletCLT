
package ca.ulaval.glo2004.domain;

/**
 *
 * @author Mathieu Chretien
 */
public enum AccessoireType {
    PORTE("Porte"),
    FENETRE("Fenetre");

    private final String displayName;

    AccessoireType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
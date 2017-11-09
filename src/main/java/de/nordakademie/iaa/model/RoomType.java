package de.nordakademie.iaa.model;

/**
 * Room type enum.
 *
 * @author Arvid Ottenberg
 */
public enum RoomType {

    COMPUTERROOM("Computerraum"),
    LABORATORY("Labor"),
    LECTUREROOM("Vorlesungsraum");

    private final String translation;

    RoomType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
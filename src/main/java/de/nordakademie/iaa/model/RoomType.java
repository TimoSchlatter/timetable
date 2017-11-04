package de.nordakademie.iaa.model;

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
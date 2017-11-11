package de.nordakademie.iaa.model;

/**
 * Seminar type enumeration.
 *
 * @author Arvid Ottenberg
 */
public enum SubjectType {

    EXAM("Klausur"),
    LECTURE("Vorlesung"),
    SEMINAR("Seminar");

    private final String translation;

    SubjectType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
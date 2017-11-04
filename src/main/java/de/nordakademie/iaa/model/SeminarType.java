package de.nordakademie.iaa.model;

public enum SeminarType {

    INTERNATIONAL("Internationales"),
    ETHICS_SOCIAL("Ethik & Soziales"),
    KEY_QUALIFICATION("Schlüsselqualifikation"),
    OTHER("Sonstiges");

    private final String translation;

    SeminarType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}

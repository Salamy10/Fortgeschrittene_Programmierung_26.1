package training.Documentation_ClassDiagrams.tanteEmmaLaden;

public enum ClassOfGoods {
    
    BAKERY_PRODUCTS("Backwaren"), 
    DAIRY_PRODUCTS("Milchprodukte"), 
    FRUITS("Obst"), 
    VEGETABLES("Gemüse");
    
    private final String description; // Das Feld aus dem Diagramm
    
    // Der Konstruktor für das Enum
    ClassOfGoods(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

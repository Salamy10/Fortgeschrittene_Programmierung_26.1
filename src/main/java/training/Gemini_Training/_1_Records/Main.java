package training.Gemini_Training._1_Records;

public class Main {
    public static void main(String[] args) {
        // 1. Gültiges Buch erstellen
        Book book = new Book("978-3-16-148410-0", "Java für Profis", 20.0);
        
        System.out.println("Erzeugtes Buch: " + book); // Baut automatisch toString() auf
        System.out.println("Titel über Getter: " + book.title()); // Getter heißt title(), nicht getTitle()
        
        // 2. Rabatt berechnen (10%)
        double discountedPrice = book.applyDiscount(10.0);
        System.out.println("Preis nach 10% Rabatt: " + discountedPrice + " €");

        // 3. Unveränderlichkeit testen (Immutability)
        System.out.println("Originalpreis unverändert: " + book.price() + " €");

        // 4. Validierung testen (löst Exception aus)
        try {
            Book invalidBook = new Book("", "Ungültiges Buch", -5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler erfolgreich abgefangen: " + e.getMessage());
        }
    }
}
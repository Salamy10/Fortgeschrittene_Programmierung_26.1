package training.Gemini_Training._8_JavaStreamAPI;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // BookService instanziieren
        BookService service = new BookService();

        // Testdaten anlegen und der Liste in BookService hinzufügen
        service.books.addAll(List.of(
            new Book("Der Herr der Ringe", "J.R.R. Tolkien", 29.99, 1200, List.of("Fantasy", "Abenteuer")),
            new Book("Der Hobbit", "J.R.R. Tolkien", 14.99, 380, List.of("Fantasy", "Kinderbuch")),
            new Book("Dune", "Frank Herbert", 18.50, 800, List.of("Sci-Fi", "Abenteuer")),
            new Book("Die Känguru-Chroniken", "Marc-Uwe Kling", 9.99, 270, List.of("Komödie", "Satire")),
            new Book("QualityLand", "Marc-Uwe Kling", 12.00, 380, List.of("Komödie", "Sci-Fi"))
        ));

        // 1. getBooksByGenre
        printHeader("1. Bücher des Genres 'Fantasy'");
        List<Book> fantasyBooks = service.getBooksByGenre("Fantasy");
        fantasyBooks.forEach(b -> System.out.println(" - " + b.getTitle() + " (" + b.getAuthor() + ")"));

        // 2. getTop3LongestBooks
        printHeader("2. Die Top 3 längsten Bücher");
        List<Book> longestBooks = service.getTop3LongestBooks();
        longestBooks.forEach(b -> System.out.printf(" - %s (%d Seiten)%n", b.getTitle(), b.getPages()));

        // 3. getAverageBookPrice
        printHeader("3. Durchschnittlicher Buchpreis");
        double avgPrice = service.getAverageBookPrice();
        System.out.printf(" - Durchschnitt: %.2f €%n", avgPrice);

        // 4. getUniqueAuthors
        printHeader("4. Eindeutige Autoren (ohne Duplikate)");
        List<String> authors = service.getUniqueAuthors();
        authors.forEach(a -> System.out.println(" - " + a));

        // 5. hasBookCheaperThan
        printHeader("5. Prüfung: Buch günstiger als 10.00 € vorhanden?");
        boolean hasCheap = service.hasBookCheaperThan(10.00);
        System.out.println(" - Ergebnis: " + (hasCheap ? "Ja, mindestens ein Buch gefunden." : "Nein."));

        // 6. getFormattedTitles
        printHeader("6. Formatierte Titel-Kette");
        String titles = service.getFormattedTitles();
        System.out.println(" - " + titles);

        // 7. groupBooksByAuthor
        printHeader("7. Bücher gruppiert nach Autor");
        Map<String, List<Book>> grouped = service.groupBooksByAuthor();
        grouped.forEach((author, bookList) -> {
            System.out.println(" [" + author + "]");
            bookList.forEach(b -> System.out.printf("   └─ %s (%.2f €)%n", b.getTitle(), b.getPrice()));
        });
    }

    private static void printHeader(String title) {
        System.out.println("\n==================================================");
        System.out.println(" " + title);
        System.out.println("==================================================");
    }
}
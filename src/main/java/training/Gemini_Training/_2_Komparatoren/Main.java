package training.Gemini_Training._2_Komparatoren;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Movie> movies = new ArrayList<>(List.of(
            new Movie("Inception", 2010, 8.8),
            new Movie("Der Pate", 1972, 9.2),
            new Movie("Interstellar", 2014, 8.6),
            new Movie("The Dark Knight", 2008, 9.0),
            new Movie("Pulp Fiction", 1994, 8.9)
        ));

        // 1. Sortierung nach Natürlicher Ordnung (Comparable -> releaseYear aufsteigend)
        movies.sort(Comparator.naturalOrder()); // oder: Collections.sort(movies);
        System.out.println("=== 1. Nach Erscheinungsjahr (aufsteigend) ===");
        movies.forEach(System.out::println);

        // 2. Externe Sortierung: Rating absteigend (Comparator)
        Comparator<Movie> byRatingDesc = Comparator.comparingDouble(Movie::rating).reversed();
        movies.sort(byRatingDesc);
        System.out.println("\n=== 2. Nach Rating (absteigend) ===");
        movies.forEach(System.out::println);

        // 3. Verkettete Sortierung: Erst Rating (absteigend), bei Gleichstand Titel (aufsteigend)
        Comparator<Movie> byRatingThenTitle = Comparator
                .comparingDouble(Movie::rating).reversed()
                .thenComparing(Movie::title);
        
        movies.sort(byRatingThenTitle);
        System.out.println("\n=== 3. Nach Rating (absteigend), dann Titel (aufsteigend) ===");
        movies.forEach(System.out::println);
    }
}

package training.Gemini_Training._4_Inner_Classes;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        // 1. Nicht-statische Elementklasse (Braucht äußere Instanz L1)
        System.out.println("=== 1. Member Inner Class ===");
        //
        ShoppingListMember L1 = new ShoppingListMember("Alex");
        ShoppingListMember.Item item1 = L1.new Item("Apfel", 2);
        item1.printDetails();

        // 2. Statische geschachtelte Klasse (Keine äußere Instanz nötig)
        System.out.println("\n=== 2. Static Nested Class ===");
        //
        boolean valid = ShoppingListStatic.ItemValidator.isValid("Milch", 1);
        System.out.println("Ist 'Milch' gültig? " + valid);

        // 3. Lokale Klasse (Ausführung in Methode)
        System.out.println("\n=== 3. Local Class ===");
        //
        ShoppingListLocal localExample = new ShoppingListLocal();
        localExample.printFormattedList("Alex", List.of("Brot", "Käse"));

        // 4. Anonyme Klasse (Einmalige Implementierung)
        System.out.println("\n=== 4. Anonymous Class ===");
        //
        List<String> shoppingList = new ArrayList<>(List.of("Milch", "Apfel", "Brot"));
        ShoppingListAnonymous anonExample = new ShoppingListAnonymous();
        anonExample.sortItemsAlphabetically(shoppingList);
        System.out.println("Sortierte Liste: " + shoppingList);
    }
}

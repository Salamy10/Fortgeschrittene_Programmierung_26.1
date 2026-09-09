package training.Gemini_Training._4_Inner_Classes;

public class ShoppingListStatic {

    // Statische geschachtelte Klasse (Static Nested Class)
    public static class ItemValidator {
        public static boolean isValid(String name, int amount) {
            return name != null && !name.isBlank() && amount > 0;
        }
    }
}
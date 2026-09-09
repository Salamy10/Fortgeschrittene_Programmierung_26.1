package training.Gemini_Training._4_Inner_Classes;

import java.util.List;

public class ShoppingListLocal {

    public void printFormattedList(String owner, List<String> items) {
        // Lokale Klasse (Local Class) innerhalb der Methode
        class ItemFormatter {
            public String format(String item, int index) {
                return (index + 1) + ". " + item + " (für " + owner + ")";
            }
        }

        ItemFormatter formatter = new ItemFormatter();
        for (int i = 0; i < items.size(); i++) {
            System.out.println(formatter.format(items.get(i), i));
        }
    }
}
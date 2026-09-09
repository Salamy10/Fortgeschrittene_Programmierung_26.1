package training.Gemini_Training._4_Inner_Classes;

import java.util.Comparator;
import java.util.List;

public class ShoppingListAnonymous {

    public void sortItemsAlphabetically(List<String> items) {
    	
        // Anonyme Klasse (Anonymous Class): Erzeugt direkt ein Comparator-Objekt
        items.sort(
        		
        	new Comparator<String>() {        	
        		@Override
        		public int compare(String item1, String item2) {
        			return item1.compareTo(item2);
            } 
            
        });
        
    }
}
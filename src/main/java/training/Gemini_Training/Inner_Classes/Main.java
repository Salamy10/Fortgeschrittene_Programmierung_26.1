package training.Gemini_Training.Inner_Classes;

public class Main {
	public static void main(String[] args) {
		
		ShoppingList L1 = new ShoppingList("Alex");
		
		ShoppingList.Item I1 = L1.new Item("Apfel", 2);
		ShoppingList.Item I2 = L1.new Item("Milch", 1);
		
	}

}

package training.Gemini_Training.Inner_Classes;

public class ShoppingList {
	
	private String owner;
	
	public ShoppingList(String owner) {
		this.owner = owner;
	}
	
	public String getOwner() {
		return owner;
	}
	
	class Item{
		private String name;
		private int amount;
		
		public Item(String name, int amount) {
			this.name = name;
			this.amount = amount;
		}
		
		void printDetails() {
			
		}
	}

}

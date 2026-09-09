package training.Gemini_Training._4_Inner_Classes;

public class ShoppingListMember {
    private String owner;

    public ShoppingListMember(String owner) {
        this.owner = owner;
    }

    // Elementklasse (Member Inner Class)
    public class Item {
        private String name;
        private int amount;

        public Item(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        public void printDetails() {
            System.out.println("[" + ShoppingListMember.this.owner + "] " + amount + "x " + name);
        }
    }
}
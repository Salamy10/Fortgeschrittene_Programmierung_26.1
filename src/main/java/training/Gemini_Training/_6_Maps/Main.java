package training.Gemini_Training._6_Maps;

public class Main {

	public static void main(String[] args) {

		WarehouseManager m = new WarehouseManager();
		
		Product p1 = new Product("p001", "Apfel");
		StockInfo s1 = new StockInfo(5, 3.50);
		Product p2 = new Product("p002", "Birne");
		StockInfo s2 = new StockInfo(7, 2.50);
		Product p3 = new Product("p003", "Orange");
		StockInfo s3 = new StockInfo(2, 6.50);
		
		System.out.println("---Hinzufügen von Produkten & StockInfos---");
		m.addProduct(p1, s1);
		m.addProduct(p2, s2);
		m.addProduct(p3, s3);
		
		System.out.println();
		System.out.println("---Ersetzen der StockInfo eines Produkts---");
		StockInfo s4 = new StockInfo(2, 5);
		m.addProduct(p1, s4);

		System.out.println();
		System.out.println("---StockInfo p2---");
		System.out.println(m.getStock(p2));
		
		System.out.println();
		System.out.println("---Existentes Produkt---");		
		System.out.println("Existiert p3?");
		System.out.println(m.checkProductExists(p3));
		
		System.out.println();
		System.out.println("---Nicht Existentes Produkt---");		
		Product p4 = new Product("", "");
		System.out.println("Existiert p4?");
		System.out.println(m.checkProductExists(p4));
		
		System.out.println();
		System.out.println("---Alle Produktnamen---");		
		m.printAllProductNames();
		
		System.out.println();
		System.out.println("---Gesamter Warenwert---");
		System.out.println(m.calculateTotalInventoryValue());
		
		System.out.println();
		System.out.println("---Normal---");
		m.printInventoryList();
		
		System.out.println();
		System.out.println("---Lambda---");
		m.printInventoryLambda();

	}

}

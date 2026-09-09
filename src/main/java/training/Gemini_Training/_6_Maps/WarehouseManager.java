package training.Gemini_Training._6_Maps;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class WarehouseManager {
	
	Map<Product, StockInfo> inventory = new HashMap<>();
	
	void addProduct(Product product, StockInfo stockInfo){		
		/*Workaround weil fehlendes wissen*/
//		if(inventory.containsKey(product)) {			
//			System.out.println("Alte StockInfo: " + inventory.get(product).quantity + " Stück zum Preis von " + inventory.get(product).price + "€.");
//		}		
//		inventory.put(product, stockInfo);
		
		StockInfo oldStockInfo = inventory.put(product, stockInfo);
		System.out.println(oldStockInfo);
	}
	
	StockInfo getStock(Product product) {
		return inventory.get(product);
	}
	
	boolean checkProductExists(Product product) {
		return inventory.containsKey(product);
	}
	
	void printAllProductNames(){
		System.out.println("Alle vorhandenen Produkte:");
		
		for(Product p : inventory.keySet()) {
			System.out.println(p.name);
		}
	}
	
	double calculateTotalInventoryValue() {
		double sum = 0;
		
		for(StockInfo s : inventory.values()) {
			sum += (s.quantity * s.price);
		}		
		return sum;
	}
	
	void printInventoryList() {
		System.out.println("Inventar:");
		for(Map.Entry<Product, StockInfo> e : inventory.entrySet()) {
			System.out.println(e.getKey().id + ": " + e.getKey().name + " -> " + e.getValue().quantity + " x " + e.getValue().price + "€");
		}
	}
	
	void printInventoryLambda() {
		System.out.println("Inventar:");
		inventory.forEach((p, s) -> System.out.println(p.id + ": " + p.name + " -> " + s.quantity + " x " + s.price + "€"));
	}
	
}

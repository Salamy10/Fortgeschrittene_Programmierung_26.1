package training.Documentation_ClassDiagrams.tanteEmmaLaden;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public abstract class Product {
	
	private final String description; 
	private double price;
	private String currency;
	
	public Product(String description, double price, String currency) {
		this.description = description;
		this.price = price;
		this.currency = currency;
	}
	
	public String getDescription() {
		return this.description;
	}
}

package training.Gemini_Training._1_Records;

public record Book(String isbn, String title, double price) {
	
	public Book{
		if(isbn == null || isbn.isBlank()) {
			throw new IllegalArgumentException("ISBN muss aufgefüllt sein!");
		}
		if(price < 0) {
			throw new IllegalArgumentException("Preis darf nicht negativ sein");
		}
	}
	
	public double applyDiscount(double percentage) {
		return price-(price*percentage/100);
	}

}

package training.Gemini_Training._1_Records;

public record Student(String name, int matrikelnummer) {
	
	public Student {
        if (matrikelnummer <= 0) {
            throw new IllegalArgumentException("Matrikelnummer muss positiv sein.");
        }
    }
	
}

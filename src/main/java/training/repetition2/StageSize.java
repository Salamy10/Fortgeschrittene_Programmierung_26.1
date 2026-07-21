package training.repetition2;

public enum StageSize {
	
	SMALL("klein"), MEDIUM("Mittel"), LARGE("Groß");
	
	private final String description;
	
	StageSize(String description){
		this.description = description;
	}

}
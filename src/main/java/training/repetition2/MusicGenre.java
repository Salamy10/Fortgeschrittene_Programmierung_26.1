package training.repetition2;

public enum MusicGenre {
	
	ROCK("Rock"), POP("Pop"), JAZZ("Jazz"), METAL("Metal"), Electronic("Electronic");
	
	private final String description;
	
	MusicGenre(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
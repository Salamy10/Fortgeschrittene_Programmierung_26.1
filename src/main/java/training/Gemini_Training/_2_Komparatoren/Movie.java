package training.Gemini_Training._2_Komparatoren;

public record Movie(String title, int releaseYear, double rating) implements Comparable<Movie>{

	@Override
	public int compareTo(Movie o) {
		return Integer.compare(this.releaseYear, o.releaseYear);
	}
}

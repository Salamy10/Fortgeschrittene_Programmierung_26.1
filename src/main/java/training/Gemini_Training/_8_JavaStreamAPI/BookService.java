package training.Gemini_Training._8_JavaStreamAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class BookService {
	
	List<Book> books = new ArrayList<>();

	List<Book> getBooksByGenre(String genre) {
		
		return books.stream()
					.filter(b -> b.getGenres()
					.contains(genre))
					.toList();
	}
	
	
	
	
}

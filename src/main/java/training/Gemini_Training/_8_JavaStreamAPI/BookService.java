package training.Gemini_Training._8_JavaStreamAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookService {
	
	List<Book> books = new ArrayList<>();

	List<Book> getBooksByGenre(String genre) {
		
		return books.stream()
					.filter(b -> b.getGenres().contains(genre))
					.toList();
	}
	
	List<Book> getTop3LongestBooks(){
		
		return books.stream()
					.sorted((b1, b2) -> Integer.compare(b2.getPages(), b1.getPages()))
					.limit(3)
					.toList();				
	}
	
	double getAverageBookPrice() {
		
		return books.stream()
					.mapToDouble(Book::getPrice)
					.average()
					.orElse(0.00);
	}
	
	List<String> getUniqueAuthors(){
		
		return books.stream()
					.map(Book::getAuthor)
					.distinct()
					.toList();
	}
	
	boolean hasBookCheaperThan(double limit) {
		
		return books.stream()
					.anyMatch(b -> b.getPrice() < limit);
	}
	
	String getFormattedTitles() {
		
		return books.stream()
					.map(Book::getTitle)
					.collect(Collectors.joining(", "));
	}
	
	Map<String, List<Book>> groupBooksByAuthor(){
		
		return books.stream()
					.collect(Collectors.groupingBy(Book::getAuthor));
	}
	
	
	
}

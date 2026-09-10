package training.Gemini_Training._8_JavaStreamAPI;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
	String title;
	String author;
	double price;
	int pages;
	List<String> genres;
}

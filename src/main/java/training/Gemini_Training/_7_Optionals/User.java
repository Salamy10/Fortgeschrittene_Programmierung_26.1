package training.Gemini_Training._7_Optionals;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	
	String id;
	String name;
	String email;
	
	public Optional<String> getOptionalEmail(){
		return Optional.ofNullable(email);
	}

}
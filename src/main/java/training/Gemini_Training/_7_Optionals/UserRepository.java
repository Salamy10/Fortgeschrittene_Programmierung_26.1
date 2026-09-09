package training.Gemini_Training._7_Optionals;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class UserRepository {
	
	List<User> users = new ArrayList<>();
	
	void addUser(User user) {
		users.add(user);
	}
	
	Optional<User> findUserById(String id) {
		for(User u : users) {
			if(u.getId().equals(id)) {
				return Optional.of(u);
			} 
		}
		return Optional.empty();
	}
	
	Optional<User> findUserByName(String name) {
		for(User u : users) {
			if(u.getId().equals(name)) {
				return Optional.of(u);
			} 			
		}
		return Optional.empty();
	}

}

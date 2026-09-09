package training.Gemini_Training._7_Optionals;

import java.util.Optional;

public class Main {
	public static void main(String[] args) {
		
		User u1 = new User("u001", "Anton", "anton@gmail.com");
		User u2 = new User("u002", "Nele", null);
		
		UserRepository r = new UserRepository();
		
		r.addUser(u1);
		r.addUser(u2);
		System.out.println("---Find by If---");
		Optional<User> userOpt = r.findUserById("u001");
		if (userOpt.isPresent()) {
		    System.out.println("User gefunden: " + userOpt.get().getName());
		}

		System.out.println();
		System.out.println("---Find by Lambda---");
		r.findUserByName("Nele").ifPresentOrElse(
			    u -> System.out.println("Gefunden: " + u.getName()),
			    () -> System.out.println("Nutzer existiert nicht")
		);

		System.out.println();
		System.out.println("---Wert oder Nachricht---");
		String email = u2.getOptionalEmail().orElse("Keine E-Mail hinterlegt");
		System.out.println(email); // Gibt "Keine E-Mail hinterlegt" aus

		System.out.println();
		System.out.println("---Wert oder Exception---");
		// Sucht nach der ID "u999", die nicht existiert -> bricht ab und wirft eine Exception
		User u = r.findUserById("u999").orElseThrow();
		
	}
}

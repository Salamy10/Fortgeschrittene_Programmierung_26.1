package training.Gemini_Training._3_Exceptions;

import javax.naming.InsufficientResourcesException;

public class Main {
    public static void main(String[] args) { // kein 'throws' mehr nötig, da try-catch verwendet wird
        BankAccount b100 = new BankAccount("b100", 500);

        try {
            System.out.println("Zahle 50 € ein...");
            b100.deposit(50); // Gültige Einzahlung
            
            System.out.println("Zahle -50 € ein...");
            b100.deposit(-50); // Ungültig -> springt sofort in den catch-Block
            
        } catch (InvalidAmountException e) { // Passende Exception abfangen
            System.out.println("Fehler gefangen: " + e.getMessage());
        } finally {
            // Wird IMMER ausgeführt, egal ob eine Exception geworfen wurde oder nicht
            System.out.println("Transaktion beendet. Aktueller Kontostand: " + b100.getBalance());
        }
    }
}

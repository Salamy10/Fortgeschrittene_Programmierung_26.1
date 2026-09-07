package training.Gemini_Training._3_Exceptions;

public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Einzahlungsbetrag muss positiv sein!");
        }
        this.balance += amount;
    }

    public double getBalance() {
        return this.balance;
    }
}

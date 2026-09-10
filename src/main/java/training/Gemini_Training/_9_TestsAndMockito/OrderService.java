package training.Gemini_Training._9_TestsAndMockito;

public class OrderService { // KEIN implements CustomerRepository!
    
    private final CustomerRepository customerRepository;

    public OrderService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public double calculateTotalPrice(double basePrice, String customerId) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Ungültiger Preis");
        }

        // Aufruf auf dem übergebenen Instanz-Objekt!
        double discount = customerRepository.getCustomerDiscount(customerId);
        
        return basePrice * (1.0 - discount);
    }
}
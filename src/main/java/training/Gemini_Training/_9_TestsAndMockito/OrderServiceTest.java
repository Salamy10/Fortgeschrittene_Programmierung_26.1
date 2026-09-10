package training.Gemini_Training._9_TestsAndMockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// 1. Manuelle Hilfsklasse als Ersatz für eine echte Datenbank
class FakeCustomerRepository implements CustomerRepository {
    @Override
    public double getCustomerDiscount(String customerId) {
        if ("k001".equals(customerId)) {
            return 0.15; // 15 % Rabatt
        }
        return 0.0; // kein Rabatt
    }
}

// 2. Die JUnit 5 Testklasse
public class OrderServiceTest {

    private OrderService service;

    @BeforeEach
    void setUp() {
        // Vor JEDEM Test bekommt der Service eine frische Fake-Datenbank
        CustomerRepository fakeRepo = new FakeCustomerRepository();
        service = new OrderService(fakeRepo);
    }

    @Test
    void testCalculateTotalPrice_WithDiscount() {
        // Arrange (Vorbereitung): Kunde "k001" hat im Fake 15 % Rabatt
        double basePrice = 100.0;
        String customerId = "k001";

        // Act (Ausführung)
        double result = service.calculateTotalPrice(basePrice, customerId);

        // Assert (Prüfung): 100 € - 15% = 85 €
        assertEquals(85.0, result, 0.001);
    }

    @Test
    void testCalculateTotalPrice_WithoutDiscount() {
        // Arrange: Kunde "k002" hat im Fake 0 % Rabatt
        double result = service.calculateTotalPrice(100.0, "k002");

        // Assert: 100 € - 0% = 100 €
        assertEquals(100.0, result, 0.001);
    }

    @Test
    void testCalculateTotalPrice_InvalidPrice_ThrowsException() {
        // Prüft, ob bei ungültigem Preis die Exception geworfen wird
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculateTotalPrice(-10.0, "k001");
        });
    }
}
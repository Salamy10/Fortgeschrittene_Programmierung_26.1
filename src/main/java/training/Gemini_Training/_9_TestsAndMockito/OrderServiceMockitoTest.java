package training.Gemini_Training._9_TestsAndMockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Aktiviert die Mockito-Verarbeitung
public class OrderServiceMockitoTest {

    @Mock
    private CustomerRepository customerRepository; // Erstellt automatisch eine Atrappe

    @InjectMocks
    private OrderService service; // Erstellt den Service und setzt den Mock automatisch ein

    @Test
    void testCalculateTotalPrice_WithDiscount() {
        // Arrange: Dem Mock beibringen, was er bei "k001" antworten soll (Stubbing)
        when(customerRepository.getCustomerDiscount("k001")).thenReturn(0.15);

        // Act
        double result = service.calculateTotalPrice(100.0, "k001");

        // Assert
        assertEquals(85.0, result, 0.001);
        
        // Zusatzprüfstein: Verifizieren, dass der Rabatt genau 1-mal abgefragt wurde
        verify(customerRepository, times(1)).getCustomerDiscount("k001");
    }

    @Test
    void testCalculateTotalPrice_InvalidPrice_NeverCallsRepository() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculateTotalPrice(-10.0, "k001");
        });

        // Verifizieren: Bei ungültigem Preis durfte die Datenbank GAR NICHT kontaktiert werden
        verifyNoInteractions(customerRepository);
    }
}
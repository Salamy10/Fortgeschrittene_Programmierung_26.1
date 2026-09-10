# Cheatsheet: Softwaretests, Unit Testing (JUnit 5) & Mockito

---

## 1. Grundlagen Softwaretests & Teststufen

### Testpyramide & Einordnung
* **Unit Tests (Komponententests):** Testen kleinste isolierte Code-Einheiten (Klassen, Methoden) ohne externe Systeme.
* **Integrationstests:** Prüfen das Zusammenspiel mehrerer Module/Komponenten (z. B. Service + Datenbank).
* **Systemtests / E2E-Tests:** Prüfen das Gesamtsystem aus Endanwendersicht.

### Das FIRST-Prinzip für saubere Unit Tests
* **F - Fast:** Tests müssen in Millisekunden ausführen.
* **I - Independent / Isolated:** Tests dürfen nicht voneinander abhängen oder eine bestimmte Ausführungsreihenfolge erzwingen.
* **R - Repeatable:** Liefern in jeder Umgebung (lokal, CI/CD) dieselben Ergebnisse.
* **S - Self-Validating:** Test besteht (Pass) oder schlägt fehl (Fail) – keine manuelle Konsolenauswertung nötig.
* **T - Timely:** Werden zeitnah mit oder vor (TDD) dem Produktivcode geschrieben.

### Das AAA-Muster (Arrange - Act - Assert)
```java
@Test
void testCalculateTotalPrice() {
    // 1. ARRANGE (Vorbereiten): Objekte erzeugen, Mocks konfigurieren, Eingaben festlegen
    double basePrice = 100.0;
    String customerId = "k001";
    when(customerRepository.getCustomerDiscount("k001")).thenReturn(0.15);

    // 2. ACT (Ausführen): Die zu testende Methode aufrufen
    double actualPrice = orderService.calculateTotalPrice(basePrice, customerId);

    // 3. ASSERT (Überprüfen): Ergebnisse validieren
    assertEquals(85.0, actualPrice, 0.001);
}
```

---

## 2. JUnit 5 (Jupiter) Framework

### Lifecycle Annotationen
| Annotation | Beschreibung |
| :--- | :--- |
| `@Test` | Markiert eine Methode als Testfall. |
| `@BeforeEach` | Läuft **vor jedem** einzelnen `@Test` ab (z. B. Objekte neu initialisieren). |
| `@AfterEach` | Läuft **nach jedem** einzelnen `@Test` ab (z. B. aufräumen). |
| `@BeforeAll` | Läuft **einmalig vor allen** Tests einer Klasse ab (Methode muss `static` sein). |
| `@AfterAll` | Läuft **einmalig nach allen** Tests einer Klasse ab (Methode muss `static` sein). |
| `@Disabled` | Deaktiviert einen Testfall temporär (optional mit Begründung: `@Disabled("WIP")`). |
| `@DisplayName("...")` | Definiert einen benutzerdefinierten Namen für die Testanzeige in der IDE. |

### Wichtige Assertions (`org.junit.jupiter.api.Assertions.*`)
```java
// Wertevergleich
assertEquals(expected, actual);
assertEquals(expected, actual, delta); // Für double/float Genauigkeit

// Boolsches Ergebnis
assertTrue(condition);
assertFalse(condition);

// Null-Checks
assertNull(object);
assertNotNull(object);

// Objektidentität vs. Gleichheit
assertSame(obj1, obj2);    // obj1 == obj2
assertNotSame(obj1, obj2);

// Exceptions prüfen
assertThrows(IllegalArgumentException.class, () -> {
    orderService.calculateTotalPrice(-10.0, "k001");
});

// Gruppierte Assertions (führt alle durch, auch wenn eine fehlschlägt)
assertAll("Kundendaten",
    () -> assertEquals("Max", customer.getFirstName()),
    () -> assertEquals("Mustermann", customer.getLastName())
);
```

### Parametrisierte Tests (`@ParameterizedTest`)
Erlaubt das Ausführen desselben Tests mit unterschiedlichen Eingabedaten.

```java
@ParameterizedTest
@ValueSource(doubles = {-1.0, 0.0, -100.0})
void testInvalidPrices(double invalidPrice) {
    assertThrows(IllegalArgumentException.class, () -> {
        orderService.calculateTotalPrice(invalidPrice, "k001");
    });
}

@ParameterizedTest
@CsvSource({
    "100.0, k001, 85.0",  // basePrice, customerId, expected
    "200.0, k002, 200.0"
})
void testCalculateTotalPriceWithCsv(double basePrice, String customerId, double expected) {
    // ...
}
```

---

## 3. Mockito Framework

### Warum Mocking?
* **Isolation:** Isolierung der zu testenden Klasse (*Class Under Test / CUT*) von externen Abhängigkeiten (Datenbank, REST-APIs, Fremddienste).
* **Geschwindigkeit:** Keine langsamen Netzwerk- oder DB-Zugriffe im Unit Test.
* **Kontrolle:** Simulation von Ausnahmesituationen (z. B. `DatabaseDownException`) ohne echten Ausfall.

### Core Annotationen
```java
@ExtendWith(MockitoExtension.class) // Aktiviert Mockito in JUnit 5
public class OrderServiceTest {

    @Mock
    private CustomerRepository customerRepository; // Erstellt eine simulierte Atrappe (Mock)

    @InjectMocks
    private OrderService orderService; // Erzeugt das Testobjekt & injiziert die @Mock-Objekte
}
```

### Stubbing: Methodenverhalten festlegen (`when`)
```java
// Standard Rückgabewert festlegen
when(mock.method(arg)).thenReturn(value);

// Mehrere Aufrufe verketten (1. Aufruf -> val1, 2. Aufruf -> val2)
when(mock.method(arg)).thenReturn(val1, val2);

// Exception bei Methodenaufruf werfen
when(mock.method(arg)).thenThrow(new RuntimeException("DB Error"));

// Void-Methoden stubben (andere Syntax nötig!)
doNothing().when(mock).voidMethod();
doThrow(new IllegalArgumentException()).when(mock).voidMethod();
```

### Argument Matcher
Verwendung, wenn der genaue Argumentwert flexibel sein soll:
```java
// Beliebige Argumente akzeptieren
when(customerRepository.getCustomerDiscount(anyString())).thenReturn(0.10);
when(repo.findById(anyInt())).thenReturn(Optional.of(user));

// WICHTIG: Werden Matcher verwendet, MÜSSEN ALLE Argumente Matcher sein!
when(service.process(eq("STAMM"), anyInt())).thenReturn(true);
```

### Verifikation: Interaktionen prüfen (`verify`)
Prüft, ob und wie oft eine Methode auf dem Mock aufgerufen wurde.

```java
// Standard: Prüft, ob Methode genau 1-mal aufgerufen wurde
verify(customerRepository).getCustomerDiscount("k001");

// Exakte Häufigkeit prüfen
verify(customerRepository, times(1)).getCustomerDiscount("k001");
verify(customerRepository, times(3)).someMethod();
verify(customerRepository, never()).someMethod();
verify(customerRepository, atLeastOnce()).someMethod();
verify(customerRepository, atMost(2)).someMethod();

// Keine Interaktionen vorhanden
verifyNoInteractions(customerRepository);

// Keine WEITEREN Interaktionen vorhanden (nach vorherigen verifies)
verifyNoMoreInteractions(customerRepository);
```

### Captor: Argumente abfangen (`ArgumentCaptor`)
Ermöglicht die Inspektion von Argumenten, die an den Mock übergeben wurden.

```java
@Captor
private ArgumentCaptor<String> stringCaptor;

@Test
void testCaptor() {
    orderService.calculateTotalPrice(100.0, "k001");

    verify(customerRepository).getCustomerDiscount(stringCaptor.capture());
    assertEquals("k001", stringCaptor.getValue());
}
```

---

## 4. Gegenüberstellung: Manuelles Mocking vs. Mockito

| Kriterium | Manuelles Fake (Stub-Klasse) | Mockito Framework |
| :--- | :--- | :--- |
| **Aufwand** | Hoch (Zusätzliche Klasse/Interface-Implementierung nötig). | Minimal (Annotation `@Mock` genügt). |
| **Wartungsaufwand** | Ändert sich das Interface, muss die Fake-Klasse manuell angepasst werden. | Mocks passen sich automatisch an Interface-Änderungen an. |
| **Flexibilität** | Aufwendig: Viele `if-else`-Verzweigungen für verschiedene Testfälle nötig. | Extrem hoch: Verhalten wird pro Testfall via `when(...).thenReturn(...)` definiert. |
| **Interaktionsprüfung** | Nicht ohne eigene Zähler/Booleans in der Fake-Klasse möglich. | Direkt eingebaut via `verify(mock, times(n))`. |
| **Einsatzbereich** | Für sehr einfache, statische Dummies oder State-based Testing. | Industriestandard für Verhaltenstests (Behavior-driven Verification). |
# Klausur-Vorbereitung: Programmierung II (DHBW)

Diese Studienhilfe basiert auf den 9 Themen-Cheatsheets (Records, Comparators, Exceptions, Innere Klassen, Lambdas, Maps, Optionals, Stream API, JUnit5/Mockito), dem erlaubten Methoden-Cheatsheet sowie der Musterklausur "RobotFactory" samt Musterlösung.

---

## 1. Typische Klausur-Fallstricke (Pitfalls)

### Optionals
- **`orElse()` vs. `orElseGet()`**: `orElse(x)` wertet `x` **immer sofort** aus (auch wenn das Optional gefüllt ist!). `orElseGet(() -> x)` wertet **nur bei Bedarf (lazy)** aus. Klassiker: `orElse(teureBerechnung())` ruft die Methode auch bei vorhandenem Wert unnötig auf.
- **`Optional.of()` vs. `ofNullable()`**: `of(null)` wirft sofort eine `NullPointerException`. Bei unsicherem Nullwert immer `ofNullable()` verwenden.
- **`get()` ohne Prüfung**: wirft `NoSuchElementException`, wenn leer — nie ungeprüft aufrufen.
- Optional **niemals als Methodenparameter oder Feld**, nur als Rückgabetyp.

### Streams
- **Seiteneffekte in Streams** (z. B. externe Variable in `map()`/`forEach()` verändern) sind schlechter Stil und bei `effectively final`-Variablen sogar ein Compilerfehler.
- Ein Stream ist **nur einmal konsumierbar** — zweiter Aufruf einer terminalen Operation wirft `IllegalStateException`.
- **Lazy Evaluation**: `filter()`/`map()` laufen erst bei der terminalen Operation.
- `average()`, `min()`, `max()` liefern `Optional`/`OptionalDouble` — nie ungeprüft `.getAsDouble()` ohne `orElse(...)`.

### Comparator / Comparable
- **Integer-Overflow-Falle**: niemals `this.wert - other.wert` zum Vergleich nutzen (negative/große Zahlen) → immer `Integer.compare()` / `Double.compare()`.
- `.reversed()` dreht bei verketteten Comparatoren (`thenComparing`) die **gesamte Kette** um, nicht nur das letzte Kriterium.
- `List.of(...)` erzeugt eine **unveränderliche** Liste → `list.sort(...)` wirft `UnsupportedOperationException`. Immer in `new ArrayList<>(List.of(...))` einpacken.

### JUnit 5 / Mockito
- **Falsche Imports**: JUnit 5 nutzt `org.junit.jupiter.api.*` (nicht `org.junit.*`). `@Before`/`@After` (JUnit 4) heißen in JUnit 5 `@BeforeEach`/`@AfterEach`.
- **`@ExtendWith(MockitoExtension.class)` vergessen** (oder alternativ `MockitoAnnotations.openMocks(this)` in `@BeforeEach`) → `@Mock`-Felder bleiben `null`, `@InjectMocks`-Objekt bekommt keine Mocks injiziert.
- **Matcher-Mischregel**: Wird bei einem Methodenaufruf ein Matcher (`anyString()`, `anyInt()`, `eq(...)`) verwendet, **müssen alle** Argumente Matcher sein — kein Mix aus konkretem Wert und Matcher.
- **Void-Methoden stubben**: `when(mock.voidMethode())` funktioniert nicht — stattdessen `doNothing().when(mock).voidMethode()` bzw. `doThrow(...).when(mock).voidMethode()`.
- `assertThrows(Exception.class, () -> { ... })` erwartet eine **Lambda/Executable**, kein direkter Methodenaufruf.

### Records
- **Keine zusätzlichen Instanzfelder** außerhalb des Record-Kopfes erlaubt.
- **Shallow Immutability**: Ein `List`- oder `Map`-Feld bleibt intern veränderlich, wenn nicht defensiv kopiert wird (`items = List.copyOf(items);` im kompakten Konstruktor).
- Im kompakten Konstruktor **keine** expliziten `this.feld = feld;`-Zuweisungen schreiben — passiert automatisch am Ende.
- Records können **kein `extends`**, aber beliebig viele `implements`.

### Exceptions
- **Catch-Reihenfolge**: speziellere Exception muss **vor** der allgemeineren stehen (`FileNotFoundException` vor `IOException`), sonst Compilerfehler (unerreichbarer Code).
- **`return` im `finally`-Block** überschreibt Rückgabewerte und verschluckt zuvor geworfene Exceptions — Klassiker in Multiple-Choice-Fragen.
- Beim Überschreiben (`@Override`) darf eine Methode **keine breiteren** Checked Exceptions deklarieren als die Elternmethode, nur speziellere oder gar keine.

### Innere Klassen & Lambdas
- **Member Inner Class** braucht ein konkretes äußeres Objekt: `outer.new Inner()` (nicht `new Outer.Inner()`).
- **`Outer.this.feld`** nötig, wenn ein Feldname durch die innere Klasse verdeckt wird (Shadowing).
- **Effectively-Final-Regel**: Lokale/anonyme Klassen und Lambdas dürfen nur auf Variablen zugreifen, die nach der Initialisierung nicht mehr verändert werden.
- Lambdas haben **kein eigenes `this`** — es bezieht sich immer auf die umgebende Klasse.
- Bei genau einem untypisierten Parameter sind runde Klammern optional (`s -> ...`), bei 0 oder ≥2 Parametern **Pflicht**.

---

## 2. Das Spickzettel-Delta (Lücken-Analyse)

Das erlaubte `cheatsheet.md` listet **nur Methodensignaturen** einzelner Klassen — keine Syntaxmuster, keine Annotationen, keine Schlüsselwörter. Folgendes fehlt komplett oder teilweise und muss auswendig sitzen:

| Bereich | Fehlt im Cheatsheet | Was du im Kopf haben musst |
| :--- | :--- | :--- |
| **Alle Annotationen** | Komplett fehlend | `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`, `@Disabled`, `@DisplayName`, `@ParameterizedTest`, `@ValueSource`, `@CsvSource`, `@Mock`, `@InjectMocks`, `@Captor`, `@ExtendWith`, `@FunctionalInterface`, `@Override` |
| **Mockito-Verifikation** | Nur `when()`, `thenReturn()`, `openMocks()` gelistet | `verify()`, `times(n)`, `never()`, `atLeastOnce()`, `atMost(n)`, `doThrow()`, `doNothing()`, `ArgumentCaptor`, `anyString()`, `anyInt()`, `eq()`, `verifyNoInteractions()`, `verifyNoMoreInteractions()` |
| **Comparator (erweitert)** | Nur `compare()`, `comparing()` gelistet | `comparingInt()`, `comparingDouble()`, `reversed()`, `thenComparing()`, `thenComparingInt()`, `naturalOrder()`, `nullsFirst()`/`nullsLast()` |
| **Optional (erweitert)** | Nur `empty/get/ifPresent/ifPresentOrElse/isPresent/of/ofNullable/orElse` | `orElseGet()`, `orElseThrow()`, `map()`, `flatMap()`, `filter()`, `isEmpty()` |
| **Map (erweitert)** | Nur `containsKey/containsValue/entrySet/forEach/get/keySet/put/putIfAbsent/values` | `computeIfAbsent()`, `computeIfPresent()`, `merge()`, `replaceAll()`, `remove()`, `size()`, `isEmpty()` |
| **Stream** | Fast vollständig, aber **`reduce()` fehlt** | `reduce()`-Signaturen (mit/ohne Identity, mit Accumulator) |
| **`java.util.Objects`** | Komplett fehlend | `Objects.requireNonNull()`, `Objects.equals()`, `Objects.hash()` |
| **`List.copyOf()`** | Fehlt | Für defensives Kopieren in Records essenziell |
| **Exception-Hierarchie** | Komplett fehlend | `Throwable` → `Error`/`Exception` → `RuntimeException`; Syntax für eigene Exceptions (`extends Exception`/`extends RuntimeException`, `super(message)`) |
| **Record-Syntax** | Komplett fehlend | `record Name(Typ feld, ...) { ... }`, kompakter Konstruktor, `implements` |
| **Lambda-Syntax** | Komplett fehlend | Kurzformen, `::`-Methodenreferenzen (4 Arten) |
| **Innere-Klassen-Syntax** | Komplett fehlend | `outer.new Inner()`, `Outer.this.feld`, `static class` |
| **try-with-resources / multi-catch** | Komplett fehlend | `try (Resource r = ...) { }`, `catch (A \| B e) { }` |

**Kernaussage:** Das Cheatsheet hilft bei *"Wie heißt die Methode und was gibt sie zurück?"*, aber **nicht** bei *"Wie sieht die Syntax drumherum aus?"*. Genau diese Syntax-Gerüste (Annotationen, Record-Kopf, Testklassen-Aufbau, Exception-Deklaration) musst du auswendig abrufen können.

---

## 3. Punkte-Hotspots & Priorisierung (Pareto 80/20)

Ein Blick in die Musterlösung (RobotFactory-Klausur, 52 Punkte gesamt über 3 Aufgaben) zeigt ein wichtiges Muster: **Punkte werden extrem granular vergeben — schon für korrekte Annotationen, Klammern und Methodensignaturen gibt es 0,5–1 Punkt.** Das heißt: Das saubere "Grundgerüst" bringt fast so viel wie die eigentliche Logik.

| Kategorie | Ungefährer Punkteanteil | Warum |
| :--- | :--- | :--- |
| **Stream-Pipelines korrekt schreiben** (`filter`, `map`, `distinct`, `sorted`, `collect`, `groupingBy`, `mapToDouble`/`average`) | ~35 % | Kommt in praktisch jeder Klausur in mind. einer Record-Methode vor, viele Einzelbewertungspunkte pro Zeile |
| **Mockito-Testklassen-Skelett** (`@Mock`, `@InjectMocks`, `@BeforeEach`-Setup, `when/thenReturn`, `assertEquals`) | ~30 % | Wiederkehrendes Muster, hoher Boilerplate-Anteil = viele "leichte" Punkte, wenn das Muster sitzt |
| **Klassen-/Record-Grundgerüst** (Vererbung, `implements`, Konstruktoren, `compareTo`) | ~25 % | Struktur- und Signaturpunkte, unabhängig von der eigentlichen "cleveren" Logik |
| **Reine Theoriefragen / Multiple-Choice** | ~10 % | Meist Fallstricke aus Abschnitt 1 (Exceptions, Overflow, Immutability) |

**Priorität zum Lernen:**
1. **Mockito-Test-Skelett blind hinschreiben können** — höchster ROI, da fast unverändert wiederkehrend.
2. **Stream-Pipeline-Bausteine automatisieren**, v. a. `groupingBy` mit Downstream-Collector und `mapToDouble().average().orElse(...)`.
3. **Record- und `Comparable`-Grundgerüst** sauber und ohne Zögern aufschreiben.
4. **Optional-Ketten** (`orElseThrow`, `ifPresentOrElse`) — klein, aber häufig abgefragt.
5. Exceptions/innere Klassen zuletzt — seltener Kernbestandteil einer Programmieraufgabe, aber leicht zu punkten, wenn die Regeln sitzen.

---

## 4. Muster-Templates für Freitext-/Programmieraufgaben

### A. Leere Mockito-Testklasse (Grundgerüst)
```java
@ExtendWith(MockitoExtension.class)
public class BeispielServiceTest {

    @Mock
    private AbhaengigkeitRepository repository;

    @InjectMocks
    private BeispielService service;

    @BeforeEach
    void setUp() {
        // ggf. zusätzliches Setup
    }

    @Test
    void testMethode() {
        // ARRANGE
        when(repository.findById(anyString())).thenReturn(Optional.of(neuesObjekt));

        // ACT
        var ergebnis = service.methode("id");

        // ASSERT
        assertEquals(erwartet, ergebnis);
        verify(repository, times(1)).findById("id");
    }
}
```

### B. Record mit Generics, Comparable & defensivem Kopieren
```java
public record Bestellung<T extends Artikel>(String kundenId, List<T> positionen)
        implements Comparable<Bestellung<T>> {

    public Bestellung {
        Objects.requireNonNull(kundenId, "kundenId darf nicht null sein");
        positionen = List.copyOf(positionen); // defensives Kopieren
    }

    @Override
    public int compareTo(Bestellung<T> other) {
        return Integer.compare(this.positionen.size(), other.positionen.size());
    }
}
```

### C. Standard-Stream-Pipeline mit `groupingBy`
```java
Map<String, List<Artikel>> gruppiert = artikel.stream()
        .filter(a -> a.getPreis() > 0)
        .distinct()
        .collect(Collectors.groupingBy(Artikel::getKategorie));

// Mit Downstream-Collector (z. B. Anzahl statt Liste)
Map<String, Long> anzahlProKategorie = artikel.stream()
        .collect(Collectors.groupingBy(Artikel::getKategorie, Collectors.counting()));

// Durchschnitt mit sicherer Optional-Behandlung
double durchschnittspreis = artikel.stream()
        .mapToDouble(Artikel::getPreis)
        .average()
        .orElse(0.0);
```

### D. Eigene Exception (checked & unchecked)
```java
// Checked – muss behandelt oder deklariert werden
public class UngueltigerZustandException extends Exception {
    public UngueltigerZustandException(String message) {
        super(message);
    }
}

// Unchecked – optionale Behandlung
public class ValidierungsException extends RuntimeException {
    public ValidierungsException(String message) {
        super(message);
    }
}

// Verwendung mit Exception-Chaining
try {
    repository.speichern(objekt);
} catch (SQLException e) {
    throw new ValidierungsException("Speichern fehlgeschlagen") ;
}
```

### E. Statische geschachtelte Klasse vs. Member Inner Class
```java
public class Aussen {
    private static String statischesFeld = "S";
    private String instanzFeld = "I";

    // Statische geschachtelte Klasse: kein äußeres Objekt nötig
    public static class Geschachtelt {
        void zeige() {
            System.out.println(statischesFeld); // nur statische Member erlaubt
        }
    }

    // Member Inner Class: braucht konkrete äußere Instanz
    public class Inner {
        void zeige() {
            System.out.println(Aussen.this.instanzFeld);
        }
    }
}

// Instanziierung:
Aussen.Geschachtelt g = new Aussen.Geschachtelt();
Aussen aussen = new Aussen();
Aussen.Inner i = aussen.new Inner();
```

### F. Comparator-Kette mit Tie-Breaker
```java
Comparator<Mitarbeiter> sortierung = Comparator
        .comparing(Mitarbeiter::nachname)
        .thenComparing(Mitarbeiter::vorname)
        .thenComparingInt(Mitarbeiter::alter);

mitarbeiterListe.sort(sortierung.reversed()); // dreht GESAMTE Kette um
```

---

## 5. Grober Überblick über die Klausurthemen (basierend auf den 9 Cheatsheets)

| Cheatsheet | Kernfokus für die Klausur |
| :--- | :--- |
| **1. Records** | Unveränderliche Datenklassen, kompakter Konstruktor für Validierung, Shallow Immutability & defensives Kopieren, keine zusätzlichen Instanzfelder, `implements` statt `extends`. |
| **2. Comparators** | `Comparable` (natürliche Ordnung, in der Klasse) vs. `Comparator` (extern, beliebig viele), Rückgabe-Vertrag (`<0/0/>0`), `Integer.compare()` statt Subtraktion, Verkettung mit `thenComparing`. |
| **3. Exceptions** | Checked vs. Unchecked, `try/catch/finally`, Multi-Catch, Try-With-Resources, eigene Exceptions, Regeln beim Überschreiben (`@Override` und Checked Exceptions), Exception-Chaining. |
| **4. Innere Klassen** | Vier Typen (statisch geschachtelt, Member Inner, lokal, anonym), Instanziierungssyntax, `Outer.this`, Effectively-Final-Regel, Brücke zu Lambdas. |
| **5. Lambdas** | Syntax-Kurzformen, funktionale Interfaces (`@FunctionalInterface`), Standard-Interfaces aus `java.util.function`, Methodenreferenzen (4 Arten), Effectively-Final-Regel, kein eigenes `this`. |
| **6. Maps** | `HashMap`-Grundlagen, die 4 Iterationswege, moderne Methoden (`computeIfAbsent`, `merge`, `replaceAll`), Bedeutung von `equals()`/`hashCode()` für Keys, Gefahr veränderlicher Keys. |
| **7. Optionals** | Erzeugung (`of`/`ofNullable`/`empty`), sicheres Auslesen, `orElse` vs. `orElseGet` vs. `orElseThrow`, funktionale Verarbeitung mit `map`/`flatMap`/`filter`, nie als Parameter/Feld. |
| **8. Stream API** | Pipeline-Aufbau (Quelle → intermediär → terminal), wichtigste Operationen, Primitiv-Streams, `Collectors` (`groupingBy`, `partitioningBy`, `joining`), Einmal-Konsumierbarkeit, Lazy Evaluation. |
| **9. JUnit 5 & Mockito** | Testpyramide, FIRST-Prinzip, AAA-Muster, JUnit-5-Lifecycle-Annotationen, Assertions, parametrisierte Tests, Mockito-Grundannotationen, Stubbing, Argument-Matcher, Verifikation, `ArgumentCaptor`. |

---

*Erstellt auf Basis der hochgeladenen Cheatsheets, des Methoden-Cheatsheets und der Musterklausur "RobotFactory" (DHBW, Programmierung II).*

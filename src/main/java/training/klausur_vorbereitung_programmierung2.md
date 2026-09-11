# Klausur-Vorbereitung: Programmierung II (DHBW)

Diese Studienhilfe basiert auf den 9 Themen-Cheatsheets (Records, Comparators, Exceptions, Innere Klassen, Lambdas, Maps, Optionals, Stream API, JUnit5/Mockito), dem erlaubten Methoden-Cheatsheet sowie der Musterklausur "RobotFactory" samt Musterlösung.

---

## 1. Grober Überblick über die Klausurthemen

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

## 2. Das Spickzettel-Delta (Lücken-Analyse)

Das erlaubte `cheatsheet.md` listet **nur Methodensignaturen** einzelner Klassen — keine Syntaxmuster, keine Annotationen, keine Schlüsselwörter. Pro Themengebiet fehlt Folgendes und muss auswendig sitzen:

| Themengebiet | Fehlt im Cheatsheet | Was du im Kopf haben musst |
| :--- | :--- | :--- |
| **1. Records** | Record-Syntax komplett; `List.copyOf()`; `java.util.Objects` komplett fehlend | `record Name(Typ feld, ...) { ... }`, kompakter Konstruktor (keine expliziten `this.x = x`-Zuweisungen), `Objects.requireNonNull()` für Validierung, `List.copyOf(feld)` für defensives Kopieren, `implements` statt `extends` |
| **2. Comparators** | Nur `compare()`, `comparing()` gelistet | `comparingInt()`, `comparingDouble()`, `reversed()`, `thenComparing()`, `thenComparingInt()`, `naturalOrder()`, `nullsFirst()`/`nullsLast()` |
| **3. Exceptions** | Exception-Hierarchie komplett fehlend | `Throwable` → `Error`/`Exception` → `RuntimeException`; Syntax für eigene Exceptions (`extends Exception`/`extends RuntimeException`, `super(message)`); `try (Resource r = ...) { }`; `catch (A \| B e) { }` (Multi-Catch) |
| **4. Innere Klassen** | Syntax komplett fehlend (cheatsheet.md kennt keine Klassendeklarationen) | `outer.new Inner()` (nicht `new Outer.Inner()`), `Outer.this.feld` bei Shadowing, `public static class` vs. `public class`, Effectively-Final-Regel für lokale/anonyme Klassen |
| **5. Lambdas** | `@FunctionalInterface`; Methodenreferenzen; einige `java.util.function`-Interfaces fehlen (`Supplier`, `UnaryOperator`, `BinaryOperator`, `BiFunction`, `BiPredicate`, primitive Spezialisierungen) | Lambda-Kurzform-Regeln (Klammern bei 1 Parameter optional, `{}`+`return` bei Mehrzeilern Pflicht), die 4 Arten von Methodenreferenzen (`Klasse::statischeMethode`, `objekt::methode`, `Klasse::instanzMethode`, `Klasse::new`) |
| **6. Maps** | Nur `containsKey/containsValue/entrySet/forEach/get/keySet/put/putIfAbsent/values` gelistet | `computeIfAbsent()`, `computeIfPresent()`, `merge()`, `replaceAll()`, `remove()`, `size()`, `isEmpty()` |
| **7. Optionals** | Nur `empty/get/ifPresent/ifPresentOrElse/isPresent/of/ofNullable/orElse` gelistet | `orElseGet()`, `orElseThrow()`, `map()`, `flatMap()`, `filter()`, `isEmpty()` |
| **8. Stream API** | `reduce()` fehlt komplett; `Stream.of()`/`IntStream.range()` fehlen | `reduce()`-Signaturen (mit/ohne Identity), `Stream.of(...)` bzw. `IntStream.range(a, b)` als alternative Datenquellen |
| **9. JUnit5 & Mockito** | Alle JUnit-Annotationen fehlen; nur `when()`, `thenReturn()`, `openMocks()` bei Mockito gelistet | `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`, `@Disabled`, `@DisplayName`, `@ParameterizedTest`, `@ValueSource`, `@CsvSource`; `verify()`, `times(n)`, `never()`, `atLeastOnce()`, `doThrow()`, `doNothing()`, `ArgumentCaptor`, `anyString()`, `anyInt()`, `eq()` |
| **Sonstiges / Allgemein** | Allgemeine Annotationen (`@Override`, Lombok `@Data`/`@EqualsAndHashCode`/`@ToString`); Klassendeklarations-Syntax generell | Vererbungssyntax (`extends`, `implements`), Zugriffsmodifizierer, Generics-Syntax (`<T extends X>`), Konstruktor-Deklaration, `new ArrayList<>()`/`new HashMap<>()` |

**Kernaussage:** Das Cheatsheet hilft bei *"Wie heißt die Methode und was gibt sie zurück?"*, aber **nicht** bei *"Wie sieht die Syntax drumherum aus?"*. Genau diese Syntax-Gerüste (Annotationen, Record-Kopf, Testklassen-Aufbau, Exception-Deklaration) musst du auswendig abrufen können.

---

## 3. Typische Klausur-Fallstricke (Pitfalls)

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

### Maps
- **`equals()`/`hashCode()` nicht überschrieben**: Selbstgeschriebene Key-Objekte werden nach dem Einfügen nicht mehr per `get(...)` gefunden, wenn beide Methoden fehlen oder inkonsistent sind.
- **Veränderliche Keys**: Wird ein Attribut eines bereits eingefügten Key-Objekts verändert, ändert sich sein `hashCode()` — das Objekt ist in der Map "verloren".
- `containsValue()` ist $O(n)$, `containsKey()` ist $O(1)$ — bei Performance-Fragen relevant.

---

## 4. Punkte-Hotspots & Priorisierung (Pareto 80/20)

Ein Blick in die Musterlösung (RobotFactory-Klausur, 52 Punkte gesamt über 3 Aufgaben) zeigt ein wichtiges Muster: **Punkte werden extrem granular vergeben — schon für korrekte Annotationen, Klammern und Methodensignaturen gibt es 0,5–1 Punkt.** Das heißt: Das saubere "Grundgerüst" bringt fast so viel wie die eigentliche Logik.

| Kategorie | Ungefährer Punkteanteil | Warum |
| :--- | :--- | :--- |
| **Stream-Pipelines korrekt schreiben** (`filter`, `map`, `distinct`, `sorted`, `collect`, `groupingBy`, `mapToDouble`/`average`) | ~35 % | Kommt in praktisch jeder Klausur in mind. einer Record-Methode vor, viele Einzelbewertungspunkte pro Zeile |
| **Mockito-Testklassen-Skelett** (`@Mock`, `@InjectMocks`, `@BeforeEach`-Setup, `when/thenReturn`, `assertEquals`) | ~30 % | Wiederkehrendes Muster, hoher Boilerplate-Anteil = viele "leichte" Punkte, wenn das Muster sitzt |
| **Klassen-/Record-Grundgerüst** (Vererbung, `implements`, Konstruktoren, `compareTo`) | ~25 % | Struktur- und Signaturpunkte, unabhängig von der eigentlichen "cleveren" Logik |
| **Reine Theoriefragen / Multiple-Choice** | ~10 % | Meist Fallstricke aus Abschnitt 3 (Exceptions, Overflow, Immutability) |

**Priorität zum Lernen:**
1. **Mockito-Test-Skelett blind hinschreiben können** — höchster ROI, da fast unverändert wiederkehrend.
2. **Stream-Pipeline-Bausteine automatisieren**, v. a. `groupingBy` mit Downstream-Collector und `mapToDouble().average().orElse(...)`.
3. **Record- und `Comparable`-Grundgerüst** sauber und ohne Zögern aufschreiben.
4. **Optional-Ketten** (`orElseThrow`, `ifPresentOrElse`) — klein, aber häufig abgefragt.
5. Exceptions/innere Klassen zuletzt — seltener Kernbestandteil einer Programmieraufgabe, aber leicht zu punkten, wenn die Regeln sitzen.

---

## 5. Muster-Templates für Freitext-/Programmieraufgaben

Zu jedem der 9 Themengebiete ein Template mit der Implementierung, die erfahrungsgemäß am meisten Punkte kostet, wenn sie nicht sitzt.

### 5.1 Records — generischer Record mit Validierung, defensivem Kopieren & lokalem Record
```java
public record Bestellung<T extends Artikel>(String kundenId, List<T> positionen)
        implements Comparable<Bestellung<T>> {

    public Bestellung {
        Objects.requireNonNull(kundenId, "kundenId darf nicht null sein");
        positionen = List.copyOf(positionen); // defensives Kopieren -> Shallow Immutability lösen
    }

    public double gesamtwert() {
        return positionen.stream().mapToDouble(Artikel::getPreis).sum();
    }

    @Override
    public int compareTo(Bestellung<T> other) {
        return Double.compare(this.gesamtwert(), other.gesamtwert());
    }

    // Lokaler Record innerhalb einer Methode (temporäre Datenstruktur)
    public String zusammenfassung() {
        record Zeile(String artikel, double preis) {}
        return positionen.stream()
                .map(p -> new Zeile(p.getName(), p.getPreis()))
                .map(z -> z.artikel() + ": " + z.preis())
                .collect(Collectors.joining(", "));
    }
}
```

### 5.2 Comparators — klassische Comparator-Klasse + verkettete Kurzform
```java
// Klassische Implementierung als eigene Klasse (wenn Lambdas verboten sind)
public class PreisAufsteigendComparator implements Comparator<Artikel> {
    @Override
    public int compare(Artikel a1, Artikel a2) {
        return Double.compare(a1.getPreis(), a2.getPreis());
    }
}

// Nutzung + moderne verkettete Variante mit Null-Sicherheit
List<Artikel> artikel = new ArrayList<>(List.of(/* ... */));
artikel.sort(new PreisAufsteigendComparator());

Comparator<Artikel> komplex = Comparator
        .comparing(Artikel::getKategorie, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparingDouble(Artikel::getPreis)
        .reversed();
artikel.sort(komplex);
```

### 5.3 Exceptions — eigene Checked/Unchecked Exception + Chaining + Try-With-Resources
```java
public class BestandNichtVerfuegbarException extends Exception {
    public BestandNichtVerfuegbarException(String message) {
        super(message);
    }
}

public class LagerServiceException extends RuntimeException {
    public LagerServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class LagerService {
    public void reserviere(String artikelId, int menge) throws BestandNichtVerfuegbarException {
        try (LagerVerbindung verbindung = new LagerVerbindung()) {
            if (!verbindung.hatBestand(artikelId, menge)) {
                throw new BestandNichtVerfuegbarException("Nicht genug Bestand für " + artikelId);
            }
        } catch (SQLException e) {
            // Exception-Chaining: ursprüngliche Ursache mitgeben
            throw new LagerServiceException("Lagerzugriff fehlgeschlagen", e);
        }
    }
}
```

### 5.4 Innere Klassen — statisch geschachtelt, Member Inner & lokale Klasse im Vergleich
```java
public class Bericht {
    private static String format = "PDF";
    private String titel = "Quartalsbericht";

    // A) Statische geschachtelte Klasse: kein äußeres Objekt nötig
    public static class Exporter {
        void exportiere() {
            System.out.println("Export als " + format);
        }
    }

    // B) Member Inner Class: an konkrete äußere Instanz gebunden
    public class Kopfzeile {
        private String titel = "Kopfzeile"; // Shadowing
        void drucke() {
            System.out.println(titel);            // "Kopfzeile"
            System.out.println(Bericht.this.titel); // "Quartalsbericht"
        }
    }

    // C) Lokale Klasse: nur innerhalb dieser Methode sichtbar
    public void generiere(String praefix) {
        class Formatter {
            String format(String text) {
                return praefix + ": " + text; // 'praefix' muss effectively final sein
            }
        }
        System.out.println(new Formatter().format(titel));
    }
}

// Instanziierung:
Bericht.Exporter exporter = new Bericht.Exporter();
Bericht bericht = new Bericht();
Bericht.Kopfzeile kopf = bericht.new Kopfzeile();
```

### 5.5 Lambdas — eigenes funktionales Interface + alle 4 Methodenreferenz-Arten
```java
@FunctionalInterface
public interface Rabattregel {
    double anwenden(double preis);
}

public class Rabattrechner {
    public double berechne(double preis, Rabattregel regel) {
        return regel.anwenden(preis);
    }

    public static double zehnProzent(double preis) {
        return preis * 0.9;
    }
}

// Nutzung mit allen 4 Referenz-Arten:
Rabattrechner rechner = new Rabattrechner();
double r1 = rechner.berechne(100.0, Rabattrechner::zehnProzent);      // 1. statische Methode
double r2 = rechner.berechne(100.0, preis -> preis * 0.8);            // Lambda direkt
Function<String, Integer> parser = Integer::parseInt;                  // 1. statische Methode
Consumer<String> printer = System.out::println;                       // 2. Instanzmethode konkretes Objekt
Function<String, String> upper = String::toUpperCase;                 // 3. Instanzmethode unbestimmtes Objekt
Supplier<ArrayList<String>> neueListe = ArrayList::new;                // 4. Konstruktorreferenz
```

### 5.6 Maps — Key-Klasse mit equals/hashCode + computeIfAbsent + merge
```java
public final class ArtikelKey {
    private final String sku;

    public ArtikelKey(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtikelKey other)) return false;
        return Objects.equals(sku, other.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}

// Verwendung:
Map<ArtikelKey, List<Bestellposition>> positionenProArtikel = new HashMap<>();
positionenProArtikel.computeIfAbsent(new ArtikelKey("SKU-1"), k -> new ArrayList<>())
        .add(neuePosition);

Map<String, Integer> wortHaeufigkeit = new HashMap<>();
for (String wort : woerter) {
    wortHaeufigkeit.merge(wort, 1, Integer::sum);
}
```

### 5.7 Optionals — verkettete map/flatMap/filter mit orElseThrow
```java
public class KundenService {
    public Optional<Kunde> findeKunden(String id) {
        return repository.findById(id); // liefert Optional<Kunde>
    }

    public String ermittleRabattcode(String kundenId) {
        return findeKunden(kundenId)
                .filter(Kunde::istPremium)
                .flatMap(Kunde::getOptionalRabattcode)
                .map(String::toUpperCase)
                .orElseThrow(() -> new IllegalStateException("Kein Rabattcode für " + kundenId));
    }
}
```

### 5.8 Stream API — groupingBy mit Downstream-Collector + reduce
```java
// Gruppieren + Downstream-Aggregation
Map<String, Double> summeProKategorie = artikel.stream()
        .collect(Collectors.groupingBy(
                Artikel::getKategorie,
                Collectors.summingDouble(Artikel::getPreis)));

// Partitionierung
Map<Boolean, List<Artikel>> guenstigVsTeuer = artikel.stream()
        .collect(Collectors.partitioningBy(a -> a.getPreis() < 15.0));

// reduce() für eigene Aggregation (nicht im Cheatsheet gelistet!)
double gesamtwert = artikel.stream()
        .map(Artikel::getPreis)
        .reduce(0.0, Double::sum);
```

### 5.9 JUnit5 & Mockito — Testklasse mit Captor, doThrow & parametrisiertem Test
```java
@ExtendWith(MockitoExtension.class)
public class LagerServiceTest {

    @Mock
    private LagerRepository repository;

    @InjectMocks
    private LagerService service;

    @Captor
    private ArgumentCaptor<String> idCaptor;

    @Test
    void testReserviereWirftBeiFehlendemBestand() throws Exception {
        when(repository.hatBestand(anyString(), anyInt())).thenReturn(false);

        assertThrows(BestandNichtVerfuegbarException.class,
                () -> service.reserviere("SKU-1", 5));

        verify(repository).hatBestand(idCaptor.capture(), eq(5));
        assertEquals("SKU-1", idCaptor.getValue());
    }

    @Test
    void testLoescheWirftBeiFehler() {
        doThrow(new RuntimeException("DB Fehler")).when(repository).loesche(anyString());
        assertThrows(RuntimeException.class, () -> service.loesche("SKU-1"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void testReserviereMitUngueltigerMenge(int menge) {
        assertThrows(IllegalArgumentException.class,
                () -> service.reserviere("SKU-1", menge));
    }
}
```

---

*Erstellt auf Basis der hochgeladenen Cheatsheets, des Methoden-Cheatsheets und der Musterklausur "RobotFactory" (DHBW, Programmierung II).*

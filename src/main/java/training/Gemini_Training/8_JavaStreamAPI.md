# Cheatsheet: Java Stream API

## 1. Was ist ein Stream?
Ein Stream (`java.util.stream.Stream`) ist eine Sequenz von Elementen, die funktionale Transformationen über eine **Pipeline** erlaubt. Streams speichern selbst keine Daten, sondern verarbeiten Daten aus Datenquellen (wie `List`, `Set` oder Arrays).

Eine Stream-Pipeline besteht immer aus drei Teilen:
1. **Datenquelle:** `collection.stream()`
2. **Intermediäre Operationen (Lazy):** Transformieren/filtern den Stream (werden erst bei der terminalen Operation ausgeführt).
3. **Terminale Operation (Eager):** Schließt den Stream ab und stößt die Berechnung an.

---

## 2. Die wichtigsten Stream-Operationen im Überblick

### A. Intermediäre Operationen (Liefern wieder einen `Stream<T>`)

| Methode | Beschreibung | Beispiel |
| :--- | :--- | :--- |
| **`filter(Predicate)`** | Filtert Elemente heraus, die dem Predicate entsprechen. | `.filter(b -> b.getPrice() < 20.0)` |
| **`map(Function)`** | Transformiert jedes Element in einen anderen Typ/Wert. | `.map(Book::getTitle)` |
| **`flatMap(Function)`** | Wandelt verschachtelte Strukturen (z.B. Listen in Listen) in einen flachen Stream um. | `.flatMap(b -> b.getGenres().stream())` |
| **`distinct()`** | Entfernt Duplikate (basiert auf `equals()`). | `.distinct()` |
| **`sorted()` / `sorted(Comparator)`** | Sortiert die Elemente im Stream (natürlich oder per Comparator). | `.sorted((b1, b2) -> Integer.compare(b2.getPages(), b1.getPages()))` |
| **`limit(long n)`** | Begrenzt den Stream auf maximal $n$ Elemente. | `.limit(3)` |
| **`skip(long n)`** | Überspringt die ersten $n$ Elemente. | `.skip(5)` |

---

### B. Terminale Operationen (Schließen den Stream ab)

| Methode | Beschreibung | Rückgabetyp |
| :--- | :--- | :--- |
| **`forEach(Consumer)`** | Führt eine Aktion für jedes Element aus. | `void` |
| **`toList()` / `toSet()`** | Sammelt alle Elemente in einer Liste oder einem Set. | `List<T>` / `Set<T>` |
| **`count()`** | Zählt die verbliebenen Elemente im Stream. | `long` |
| **`max(Comparator)` / `min(Comparator)`** | Findet das größte bzw. kleinste Element. | `Optional<T>` |
| **`findFirst()` / `findAny()`** | Liefert das erste bzw. ein beliebiges Element. | `Optional<T>` |
| **`anyMatch(Predicate)`** | Prüft, ob **mindestens ein** Element die Bedingung erfüllt. | `boolean` |
| **`allMatch(Predicate)`** | Prüft, ob **alle** Elemente die Bedingung erfüllen. | `boolean` |
| **`noneMatch(Predicate)`** | Prüft, ob **kein** Element die Bedingung erfüllt. | `boolean` |

---

## 3. Primitiv-Streams (Numerische Berechnungen)

Normale Streams verarbeiten Objekte. Für Zahlen gibt es optimierte Streams, um Autoboxing zu vermeiden (`IntStream`, `DoubleStream`, `LongStream`).

```java
// Umwandlung zu DoubleStream
double avg = books.stream()
    .mapToDouble(Book::getPrice)
    .average()
    .orElse(0.0);

// Summe oder Statistik
double sum = books.stream().mapToDouble(Book::getPrice).sum();
```

---

## 4. Leistungsstarke Sammeloperatoren (`Collectors`)

`Collectors` werden mit `.collect(...)` verwendet, um Stream-Ergebnisse komplex zu verarbeiten:

### A. Zeichenketten verknüpfen
```java
String titles = books.stream()
    .map(Book::getTitle)
    .collect(Collectors.joining(", "));
```

### B. Gruppierung (`groupingBy`)
Gruppiert Elemente anhand eines Kriteriums in eine `Map<K, List<V>>`.
```java
Map<String, List<Book>> booksByAuthor = books.stream()
    .collect(Collectors.groupingBy(Book::getAuthor));
```

### C. Partitionierung (`partitioningBy`)
Teilt den Stream in genau zwei Gruppen auf (`true` und `false`).
```java
Map<Boolean, List<Book>> cheapVsExpensive = books.stream()
    .collect(Collectors.partitioningBy(b -> b.getPrice() < 15.0));
```

### D. Gruppieren mit Transformation / Aggregation
```java
// Gruppiert Autoren und zählt deren Bücher
Map<String, Long> bookCountPerAuthor = books.stream()
    .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting()));
```

---

## 5. Klausur-Checkliste & Fallstricke

1. **Ein Stream ist nur einmal verwendbar:** Sobald eine terminale Operation (z. B. `toList()`, `forEach()`) aufgerufen wurde, ist der Stream "konsumiert" und kann nicht erneut verwendet werden.
2. **Lazy Evaluation:** Intermediäre Operationen wie `filter()` oder `map()` werden erst ausgeführt, wenn eine terminale Operation aufgerufen wird.
3. **Null-Safety bei `average()`, `min()`, `max()`:** Diese Methoden liefern ein `Optional` bzw. `OptionalDouble` zurück. Verwende stets `.orElse(...)` zur sicheren Behandlung leerer Streams.
4. **`Collectors.toList()` vs. `.toList()`:** Seit Java 16 erzeugt `.toList()` direkt eine unveränderliche Liste, was kompakter und bevorzugt ist.

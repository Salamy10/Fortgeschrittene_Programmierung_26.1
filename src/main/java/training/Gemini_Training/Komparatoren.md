# Cheatsheet: Komparatoren in Java (`Comparable` vs. `Comparator`)

## 1. Übersicht & Gegenüberstellung

Java bietet zwei Schnittstellen zum Vergleichen und Sortieren von Objekten. Beide dienen unterschiedlichen Zwecken:

| Merkmal | `Comparable<T>` | `Comparator<T>` |
| :--- | :--- | :--- |
| **Bedeutung** | **Natürliche Ordnung** (*Natural Ordering*) | **Externe / Flexible Ordnung** |
| **Paket** | `java.lang.Comparable` | `java.util.Comparator` |
| **Wo definiert?** | **In** der Klasse des zu vergleichenden Objekts | **Außerhalb** als eigenes Objekt, Lambda oder Referenz |
| **Hauptmethode** | `int compareTo(T other)` | `int compare(T o1, T o2)` |
| **Anzahl Schemata** | **Genau 1** Standard-Sortierung pro Klasse | **Beliebig viele** unterschiedliche Kriterien |
| **Typischer Aufruf** | `list.sort(Comparator.naturalOrder())`<br>`Collections.sort(list)` | `list.sort(comparator)`<br>`Collections.sort(list, comparator)` |

---

## 2. Der Rückgabe-Vertrag (*Compare Contract*)

Beide Schnittstellen arbeiten nach demselben dreiwertigen Logik-Schema:

$$	ext{Rückgabewert} =  egin{cases} < 0 & 	ext{wenn } o_1 	ext{ kleiner ist als } o_2 	ext{ (bzw. } 	ext{this} < 	ext{other}) \ 0 & 	ext{wenn } o_1 	ext{ und } o_2 	ext{ sortiertechnisch gleich sind} \ > 0 & 	ext{wenn } o_1 	ext{ größer ist als } o_2 	ext{ (bzw. } 	ext{this} > 	ext{other}) \end{cases}$$

> **⚠️ Wichtige Regel für numerische Vergleiche:**
> NIEMALS Werte mit Subtraktion vergleichen (`this.age - other.age`), da dies bei negativen Zahlen oder großen Werten zu einem **Integer Overflow** führen kann!
> **Richtig:** `Integer.compare(a, b)` oder `Double.compare(a, b)`.

---

## 3. `Comparable<T>` (Natürliche Ordnung)

Wird direkt im Klassenkopf mit `implements Comparable<T>` deklariert.

### Beispiel in einer Standard-Klasse / Record:
```java
public record Student(String name, int matrikelnummer) implements Comparable<Student> {

    @Override
    public int compareTo(Student other) {
        // Sortierung aufsteigend nach Matrikelnummer
        return Integer.compare(this.matrikelnummer, other.matrikelnummer);
    }
}
```

---

## 4. `Comparator<T>` (Moderne Java-API)

Seit Java 8 wird `Comparator` selten manuell ausprogrammiert, sondern über **statische Factory-Methoden** und **Methodenreferenzen** gebaut.

### A. Grundlegende Komparatoren
```java
// Nach Name (String / Objekte mit Comparable)
Comparator<Student> byName = Comparator.comparing(Student::name);

// Für primitive Datentypen (vermeidet Autoboxing / Performance-Vorteil!)
Comparator<Student> byId = Comparator.comparingInt(Student::matrikelnummer);
Comparator<Book> byPrice = Comparator.comparingDouble(Book::price);
```

### B. Sortierreihenfolge umkehren (`reversed`)
```java
// Sortiert absteigend nach Preis (teuerste zuerst)
Comparator<Book> priceDesc = Comparator.comparingDouble(Book::price).reversed();
```

### C. Verkettung von Komparatoren (`thenComparing`)
Falls das erste Kriterium Gleichstand liefert, greift das zweite Kriterium (*Tie-Breaker*):

```java
// Erst nach Nachname, bei gleichem Nachnamen nach Vorname
Comparator<Person> complexOrder = Comparator
    .comparing(Person::lastName)
    .thenComparing(Person::firstName)
    .thenComparingInt(Person::age);
```

### D. Null-Sicherheit (`nullsFirst` / `nullsLast`)
Verhindert `NullPointerException` beim Sortieren von Listen mit `null`-Werten:

```java
// Setzt alle null-Elemente an den Anfang der Liste
Comparator<String> safeStringComp = Comparator.nullsFirst(String::compareTo);
```

---

## 5. Anwendung in der Praxis

```java
List<Student> students = new ArrayList<>(List.of(
    new Student("Anna", 1002),
    new Student("Ben", 1001)
));

// 1. Nutzt Comparable (natürliche Ordnung):
students.sort(Comparator.naturalOrder()); 

// 2. Nutzt spezifischen Comparator:
students.sort(Comparator.comparing(Student::name));
```

---

## 6. Klausur-Checkliste & Typische Fallstricke

1. **Unveränderliche Listen:** `List.of(...)` erzeugt eine unmodifizierbare Liste! Der Aufruf von `list.sort(...)` wirft eine `UnsupportedOperationException`. Immer in `new ArrayList<>(List.of(...))` verpacken!
2. **Kompaktheit vs. Autoboxing:** Nutze für `int`, `double` und `long` immer `comparingInt()`, `comparingDouble()` etc., anstatt `comparing()`.
3. **Konsistenz mit `equals`:** Wenn `compareTo` den Wert `0` liefert, sollte im Idealfall auch `equals(...) == true` sein (wichtig für `TreeSet` und `TreeMap`).
4. **Verkehrte Welt bei `reversed()`:** Achte darauf, worauf sich `.reversed()` bezieht. `Comparator.comparing(...).thenComparing(...).reversed()` dreht den **gesamten** verketteten Komparator um, nicht nur das letzte Element!
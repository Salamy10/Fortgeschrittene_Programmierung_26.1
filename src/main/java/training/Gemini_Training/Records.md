# Cheatsheet: Java Records (Datenklassen)

## 1. Was ist ein Record?
Ein **Record** (eingeführt ab Java 14/16) ist ein spezialisierter Klassentyp, der als **unveränderlicher Datenbehälter** (*Immutable Data Carrier*) dient. Er eliminiert manuellen Boilerplate-Code für DTOs, Schlüssel-Objekte und reine Datenstrukturen.

```java
public record Person(String name, int age) {}
```

### Vom Compiler automatisch generierte Elemente:
- **Felder:** `private final String name;` und `private final int age;`
- **Kanonischer Konstruktor:** `public Person(String name, int age)` mit Zuweisung aller Komponenten.
- **Getter / Accessoren:** `name()` und `age()` (Achtung: **Kein** `get`-Präfix!).
- **Object-Methoden:**
  - `equals()`: Prüft Wertgleichheit aller Komponenten.
  - `hashCode()`: Basiert auf den Werten aller Komponenten.
  - `toString()`: Liefert `Person[name=..., age=...]`.

---

## 2. Regeln & Einschränkungen (Klausurrelevant!)
- **Vererbung:**
  - Records sind implizit **`final`** (können nicht erweitert werden).
  - Records erben bereits von `java.lang.Record` $
ightarrow$ **kein `extends` möglich!**
  - **Interfaces:** Können beliebig viele Interfaces implementieren (`implements`).
- **Instanzvariablen:** Keine zusätzlichen Instanzfelder erlaubt! Alle Felder müssen im Record-Kopf (*Header*) definiert werden.
- **Statische Member:** `static`-Felder, `static`-Methoden und `static`-Initialisierer sind **erlaubt**.

---

## 3. Konstruktoren in Records

### A. Kompakter Konstruktor (Compact Constructor)
Ideal für **Validierungen** und **Invarianten-Prüfungen**. Parametertypen und `-namen` werden weggelassen. Die Zuweisung zu den Feldern erfolgt automatisch am Ende der Methode.

```java
public record User(String username, String email) {
    public User {
        // Validierung vor der automatischen Zuweisung
        Objects.requireNonNull(username, "Username darf nicht null sein");
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Ungültige E-Mail");
        }
        // Keine expliziten "this.username = username;" Zuweisungen nötig!
    }
}
```

### B. Überladene / Zusätzliche Konstruktoren
Jeder zusätzliche Konstruktor **muss** als erste Zeile einen anderen Konstruktor desselben Records mit `this(...)` aufrufen.

```java
public record Point(int x, int y) {
    // Überladener Konstruktor (z.B. Ursprung (0,0))
    public Point() {
        this(0, 0); // Pflicht: Delegierung an den Hauptkonstruktor!
    }
}
```

---

## 4. Flache Unveränderlichkeit (Shallow Immutability) & Defensive Copying

> **⚠️ Wichtige Fallstricke:**
> Record-Felder selbst sind `final` (Referenz unbegrenzt stabil). Ist ein Feld jedoch ein **änderbares Objekt** (z. B. `List`, `Map`, `Date`), kann der *Inhalt* des Objekts weiterhin verändert werden!

### Lösung: Defensives Kopieren (Defensive Copying)

```java
import java.util.List;

public record ShoppingCart(String customerId, List<String> items) {
    public ShoppingCart {
        // 1. Defensives Kopieren im Konstruktor (macht die Liste unveränderlich)
        items = List.copyOf(items); 
    }
}
```

---

## 5. Zusatzmethoden & Interfaces

Records können eigene Methoden enthalten und Interfaces implementieren:

```java
public record Employee(String name, double salary) implements Comparable<Employee> {
    
    // Eigene Hilfsmethode
    public double annualSalary() {
        return salary * 12;
    }

    // Interface-Implementierung
    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.salary, other.salary);
    }
}
```

---

## 6. Lokale Records (Local Records)
Records können direkt innerhalb von Methoden deklariert werden, um temporäre Datenstrukturen für komplexe Berechnungen oder Stream-Pipelines zu kapseln.

```java
public void processData() {
    record TempData(String key, int count) {}
    TempData data = new TempData("A", 10);
}
```

---

## 7. Zusammenfassung Cheat-Sheet

| Eigenschaft | Standard-Klasse | Java Record |
| :--- | :--- | :--- |
| **Zweck** | Allgemeine Logik & Zustand | Reine Datenkapselung |
| **Getter-Syntax** | `getName()` | `name()` |
| **Setter vorhanden?** | Ja (optional) | **Nein** (Immutable) |
| **Erbt von** | `Object` (oder Klassen) | `java.lang.Record` |
| **Klasse erweiterbar?** | Ja | **Nein** (`final`) |
| **Zusätzliche Instanzfelder** | Ja | **Nein** |
| **Interfaces implementieren** | Ja | **Ja** |
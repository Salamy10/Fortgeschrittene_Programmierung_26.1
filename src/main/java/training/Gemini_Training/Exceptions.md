# Cheatsheet: Ausnahmen in Java (Exceptions)

## 1. Die Ausnahmen-Hierarchie (*Exception Hierarchy*)

Alle Fehler- und Ausnahmeklassen stammen von `java.lang.Throwable` ab.

```text
                       Throwable
                       /       \
                  Error         Exception (Checked)
                                 /
                         RuntimeException (Unchecked)
```

| Klasse | Typ | Verhalten / Zwang | Beispiele |
| :--- | :--- | :--- | :--- |
| **`Error`** | Unchecked | Schwerwiegende Systemfehler. Werden **nicht** abgefangen! | `OutOfMemoryError`, `StackOverflowError` |
| **`Exception`** | **Checked** (Prüfpflichtig) | **Compiler-Zwang:** Muss mit `try-catch` behandelt oder per `throws` deklariert werden. | `IOException`, `SQLException`, `FileNotFoundException` |
| **`RuntimeException`** | **Unchecked** (Nicht prüfpflichtig) | Programmierfehler. **Kein Compiler-Zwang** zur Behandlung. | `NullPointerException`, `IndexOutOfBoundsException`, `IllegalArgumentException` |

---

## 2. Die Schlüsselwörter im Überblick

* **`try`**: Baut den Block auf, in dem fehleranfälliger Code ausgeführt wird.
* **`catch`**: Fängt eine spezifische Exception ab und verarbeitet sie.
* **`finally`**: Wird **immer** ausgeführt (egal ob eine Exception auftrat, gefangen wurde oder ein `return` vorliegt). Ideal für Aufräumarbeiten.
* **`throw`**: Löst explizit eine Exception aus (z. B. `throw new IllegalArgumentException("...");`).
* **`throws`**: Deklariert im Methodenkopf, dass eine Methode eine **Checked Exception** nach oben weiterreicht (z. B. `public void read() throws IOException`).

---

## 3. Moderne Exception-Handling Patterns

### A. Multi-Catch (Abfangen mehrerer Ausnahmen)
Mehrere nicht-verwandte Ausnahmen können in einem einzigen `catch`-Block zusammengefasst werden. Die Variable `e` ist dabei implizit `final`.

```java
try {
    // Code, der IOException oder SQLException werfen kann
} catch (IOException | SQLException e) {
    System.err.println("Datenbank- oder E/A-Fehler: " + e.getMessage());
}
```

### B. Try-With-Resources (Automatisches Ressourcen-Management)
Ersetzt manuelle `finally`-Blöcke zum Schließen von Dateien, Streams oder Datenbankverbindungen. Voraussetzung: Die Ressource implementiert `AutoCloseable` oder `Closeable`.

```java
// Die Ressource wird am Ende des try-Blocks automatisch geschlossen!
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line = reader.readLine();
} catch (IOException e) {
    System.err.println("Datei konnte nicht gelesen werden: " + e.getMessage());
}
```

---

## 4. Benutzerdefinierte Exceptions (Custom Exceptions)

```java
// 1. Checked Exception (erfordert Abfangen/Deklarieren)
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// 2. Unchecked Exception (optionales Abfangen)
public class InvalidAccountStateException extends RuntimeException {
    public InvalidAccountStateException(String message) {
        super(message);
    }
}
```

---

## 5. Exception Chaining (Ausnahmeketten)
Koppelt die ursprüngliche Ursache (*Cause*) an eine neu geworfene, domänenspezifische Exception an.

```java
try {
    database.connect();
} catch (SQLException e) {
    // Ursprüngliche Exception e wird als "Cause" mitgegeben
    throw new ServiceException("Dienst nicht erreichbar", e);
}
```

---

## 6. Vererbungsregeln beim Überschreiben (`@Override`)

> **⚠️ Klausur-Klassiker:**
> Beim Überschreiben einer Methode in einer Unterklasse darf die überschreibende Methode:
> 1. **Keine neuen oder breiteren Checked Exceptions** deklarieren als die Methode der Oberklasse!
> 2. **Weniger oder spezifischere (Subklassen-)Checked Exceptions** deklarieren.
> 3. Beliebige **`RuntimeException`s** deklarieren (da unchecked).

```java
class Parent {
    void process() throws IOException {}
}

class Child extends Parent {
    // ERLAUBT: Spezifischere Checked Exception
    @Override
    void process() throws FileNotFoundException {} 
    
    // VERBOTEN! (Exception ist breiter als IOException -> Compilerfehler)
    // void process() throws Exception {} 
}
```

---

## 7. Klausur-Checkliste & Fallstricke

1. **Unerreichbarer Catch-Block:** Ein `catch` für eine **Checked Exception** führt zu einem Compiler-Fehler, wenn im `try`-Block kein Code steht, der diese spezifische Exception (oder eine Unterklasse) werfen kann.
2. **Reihenfolge der Catch-Blöcke:** Spezifischere Exceptions müssen **vor** allgemeinen Oberklassen-Exceptions stehen (`FileNotFoundException` vor `IOException`), da sonst der Unterklasse-Block nie erreicht wird.
3. **Return im `finally`-Block:** Ein `return` im `finally`-Block überschreibt Rückgabewerte und verschluckt vorher geworfene Exceptions!
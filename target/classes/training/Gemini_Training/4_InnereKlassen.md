# Cheatsheet: Innere und Geschachtelte Klassen in Java (Nested & Inner Classes)

## 1. Übersicht & Klassifizierung

In Java können Klassen innerhalb anderer Klassen oder Methoden deklariert werden. Man unterscheidet grundsätzlich vier Typen:

| Typ | Deklarationsort | `static`? | Zugriff auf äußere Instanz? | Erzeugung / Aufruf |
| :--- | :--- | :--- | :--- | :--- |
| **Statische geschachtelte Klasse** (*Static Nested Class*) | Äußere Klasse (Klassenebene) | **Ja** | **Nein** (nur auf statische Member) | `new Outer.Nested()` |
| **Nicht-statische Elementklasse** (*Member Inner Class*) | Äußere Klasse (Klassenebene) | **Nein** | **Ja** (direkt & via `Outer.this`) | `outerInst.new Inner()` |
| **Lokale Klasse** (*Local Class*) | Innerhalb einer Methode | **Nein** | **Ja** (sofern äußere Variablen *effectively final* sind) | Innerhalb der Methode via `new Local()` |
| **Anonyme Klasse** (*Anonymous Class*) | Innerhalb einer Methode / eines Ausdrückes | **Nein** | **Ja** (sofern äußere Variablen *effectively final* sind) | `new InterfaceOrClass() { ... }` |

---

## 2. Die 4 Typen im Detail

### A. Statische geschachtelte Klasse (`static nested class`)
Arbeitet wie eine normale Top-Level-Klasse, ist aber aus Gründen der Kapselung und Zugehörigkeit in eine andere Klasse eingebettet.

```java
public class Outer {
    private static String staticField = "Static";

    public static class Nested {
        public void print() {
            System.out.println(staticField); // Zugriff NUR auf statische Attribute erlaubt
        }
    }
}

// Instanziierung ohne äußeres Objekt:
Outer.Nested nested = new Outer.Nested();
```

---

### B. Nicht-statische Elementklasse (`member inner class`)
Ist strikt an eine **konkrete Instanz** der äußeren Klasse gebunden.

```java
public class Outer {
    private String name = "OuterInstance";

    public class Inner {
        private String name = "InnerInstance"; // Namensverdeckung (Shadowing)

        public void printDetails() {
            System.out.println(name);             // Greift auf Inner.name zu ("InnerInstance")
            System.out.println(Outer.this.name);  // Expliziter Zugriff auf Outer.name ("OuterInstance")
        }
    }
}

// Instanziierung ERFORDERT ein äußeres Objekt:
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

---

### C. Lokale Klasse (`local class`)
Wird direkt im Rumpf einer Methode deklariert. Sie ist nur **innerhalb dieser einen Methode** sichtbar.

```java
public class Processor {
    public void processData(String prefix) {
        // Lokale Klasse
        class Formatter {
            public String format(String text) {
                return prefix + ": " + text; // 'prefix' muss effectively final sein!
            }
        }

        Formatter formatter = new Formatter();
        System.out.println(formatter.format("Daten"));
    }
}
```

---

### D. Anonyme Klasse (`anonymous class`)
Eine lokale Klasse ohne Namen. Sie deklariert und instanziiert eine Klasse gleichzeitig, indem sie ein Interface implementiert oder eine Klasse erweitert.

```java
List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));

// Anonyme Klasse zur Implementierung von Comparator<String>
names.sort(new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
});
```

---

## 3. Der Weg zur funktionalen Programmierung (Vorlesungsbezug)

Anonyme Klassen bilden in Java das historische Fundament für die Weiterentwicklung zu **Lambda-Ausdrücken** und **Methodenreferenzen**. 

Am Beispiel von Sortier- und Durchlauf-Logiken (z. B. mit `Comparator` oder `Consumer`):

1. **Lokale Klasse:** Explizite Klassendeklaration innerhalb der Methode.
2. **Anonyme Klasse:** Entfernt den Klassennamen, behält aber Methoden-Boilerplate.
3. **Lambda-Ausdruck:** Reduziert den Code auf Parameter und Rumpf.
4. **Methodenreferenzen:** Die kompakteste Form für direkte Methodendurchrechnungen.

---

## 4. Klausur-Checkliste & Fallstricke

1. **Instanziierungs-Syntax (Klausur-Klassiker!):**
   * Static Nested: `Outer.Nested n = new Outer.Nested();`
   * Member Inner: `Outer.Inner i = outerRef.new Inner();` (Achte auf `outerRef.new`)
2. **`Outer.this`-Syntax:**
   * Um bei Namensgleichheit (*Shadowing*) aus der inneren Klasse auf ein Attribut der äußeren Klasse zuzugreifen, muss `OuterClass.this.attributeName` genutzt werden.
3. **Effectively Final Rule:**
   * Lokale und anonyme Klassen können nur auf lokale Variablen der umgebenden Methode zugreifen, wenn diese nicht mehr verändert werden (`effectively final`).
4. **Statische Member in Inneren Klassen:**
   * Erst seit Java 16 dürfen nicht-statische innere Klassen `static` Member (Felder/Methoden) enthalten.

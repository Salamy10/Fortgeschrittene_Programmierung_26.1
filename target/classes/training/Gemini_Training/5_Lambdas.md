# Cheatsheet: Lambda-Ausdrücke & Methodenreferenzen

## 1. Was ist ein Lambda-Ausdruck?
Ein **Lambda-Ausdruck** ist eine **anonyme Funktion** (eine Methode ohne Namen), die als Argument an Methoden übergeben oder in Variablen gespeichert werden kann. Lambdas verkürzen die Syntax von anonymen inneren Klassen drastisch.

```java
// Anonyme innere Klasse (alt)
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello World");
    }
};

// Lambda-Ausdruck (neu)
Runnable r2 = () -> System.out.println("Hello World");
```

---

## 2. Syntax & Kurzformen (Klausurrelevant!)

Die Grundstruktur lautet: `(Parameter) -> { Anweisungsblock }`

### Regeln zur Code-Verkürzung:
1. **Typ-Inferenz (Type Inference):** Parameter-Typen können weggelassen werden, wenn der Compiler sie aus dem Kontext ableiten kann.
2. **Klammern bei Parametern:** Bei **exakt einem** Parameter ohne Typangabe können die runden Klammern `()` weggelassen werden. Bei 0 oder 2+ Parametern sind `()` Pflicht.
3. **Geschweifte Klammern & `return`:**
   * Besteht der Rumpf aus **nur einer Anweisung**, können `{}` und das Semikolon am Ende entfallen.
   * Wenn diese einzelne Anweisung einen Wert zurückgibt, **muss** das Schlüsselwort `return` weggelassen werden.
   * Werden `{}` genutzt, **muss** ein `return` mit Semikolon geschrieben werden (falls die Methode nicht `void` ist).

### Syntax-Beispiele im Vergleich:

| Vollständige Syntax | Erlaubte Kurzform | Ungültige Syntax (Compilerfehler) |
| :--- | :--- | :--- |
| `(String s) -> { return s.length(); }` | `s -> s.length()` | `s -> return s.length();` *(return ohne `{}`)* |
| `(int a, int b) -> { return a + b; }` | `(a, b) -> a + b` | `a, b -> a + b` *(Fehlende `()` bei 2 Parametern)* |
| `() -> { System.out.println("X"); }` | `() -> System.out.println("X")` | `-> System.out.println("X")` *(Fehlende `()` bei 0 Parametern)* |

---

## 3. Funktionale Schnittstellen (`@FunctionalInterface`)

Ein Lambda-Ausdruck kann **nur** dort eingesetzt werden, wo ein **Funktionales Interface** erwartet wird.

* **Definition:** Ein Interface mit **exakt einer abstrakten Methode** (*Single Abstract Method* / SAM).
* **`default`- und `static`-Methoden:** Können beliebig viele enthalten sein, da sie nicht abstrakt sind.
* **Annotation `@FunctionalInterface`:** Optional, erzwingt aber beim Kompilieren, dass die SAM-Bedingung eingehalten wird.

```java
@FunctionalInterface
public interface MathOperation {
    int operate(int a, int b); // Exakt eine abstrakte Methode
}
```

---

## 4. Vordefinierte Funktionale Interfaces (`java.util.function`)

Java stellt die häufigsten funktionalen Schnittstellen bereits im Paket `java.util.function` bereit:

| Interface | Abstrakte Methode | Beschreibung | Typischer Anwendungsfall |
| :--- | :--- | :--- | :--- |
| **`Predicate<T>`** | `boolean test(T t)` | Prüft eine Bedingung für `t` | `list.removeIf(p -> p.getAge() < 18)` |
| **`Consumer<T>`** | `void accept(T t)` | Verarbeitet `t` (kein Rückgabewert) | `list.forEach(x -> System.out.println(x))` |
| **`Function<T, R>`** | `R apply(T t)` | Wandelt `t` vom Typ `T` in Typ `R` um | `list.stream().map(String::length)` |
| **`Supplier<T>`** | `T get()` | Liefert ein Objekt vom Typ `T` (kein Parameter) | `Supplier<Book> b = () -> new Book(...)` |
| **`UnaryOperator<T>`** | `T apply(T t)` | Spezialfall von `Function`: Eingabe & Ausgabe gleicher Typ | `list.replaceAll(s -> s.toUpperCase())` |
| **`BinaryOperator<T>`** | `T apply(T t1, T t2)` | Verknüpft zwei Objekte desselben Typs | `(a, b) -> Math.max(a, b)` |

### Erweiterungen für 2 Parameter (Bi-Varianten):
* **`BiPredicate<T, U>`** → `boolean test(T t, U u)`
* **`BiConsumer<T, U>`** → `void accept(T t, U u)`
* **`BiFunction<T, U, R>`** → `R apply(T t, U u)`

### Primitive Spezialisierungen (Vermeidung von Autoboxing):
* `IntPredicate`, `DoubleConsumer`, `IntToDoubleFunction` etc.

---

## 5. Methodenreferenzen (`::`)

Methodenreferenzen sind eine noch weiter verkürzte Schreibweise für Lambdas, die lediglich eine bestehende Methode oder einen Konstruktor aufrufen.

Es gibt vier Arten von Methodenreferenzen:

```java
// 1. Statische Methode (Class::staticMethod)
Function<String, Integer> parser = Integer::parseInt; 
// Lambda: s -> Integer.parseInt(s)

// 2. Instanzmethode eines bestimmten Objekts (instance::instanceMethod)
Consumer<String> printer = System.out::println; 
// Lambda: s -> System.out.println(s)

// 3. Instanzmethode eines unbestimmten Objekts eines bestimmten Typs (Class::instanceMethod)
Function<String, String> toUpper = String::toUpperCase; 
// Lambda: s -> s.toUpperCase()

// 4. Konstruktorreferenz (Class::new)
Supplier<List<String>> listSupplier = ArrayList::new; 
// Lambda: () -> new ArrayList<>()
```

---

## 6. Gültigkeitsbereich & *Effectively Final*

Lambda-Ausdrücke können auf Variablen aus dem umgebenden Scope (z. B. lokale Variablen der Methode) zugreifen.

* **Regel:** Lokale Variablen, auf die im Lambda zugegriffen wird, müssen **`final`** oder **`effectively final`** sein.
* **Effectively Final bedeutet:** Die Variable wurde nach ihrer Initialisierung an keiner Stelle im Code mehr verändert (keine erneute Zuweisung).

```java
int factor = 2; // effectively final
List<Integer> numbers = List.of(1, 2, 3);

// ERLAUBT: Lesender Zugriff auf factor
numbers.forEach(n -> System.out.println(n * factor)); 

// VERBOTEN (Compilerfehler):
// factor = 3; // Ändert factor -> nicht mehr effectively final!
```

> **Achtung:** Lambdas besitzen **keinen eigenen Scope** für `this`. Innerhalb eines Lambdas bezieht sich `this` auf die umgebende Klasse, nicht auf das Lambda selbst!

---

## 7. Klausur-Checkliste & Typische Fallstricke

1. **Syntaxfalle mit `{}` und `return`:** 
   * `{ s -> return s.length(); }` → **Falsch!** (Klammern fehlen).
   * `s -> { return s.length(); }` → **Richtig!**
   * `s -> s.length()` → **Richtig!**
2. **Mehrere abstrakte Methoden:** Wenn ein Interface mehr als eine abstrakte Methode besitzt, kann **kein** Lambda verwendet werden.
3. **Typ-Verwechslung bei Methodenreferenzen:** 
   * `String::toUpperCase` entspricht `(String s) -> s.toUpperCase()`
   * `s::toUpperCase` funktioniert nur, wenn `s` ein konkretes `String`-Objekt in der Umgebung ist.
4. **Schreibzugriff auf Umgebungsvariablen:** Das Ändern einer lokalen Variable innerhalb eines Lambdas (z. B. `int sum = 0; list.forEach(n -> sum += n);`) ist ein **Compilerfehler**!

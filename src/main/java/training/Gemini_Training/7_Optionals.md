# Cheatsheet: Optionals (`java.util.Optional`) & Lambdas

## 1. Was ist ein Optional?
Ein `Optional<T>` ist ein Container-Objekt, das entweder genau einen Wert vom Typ `T` enthalten kann oder **leer** (*empty*) ist.

* **Zweck:** Dient als typsichere Alternative zur Rückgabe von `null`. Es verhindert `NullPointerException` (NPE) und zwingt den Aufrufer explizit zur Behandlung eines fehlenden Wertes.
* **Typischer Einsatzort:** Ausschließlich als **Rückgabetyp** von Methoden, bei denen kein Ergebnis existieren könnte (z. B. Suchen in Datenbanken/Listen).

---

## 2. Erzeugung von Optionals

| Methode | Beschreibung | Beispiel |
| :--- | :--- | :--- |
| **`Optional.empty()`** | Erzeugt ein leeres `Optional`. | `Optional<User> opt = Optional.empty();` |
| **`Optional.of(value)`** | Hüllt einen Wert ein. Wirft sofort `NullPointerException`, wenn `value == null` ist! | `Optional<User> opt = Optional.of(user);` |
| **`Optional.ofNullable(value)`** | Hüllt den Wert ein. Falls `value == null` ist, entsteht sicher ein leeres `Optional.empty()`. | `Optional<String> opt = Optional.ofNullable(email);` |

---

## 3. Werte abfragen & auslesen

### A. Klassischer Ansatz (Mit expliziter Prüfung)
```java
Optional<User> userOpt = repository.findUserById("u001");

if (userOpt.isPresent()) {
    User user = userOpt.get(); // Liefert den Wert (wirft NoSuchElementException falls leer)
    System.out.println(user.getName());
} else {
    System.out.println("Benutzer nicht gefunden.");
}
```
* **`isPresent()`:** Liefert `true`, wenn ein Wert enthalten ist.
* **`isEmpty()`:** Liefert `true`, wenn das `Optional` leer ist.

---

## 4. Modernes & funktionales Arbeiten mit Lambdas

### A. `ifPresentOrElse(Consumer, Runnable)`
Führt die erste Lambda-Funktion aus, wenn ein Wert vorhanden ist, ansonsten die zweite.
```java
userOpt.ifPresentOrElse(
    u -> System.out.println("Gefunden: " + u.getName()), // Consumer<T>
    () -> System.out.println("Nicht gefunden")          // Runnable
);
```

### B. `ifPresent(Consumer)`
Führt die Aktion nur aus, wenn ein Wert existiert.
```java
userOpt.ifPresent(u -> System.out.println("Gefunden: " + u.getName()));
```

### C. `orElse(defaultVal)`
Gibt den Wert zurück. Ist das `Optional` leer, wird stattdessen der übergebene Ersatzwert zurückgegeben.
```java
String email = user.getOptionalEmail().orElse("Keine E-Mail hinterlegt");
```

### D. `orElseGet(Supplier)`
Ähnlich wie `orElse`, berechnet den Ersatzwert aber erst verzögert (*lazy*) über ein Lambda, wenn das `Optional` wirklich leer ist.
```java
String email = user.getOptionalEmail().orElseGet(() -> fetchDefaultEmailFromDb());
```

### E. `orElseThrow()` / `orElseThrow(Supplier)`
Entpackt den Wert. Ist das `Optional` leer, wird eine Exception geworfen (`NoSuchElementException` oder eine eigene Exception).
```java
// Wirft NoSuchElementException falls leer
User user = repository.findUserById("u999").orElseThrow();

// Wirft eine benutzerdefinierte Exception
User user2 = repository.findUserById("u999")
    .orElseThrow(() -> new IllegalArgumentException("User ID existiert nicht!"));
```

---

## 5. Weiterverarbeitung mit `map`, `flatMap` & `filter`

### A. `map(Function)`
Transformiert den Wert im `Optional`, sofern einer vorhanden ist. Falls leer, bleibt das Ergebnis `Optional.empty()`.
```java
Optional<String> nameUpper = userOpt.map(u -> u.getName().toUpperCase());
```

### B. `flatMap(Function)`
Wird genutzt, wenn die Transformationsfunktion selbst bereits ein `Optional` zurückgibt (verhindert Schachtelungen wie `Optional<Optional<String>>`).
```java
Optional<String> email = userOpt.flatMap(User::getOptionalEmail);
```

### C. `filter(Predicate)`
Behält den Wert nur bei, wenn er die Bedingung erfüllt. Passt die Bedingung nicht (oder ist leer), entsteht `Optional.empty()`.
```java
Optional<User> adminOpt = userOpt.filter(u -> u.getName().equals("Admin"));
```

---

## 6. Klausur-Checkliste & Best Practices

1. **Niemals `get()` ohne Prüfung aufrufen:** Ein direkter Aufruf von `opt.get()` auf einem leeren `Optional` führt zu einer `NoSuchElementException`.
2. **`Optional` niemals als Parameter nutzen:** Verwende `Optional` nicht in Methodenparametern oder als Instanzvariable/Feld in Klassen (da `Optional` nicht serialisierbar ist). Nutzen Sie es fast ausschließlich als Rückgabetyp.
3. **`Optional.ofNullable()` bevorzugen:** Wenn du nicht zu 100% garantieren kannst, dass eine Variable ungleich `null` ist, verwende immer `Optional.ofNullable(...)` statt `Optional.of(...)`.

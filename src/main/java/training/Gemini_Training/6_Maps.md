# Cheatsheet: Assoziativspeicher (Maps) & Lambdas

## 1. Was ist eine Map?
Eine `Map<K, V>` speichert **Schlüssel-Wert-Paare** (*Key-Value Pairs*).

* **Eindeutigkeit:** Jeder Schlüssel (*Key*) ist **eindeutig**. Wird ein bestehender Key erneut hinzugefügt, wird sein alter Wert überschrieben.
* **Duplikate bei Werten:** Werte (*Values*) dürfen sich beliebig oft wiederholen.
* **HashMap:** Der Standard-Typ in Java. Nutzt Hash-Tabellen für extrem schnelle Zugriffe in $\mathcal{O}(1)$.

---

## 2. Die wichtigsten Methoden im Überblick

| Methode | Beschreibung | Rückgabewert |
| :--- | :--- | :--- |
| **`put(K key, V value)`** | Fügt ein Paar hinzu oder überschreibt den Wert eines Keys. | Alter Wert `V` (oder `null` falls neu) |
| **`get(Object key)`** | Liest den Wert zum Schlüssel aus. | Wert `V` oder `null` |
| **`getOrDefault(key, defaultVal)`** | Liest den Wert aus oder liefert einen Standardwert, falls Key nicht existiert. | Wert `V` oder `defaultVal` |
| **`putIfAbsent(K key, V value)`** | Fügt den Wert nur ein, wenn der Key noch nicht existiert oder `null` ist. | Bisheriger Wert oder `null` |
| **`containsKey(Object key)`** | Prüft, ob der Schlüssel existiert ($\mathcal{O}(1)$). | `boolean` |
| **`containsValue(Object value)`** | Prüft, ob der Wert mindestens einmal existiert ($\mathcal{O}(n)$). | `boolean` |
| **`remove(Object key)`** | Entfernt den Schlüssel samt Wert. | Entferntes Objekt `V` oder `null` |
| **`size()` / `isEmpty()`** | Gibt die Anzahl der Einträge zurück bzw. prüft auf Leere. | `int` / `boolean` |

---

## 3. Die 4 Wege der Iteration

```java
Map<Student, Exam> map = new HashMap<>();
```

### A. Über alle Schlüssel iterieren (`keySet()`)
```java
for (Student s : map.keySet()) {
    System.out.println(s.getName());
}
```

### B. Über alle Werte iterieren (`values()`)
```java
for (Exam e : map.values()) {
    System.out.println(e.getGrade());
}
```

### C. Über Paare iterieren (`entrySet()`)
```java
for (Map.Entry<Student, Exam> entry : map.entrySet()) {
    Student s = entry.getKey();
    Exam e = entry.getValue();
    System.out.println(s.getId() + ": " + e.getGrade());
}
```

### D. Funktionale Iteration (`forEach` mit Lambda)
```java
map.forEach((student, exam) -> System.out.println(student.getId() + ": " + exam.getGrade()));
```

---

## 4. Lambda-Ausdrücke & Moderne Map-Operationen

Java bietet leistungsfähige Methoden, die funktionale Schnittstellen direkt auf Maps anwenden:

### A. `forEach(BiConsumer<K, V>)`
Führt eine Aktion für jedes Key-Value-Paar aus.
```java
map.forEach((key, val) -> System.out.println(key + " -> " + val));
```

### B. `replaceAll(BiFunction<K, V, V>)`
Ersetzt die Werte aller Einträge durch das Ergebnis der Lambda-Funktion.
```java
// Beispiel: Preise um 10% erhöhen
priceMap.replaceAll((product, price) -> price * 1.10);
```

### C. `computeIfAbsent(K key, Function<K, V>)`
Berechnet einen Wert und fügt ihn ein, falls der Key **noch nicht** existiert.
```java
// Erstellt automatisch eine neue Liste, falls der Key noch nicht vorhanden ist
map.computeIfAbsent("Abt_1", k -> new ArrayList<>()).add(neuerMitarbeiter);
```

### D. `computeIfPresent(K key, BiFunction<K, V, V>)`
Berechnet einen neuen Wert, falls der Key **bereits existiert**.
```java
// Boni für existierenden Mitarbeiter anpassen
map.computeIfPresent("Emp1", (id, currentSalary) -> currentSalary + 500);
```

### E. `merge(K key, V value, BiFunction<V, V, V>)`
Fügt den Wert ein oder verknüpft/berechnet ihn mit dem bereits existierenden Wert.
```java
// Wortzähler: Wenn Wort existiert, addiere 1, sonst starte bei 1
wordCounts.merge(word, 1, (oldVal, newVal) -> oldVal + newVal);
```

---

## 5. Schlüssel-Klassen: `equals()` und `hashCode()`

Damit eine `HashMap` selbstgeschriebene Objekte als Key korrekt auflösen kann, **müssen** `equals()` und `hashCode()` konsistent überschrieben werden!

1. **HashCode-Vertrag:** Wenn zwei Objekte laut `equals()` gleich sind, **müssen** ihre `hashCode()`-Werte identisch sein.
2. **Lombok-Nutzen:** `@Data` oder `@EqualsAndHashCode` generiert beide Methoden automatisch basierend auf den Attributen.
3. **Konsequenz bei Fehlern:** Ohne funktionierendes `hashCode()`/`equals()` wird ein eben eingefügtes Objekt beim Aufruf von `get(new Product("p1", "Apfel"))` nicht mehr wiedergefunden und liefert `null`.

---

## 6. Klausur-Checkliste & Typische Fallstricke

1. **Rückgabewert von `put()`:** 
   * Liefert den **alten** Wert zurück, wenn der Key bereits da war.
   * Liefert `null`, wenn der Key neu eingefügt wurde.
2. **`containsValue()` ist langsam:** Sucht linear ($\mathcal{O}(n)$) durch die Map, während `containsKey()` per Hash-Wert instantan ist ($\mathcal{O}(1)$).
3. **Veränderbare Keys (Mutable Keys):** Werden Attribute eines Objekts verändert, nachdem es als Key in eine `HashMap` eingefügt wurde, ändert sich sein `hashCode()`. Das Objekt kann in der Map **nicht mehr gefunden werden**! Keys sollten daher unveränderlich (*immutable* / `final`) sein.

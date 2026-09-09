package training.Gemini_Training._5_Lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

public class Main {

    public static void main(String[] args) {

        // ==========================================
        // 1. Anonyme innere Klasse vs. Lambda
        // ==========================================
        System.out.println("=== 1. Anonyme Klasse vs. Lambda ===");
        
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hallo aus der anonymen Klasse!");
            }
        };
        r1.run();

        Runnable r2 = () -> System.out.println("Hallo aus dem Lambda!");
        r2.run();


        // ==========================================
        // 2. Custom Functional Interface (@FunctionalInterface)
        // ==========================================
        System.out.println("\n=== 2. Eigenes Funktionales Interface ===");

        // Kurzform (1 Anweisung): {} und return entfallen
        MathOperation addition = (a, b) -> a + b;

        // Ausführliche Form mit Block und explizitem return
        MathOperation multiplication = (a, b) -> {
            System.out.println("Multipliziere " + a + " und " + b);
            return a * b;
        };

        System.out.println("Addition (5 + 3): " + addition.operate(5, 3));
        System.out.println("Multiplikation (5 * 3): " + multiplication.operate(5, 3));


        // ==========================================
        // 3. Vordefinierte Funktionale Interfaces (java.util.function)
        // ==========================================
        System.out.println("\n=== 3. Vordefinierte Funktionale Interfaces ===");

        // Predicate<T>: test(T t) -> boolean
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("Ist 4 gerade? " + isEven.test(4));

        // Consumer<T>: accept(T t) -> void
        Consumer<String> printUpper = s -> System.out.println("Consumer-Ausgabe: " + s.toUpperCase());
        printUpper.accept("java lambdas");

        // Function<T, R>: apply(T t) -> R
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("Länge von 'Klausur': " + stringLength.apply("Klausur"));

        // Supplier<T>: get() -> T
        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("Zufallszahl vom Supplier: " + randomSupplier.get());

        // UnaryOperator<T>: apply(T t) -> T
        UnaryOperator<String> exclaim = s -> s + "!";
        System.out.println(exclaim.apply("Achtung"));

        // BinaryOperator<T>: apply(T t1, T t2) -> T
        BinaryOperator<Integer> maxCalculator = (a, b) -> Math.max(a, b);
        System.out.println("Max von 10 und 20: " + maxCalculator.apply(10, 20));

        // BiConsumer<T, U>: accept(T t, U u) -> void
        BiConsumer<String, Integer> printAge = (name, age) -> System.out.println(name + " ist " + age + " Jahre alt.");
        printAge.accept("Anna", 22);


        // ==========================================
        // 4. Methodenreferenzen (::)
        // ==========================================
        System.out.println("\n=== 4. Methodenreferenzen (::) ===");

        // 1. Statische Methode (Class::staticMethod)
        // Lambda: s -> Integer.parseInt(s)
        Function<String, Integer> parser = Integer::parseInt;
        System.out.println("Geparster Wert: " + parser.apply("123"));

        // 2. Instanzmethode eines bestimmten Objekts (instance::instanceMethod)
        // Lambda: s -> System.out.println(s)
        Consumer<String> printer = System.out::println;
        printer.accept("Direkt ausgegeben über System.out::println");

        // 3. Instanzmethode eines unbestimmten Objekts (Class::instanceMethod)
        // Lambda: s -> s.toUpperCase()
        Function<String, String> toUpper = String::toUpperCase;
        System.out.println("In Großbuchstaben: " + toUpper.apply("methodenreferenz"));

        // 4. Konstruktorreferenz (Class::new)
        // Lambda: () -> new ArrayList<>()
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> dynamicList = listSupplier.get();
        dynamicList.add("Erstes Element");
        System.out.println("Liste erzeugt über Konstruktorreferenz: " + dynamicList);


        // ==========================================
        // 5. Gültigkeitsbereich & Effectively Final
        // ==========================================
        System.out.println("\n=== 5. Effectively Final Scope ===");

        int multiplier = 3; // Intentionally NOT modified afterwards -> Effectively Final
        List<Integer> numbers = List.of(1, 2, 3, 4);

        // Erlaubt: Lesender Zugriff auf die locale Variable multiplier
        numbers.forEach(n -> System.out.println(n + " * " + multiplier + " = " + (n * multiplier)));

        // multiplier = 5; 
        // ⚠️ HINWEIS: Würde die Zeile darüber einkommentiert, gäbe es einen Compiler-Fehler 
        // im Lambda oben ("local variables referenced from a lambda expression must be final or effectively final").
    }
}

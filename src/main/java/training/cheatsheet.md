Cheatsheet Programmierung II
Daniel Appenmaier, 27. Juli 2026

Cheatsheet Programmierung II
Cheatsheet
java.lang.Boolean
compare(x: boolean, y: boolean) int S
valueOf(s: String) Boolean S
valueOf(b: boolean) Boolean S
java.lang.Comparable<T>
compareTo(o: T) int
java.lang.Double
compare(d1: double, d2: double) int S
valueOf(s: String) Double S
valueOf(d: double) Double S
java.lang.Integer
compare(x: int, y: int) int S
valueOf(s: String) Integer S
valueOf(i: int) Integer S
java.lang.Long
compare(x: long, y: long) long S
valueOf(s: String) Long S
valueOf(l: long) Long S
java.lang.Object
equals(object: Object) boolean
hashCode() int
toString() String
java.lang.Runnable
run() void
java.lang.String
charAt(index: int) char
length() int
split(regex: String) String[]
toLowerCase() String
toUpperCase() String
java.lang.System
currentTimeMillis() long S
java.io.PrintStream
print(obj: Object) void
printf(String format, Object... args) PrintStream
println() void
println(x: Object) void
1

Cheatsheet Programmierung II
java.util.Collections
| sort(list: List<T>)                   |     | void S |     |     |
| ------------------------------------- | --- | ------ | --- | --- |
| sort(list: List<T>, c: Comparator<T>) |     | void S |     |     |
java.util.Comparator<T>
| compare(o1: T, o2: T)                  |     | int           |     |     |
| -------------------------------------- | --- | ------------- | --- | --- |
| comparing(keyExtractor: Function<T,U>) |     | Comparator<T> | S   |     |
java.util.Entry<K,V>
| getKey()           | K   |     |     |     |
| ------------------ | --- | --- | --- | --- |
| getValue()         | V   |     |     |     |
| setValue(value: V) | V   |     |     |     |
java.util.Enumeration
| valueOf(arg0: String) | Enumeration   | S   |     |     |
| --------------------- | ------------- | --- | --- | --- |
| values()              | Enumeration[] | S   |     |     |
java.util.List<E>
| add(e: E)                    | boolean |     |     |     |
| ---------------------------- | ------- | --- | --- | --- |
| add(index: int, element: E)  | void    |     |     |     |
| contains(o: Object)          | boolean |     |     |     |
| forEach(action: Consumer<T>) | void    |     |     |     |
| get(index: int)              | E       |     |     |     |
| of(elements: E...)           | List<E> | S   |     |     |
| remove(index: int)           | E       |     |     |     |
| remove(o: Object)            | boolean |     |     |     |
| reversed()                   | List<T> |     |     |     |
| size()                       | int     |     |     |     |
| sort(c: Comparator<T>)       | void    |     |     |     |
java.util.Map<K,V>
| containsKey(key: Object)         |     | boolean         |     |     |
| -------------------------------- | --- | --------------- | --- | --- |
| containsValue(value: Object)     |     | boolean         |     |     |
| entrySet()                       |     | Set<Entry<K,V>> |     |     |
| forEach(action: BiConsumer<K,V>) |     | void            |     |     |
| get(key: Object)                 |     | V               |     |     |
| keySet()                         |     | Set<K>          |     |     |
| put(key: K, value: V)            |     | V               |     |     |
| putIfAbsent(key: K, value: V)    |     | V               |     |     |
| values()                         |     | Collection<V>   |     |     |
java.util.Optional<T>
| empty()                                                     |     |     | Optional<T> | S   |
| ----------------------------------------------------------- | --- | --- | ----------- | --- |
| get()                                                       |     |     | T           |     |
| ifPresent(action: Consumer<T>)                              |     |     | void        |     |
| ifPresentOrElse(action: Consumer<T>, emptyAction: Runnable) |     |     | void        |     |
| isPresent()                                                 |     |     | boolean     |     |
| of(t: T)                                                    |     |     | Optional<T> | S   |
| ofNullable(t: T)                                            |     |     | Optional<T> | S   |
| orElse(other: T)                                            |     |     | T           |     |
2

Cheatsheet Programmierung II
java.util.OptionalDouble
| empty()                           |     |     | OptionalDouble | S   |
| --------------------------------- | --- | --- | -------------- | --- |
| getAsDouble()                     |     |     | double         |     |
| ifPresent(action: DoubleConsumer) |     |     | void           |     |
ifPresentOrElse(action: DoubleConsumer, emptyAction: Runnable) void
| isPresent()           |     |     | boolean        |     |
| --------------------- | --- | --- | -------------- | --- |
| of(value: double)     |     |     | OptionalDouble | S   |
| orElse(other: double) |     |     | double         |     |
java.util.Random
| nextInt(origin: int, bound: int) int |     |     |     |     |
| ------------------------------------ | --- | --- | --- | --- |
java.util.function.BiConsumer
accept(t: T, u: U) void
java.util.function.Consumer<T>
accept(t: T) void
java.util.function.DoubleConsumer
accept(value: double) void
java.util.function.Function<T,R>
apply(t: T) R
java.util.function.Predicate<T>
test(t: T) boolean
java.util.function.ToDoubleFunction<T,R>
applyAsDouble(value: T) double
java.util.function.ToIntFunction<T,R>
applyAsInt(value: T) int
java.time.LocalDate
| getDayOfMonth()                            | int       |     |     |     |
| ------------------------------------------ | --------- | --- | --- | --- |
| getDayOfYear()                             | int       |     |     |     |
| getMonth()                                 | Month     |     |     |     |
| getMonthValue()                            | int       |     |     |     |
| getYear()                                  | int       |     |     |     |
| now()                                      | LocalDate | S   |     |     |
| of(year: int, month: int, dayOfMonth: int) | LocalDate | S   |     |     |
| parse(text: CharSequence)                  | LocalDate | S   |     |     |
java.time.LocalTime
| getHour()                               | int       |     |     |     |
| --------------------------------------- | --------- | --- | --- | --- |
| getMinute()                             | int       |     |     |     |
| getSecond()                             | int       |     |     |     |
| now()                                   | LocalTime | S   |     |     |
| of(hour: int, minute: int, second: int) | LocalTime | S   |     |     |
| parse(text: CharSequence)               | LocalTime | S   |     |     |
3

Cheatsheet Programmierung II
java.util.stream.Collectors
averagingDouble(mapper: ToDoubleFunction<T>) Collector<T,?,Double> S
| counting() | Collector<T,?,Long> | S   |
| ---------- | ------------------- | --- |
groupingBy(classifier: Function<T,K>) Collector<T,?,Map<K,List<T>>> S
joining(delimiter: CharSequence) Collector<CharSequence,?,String> S
S
mapping(m: Function<T,U>, ds: Collector<U,A,R>) Collector<T,?,R>
partitioningBy(predicate: Predicate<T>) Collector<T,?,Map<Boolean,List<T>>> S
summingDouble(mapper: ToDoubleFunction<? super T>) Collector<T,?,Double> S
| toList() | Collector<T,?,List<T>> | S   |
| -------- | ---------------------- | --- |
toMap(keyM: Function<T,K>, valueM: Function<T,U>) Collector<T,?,Map<K,U>> S
| toSet() | Collector<T,?,Set<T>> | S   |
| ------- | --------------------- | --- |
java.util.stream.DoubleStream
average() OptionalDouble
sum() double
java.util.stream.IntStream
average() OptionalDouble
sum() int
java.util.stream.Stream<T>
| allMatch(predicate: Predicate<T>) boolean               |     |     |
| ------------------------------------------------------- | --- | --- |
| anyMatch(predicate: Predicate<T>) boolean               |     |     |
| collect(collector: Collector<T,A,R>) R                  |     |     |
| count() long                                            |     |     |
| distinct() Stream<T>                                    |     |     |
| filter(predicate: Predicate<T>) Stream<T>               |     |     |
| findAny() Optional<T>                                   |     |     |
| findFirst() Optional<T>                                 |     |     |
| flatMap(mapper: Function<T,Stream<R>>) Stream<R>        |     |     |
| forEach(action: Consumer<T>) void                       |     |     |
| limit(maxSize: long) Stream<T>                          |     |     |
| map(mapper: Function<T,R>) Stream<R>                    |     |     |
| mapToDouble(mapper: ToDoubleFunction<T,R>) DoubleStream |     |     |
| mapToInt(mapper: ToIntFunction<T,R>) IntStream          |     |     |
| max(comparator: Comparator<T>) Optional<T>              |     |     |
| min(comparator: Comparator<T>) Optional<T>              |     |     |
| noneMatch(predicate: Predicate<T>) boolean              |     |     |
| skip(n: long) Stream<T>                                 |     |     |
| sorted() Stream<T>                                      |     |     |
| sorted(comparator: Comparator<T>) Stream<T>             |     |     |
| toList() List<T>                                        |     |     |
4

Cheatsheet Programmierung II
org.junit.jupiter.api.Assertions
assertEquals(expected: Object, actual: Object) void S
assertFalse(condition: boolean) void S
assertNotEquals(expected: Object, actual: Object) void S
assertNotNull(actual: Object) void S
assertNotSame(expected: Object, actual: Object) void S
assertNull(actual: Object) void S
assertSame(expected: Object, actual: Object) void S
assertThrows(expectedType: Class<T>, executable: Executable) T S
assertTrue(condition: boolean) void S
org.junit.jupiter.api.Executable
execute() void
org.mockito.Mockito
when(methodCall: T) OngoingStubbing<T> S
org.mockito.MockitoAnnotations
openMocks(testClass: Object) AutoCloseable S
org.mockito.stubbing.OngoingStubbing<T>
thenReturn(value: T) OngoingStubbing<T>
5

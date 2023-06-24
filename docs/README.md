### Tuples

There are 5 types of tuples in this project: 

UnitTuple: A tuple of one element.
```java
UnitTuple<A>
```
Pair: A tuple of 2 elements.
```java
Pair<A, B>
```
Triplet: A tuple of 3 elements.
```java
Triplet<A, B, C>
```
Quadruplet: A tuple of 4 elements.
```java
Quadruplet<A, B, C, D>
```
Quintuplet: A tuple of 5 elements.
```java
Quintuplet<A, B, C, D, E>
```

#### Tuple traits: 
They are type safe and all tuple classes are final.
You can compare one tuple with another using .euqals method: 

```java
final Pair<String, Integer> person = Pair.of("John", 25);
final Pair<String, Integer> other = Pair.of("John", 25);

assertEquals(other, person);
```

The equals method is also null safe (won't throw NPE):
```java
final Pair<String, Integer> person = Pair.of("John", null);
final Pair<String, Integer> other = Pair.of("John", null);

assertEquals(other, person);
```

### WrappingMonad
WrappingMonads are useful when you intend to transform any element of one category to another category. 
For example, you can transform a string into a integer using the .map or .flatMap methods, or you can even
transform a tuple of String elements into a tuple of integer elements using the ```Monadify<A>``` implementation:


```java
final UnitTuple<String> tuple = UnitTuple.of("FooBar");

final UnitTuple<Integer> actualResultingTuple = Monadify
        .of(tuple)
        .flatMap(el->Monadify.of(el.first))
        .flatMap(s->Monadify.of(s.length()))
        .map(UnitTuple::of)
        .get();

final UnitTuple<Integer> expectedResultingTuple = UnitTuple.of(6);

assertEquals(expectedResultingTuple, actualResultingTuple);
```

The ```Monadify<B>.flatMap(Fn1<A, WrappingMonad<B>> execution)``` is also throw-safe. If 
any flatMap lambda execution were to throw some Exception, the subsequent flatMaps chained call won't execute:
```java 
final UnitTuple<String> tuple = UnitTuple.of("FooBar");

final WrappingMonad<?> actualMonad = Monadify
        .of(tuple)
        .flatMap(el->Monadify.of(0/0)) // <- Will throw
        .flatMap(el->Monadify.of(1)) // Won't execute
        .flatMap(el->Monadify.of(2)) // Won't execute
        .flatMap(el->Monadify.of(UnitTuple.of(el))) // Won't execute;

// At the end, the element inside the Monidify Wrapper is still "FooBar"
assertEquals(tuple, actualMonad.get());
```
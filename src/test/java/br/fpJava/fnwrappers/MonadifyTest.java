package br.fpJava.fnwrappers;

import br.fpJava.tuple.UnitTuple;
import junit.framework.TestCase;
import org.junit.Test;

public class MonadifyTest extends TestCase {

    @Test
    public void testingTurningAUnitTupleOfStringIntoAUnitTupleOfInteger(){
        final UnitTuple<String> tuple = UnitTuple.of("FooBar");

        final UnitTuple<Integer> actualResultingTuple = Monadify
                .of(tuple)
                .map(el->el.first)
                .map(String::length)
                .map(UnitTuple::of)
                .get();

        final UnitTuple<Integer> expectedResultingTuple = UnitTuple.of(6);

        assertEquals(expectedResultingTuple, actualResultingTuple);
    }

    @Test
    public void testingTurningAUnitTupleOfStringIntoAUnitTupleOfIntegerUsingFlatMap(){
        final UnitTuple<String> tuple = UnitTuple.of("FooBar");

        final UnitTuple<Integer> actualResultingTuple = Monadify
                .of(tuple)
                .flatMap(el->Monadify.of(el.first))
                .flatMap(s->Monadify.of(s.length()))
                .map(UnitTuple::of)
                .get();

        final UnitTuple<Integer> expectedResultingTuple = UnitTuple.of(6);

        assertEquals(expectedResultingTuple, actualResultingTuple);
    }

    @Test
    public void WhenFirstFlatMapThrowsExceptionThenTheSubsequentFlatMapsArentExecuted(){
        final UnitTuple<String> tuple = UnitTuple.of("FooBar");

        final WrappingMonad<?> actualMonad = Monadify
                .of(tuple)
                .flatMap(el->Monadify.of(0/0)) // <- Will throw
                .flatMap(el->Monadify.of(1))
                .flatMap(el->Monadify.of(2))
                .flatMap(el->Monadify.of(UnitTuple.of(el)));

        // Wrapped element should still be "FooBar"
        assertEquals(tuple, actualMonad.get());
    }


}
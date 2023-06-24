package br.fpJava.tuple;

import junit.framework.TestCase;
import org.junit.Test;

public class UnitTupleTest extends TestCase implements EqualsTest{

    @Override
    public void testingEquals(){
        // Given
        final UnitTuple<String> person = UnitTuple.of("John");
        final UnitTuple<String> other = UnitTuple.of("John");

        assertEquals(other, person);
    }

    @Override
    public void testingNotEqual(){
        // Given
        final UnitTuple<String> person = UnitTuple.of("John");
        final UnitTuple<String> other = UnitTuple.of("Maire");

        assertFalse(other.equals(person));
    }

    @Override
    public void testingNullSafeEqual(){
        // Given
        final UnitTuple<String> person = UnitTuple.of(null);
        final UnitTuple<String> other = UnitTuple.of(null);

        assertEquals(other, person);
    }

}
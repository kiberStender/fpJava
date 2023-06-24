package br.fpJava.tuple;

import junit.framework.TestCase;
import org.junit.Test;

public class PairTest extends TestCase implements EqualsTest{

    @Override
    public void testingEquals(){
        // Given
        final Pair<String, Integer> person = Pair.of("John", 25);
        final Pair<String, Integer> other = Pair.of("John", 25);

        assertEquals(other, person);
    }

    @Override
    public void testingNotEqual(){
        // Given
        final Pair<String, Integer> person = Pair.of("John", 25);
        final Pair<String, Integer> other = Pair.of("John", 24);

        assertFalse(other.equals(person));
    }

    @Override
    public void testingNullSafeEqual(){
        // Given
        final Pair<String, Integer> person = Pair.of("John", null);
        final Pair<String, Integer> other = Pair.of("John", null);

        assertEquals(other, person);
    }
}
package br.fpJava.tuple;

import junit.framework.TestCase;
import org.junit.Test;

public class TripletTest extends TestCase implements EqualsTest{

    @Override
    public void testingEquals(){
        // Given
        final Triplet<String, Integer, Integer> person = Triplet.of("John", 25, 177);
        final Triplet<String, Integer, Integer> other = Triplet.of("John", 25, 177);

        assertEquals(other, person);
    }

    @Override
    public void testingNotEqual(){
        // Given
        final Triplet<String, Integer, Integer> person = Triplet.of("John", 25, 177);
        final Triplet<String, Integer, Integer> other = Triplet.of("John", 24, 177 );

        assertFalse(other.equals(person));
    }

    @Override
    public void testingNullSafeEqual(){
        // Given
        final Triplet<String, Integer, Integer> person = Triplet.of("John", null, 177);
        final Triplet<String, Integer, Integer> other = Triplet.of("John", null, 177 );

        assertTrue(other.equals(person));
    }
}
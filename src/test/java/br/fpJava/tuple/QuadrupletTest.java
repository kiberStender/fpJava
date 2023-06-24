package br.fpJava.tuple;

import junit.framework.TestCase;
import org.junit.Test;

public class QuadrupletTest extends TestCase implements EqualsTest{

    @Override
    public void testingEquals(){
        // Given
        final Quadruplet<String, Integer, Integer, Boolean> person = Quadruplet.of("John", 25, 177, true);
        final Quadruplet<String, Integer, Integer, Boolean> other = Quadruplet.of("John", 25, 177, true);

        assertEquals(other, person);
    }

    @Override
    public void testingNotEqual(){
        // Given
        final Quadruplet<String, Integer, Integer, Boolean> person = Quadruplet.of("John", 25, 177, true);
        final Quadruplet<String, Integer, Integer, Boolean> other = Quadruplet.of("John", 24, 177, true);

        assertFalse(other.equals(person));
    }

    @Override
    public void testingNullSafeEqual(){
        // Given
        final Quadruplet<String, Integer, Integer, Boolean> person = Quadruplet.of("John", 25, 177, null);
        final Quadruplet<String, Integer, Integer, Boolean> other = Quadruplet.of("John", 24, 177, null);

        assertFalse(other.equals(person));
    }
}
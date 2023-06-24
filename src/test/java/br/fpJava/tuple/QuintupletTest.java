package br.fpJava.tuple;

import junit.framework.TestCase;

public class QuintupletTest extends TestCase implements EqualsTest{

    @Override
    public void testingEquals(){
        // Given
        final Quintuplet<String, Integer, Integer, Boolean, String> person = Quintuplet.of("John", 25, 177,true,"Intersex- Male-Indentifying");
        final Quintuplet<String, Integer, Integer, Boolean, String> other = Quintuplet.of("John", 25, 177, true, "Intersex- Male-Indentifying");

        assertEquals(other, person);
    }

    @Override
    public void testingNotEqual(){
        // Given
        final Quintuplet<String, Integer, Integer, Boolean, String> person = Quintuplet.of("John", 25, 177, true, "Intersex- Male-Indentifying");
        final Quintuplet<String, Integer, Integer, Boolean, String> other = Quintuplet.of("John", 24, 177, true, "Intersex- Male-Indentifying");

        assertFalse(other.equals(person));
    }

    @Override
    public void testingNullSafeEqual(){
        // Given
        final Quintuplet<String, Integer, Integer, Boolean, String> person = Quintuplet.of("John", 25, 177, null, "Intersex- Male-Indentifying");
        final Quintuplet<String, Integer, Integer, Boolean, String> other = Quintuplet.of("John", 25, 177, null, "Intersex- Male-Indentifying");

        assertTrue(other.equals(person));
    }
}
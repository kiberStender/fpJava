package br.fpJava.setTest;

import br.fpJava.collections.set.Set;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static br.fpJava.collections.set.Set.set;

/**
 * Created by sirkleber on 11/10/14.
 */
public class SetTest {
    @Test
    public void testEquality(){
        assertEquals(set(3, 1, 2), set(1, 2, 3));
    }

    @Test
    public void testEquality1(){
        final Set<String> ss = set("Kleber", "Scalise", "Eduardo");
        final Set<String> ss1 = set("Kleber", "Eduardo", "Scalise");

        assertEquals(ss, ss1);
    }
}
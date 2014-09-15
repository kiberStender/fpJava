package br.fpJava.mapTest;

import br.fpJava.collections.map.Map;
import br.fpJava.tuple.Tuple2;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static br.fpJava.collections.map.Map.Map;
import static br.fpJava.tuple.Tuple2.tuple2;

/**
 * Created by sirkleber on 15/09/14.
 */
public class MapTest {
    private final Map<Integer, String> mi = Map(tuple2(1, "kleber"), tuple2(2, "eduardo"));
    private final Map<Integer, Double> md = Map(tuple2(1, 2.0));

    @Test
    public void testEquality(){
        assertTrue(mi.equals(mi));
    }

    @Test
    public void testEquality1(){
        assertTrue(!mi.equals(md));
    }

}

package br.fpJava.mapTest;

import br.fpJava.collections.map.Map;
import br.fpJava.fn.Fn1;
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

    @Test
    public void testFind() throws Exception{
        assertTrue(mi.find(new Fn1<Tuple2<Integer, String>, Boolean>() {
            @Override
            public Boolean apply(Tuple2<Integer, String> x) {
                return x._1.equals(2);
            }
        }).get().equals(tuple2(2, "eduardo")));
    }

    @Test
    public void testGet() throws Exception{
        assertTrue(mi.get(2).get().equals("eduardo"));
    }

    @Test
    public void testFoldLeft(){
        assertTrue(mi.foldLeft(0, new Fn1<Integer, Fn1<Tuple2<Integer, String>, Integer>>() {
            @Override
            public Fn1<Tuple2<Integer, String>, Integer> apply(final Integer acc) {
                return new Fn1<Tuple2<Integer, String>, Integer>() {
                    @Override
                    public Integer apply(final Tuple2<Integer, String> item) {
                        return acc - item._1;
                    }
                };
            }
        }).equals(-3));
    }

    @Test
    public void testFoldRight(){
        assertTrue(mi.foldRight(0, new Fn1<Tuple2<Integer, String>, Fn1<Integer, Integer>>() {
            @Override
            public Fn1<Integer, Integer> apply(final Tuple2<Integer, String> item) {
                return new Fn1<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer acc) {
                        return acc - item._1;
                    }
                };
            }
        }).equals(-3));
    }

}

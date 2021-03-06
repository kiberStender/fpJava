package br.fpJava.mapTest;

import br.fpJava.collections.map.Map;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Monad;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static br.fpJava.collections.map.Map.map;
import static br.fpJava.tuple.Tuple2.tuple2;

/**
 * Created by sirkleber on 15/09/14.
 */
public class MapTest {
    private final Map<Integer, String> mi = map(tuple2(1, "kleber"), tuple2(2, "eduardo"));
    private final Map<Integer, Double> md = map(tuple2(1, 2.0));

    @Test
    public void testConstructor(){
        assertEquals(tuple2(1, "Kleber"), map(tuple2(2, "eduardo"), tuple2(1, "Kleber")).head());
    }

    @Test
    public void testEquality(){
        assertTrue(mi.equals(mi));
    }

    @Test
    public void testEquality1(){
        assertTrue(!mi.equals(md));
    }

    @Test
    public void testCons(){
        assertEquals(map(tuple2(1, 2.0), tuple2(2, 3.2)), md.cons(tuple2(2, 3.2)));
    }

    @Test
    public void testCons1(){
        assertEquals(map(tuple2(1, 2.0), tuple2(2, 3.2), tuple2(3, 1.5)), md.cons(tuple2(3, 1.5)).cons(tuple2(2, 3.2)));
    }

    @Test
    public void testCons2() {
        assertEquals(map(tuple2(1, 2.0), tuple2(2, 3.2), tuple2(3, 1.5)), md.cons(tuple2(3, 1.7)).cons(tuple2(2, 3.2)).cons(tuple2(3, 1.5)));
    }

    @Test
    public void testConcat(){
        assertEquals(map(tuple2(1, 2.0), tuple2(2, 3.1)), md.concat(map(tuple2(2, 3.1))));
    }

    @Test
    public void testConcat1(){
        assertEquals(map(tuple2(1, 2.0), tuple2(2, 3.1), tuple2(3, 2.5)), md.concat(map(tuple2(3, 2.5), tuple2(2, 3.1))));
    }

    @Test
    public void testHead(){
        assertEquals(tuple2(1, 2.0), md.head());
    }

    @Test
    public void testTail(){
        assertEquals(map(tuple2(2, "eduardo")), mi.tail());
    }

    @Test
    public void testInit(){
        assertEquals(map(tuple2(1, "kleber")), mi.init());
    }

    @Test
    public void testLast(){
        assertEquals(tuple2(2, "eduardo"), mi.last());
    }

    @Test
    public void testFind() throws Exception{
        assertEquals(Just.just(tuple2(2, "eduardo")), mi.find(new Fn1<Tuple2<Integer, String>, Boolean>() {
            @Override
            public Boolean apply(Tuple2<Integer, String> x) {
                return x._1.equals(2);
            }
        }));
    }

    @Test
    public void testGet() throws Exception{
        assertEquals(Just.just("eduardo"), mi.get(2));
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

    @Test
    public void testMap(){
        assertEquals(map(tuple2(1, 4.0)), md.map(new Fn1<Tuple2<Integer, Double>, Tuple2<Integer, Double>>() {
            @Override
            public Tuple2<Integer, Double> apply(Tuple2<Integer, Double> x) {
                return tuple2(x._1, x._2 * 2);
            }
        }));
    }

    @Test
    public void testMap1(){
        assertEquals(map(tuple2("1k", "kleberl"), tuple2("2k", "eduardol")), mi.map(new Fn1<Tuple2<Integer, String>, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> apply(Tuple2<Integer, String> x) {
                return tuple2(x._1 + "k", x._2 + "l");
            }
        }));
    }

    @Test
    public void testFlatMap(){
        assertEquals(map(tuple2(1, 4.2)), md.flatMap(new Fn1<Tuple2<Integer, Double>, Monad<Map, Tuple2<Integer, Double>>>() {
            @Override
            public Monad<Map, Tuple2<Integer, Double>> apply(final Tuple2<Integer, Double> x) {
                return map(tuple2(x._1, x._2 + 2.2));
            }
        }));
    }

    @Test
    public void testFlatMap1(){
        assertEquals(map(tuple2("1k", 2.0)), md.flatMap(new Fn1<Tuple2<Integer, Double>, Monad<Map, Tuple2<String, Double>>>() {
            @Override
            public Monad<Map, Tuple2<String, Double>> apply(final Tuple2<Integer, Double> x) {
                return map(tuple2(x._1 + "k", x._2));
            }
        }));
    }

    @Test
    public void testSplitAt(){
        assertEquals(tuple2(map(tuple2(1, "kleber")), map(tuple2(2, "eduardo"))), mi.splitAt(1));
    }

    @Test
    public void testSplitAt1(){
        final Tuple2<Map<Integer, String>, Map<Integer, String>> v = tuple2(map(tuple2(1, "kleber")), map(tuple2(2, "eduardo"), tuple2(3, "scalise")));
        assertEquals(v, mi.cons(tuple2(3, "scalise")).splitAt(1));
    }

    @Test
    public void testSplitAt2(){
        final Tuple2<Map<Integer, String>, Map<Integer, String>> v = tuple2(map(tuple2(1, "kleber"), tuple2(2, "eduardo")), map(tuple2(3, "scalise")));
        assertEquals(v, mi.cons(tuple2(3, "scalise")).splitAt(2));
    }
}

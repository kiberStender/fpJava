package br.fpJava.mapTest;

import br.fpJava.collections.map.Map;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.tuple.Pair;
import br.fpJava.typeclasses.Monad;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static br.fpJava.collections.map.Map.map;
import static br.fpJava.tuple.Pair.of;

/**
 * Created by sirkleber on 15/09/14.
 */
public class MapTest {
    private final Map<Integer, String> mi = map(of(1, "kleber"), of(2, "eduardo"));
    private final Map<Integer, Double> md = map(of(1, 2.0));

    @Test
    public void testConstructor(){
        assertEquals(of(1, "Kleber"), map(of(2, "eduardo"), of(1, "Kleber")).head());
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
        assertEquals(map(of(1, 2.0), of(2, 3.2)), md.cons(of(2, 3.2)));
    }

    @Test
    public void testCons1(){
        assertEquals(map(of(1, 2.0), of(2, 3.2), of(3, 1.5)), md.cons(of(3, 1.5)).cons(of(2, 3.2)));
    }

    @Test
    public void testCons2() {
        assertEquals(map(of(1, 2.0), of(2, 3.2), of(3, 1.5)), md.cons(of(3, 1.7)).cons(of(2, 3.2)).cons(of(3, 1.5)));
    }

    @Test
    public void testConcat(){
        assertEquals(map(of(1, 2.0), of(2, 3.1)), md.concat(map(of(2, 3.1))));
    }

    @Test
    public void testConcat1(){
        assertEquals(map(of(1, 2.0), of(2, 3.1), of(3, 2.5)), md.concat(map(of(3, 2.5), of(2, 3.1))));
    }

    @Test
    public void testHead(){
        assertEquals(of(1, 2.0), md.head());
    }

    @Test
    public void testTail(){
        assertEquals(map(of(2, "eduardo")), mi.tail());
    }

    @Test
    public void testInit(){
        assertEquals(map(of(1, "kleber")), mi.init());
    }

    @Test
    public void testLast(){
        assertEquals(of(2, "eduardo"), mi.last());
    }

    @Test
    public void testFind() throws Exception{
        assertEquals(Just.just(of(2, "eduardo")), mi.find(new Fn1<Pair<Integer, String>, Boolean>() {
            @Override
            public Boolean apply(Pair<Integer, String> x) {
                return x.first.equals(2);
            }
        }));
    }

    @Test
    public void testGet() throws Exception{
        assertEquals(Just.just("eduardo"), mi.get(2));
    }

    @Test
    public void testFoldLeft(){
        assertTrue(mi.foldLeft(0, new Fn1<Integer, Fn1<Pair<Integer, String>, Integer>>() {
            @Override
            public Fn1<Pair<Integer, String>, Integer> apply(final Integer acc) {
                return new Fn1<Pair<Integer, String>, Integer>() {
                    @Override
                    public Integer apply(final Pair<Integer, String> item) {
                        return acc - item.first;
                    }
                };
            }
        }).equals(-3));
    }

    @Test
    public void testFoldRight(){
        assertTrue(mi.foldRight(0, new Fn1<Pair<Integer, String>, Fn1<Integer, Integer>>() {
            @Override
            public Fn1<Integer, Integer> apply(final Pair<Integer, String> item) {
                return new Fn1<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer acc) {
                        return acc - item.first;
                    }
                };
            }
        }).equals(-3));
    }

    @Test
    public void testMap(){
        assertEquals(map(of(1, 4.0)), md.map(new Fn1<Pair<Integer, Double>, Pair<Integer, Double>>() {
            @Override
            public Pair<Integer, Double> apply(Pair<Integer, Double> x) {
                return of(x.first, x.second * 2);
            }
        }));
    }

    @Test
    public void testMap1(){
        assertEquals(map(of("1k", "kleberl"), of("2k", "eduardol")), mi.map(new Fn1<Pair<Integer, String>, Pair<String, String>>() {
            @Override
            public Pair<String, String> apply(Pair<Integer, String> x) {
                return of(x.first + "k", x.second + "l");
            }
        }));
    }

    @Test
    public void testFlatMap(){
        assertEquals(map(of(1, 4.2)), md.flatMap(new Fn1<Pair<Integer, Double>, Monad<Map, Pair<Integer, Double>>>() {
            @Override
            public Monad<Map, Pair<Integer, Double>> apply(final Pair<Integer, Double> x) {
                return map(of(x.first, x.second + 2.2));
            }
        }));
    }

    @Test
    public void testFlatMap1(){
        assertEquals(map(of("1k", 2.0)), md.flatMap(new Fn1<Pair<Integer, Double>, Monad<Map, Pair<String, Double>>>() {
            @Override
            public Monad<Map, Pair<String, Double>> apply(final Pair<Integer, Double> x) {
                return map(of(x.first + "k", x.second));
            }
        }));
    }

    @Test
    public void testSplitAt(){
        assertEquals(of(map(of(1, "kleber")), map(of(2, "eduardo"))), mi.splitAt(1));
    }

    @Test
    public void testSplitAt1(){
        final Pair<Map<Integer, String>, Map<Integer, String>> v = of(map(of(1, "kleber")), map(of(2, "eduardo"), of(3, "scalise")));
        assertEquals(v, mi.cons(of(3, "scalise")).splitAt(1));
    }

    @Test
    public void testSplitAt2(){
        final Pair<Map<Integer, String>, Map<Integer, String>> v = of(map(of(1, "kleber"), of(2, "eduardo")), map(of(3, "scalise")));
        assertEquals(v, mi.cons(of(3, "scalise")).splitAt(2));
    }
}

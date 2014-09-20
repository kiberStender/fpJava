package br.fpJava.seqTest;

import br.fpJava.collections.seq.Seq;
import br.fpJava.collections.seq.Cons;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import br.fpJava.typeclasses.Monad;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static br.fpJava.collections.seq.Seq.Seq;
import static br.fpJava.collections.seq.Nil.Nil;
import static br.fpJava.tuple.Tuple2.tuple2;

/**
 * Created by sirkleber on 09/09/14.
 */
public class SeqTest {
    private Seq<Integer> s = Seq(1, 2, 3);
    private Seq<Integer> seqi = new Cons<>(1, new Cons<>(2, new Cons<>(3, (Seq<Integer>) Nil())));
    private Seq<String> seqs = Seq("Joao", "Luiz");
    private Fn1<Integer, Boolean> filter = new Fn1<Integer, Boolean>() {
        @Override
        public Boolean apply(Integer x) {
            return x.equals(2);
        }
    };

    @Test
    public void tesEquality(){
        assertEquals(seqi, seqi);
    }

    @Test
    public void testEquality1(){
        assertEquals(s, s);
    }

    @Test
    public void testEquality2(){
        assertEquals(s, seqi);
    }

    @Test
    public void testEquality3(){
        assertEquals(seqi, s);
    }

    @Test
    public void testEquality4(){
        assertTrue(!seqi.equals(seqs));
    }

    @Test
    public void testEquality5(){
        assertNotEquals(seqs, s);
    }

    @Test
    public void testToString(){
        assertEquals("Seq(1, 2, 3)", seqi.toString());
    }

    @Test
    public void testToString1(){
        assertEquals("Seq(1, 2, 3)", s.toString());
    }

    @Test
    public void testToString2(){
        assertEquals("Seq(Joao, Luiz)", seqs.toString());
    }

    @Test
    public void testLength(){
        assertTrue(seqi.length().equals(3));
    }

    @Test
    public void testLength1(){
        assertTrue(s.length().equals(3));
    }

    @Test
    public void testLength2(){
        assertTrue(seqs.length().equals(2));
    }

    @Test
    public void testCons(){
        assertEquals(Seq(0, 1, 2, 3), seqi.cons(0));
    }

    @Test
    public void testConcat(){
        assertEquals(Seq(1, 2, 3, 1, 2, 3), seqi.concat(s));
    }

    @Test
    public void testReverse(){
        assertEquals(Seq(3, 2, 1), seqi.reverse());
    }

    @Test
    public void testFoldLeft(){
        assertTrue(seqi.foldLeft(0, new Fn1<Integer, Fn1<Integer, Integer>>() {
            @Override
            public Fn1<Integer, Integer> apply(final Integer acc) {
                return new Fn1<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer item) {
                        return acc - item;
                    }
                };
            }
        }).equals(-6));
    }

    @Test
    public void testFoldRight(){
        assertTrue(seqi.foldRight(0, new Fn1<Integer, Fn1<Integer, Integer>>() {
            @Override
            public Fn1<Integer, Integer> apply(final Integer item) {
                return new Fn1<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer acc) {
                        return item - acc;
                    }
                };
            }
        }).equals(2));
    }

    @Test
    public void testInit(){
        assertEquals(Seq(1, 2), seqi.init());
    }

    @Test
    public void testLast(){
        assertTrue(seqi.last().equals(3));
    }

    @Test
    public void testMaybeLast() throws Exception{
        assertTrue(seqi.maybeLast().get().equals(3));
    }

    @Test
    public void testFilter(){
        assertEquals(Seq(2), seqi.filter(filter));
    }

    @Test
    public void testFilterNot(){
        assertEquals(Seq(1, 3), seqi.filterNot(filter));
    }

    @Test
    public void testPartition(){
        assertEquals(tuple2(Seq(2), Seq(1, 3)), seqi.partition(filter));
    }

    @Test
    public void testFind() throws Exception{
        assertTrue(seqi.find(new Fn1<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer a) {
                return a.equals(2);
            }
        }).get().equals(2));
    }

    @Test
    public void testMap(){
        assertEquals(Seq(2, 4, 6), seqi.map(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * 2;
            }
        }));
    }

    @Test
    public void testFlatMap(){
        assertEquals(Seq(2, 3, 4), seqi.flatMap(new Fn1<Integer, Monad<Seq, Integer>>() {
            @Override
            public Monad<Seq, Integer> apply(Integer x) {
                return Seq(1 + x);
            }
        }));
    }

    @Test
    public void testFindWithMonad() throws Exception{
        final Seq<Integer> nums = Seq(1, 2, 3, 4, 5);
        final Fn1<Integer, Fn1<Integer, Boolean>> find = new Fn1<Integer, Fn1<Integer, Boolean>>() {
            @Override
            public Fn1<Integer, Boolean> apply(final Integer x) {
                return new Fn1<Integer, Boolean>() {
                    @Override
                    public Boolean apply(final Integer y) {
                        return y.equals(x);
                    }
                };
            }
        };
        final Fn1<Integer, Boolean> find1 = find.apply(1);
        final Fn1<Integer, Boolean> find2 = find.apply(2);
        final Fn1<Integer, Boolean> gt4 = new Fn1<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer x) {
                return x > 4;
            }
        };

        final Maybe<Integer> res = nums.find(find1).flatMap(new Fn1<Integer, Monad<Maybe, Integer>>() {
            @Override
            public Monad<Maybe, Integer> apply(final Integer um) {
                return nums.find(find2).flatMap(new Fn1<Integer, Monad<Maybe, Integer>>() {
                    @Override
                    public Monad<Maybe, Integer> apply(final Integer dois) {
                        return nums.find(gt4).map(new Fn1<Integer, Integer>() {
                            @Override
                            public Integer apply(final Integer mq4) {
                                return um + dois + mq4;
                            }
                        });
                    }
                });
            }
        });

        assertEquals(new Just<Integer>(8), res);
    }

    @Test
    public void testSplit(){
        assertEquals(tuple2(Seq(1, 2), Seq(3)), seqi.splitAt(2));
    }
}

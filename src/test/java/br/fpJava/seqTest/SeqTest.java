package br.fpJava.seqTest;

import br.fpJava.collections.seq.Seq;
import br.fpJava.collections.seq.Cons;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Maybe;
import br.fpJava.typeclasses.Monad;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
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
        assertTrue(seqi.equals(seqi));
    }

    @Test
    public void testEquality1(){
        assertTrue(s.equals(s));
    }

    @Test
    public void testEquality2(){
        assertTrue(seqi.equals(s));
    }

    @Test
    public void testEquality3(){
        assertTrue(s.equals(seqi));
    }

    @Test
    public void testEquality4(){
        assertTrue(!seqi.equals(seqs));
    }

    @Test
    public void testEquality5(){
        assertTrue(!s.equals(seqs));
    }

    @Test
    public void testToString(){
        assertTrue(seqi.toString().equals("Seq(1, 2, 3)"));
    }

    @Test
    public void testToString1(){
        assertTrue(s.toString().equals("Seq(1, 2, 3)"));
    }

    @Test
    public void testToString2(){
        assertTrue(seqs.toString().equals("Seq(Joao, Luiz)"));
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
        assertTrue(seqi.cons(0).equals(Seq(0, 1, 2, 3)));
    }

    @Test
    public void testConcat(){
        assertTrue(seqi.concat(s).equals(Seq(1, 2, 3, 1, 2, 3)));
    }

    @Test
    public void testReverse(){
        assertTrue(seqi.reverse().equals(Seq(3, 2, 1)));
    }

    @Test
    public void testInit(){
        assertTrue(seqi.init().equals(Seq(1, 2)));
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
        assertTrue(seqi.filter(filter).equals(Seq(2)));
    }

    @Test
    public void testFilterNot(){
        assertTrue(seqi.filterNot(filter).equals(Seq(1, 3)));
    }

    @Test
    public void testPartition(){
        assertTrue(seqi.partition(filter).equals(tuple2(Seq(2), Seq(1, 3))));
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
        assertTrue(seqi.map(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * 2;
            }
        }).equals(Seq(2, 4, 6)));
    }

    @Test
    public void testFlatMap(){
        assertTrue(seqi.flatMap(new Fn1<Integer, Monad<Seq, Integer>>() {
            @Override
            public Monad<Seq, Integer> apply(Integer x) {
                return Seq(1 + x);
            }
        }).equals(Seq(2, 3, 4)));
    }

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

        assertTrue(res.get().equals(8));
    }

    @Test
    public void testSplit(){
        assertTrue(seqi.splitAt(2).equals(tuple2(Seq(1, 2), Seq(3))));
    }
}

package br.fpJava.seqTest;

import br.fpJava.collections.seq.Seq;
import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;
import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static br.fpJava.collections.seq.Seq.seq;

/**
 * Created by sirkleber on 09/09/14.
 */
public class SeqTest {
    @Test
    public void testSeqLength(){
        Seq<Integer> seqi = seq(1, 2, 3);

        assertTrue(seqi.length().equals(3));
    }

    @Test
    public void testSeqCons(){
        Seq<Integer> seqi = seq(1).cons(2).cons(3).cons(4);

        assertTrue(seqi.length().equals(4));

    }

    @Test
    public void testConcat(){
        Seq<Integer> seqi = seq(1, 2, 3, 4);
        Seq<Integer> seqi2 = seq(5, 6, 7);

        assertTrue(seqi.concat(seqi2).equals(seq(1, 2, 3, 4, 5, 6, 7)));
    }

    @Test
    public void testMap(){
        assertTrue(seq(1, 2, 3).map(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * 2;
            }
        }).equals(seq(2, 4, 6)));
    }

    @Test
    public void testFlatMap(){
        assertTrue(seq(1, 2, 3).flatMap(new Fn1<Integer, Monad<Seq, Integer>>() {
            @Override
            public Monad<Seq, Integer> apply(Integer x) {
                return seq(x + 1);
            }
        }).equals(seq(2, 3, 4)));
    }
}

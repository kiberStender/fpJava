package br.fpJava.seqTest;

import br.fpJava.collections.seq.Seq;
import br.fpJava.collections.seq.Cons;
import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static br.fpJava.collections.seq.Seq.seq;
import static br.fpJava.collections.seq.Nil.nil;

/**
 * Created by sirkleber on 09/09/14.
 */
public class SeqTest {
    private Seq<Integer> s = seq(1, 2, 3);
    private Seq<Integer> seqi = new Cons<>(1, new Cons<>(2, new Cons<>(3, (Seq<Integer>) nil())));
    private Seq<String> seqs = seq("Joao", "Luiz");

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
        assertTrue(seqi.cons(0).equals(seq(0, 1, 2, 3)));
    }

    @Test
    public void testConcat(){
        assertTrue(seqi.concat(s).equals(seq(1, 2, 3, 1, 2, 3)));
    }
}

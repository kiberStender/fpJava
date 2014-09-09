package br.fpJava.seqTest;

import br.fpJava.collections.seq.Seq;
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

        System.out.println(seqi.concat(seqi2));

        assertTrue(seqi.concat(seqi2).equals(seq(1, 2, 3, 4, 5, 6, 7)));
    }
}

package br.fpJava.seqTest;

import br.fpJava.collections.seq.Seq;
import br.fpJava.typeclasses.Monad;
import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static br.fpJava.collections.seq.Seq.seq;

/**
 * Created by sirkleber on 09/09/14.
 */
public class SeqTest {

    public void testSeqLength(){
        Seq<Integer> seqi = seq(1, 2, 3);

        assertTrue(seqi.lenght().equals(3));
    }

    @Test
    public void testSeqCons(){
        Seq<Integer> seqi = seq(1).cons(2).cons(3).cons(4);

        assertTrue(seqi.lenght().equals(4));

    }
}

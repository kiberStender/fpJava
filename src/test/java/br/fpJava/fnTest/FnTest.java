package br.fpJava.fnTest;

import br.fpJava.fn.Fn;
import static junit.framework.Assert.assertTrue;

import br.fpJava.fn.Fn1;
import org.junit.Test;

/**
 * Created by sirkleber on 07/09/14.
 */

public class FnTest {
    @Test
    public void testFn(){
        Fn<Integer> one = new Fn<Integer>() {
            @Override
            public Integer apply() {
                return 1;
            }
        };

        assertTrue(one.apply() == 1);
    }

    @Test
    public void testFn1(){
        Fn1<Integer, Integer> a = new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(final Integer integer) {
                return 1 + integer;
            }
        };

        assertTrue(a.apply(1) == 2);
    }

    @Test
    public void testFunctionalSum(){
        Fn1<Integer, Fn1<Integer, Integer>> sum = new Fn1<Integer, Fn1<Integer, Integer>>() {
            @Override
            public Fn1<Integer, Integer> apply(final Integer a) {
                return new Fn1<Integer, Integer>() {
                    @Override
                    public Integer apply(final Integer b) {
                        return a + b;
                    }
                };
            }
        };

        assertTrue(sum.apply(1).apply(2) == 3);
    }

}

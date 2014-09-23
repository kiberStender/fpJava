package br.fpJava.tryTest;

import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.fptry.Success;
import br.fpJava.fptry.Try;
import br.fpJava.typeclasses.Monad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static br.fpJava.fptry.Try.Try;

/**
 * Created by sirkleber on 23/09/14.
 */
public class TryTest {

    @Test
    public void testEquality(){
        assertEquals(new Success<Integer>(2), Try(new Fn<Object>() {
            @Override
            public Object apply() {
                return Integer.parseInt("2");
            }
        }));
    }

    @Test
    public void testMap(){
        assertEquals(new Success<Integer>(8), Try(new Fn<Integer>() {
            @Override
            public Integer apply() {
                return Integer.parseInt("4");
            }
        }).map(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * x;
            }
        }).map(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x / 2;
            }
        }));
    }

    @Test
    public void testFlatMap(){
        assertEquals(new Success<Integer>(20), Try(new Fn<Integer>() {
            @Override
            public Integer apply() {
                return Integer.parseInt("5");
            }
        }).flatMap(new Fn1<Integer, Monad<Try, Integer>>() {
            @Override
            public Monad<Try, Integer> apply(final Integer five) {
                return Try(new Fn<Integer>() {
                    @Override
                    public Integer apply() {
                        return Integer.parseInt("6");
                    }
                }).flatMap(new Fn1<Integer, Monad<Try, Integer>>() {
                    @Override
                    public Monad<Try, Integer> apply(final Integer six) {
                        return Try(new Fn<Integer>() {
                            @Override
                            public Integer apply() {
                                return Integer.parseInt("9");
                            }
                        }).map(new Fn1<Integer, Integer>() {
                            @Override
                            public Integer apply(Integer nine) {
                                return five + six + nine;
                            }
                        });
                    }
                });
            }
        }));
    }

}

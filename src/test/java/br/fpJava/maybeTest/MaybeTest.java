package br.fpJava.maybeTest;

import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import static br.fpJava.maybe.Nothing.nothing;

import br.fpJava.typeclasses.Monad;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.Assert.assertTrue;

/**
 * Created by sirkleber on 07/09/14.
 */

public class MaybeTest {

    private final Fn1<Double, Fn1<Double, Maybe<Double>>> div = new Fn1<Double, Fn1<Double, Maybe<Double>>>() {
        @Override
        public Fn1<Double, Maybe<Double>> apply(final Double a) {
            return new Fn1<Double, Maybe<Double>>() {
                @Override
                public Maybe<Double> apply(final Double b) {
                    if(b == 0) {
                        return (Maybe<Double>) nothing();
                    } else {
                        return new Just<>(a / b);
                    }
                }
            };
        }
    };

    @Test
    public void testEquality(){
        assertTrue(div.apply(2.0).apply(2.0).equals(new Just<Double>(1.0)));
    }

    @Test
    public void testEquality1(){
        assertTrue(div.apply(2.0).apply(0.0).equals(nothing()));
    }

    @Test
    public void testMap() throws NoSuchElementException{

        Maybe<Double> mdb = div.apply(2.0).apply(2.0).map(new Fn1<Double, Double>() {
            @Override
            public Double apply(final Double x) {
                return x * 3;
            }
        });

        assertTrue(mdb.get().equals(3.0));
    }

    @Test
    public void testFlatMap() throws NoSuchElementException{
        Maybe<Double> mbd = div.apply(4.0).apply(2.0).flatMap(new Fn1<Double, Monad<Maybe, Double>>() {
            @Override
            public Monad<Maybe, Double> apply(Double aDouble) {
                return new Just<Double>(aDouble * 2.0);
            }
        });

        assertTrue(mbd.get().equals(4.0));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGet(){
        Maybe<Double> mdb = div.apply(4.0).apply(0.0);

        assertTrue(mdb.get() > 0);
    }

    @Test
    public void testGetOrElse(){
        Maybe<Double> mdb = div.apply(4.0).apply(2.0);

        assertTrue(mdb.getOrElse(new Fn<Object>() {
            @Override
            public Object apply() {
                return "Error";
            }
        }).equals(2.0));
    }

}

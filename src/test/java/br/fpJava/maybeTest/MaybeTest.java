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

    private final Fn1<Double, Fn1<Double, Maybe<Double>>> div = (a) -> (b) -> {
        if(b == 0) {
            return (Maybe<Double>) nothing();
        } else {
            return Just.just(a / b);
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
        assertTrue(div.apply(2.0).apply(2.0).map( x -> x * 3).get().equals(3.0));
    }

    @Test
    public void testFlatMap() throws NoSuchElementException {
        assertTrue(div.apply(4.0).apply(2.0).flatMap((x) -> Just.just(x * 2.0)).get().equals(4.0));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGet(){
        assertTrue(div.apply(4.0).apply(0.0).get() > 0);
    }

    @Test
    public void testGetOrElse() {
        assertTrue(div.apply(4.0).apply(2.0).getOrElse(() -> "Error").equals(2.0));
    }

}

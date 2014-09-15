package br.fpJava.collections.seq;

import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Maybe;

import static br.fpJava.maybe.Nothing.Nothing;

import java.util.NoSuchElementException;

/**
 * Created by sirkleber on 09/09/14.
 */
public class Nil extends Seq<Object>{
    private static Nil ourInstance = null;

    public static Seq<?> Nil() {
        if(ourInstance == null){
            ourInstance = new Nil();
        }
        return ourInstance;
    }

    private Nil() {}

    @Override
    public Boolean isEmpty() {
        return true;
    }

    @Override
    public Object head() throws NoSuchElementException{
        throw new NoSuchElementException();
    }

    @Override
    public Seq<Object> tail() throws NoSuchElementException{
        throw new NoSuchElementException();
    }

    @Override
    public Seq<Object> init() throws NoSuchElementException{
        throw new NoSuchElementException();
    }

    @Override
    public Object last() throws NoSuchElementException{
        throw new NoSuchElementException();
    }

    @Override
    public Maybe<Object> maybeHead() {
        return (Maybe<Object>) Nothing();
    }

    @Override
    public Maybe<Object> maybeLast() {
        return (Maybe<Object>) Nothing();
    }

    @Override
    public boolean equals(Object x) {
        return x instanceof Nil;
    }
}

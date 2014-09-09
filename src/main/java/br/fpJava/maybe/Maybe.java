package br.fpJava.maybe;

import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;

import java.util.NoSuchElementException;

import static br.fpJava.maybe.Nothing.nothing;

/**
 * Created by sirkleber on 07/09/14.
 */

public abstract class Maybe<A> implements Monad<Maybe, A>{

    public <B> Maybe<B> map(Fn1<A, B> f){
        if(this instanceof Nothing){
            return (Maybe<B>) nothing();
        } else {
            Just<A> j = (Just<A>) this;
            return new Just<>(f.apply(j.a));
        }
    }

    public <B> Maybe<B> flatMap(Fn1<A, Monad<Maybe, B>> f){
        if(this instanceof Nothing){
            return (Maybe<B>) nothing();
        } else {
            Just<A> j = (Just<A>) this;
            return (Just<B>) f.apply(j.a);
        }
    }

    public abstract Object getOrElse(Fn<Object> v);

    public abstract A get() throws NoSuchElementException;
}
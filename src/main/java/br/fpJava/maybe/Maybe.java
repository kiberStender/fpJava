package br.fpJava.maybe;

import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;

import java.util.NoSuchElementException;

import static br.fpJava.maybe.Nothing.nothing;

/**
 * Created by sirkleber on 07/09/14.
 */

public abstract class Maybe<A> extends Monad<Maybe, A>{

    public <B> Maybe<B> map(Fn1<A, B> f){
        if(this instanceof Nothing){
            return (Maybe<B>) nothing();
        } else {
            return Just.just(f.apply(get()));
        }
    }

    public <B> Maybe<B> flatMap(Fn1<A, Monad<Maybe, B>> f){
        if(this instanceof Nothing){
            return (Maybe<B>) nothing();
        } else {
            return (Just<B>) f.apply(get());
        }
    }

    public abstract Object getOrElse(Fn<Object> v);

    public abstract A get() throws NoSuchElementException;
}

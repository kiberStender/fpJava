package br.fpJava.typeclasses;

import br.fpJava.fn.Fn1;
import br.fpJava.utils.Unit;

import static br.fpJava.utils.Unit.unit;

/**
 * Created by sirkleber on 07/09/14.
 */

public abstract class Monad<M, A> implements Functor<M, A>{

    /**
     * Haskell >> . Does the same as >>= (flatMap/ bind) but ignores the
     * anonymous function return value
     * @param f Function to be applied to 'loop'
     * @param <B> anonymous function return type
     * @return Unit
     */
    public <B> Unit foreach(Fn1<A, B> f){
        //TODO-- Improve this using a functional way
        map(f);
        return unit();
    }

    public abstract <B> Monad<M, B> map(Fn1<A, B> f);

    /**
     * Haskell >>= (a.k.a bind) Receive a function to apply to a container and return
     * an object of the same type
     * @param f Function to be applied
     * @param <B> Type of the new Monad Container
     * @return Monad<B>
     */
    public abstract <B> Monad<M, B> flatMap(Fn1<A, Monad<M, B>> f);
}

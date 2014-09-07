package br.fpJava.typeclasses;

import br.fpJava.fn.Fn1;

/**
 * Created by sirkleber on 07/09/14.
 */

public interface Monad<M, A> extends Functor<M, A>{
    <B> Monad<M, B> map(Fn1<A, B> f);

    <B> Monad<M, B> flatMap(Fn1<A, Monad<M, B>> f);
}

package br.fpJava.fnwrappers;

import br.fpJava.fn.Fn1;

public interface WrappingMonad<A> extends WrappingFunctor<A>{

    /**
     * Maps Monad of A to Monad of B by unwrapping the returned Monad from the execution.
     * @param execution
     * @return
     * @param <B>
     */
    <B> WrappingMonad<B> flatMap(Fn1<A, WrappingMonad<B>> execution);

    <B> WrappingMonad<B> map(Fn1<A, B> transformer);
}

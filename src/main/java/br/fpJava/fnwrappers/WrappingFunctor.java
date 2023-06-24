package br.fpJava.fnwrappers;

import br.fpJava.fn.Fn1;

import java.util.function.Supplier;

public interface WrappingFunctor<A> extends Supplier<A> {

    /**
     * Maps category A to category B (Functor of A to Functor of B)
     * @param transformer
     * @return
     * @param <B>
     */
    <B> WrappingFunctor<B> map(Fn1<A, B> transformer);
}

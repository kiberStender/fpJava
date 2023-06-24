package br.fpJava.fnwrappers;

import br.fpJava.fn.Fn1;

import java.util.Objects;

public class Monadify<A> implements WrappingMonad<A> {

    private final A element;

    private Monadify(final A element) {
        this.element = element;
    }

    public static <A> Monadify<A> of(final A element) {
        return new Monadify<>(element);
    }

    @Override
    public <B> Monadify<B> flatMap(Fn1<A, WrappingMonad<B>> execution) {
        try {
            if (Objects.nonNull(element)) {
                return (Monadify<B>) execution.apply(element);
            } else {
                return (Monadify<B>) new Monadify<>(element);
            }
        } catch (Exception e) {
            return (Monadify<B>) new Monadify<>(element);
        }
    }

    @Override
    public <B> Monadify<B> map(Fn1<A, B> transformer) {
        return new Monadify<>(transformer.apply(element));
    }

    @Override
    public A get() {
        return element;
    }
}

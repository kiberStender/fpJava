package br.fpJava.collections;

import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;

/**
 * Created by sirkleber on 09/09/14.
 */
public abstract class Iterable<I, A> extends Traversable<I, A> {
    public abstract <B> B foldLeft(final B acc, final Fn1<B, Fn1<A, B>> f);

    public abstract <B> B foldRight(final B acc, final Fn1<A, Fn1<B, B>> f);
}

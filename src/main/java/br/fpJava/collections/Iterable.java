package br.fpJava.collections;

import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Maybe;
import br.fpJava.tuple.Tuple2;

import java.util.NoSuchElementException;

import static br.fpJava.tuple.Tuple2.tuple2;

/**
 * Created by sirkleber on 09/09/14.
 */

@Deprecated
public abstract class Iterable<I, A> extends Traversable<I, A> {
    public abstract A head() throws NoSuchElementException;

    public abstract Iterable<I, A> tail() throws NoSuchElementException;

    public abstract Iterable<I, A> init() throws NoSuchElementException;

    public abstract A last() throws NoSuchElementException;

    public abstract Maybe<A> maybeHead();

    public abstract Maybe<A> maybeLast();

    public abstract Iterable<I, A> filter(final Fn1<A, Boolean> p);

    public Iterable<I, A> filterNot(final Fn1<A, Boolean> p){
        return filter(new Fn1<A, Boolean>() {
            @Override
            public Boolean apply(A a) {
                return !p.apply(a);
            }
        });
    }

    public final Tuple2<Iterable<I, A>, Iterable<I, A>> partition(final Fn1<A, Boolean> p){
        return tuple2(filter(p), filterNot(p));
    }

    public abstract Maybe<A> find(final Fn1<A, Boolean> p);

    public abstract Tuple2<Iterable<I, A>, Iterable<I, A>> splitAt(final Integer n);

    public abstract <B> B foldLeft(final B acc, final Fn1<B, Fn1<A, B>> f);

    public abstract <B> B foldRight(final B acc, final Fn1<A, Fn1<B, B>> f);
}
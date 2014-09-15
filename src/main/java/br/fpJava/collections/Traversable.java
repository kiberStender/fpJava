package br.fpJava.collections;

import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import br.fpJava.maybe.Nothing;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Monad;
import br.fpJava.utils.Unit;

import java.util.NoSuchElementException;

import static br.fpJava.tuple.Tuple2.tuple2;
import static br.fpJava.utils.Unit.unit;

/**
 * Created by sirkleber on 09/09/14.
 */

public abstract class Traversable<T, A> extends Monad<T, A>{
    public abstract Boolean isEmpty();

    public abstract A head() throws NoSuchElementException;

    public abstract <I extends Traversable<T, A>> I tail() throws NoSuchElementException;

    public abstract <I extends Traversable<T, A>> I init() throws NoSuchElementException;

    public abstract A last() throws NoSuchElementException;

    public abstract Maybe<A> maybeHead();

    public abstract Maybe<A> maybeLast();

    public abstract Traversable<T, A> filter(final Fn1<A, Boolean> p);

    public Traversable<T, A> filterNot(final Fn1<A, Boolean> p){
        return filter(new Fn1<A, Boolean>() {
            @Override
            public Boolean apply(A a) {
                return !p.apply(a);
            }
        });
    }

    public final <I extends Traversable<T, A>> Tuple2<I, I> partition(final Fn1<A, Boolean> p){
        return tuple2((I) filter(p), (I) filterNot(p));
    }

    public Maybe<A> find(final Fn1<A, Boolean> p){
        if(isEmpty()){
            return (Maybe<A>) Nothing.Nothing();
        } else {
            if(p.apply(head())){
                return new Just<>(head());
            } else {
                return tail().find(p);
            }
        }
    }

    public abstract <I extends Traversable<T, A>> Tuple2<I, I> splitAt(final Integer n);

    public <B> B foldLeft(final B acc, final Fn1<B, Fn1<A, B>> f){
        if(isEmpty()){
            return acc;
        } else {
            return tail().foldLeft(f.apply(acc).apply(head()), f);
        }
    }

    public abstract <B> B foldRight(final B acc, final Fn1<A, Fn1<B, B>> f);
}

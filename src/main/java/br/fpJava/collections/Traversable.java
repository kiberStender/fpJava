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

    protected abstract <I extends Traversable<T, A>> I empty();

    /**
     * Scala :: and Haskell : functions
     * @param item the item to be appended to the collection
     * @return a new collection
     */
    public abstract Traversable<T, A> cons(final A item);

    /**
     * Scala and Haskell ++ function
     * @param prefix new collection to be concat in the end of this collection
     * @return a new collection
     */
    public abstract Traversable<T, A> concat(final Traversable<T, A> prefix);

    public final String toString(){
        return prefix() + "(" + foldLeft("", new Fn1<String, Fn1<A, String>>() {
            @Override
            public Fn1<A, String> apply(final String acc) {
                return new Fn1<A, String>() {
                    @Override
                    public String apply(A item) {
                        return toStringFrmt(acc, item);
                    }
                };
            }
        }) + ")";
    }

    protected abstract String prefix();

    protected abstract String toStringFrmt(final String acc, final A item);

    public Integer length(){
        return foldLeft(0, new Fn1<Integer, Fn1<A, Integer>>() {
            @Override
            public Fn1<A, Integer> apply(final Integer acc) {
                return new Fn1<A, Integer>() {
                    @Override
                    public Integer apply(A a) {
                        return acc + 1;
                    }
                };
            }
        });
    }

    public Traversable<T, A> filter(final Fn1<A, Boolean> p){
        return foldRight((Traversable<T, A>) empty(), new Fn1<A, Fn1<Traversable<T, A>, Traversable<T, A>>>() {
            @Override
            public Fn1<Traversable<T, A>, Traversable<T, A>> apply(final A item) {
                return new Fn1<Traversable<T, A>, Traversable<T, A>>() {
                    @Override
                    public Traversable<T, A> apply(final Traversable<T, A> acc) {
                        if(p.apply(item)){
                            return acc.cons(item);
                        } else {
                            return acc;
                        }
                    }
                };
            }
        });
    }

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

    public <B> B foldRight(final B acc, final Fn1<A, Fn1<B, B>> f){
        if(isEmpty()){
            return acc;
        } else {
            return f.apply(head()).apply(tail().foldRight(acc, f));
        }
    }

    @Override
    public <B> Traversable<T, B> map(final Fn1<A, B> f) {
        return foldRight((Traversable<T, B>) empty(), new Fn1<A, Fn1<Traversable<T, B>, Traversable<T, B>>>() {
            @Override
            public Fn1<Traversable<T, B>, Traversable<T, B>> apply(final A item) {
                return new Fn1<Traversable<T, B>, Traversable<T, B>>() {
                    @Override
                    public Traversable<T, B> apply(final Traversable<T, B> acc) {
                        return acc.cons(f.apply(item));
                    }
                };
            }
        });
    }

    @Override
    public <B> Traversable<T, B> flatMap(Fn1<A, Monad<T, B>> f) {
        if(isEmpty()){
            return (Traversable<T, B>) empty();
        } else {
            return tail().flatMap(f).concat((Traversable<T, B>) f.apply(head()));
        }
    }
}

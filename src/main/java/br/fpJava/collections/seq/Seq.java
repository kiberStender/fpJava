package br.fpJava.collections.seq;

import br.fpJava.collections.*;
import br.fpJava.fn.Fn1;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Monad;
import static br.fpJava.collections.seq.Nil.Nil;
import static br.fpJava.tuple.Tuple2.tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sirkleber on 09/09/14.
 */

public abstract class Seq<A> extends Traversable<Seq, A> {

    @Override
    protected Seq<A> empty() {
        return (Seq<A>) Nil();
    }

    public Seq<A> cons(A item){
        return new Cons<>(item, this);
    }

    private Fn1<Seq<A>, Seq<A>> helper(final Seq<A> acc){
        return new Fn1<Seq<A>, Seq<A>>() {
            @Override
            public Seq<A> apply(final Seq<A> other) {
                if(other instanceof Nil){
                    return acc;
                } else {
                    Cons<A> c = (Cons<A>) other;
                    return helper(acc.cons(c.head())).apply(c.tail());
                }
            }
        };
    }

    @Override
    public Seq<A> concat(Traversable<Seq, A> prefix) {
        return helper(this).apply(((Seq<A>) prefix).reverse());
    }

    public static final <A> Seq<A> Seq(final A... a){
        if(a.length == 0){
            return (Seq<A>) Nil();
        } else {
            List<A> l = Arrays.asList(a);
            return new Cons<A>(a[0], (Seq<A>) Seq(l.subList(1, a.length).toArray()));
        }
    }

    public String toString(){
        return "Seq(" + foldLeft("", new Fn1<String, Fn1<A, String>>() {
            @Override
            public Fn1<A, String> apply(final String acc) {
                return new Fn1<A, String>() {
                    @Override
                    public String apply(A item) {
                        if(acc.equals("")){
                            return "" + item;
                        } else {
                            return acc + ", " + item;
                        }
                    }
                };
            }
        }) + ")";
    }

    public Seq<A> reverse(){
        return foldLeft(((Seq<A>) Seq()), new Fn1<Seq<A>, Fn1<A, Seq<A>>>() {
            @Override
            public Fn1<A, Seq<A>> apply(final Seq<A> acc) {
                return new Fn1<A, Seq<A>>() {
                    @Override
                    public Seq<A> apply(A item) {
                        return new Cons<A>(item, acc);
                    }
                };
            }
        });
    }

    @Override
    public Seq<A> filter(final Fn1<A, Boolean> p) {
        return foldRight((Seq<A>) Seq(), new Fn1<A, Fn1<Seq<A>, Seq<A>>>(){
            @Override
            public Fn1<Seq<A>, Seq<A>> apply(final A item) {
                return new Fn1<Seq<A>, Seq<A>>() {
                    @Override
                    public Seq<A> apply(final Seq<A> acc) {
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

    private final Tuple2<Seq<A>, Seq<A>> splitR(Integer curN, Seq<A> curL, Seq<A> pre){
        if(curL instanceof Nil){
            return tuple2(pre.reverse(), (Seq<A>) Nil());
        } else {
            if(curN.equals(0)){
                return tuple2(pre.reverse(), (Seq<A>) curL);
            } else {
                Cons<A> c = (Cons<A>) curL;
                return splitR(curN - 1, c.tail(), pre.cons(c.head()));
            }
        }
    }

    @Override
    public Tuple2<Seq<A>, Seq<A>> splitAt(Integer n) {
        return splitR(n, this, (Seq<A>) Nil());
    }

    @Override
    public <B> Seq<B> flatMap(final Fn1<A, Monad<Seq, B>> f) {
        if(this instanceof Nil){
            return (Seq<B>) Nil();
        } else {
            Cons<A> c = (Cons<A>) this;
            return c.tail().flatMap(f).concat((Seq<B>) f.apply(c.head()));
        }
    }
}

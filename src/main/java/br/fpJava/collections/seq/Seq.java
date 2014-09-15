package br.fpJava.collections.seq;

import br.fpJava.collections.*;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Maybe;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Monad;
import static br.fpJava.collections.seq.Nil.nil;
import static br.fpJava.tuple.Tuple2.tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sirkleber on 09/09/14.
 */

public abstract class Seq<A> extends Traversable<Seq, A> {

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

    public Seq<A> concat(Seq<A> prefix){
        return helper(this).apply(prefix.reverse());
    }

    public static final <A> Seq<A> seq(final A... a){
        if(a.length == 0){
            return (Seq<A>) nil();
        } else {
            List<A> l = Arrays.asList(a);
            return new Cons<A>(a[0], (Seq<A>) seq(l.subList(1, a.length).toArray()));
        }
    }

    public Integer length(){
        return foldLeft(0, new Fn1<Integer, Fn1<A, Integer>>() {
            @Override
            public Fn1<A, Integer> apply( final Integer acc) {
                return new Fn1<A, Integer>() {
                    @Override
                    public Integer apply(A ig) {
                        return acc + 1;
                    }
                };
            }
        });
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
        return foldLeft(((Seq<A>) seq()), new Fn1<Seq<A>, Fn1<A, Seq<A>>>() {
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
        return foldRight((Seq<A>) seq(), new Fn1<A, Fn1<Seq<A>, Seq<A>>>(){
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
            return tuple2(pre.reverse(), (Seq<A>) nil());
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
        return splitR(n, this, (Seq<A>) nil());
    }

    @Override
    public <B> B foldLeft(final B acc, final Fn1<B, Fn1<A, B>> f) {
        if(this instanceof Nil){
            return acc;
        } else {
            Cons<A> c = (Cons<A>) this;
            return c.tail().foldLeft(f.apply(acc).apply(c.head()), f);
        }
    }

    @Override
    public <B> B foldRight(final B acc, final Fn1<A, Fn1<B, B>> f) {
        return reverse().foldLeft(acc, new Fn1<B, Fn1<A, B>>() {
            @Override
            public Fn1<A, B> apply(final B accu) {
                return new Fn1<A, B>() {
                    @Override
                    public B apply(A item) {
                        return f.apply(item).apply(accu);
                    }
                };
            }
        });
    }

    @Override
    public <B> Seq<B> map(final Fn1<A, B> f) {
        return foldRight((Seq<B>) seq(), new Fn1<A, Fn1<Seq<B>, Seq<B>>>() {
            @Override
            public Fn1<Seq<B>, Seq<B>> apply(final A item) {
                return new Fn1<Seq<B>, Seq<B>>() {
                    @Override
                    public Seq<B> apply(Seq<B> acc) {
                        return acc.cons(f.apply(item));
                    }
                };
            }
        });
    }

    @Override
    public <B> Seq<B> flatMap(final Fn1<A, Monad<Seq, B>> f) {
        if(this instanceof Nil){
            return (Seq<B>) nil();
        } else {
            Cons<A> c = (Cons<A>) this;
            return c.tail().flatMap(f).concat((Seq<B>) f.apply(c.head()));
        }
    }
}

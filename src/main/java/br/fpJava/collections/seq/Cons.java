package br.fpJava.collections.seq;

import br.fpJava.maybe.Maybe;

import static br.fpJava.maybe.Just.just;

/**
 * Created by sirkleber on 09/09/14.
 */
public class Cons<A> extends Seq<A>{
    private final A head_;
    private final Seq<A> tail_;

    public Cons(final A head_, final Seq<A> tail_){
        this.head_ = head_;
        this.tail_ = tail_;
    }

    public static <A> Seq<A> cons(final A head, final Seq<A> tail){
        return new Cons<A>(head, tail);
    }

    @Override
    public Boolean isEmpty() {
        return false;
    }

    @Override
    public A head(){ return head_; }

    @Override
    public Seq<A> tail(){ return tail_; }

    @Override
    public Seq<A> init(){ return ((Cons<A>) reverse()).tail_.reverse();}

    @Override
    public A last(){ return reverse().head(); }

    @Override
    public Maybe<A> maybeHead() { return just(head_); }

    @Override
    public Maybe<A> maybeLast() { return just(last()); }

    public boolean equals(Object x){
        if(x instanceof Cons){
            Cons<A> s = (Cons<A>) x;
            return head_.equals(s.head()) && tail_.equals(s.tail());
        } else {
            return false;
        }
    }
}

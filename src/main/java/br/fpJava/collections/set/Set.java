package br.fpJava.collections.set;

import br.fpJava.collections.Traversable;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import br.fpJava.maybe.Nothing;
import br.fpJava.tuple.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static br.fpJava.collections.set.EmptySet.EmptySet;

/**
 * Created by sirkleber on 10/10/14.
 */

public abstract class Set<A extends Comparable<A>> extends Traversable<Set, A> {

    public final static <A extends Comparable<A>> Set<A> Set(final A...as){
        if(as.length == 0){
            return (Set<A>) EmptySet();
        } else {
            Set<A> s = (Set<A>) EmptySet();

            for(A a : as){
                s = s.cons(a);
            }

            return s;
        }
    }

   @Override
    protected Set<A> empty() {
        return (Set<A>) EmptySet();
    }

    protected Set<A> add(A item){
        return new ValSet<>(item, this);
    }

    @Override
    public Set<A> cons(A item) {
        if(isEmpty()){
            return add(item);
        } else {
            ValSet<A> s = (ValSet<A>) this;

            switch(item.compareTo(head())){
                case 1: return s.tail().cons(item).add(s.head());
                case 0:
                    if(item.equals(s.head())){
                        return this;
                    } else {
                        return s.tail().cons(item);
                    }
                default: return s.tail().add(s.head()).add(item);
            }
        }
    }

    private Fn1<Set<A>, Set<A>> helper(final Set<A> acc){
        return new Fn1<Set<A>, Set<A>>() {
            @Override
            public Set<A> apply(Set<A> other) {
                if(other.isEmpty()){
                    return acc;
                } else {
                    ValSet<A> vs = (ValSet<A>) other;
                    return helper(acc.cons(vs.head())).apply(vs.tail());
                }
            }
        };
    }

    @Override
    public Traversable<Set, A> concat(Traversable<Set, A> prefix) {
        return helper(this).apply((Set<A>) prefix);
    }

    @Override
    protected String prefix() {
        return "Set";
    }

    @Override
    protected String toStringFrmt(String acc, A item) {
        if(acc.equals("")){
            return "" + item;
        } else {
            return acc + ", " + item;
        }
    }

    @Override
    public Tuple2<Set<A>, Set<A>> splitAt(Integer n) {
        return null;
    }
}

class EmptySet extends Set {
    private static EmptySet emptySet = null;

    public static final Set<? extends Comparable<?>> EmptySet(){
        if(emptySet == null){
            emptySet = new EmptySet();
        }

        return emptySet;
    }

    private EmptySet(){}

    @Override
    public Boolean isEmpty() {
        return true;
    }

    @Override
    public Object head() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Set tail() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Set init() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Object last() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Maybe maybeHead() {
        return Nothing.Nothing();
    }

    @Override
    public Maybe maybeLast() {
        return Nothing.Nothing();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptySet;
    }
}

class ValSet<A extends Comparable<A>> extends Set<A> {
    private final A head_;
    private final Set<A> tail_;

    public ValSet(final A head_, final Set<A> tail_) {
        this.head_ = head_;
        this.tail_ = tail_;
    }

    @Override
    public Boolean isEmpty() {
        return false;
    }

    @Override
    public A head(){
        return head_;
    }

    @Override
    public Set<A> tail() {
        return tail_;
    }

    @Override
    public Set<A> init() {
        if(tail_.isEmpty()){
            return empty();
        } else {
            return (Set<A>) tail_.init().cons(head_);
        }
    }

    @Override
    public A last() {
        if(tail_.isEmpty()){
            return head_;
        } else {
            return tail_.last();
        }
    }

    @Override
    public Maybe<A> maybeHead() {
        return new Just<>(head_);
    }

    @Override
    public Maybe<A> maybeLast() {
        return new Just<>(last());
    }

    public boolean equals(Object x){
        if(x instanceof ValSet){
            ValSet<A> s = (ValSet<A>) x;
            return head_.equals(s.head()) && tail_.equals(s.tail());
        } else {
            return false;
        }
    }
}
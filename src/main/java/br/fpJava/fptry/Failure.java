package br.fpJava.fptry;

import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;

/**
 * Created by sirkleber on 23/09/14.
 */
public class Failure<A> extends Try<A> {
    public final A value;

    public Failure(A value) {
        this.value = value;
    }

    @Override
    public <B> Try<B> flatMap(Fn1<A, Monad<Try, B>> f) {
        return (Try<B>) this;
    }

    @Override
    public <B> Try<B> map(Fn1<A, B> f) {
        return (Try<B>) this;
    }

    @Override
    public Object getOrElse(Fn<Object> f) {
        return f.apply();
    }

    @Override
    public String toString() {
        return "Failure(" + value + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Failure){
            Failure<A> f = (Failure<A>) obj;
            return value.equals(f.value);
        } else {
            return false;
        }
    }
}

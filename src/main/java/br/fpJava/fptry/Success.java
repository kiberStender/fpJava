package br.fpJava.fptry;

import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;
import static br.fpJava.fptry.Try.Try;

/**
 * Created by sirkleber on 23/09/14.
 */
public class Success<A> extends Try<A> {

    final A value;

    public Success(A value) {
        this.value = value;
    }

    @Override
    public <B> Try<B> flatMap(Fn1<A, Monad<Try, B>> f) {
        try{
            return (Try<B>) f.apply(value);
        } catch (Exception e){
            return (Try<B>) new Failure<>(e);
        }
    }

    @Override
    public <B> Try<B> map(final Fn1<A, B> f) {
        return Try(new Fn <B>() {
            @Override
            public B apply() {
                return f.apply(value);
            }
        });
    }

    @Override
    public A getOrElse(Fn<Object> f) {
        return value;
    }

    @Override
    public String toString() {
        return "Success(" + value + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Success){
            Success<A> s = (Success<A>) obj;
            return value.equals(s.value);
        } else {
            return false;
        }
    }

    @Override
    public Boolean isFailure() {
        return false;
    }

    @Override
    public Boolean isSuccess() {
        return true;
    }
}

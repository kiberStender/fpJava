package br.fpJava.fptry;

import br.fpJava.fn.Fn;
import br.fpJava.typeclasses.Monad;

/**
 * Created by sirkleber on 23/09/14.
 */
public abstract class Try<A> extends Monad<Try, A> {

    public static <A> Try<A> Try(Fn<A> f){
        try {
            return new Success<>(f.apply());

        } catch(Exception e) {
            return (Try<A>) new Failure<>(e);
        }
    }

    public abstract Object getOrElse(Fn<Object> f);
}

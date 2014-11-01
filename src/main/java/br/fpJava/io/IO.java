package br.fpJava.io;

import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;
import br.fpJava.utils.Unit;

/**
 * Created by sirkleber on 01/11/14.
 */
public class IO<A> extends Monad<IO, A>{
    final Fn<A> value;

    public IO(Fn<A> value) {
        this.value = value;
    }

    /**
     * Warning! May have arbitrary side-effects!
     * @return
     */
    public A unsafePerformIO() {
        return value.apply();
    }

    public <B> IO<B> map(final Fn1<A,B> func) {
        return new IO<>(new Fn<B>() {
            @Override
            public B apply() {
                return func.apply(unsafePerformIO());
            }
        });
    }

    @Override
    public <B> IO<B> flatMap(final Fn1<A, Monad<IO, B>> f) {
        return new IO<>(new Fn<B>() {
            @Override
            public B apply() {
                return ((IO<B>) map(f).unsafePerformIO()).unsafePerformIO();
            }
        });
    }

    /**
     * A variant of bind, which discards the bound value.
     * @param b
     * @return
     */
    public IO<Unit> bind_(final IO<Unit> b) {
        return flatMap(new Fn1<A, Monad<IO, Unit>>() {
            @Override
            public IO<Unit> apply(A a) {
                return b;
            }
        });
    }
}

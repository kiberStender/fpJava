package br.fpJava.state;

import br.fpJava.fn.Fn1;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Monad;

import static br.fpJava.tuple.Tuple2.tuple2;

/**
 * Created by sirkleber on 25/09/14.
 */
public class State<S, A> extends Monad<State, A> {
    public final Fn1<S, Tuple2<S, A>> run;

    public State(Fn1<S, Tuple2<S, A>> run) {
        this.run = run;
    }

    @Override
    public <B> Monad<State, B> map(final Fn1<A, B> f) {
        return new State<S, B>(new Fn1<S, Tuple2<S, B>>() {
            @Override
            public Tuple2<S, B> apply(S s) {
                Tuple2<S, A> t = run.apply(s);
                return tuple2(t._1, f.apply(t._2));
            }
        });
    }

    @Override
    public <B> Monad<State, B> flatMap(final Fn1<A, Monad<State, B>> f) {
        return new State<S, B>(new Fn1<S, Tuple2<S, B>>() {
            @Override
            public Tuple2<S, B> apply(S s) {
                Tuple2<S, A> t = run.apply(s);
                return ((State<S, B>) f.apply(t._2)).run.apply(t._1);
            }
        });
    }

    public A evaluate (S s){
        return run.apply(s)._2;
    }
}

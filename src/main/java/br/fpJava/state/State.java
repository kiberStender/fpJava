package br.fpJava.state;

import br.fpJava.fn.Fn1;
import br.fpJava.tuple.Pair;
import br.fpJava.utils.Unit;
//import br.fpJava.typeclasses.Monad;

import static br.fpJava.tuple.Pair.of;
import static br.fpJava.utils.Unit.unit;


/**
 * Created by sirkleber on 25/09/14.
 */
public class State<S, A> {
    public final Fn1<S, Pair<S, A>> run;

    public State(Fn1<S, Pair<S, A>> run) {
        this.run = run;
    }

    //@Override
    public <B> State<S, B> map(final Fn1<A, B> f) {
        return new State<S, B>(new Fn1<S, Pair<S, B>>() {
            @Override
            public Pair<S, B> apply(S s) {
                Pair<S, A> t = run.apply(s);
                return of(t.first, f.apply(t.second));
            }
        });
    }

    //@Override
    public <B> State<S, B> flatMap(final Fn1<A, State<S, B>> f) {
        return new State<S, B>(new Fn1<S, Pair<S, B>>() {
            @Override
            public Pair<S, B> apply(S s) {
                Pair<S, A> t = run.apply(s);
                return ((State<S, B>) f.apply(t.second)).run.apply(t.first);
            }
        });
    }

    public A evaluate (S s){
        return run.apply(s).second;
    }

    public static final <S, A> State<S, A> insert(final A a){
        return new State<S, A>(new Fn1<S, Pair<S, A>>() {
            @Override
            public Pair<S, A> apply(S s) {
                return of(s, a);
            }
        });
    }

    public static final <S, A> State<S, A> get(final Fn1<S, A> f) {
        return new State<S, A>(new Fn1<S, Pair<S, A>>() {
            @Override
            public Pair<S, A> apply(S s) {
                return of(s, f.apply(s));
            }
        });
    }

    public static final <S> State<S, Unit> mod(final Fn1<S, S> f){
        return new State<S, Unit>(new Fn1<S, Pair<S, Unit>>() {
            @Override
            public Pair<S, Unit> apply(S s) {
                return of(f.apply(s), unit());
            }
        });
    }
}

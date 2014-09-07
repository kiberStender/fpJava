package br.fpJava.maybe;

/**
 * Created by sirkleber on 07/09/14.
 */
public class Just<A> extends Maybe<A> {
    public final A a;

    public Just(final A a){
        this.a = a;
    }
}

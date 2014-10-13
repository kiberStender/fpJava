package br.fpJava.maybe;

import br.fpJava.fn.Fn;

/**
 * Created by sirkleber on 07/09/14.
 */
public class Just<A> extends Maybe<A> {
    public final A a;

    public Just(final A a){
        this.a = a;
    }

    public String toString(){
        return "Just(" + a + ")";
    }

    public A get() {
        return a;
    }

    @Override
    public Object getOrElse(Fn<Object> v) {
        return a;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Just){
            return a.equals(((Just) obj).a);
        } else {
            return false;
        }
    }
}

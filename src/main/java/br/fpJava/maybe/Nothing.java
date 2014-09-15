package br.fpJava.maybe;

import br.fpJava.fn.Fn;

import java.util.NoSuchElementException;

/**
 * Created by sirkleber on 07/09/14.
 */

public class Nothing extends Maybe<Object>{
    private static Nothing ourInstance = null;

    public static Maybe<?> Nothing() {
        if(ourInstance == null){
            ourInstance = new Nothing();
        }
        return ourInstance;
    }

    private Nothing() {}

    public String toString(){
        return "Nothing";
    }

    @Override
    public Object get() throws NoSuchElementException {
        throw new NoSuchElementException("Nothing.get");
    }

    @Override
    public Object getOrElse(Fn<Object> v) {
        return v.apply();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Nothing;
    }
}

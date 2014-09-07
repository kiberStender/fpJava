package br.fpJava.maybe;

/**
 * Created by sirkleber on 07/09/14.
 */

public class Nothing extends Maybe<Object>{
    private static Nothing ourInstance = null;

    public static Maybe<?> none() {
        if(ourInstance == null){
            ourInstance = new Nothing();
        }
        return ourInstance;
    }

    private Nothing() {}
}

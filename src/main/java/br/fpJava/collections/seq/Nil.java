package br.fpJava.collections.seq;

/**
 * Created by sirkleber on 09/09/14.
 */
public class Nil extends Seq<Object>{
    private static Nil ourInstance = null;

    public static Seq<?> nil() {
        if(ourInstance == null){
            ourInstance = new Nil();
        }
        return ourInstance;
    }

    private Nil() {}

    @Override
    public boolean equals(Object x) {
        return x instanceof Nil;
    }
}

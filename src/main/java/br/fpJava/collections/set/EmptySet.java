package br.fpJava.collections.set;

/**
 * Created by sirkleber on 10/10/14.
 */
public class EmptySet extends Set {
    private static EmptySet emptySet = null;

    public static final Set<? extends Comparable<?>> EmptySet(){
        if(emptySet == null){
            emptySet = new EmptySet();
        }

        return emptySet;
    }

    private EmptySet(){}
}

package br.fpJava.utils;

/**
 * Created by sirkleber on 09/09/14.
 */
public class Unit {
    private static Unit ourInstance = null;

    public static Unit unit() {
        if(ourInstance == null){
            ourInstance = new Unit();
        }
        return ourInstance;
    }

    private Unit() {}
}

package br.fpJava.utils;

/**
 * Created by sirkleber on 09/09/14.
 */
public class Unit {
    private static Unit ourInstance = new Unit();

    public static Unit unit() {
        return ourInstance;
    }

    private Unit() {}
}

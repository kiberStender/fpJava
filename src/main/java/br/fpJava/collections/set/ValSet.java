package br.fpJava.collections.set;

/**
 * Created by sirkleber on 10/10/14.
 */
public class ValSet<A extends Comparable<A>> extends Set<A> {
    private final A val;

    public ValSet(A val) {
        this.val = val;
    }
}

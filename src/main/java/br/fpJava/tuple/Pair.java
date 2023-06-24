package br.fpJava.tuple;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by sirkleber on 14/09/14.
 */
public final class Pair<A, B> implements Tuple{
    public final A first;
    public final B second;

    private Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A, B> Pair<A, B> of(A _1, B _2){
        return new Pair<>(_1, _2);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Pair){
            Pair<A, B> tp2 = (Pair<A, B>) o;
            return Objects.equals(first, tp2.first) && Objects.equals(second, tp2.second);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ODD_PRIME_NUMBER_HASHCODE_MULTIPLIER * first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }
}

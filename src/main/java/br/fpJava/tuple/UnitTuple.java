package br.fpJava.tuple;

import br.fpJava.tuple.Tuple;

import java.util.Objects;

/**
 * Calling this UnitTuple so that it's not mistaken with Unit as in return value from utils package.
 */
public final class UnitTuple<A> implements Tuple {
    public final A first;

    protected UnitTuple(A first) {
        this.first = first;
    }

    public static <A> UnitTuple<A> of(final A first){
        return new UnitTuple<>(first);
    }

    @Override
    public boolean equals(final Object o) {
        if(o instanceof UnitTuple){
            final UnitTuple<A> otherTuple = (UnitTuple<A>) o;
            return Objects.equals(first, otherTuple.first);
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(ODD_PRIME_NUMBER_HASHCODE_MULTIPLIER * first.hashCode());
    }

    @Override
    public String toString() {
        return String.format("(%s)", first);
    }
}

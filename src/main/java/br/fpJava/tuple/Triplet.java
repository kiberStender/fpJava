package br.fpJava.tuple;

import java.util.Objects;

public final class Triplet<A, B, C> implements Tuple{

    public final A first;
    public final B second;
    public final C third;

    private Triplet(final A first, final B second, final C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <A, B, C> Triplet<A, B, C> of(final A first, final B second, final C third){
        return new Triplet<>(first, second, third);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Triplet){
            final Triplet<A, B, C> otherTuple = (Triplet<A, B, C>) o;
            return Objects.equals(otherTuple.first, first) && Objects.equals(otherTuple.second, second) &&
                    Objects.equals(otherTuple.third, third);
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ODD_PRIME_NUMBER_HASHCODE_MULTIPLIER * first.hashCode() + second.hashCode() + third.hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", first, second, third);
    }
}

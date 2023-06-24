package br.fpJava.tuple;

import java.util.Objects;

public final class Quintuplet<A, B, C, D, E> implements Tuple{

    public final A first;
    public final B second;
    public final C third;
    public final D fourth;
    public final E fifth;

    private Quintuplet(final A first, final B second, final C third, final D fourth, final E fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public static <A, B, C, D, E> Quintuplet<A, B, C, D, E> of(final A first, final B second, final C third, final D fourth,
                                                         final E fifth){
        return new Quintuplet<>(first, second, third, fourth, fifth);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Quintuplet){
            final Quintuplet<A, B, C, D, E> otherTuple = (Quintuplet<A, B, C, D, E>) o;
            return Objects.equals(otherTuple.first, first) && Objects.equals(otherTuple.second, second) && Objects.equals(otherTuple.third, third) &&
                    Objects.equals(otherTuple.fourth, fourth) && Objects.equals(otherTuple.fifth, fifth);
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ODD_PRIME_NUMBER_HASHCODE_MULTIPLIER * first.hashCode() + second.hashCode() + third.hashCode() +
                fourth.hashCode() + fourth.hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %s, %s)", first, second, third, fourth, fifth);
    }
}

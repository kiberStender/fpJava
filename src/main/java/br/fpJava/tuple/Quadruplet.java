package br.fpJava.tuple;

public final class Quadruplet<A, B, C, D> implements Tuple{

    public final A first;
    public final B second;
    public final C third;
    public final D fourth;

    private Quadruplet(final A first, final B second, final C third, final D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public static <A, B, C, D> Quadruplet<A, B, C, D> of(final A first, final B second, final C third, final D fourth){
        return new Quadruplet<>(first, second, third, fourth);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Quadruplet){
            final Quadruplet<A, B, C, D> otherTuple = (Quadruplet<A, B, C, D>) o;
            return otherTuple.first.equals(first) && otherTuple.second.equals(second) && otherTuple.third.equals(third) &&
                    otherTuple.fourth.equals(fourth);
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ODD_PRIME_NUMBER_HASHCODE_MULTIPLIER * first.hashCode() + second.hashCode() + third.hashCode() +
                fourth.hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %s)", first, second, third, fourth);
    }
}

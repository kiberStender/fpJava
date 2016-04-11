package br.fpJava.tuple;

/**
 * Created by sirkleber on 14/09/14.
 */
public class Tuple2<A, B> {
    public final A _1;
    public final B _2;

    public Tuple2(A _1, B _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static <A, B> Tuple2<A, B> tuple2(A _1, B _2){
        return new Tuple2<>(_1, _2);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Tuple2){
            Tuple2<A, B> tp2 = (Tuple2<A, B>) o;
            return _1.equals(tp2._1) && _2.equals(tp2._2);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * _1.hashCode() + _2.hashCode();
    }

    @Override
    public String toString() {
        return "Tuple2(" + _1 + ", " + _2 + ')';
    }
}

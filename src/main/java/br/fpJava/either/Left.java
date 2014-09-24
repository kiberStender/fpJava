package br.fpJava.either;

/**
 * Created by sirkleber on 24/09/14.
 */
public class Left<L> extends Either<Object, L> {
    public final L value;

    public Left(L value) {
        this.value = value;
    }

    @Override
    public Boolean isLeft() {
        return true;
    }

    @Override
    public Boolean isRight() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Left){
            Left<L> l = (Left<L>) this;
            return value.equals(l.value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Left("+ value +")";
    }
}

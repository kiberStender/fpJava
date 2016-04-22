package br.fpJava.either;

/**
 * Created by sirkleber on 24/09/14.
 */
public class Left<L> extends Either<L, Object> {
    public final L value;

    private Left(L value) {
        this.value = value;
    }

    public static final <L> Either<L, ?> left(final L value){
        return new Left<L>(value);
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
            return value.equals(((Left<L>) obj).value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Left("+ value +")";
    }
}

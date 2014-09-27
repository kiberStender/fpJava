package br.fpJava.either;

/**
 * Created by sirkleber on 24/09/14.
 */
public class Right<R> extends Either<Object, R> {
    public final R value;

    private Right(R value) {
        this.value = value;
    }

    public static final <R> Either<?, R> Right(final R value){
        return new Right<R>(value);
    }

    @Override
    public Boolean isLeft() {
        return false;
    }

    @Override
    public Boolean isRight() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Right){
            Right<R> r =(Right<R>) this;
            return value.equals(r.value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Right("+ value +")";
    }
}

package br.fpJava.either;

/**
 * Created by sirkleber on 24/09/14.
 */
public class Right<R> extends Either<R, Object> {
    public final R value;

    public Right(R value) {
        this.value = value;
    }

    @Override
    public Boolean isLeft() {
        return false;
    }

    @Override
    public Boolean isRight() {
        return true;
    }
}

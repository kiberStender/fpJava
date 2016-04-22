package br.fpJava.either;

import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;

/**
 * Created by sirkleber on 24/09/14.
 */
public abstract class Either<L, R> {
    public abstract Boolean isLeft();
    public abstract Boolean isRight();

    public <B> B fold(Fn1<R, B> rfn, Fn1<L, B> lfn){
        if(isLeft()){
            return lfn.apply(((Left<L>) this).value);
        } else {
            return rfn.apply(((Right<R>) this).value);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Either){
            if(obj instanceof Left){
                return ((Left<L>) this).value == ((Left<L>) obj).value;
            } else {
                return ((Right<R>) this).value == ((Right<R>) obj).value;
            }
        } else {
            return false;
        }
    }
}

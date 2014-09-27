package br.fpJava.either;

import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;

/**
 * Created by sirkleber on 24/09/14.
 */
public abstract class Either<L, R>{
    public abstract Boolean isLeft();
    public abstract Boolean isRight();

    public <B> B fold(Fn1<R, B> rfn, Fn1<L, B> lfn){
        if(isLeft()){
            Left<L> l = (Left<L>) this;
            return lfn.apply(l.value);
        } else {
            Right<R> r =(Right<R>) this;
            return rfn.apply(r.value);
        }
    }
}

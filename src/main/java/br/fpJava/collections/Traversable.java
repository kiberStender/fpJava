package br.fpJava.collections;

import br.fpJava.fn.Fn1;
import br.fpJava.typeclasses.Monad;
import br.fpJava.utils.Unit;
import static br.fpJava.utils.Unit.unit;

/**
 * Created by sirkleber on 09/09/14.
 */

public abstract class Traversable<T, A> implements Monad<T, A>{
    public <B> Unit foreach(Fn1<A, B> f){
        map(f);
        return unit();
    }
}

package br.fpJava.stateTest;

import br.fpJava.collections.map.Map;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Maybe;
import br.fpJava.state.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static br.fpJava.collections.map.Map.map;
import static br.fpJava.tuple.Pair.of;

/**
 * Created by sirkleber on 25/09/14.
 */
public class StateTest {
    private final Fn1<Integer, Integer> identity = a -> a;

    //http://tonymorris.github.io/blog/posts/memoisation-with-state-using-scala/
    //fibMemo4 was used as a reference to this function

    private State<Map<Integer, Integer>, Integer> fibMemoR(final Integer z){
        if(z <= 1){
            return State.insert(z);
        } else {
            return State.get((Map<Integer, Integer> m) -> m.get(z))
                    .flatMap((Maybe<Integer> u) -> ((State<Map<Integer, Integer>, Integer>) u.map(i -> State.insert(i))
                            .getOrElse(() -> fibMemoR(z - 1)
                                    .flatMap(r -> fibMemoR(z - 2)
                                            .flatMap(s -> State.mod((Map<Integer, Integer> m) -> m.cons(of(z, r + s)))
                                                    .map(_u -> r + s)))))
                            .map(identity));
        }
    }

    private Integer fibMemo(Integer n){
        return fibMemoR(n).evaluate(map());
    }

    @Test
    public void fibonacciTest(){
        assertEquals(832040, fibMemo(30).intValue());
    }
}

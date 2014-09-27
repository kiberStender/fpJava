package br.fpJava.stateTest;

import br.fpJava.collections.map.Map;
import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Maybe;
import br.fpJava.state.State;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Monad;
import br.fpJava.utils.Unit;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static br.fpJava.collections.map.Map.Map;
import static br.fpJava.tuple.Tuple2.tuple2;

/**
 * Created by sirkleber on 25/09/14.
 */
public class StateTest {
    private final Map<Integer, Integer> m = Map();

    private State<Map<Integer, Integer>, Integer> fibMemoR(final Integer z){
        if(z <= 1){
            return State.insert(z);
        } else {
            return State.get(new Fn1<Map<Integer, Integer>, Maybe<Integer>>() {
                @Override
                public Maybe<Integer> apply(Map<Integer, Integer> m) {
                    return m.get(z);
                }
            }).flatMap(new Fn1<Maybe<Integer>, State<Map<Integer, Integer>, Integer>>() {
                @Override
                public State<Map<Integer, Integer>, Integer> apply(final Maybe<Integer> u) {
                    return ((State<Map<Integer, Integer>, Integer>) u.map(new Fn1<Integer, State<Map<Integer, Integer>, Integer>>() {
                        @Override
                        public State<Map<Integer, Integer>, Integer> apply(Integer integer) {
                            return State.insert(integer);
                        }
                    }).getOrElse(new Fn<Object>() {
                        @Override
                        public Object apply() {
                            return fibMemoR(z - 1).flatMap(new Fn1<Integer, State<Map<Integer, Integer>, Integer>>() {
                                @Override
                                public State<Map<Integer, Integer>, Integer> apply(final Integer r) {
                                    return fibMemoR(z - 2).flatMap(new Fn1<Integer, State<Map<Integer, Integer>, Integer>>() {
                                        @Override
                                        public State<Map<Integer, Integer>, Integer> apply(final Integer s) {
                                            return State.mod(new Fn1<Map<Integer, Integer>, Map<Integer, Integer>>() {
                                                @Override
                                                public Map<Integer, Integer> apply(Map<Integer, Integer> m) {
                                                    return m.cons(tuple2(z, r + s));
                                                }
                                            }).map(new Fn1<Unit, Integer>() {
                                                @Override
                                                public Integer apply(Unit unit) {
                                                    return r + s;
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    })).map(new Fn1<Integer, Integer>() {
                        @Override
                        public Integer apply(Integer v) {
                            return v;
                        }
                    });
                }
            });
        }
    }

    private Integer fibMemo(Integer n){
        return fibMemoR(n).evaluate(m);
    }

    @Test
    public void fibonacciTest(){
        assertEquals(832040, fibMemo(30).intValue());
    }
}

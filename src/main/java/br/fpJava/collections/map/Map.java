package br.fpJava.collections.map;

import br.fpJava.collections.Traversable;
import br.fpJava.fn.Fn;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import br.fpJava.maybe.Nothing;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Functor;
import br.fpJava.typeclasses.Monad;

import java.util.NoSuchElementException;

import static br.fpJava.maybe.Nothing.Nothing;
import static br.fpJava.collections.map.EmptyMap.empty;


/**
 * Created by sirkleber on 15/09/14.
 */

public abstract class Map<K extends Comparable, V> extends Traversable<Map, Tuple2<K, V>> {

    private final static <K, V> Tuple2<K, V>[] getTail(Tuple2<K, V>[] tp){
        Tuple2<K, V>[] tmp = new Tuple2[tp.length - 1];

        for(int i = 1; i < tp.length; i++){
            tmp[i - 1] = tp[i];
        }

        return tmp;
    }

    public final static <K extends Comparable, V> Map<K, V> Map(final Tuple2<K, V>... tp){
        if(tp.length == 0){
            return (Map<K, V>) empty();
        } else {
            return new MapCons<K, V>(tp[0], Map(getTail(tp)));
        }
    }

    private Map<K, V> orderer(Tuple2<K, V> m){
        return Map(m);
    }

    @Override
    public Map<K, V> cons(final Tuple2<K, V> item) {
        if(length().compareTo(2) == -1){
            if(item._1.compareTo(head()._1) == -1){
            }
            return Map();
        } else {
            Tuple2<Map<K, V>, Map<K, V>> tp = splitAt(length() / 2);
            return tp._1;
        }
    }

    @Override
    public Traversable<Map, Tuple2<K, V>> concat(Traversable<Map, Tuple2<K, V>> t) {
        return null;
    }

    public final Maybe<V> get(final K key){
        return find(new Fn1<Tuple2<K, V>, Boolean>() {
            @Override
            public Boolean apply(Tuple2<K, V> x) {
                return x._1.equals(key);
            }
        }).map(new Fn1<Tuple2<K, V>, V>() {
            @Override
            public V apply(Tuple2<K, V> x) {
                return x._2;
            }
        });
    }

    @Override
    public Map<K, V> filter(final Fn1<Tuple2<K, V>, Boolean> p) {
        return foldRight((Map<K, V>) Map(), new Fn1<Tuple2<K, V>, Fn1<Map<K, V>, Map<K, V>>>() {
            @Override
            public Fn1<Map<K, V>, Map<K, V>> apply(final Tuple2<K, V> item) {
                return new Fn1<Map<K, V>, Map<K, V>>() {
                    @Override
                    public Map<K, V> apply(Map<K, V> acc) {
                        if(p.apply(item)){
                            return new MapCons<K, V>(item, acc);
                        } else {
                            return acc;
                        }
                    }
                };
            }
        });
    }

    @Override
    public Tuple2<Map<K, V>, Map<K, V>> splitAt(Integer n) {
        return null;
    }

    @Override
    public <B> Traversable<Map, B> map(Fn1<Tuple2<K, V>, B> f) {
        if(isEmpty()){
            return (Traversable<Map, B>) empty();
        } else {
            MapCons<K, V> m = (MapCons<K, V>) this;
            Tuple2<K, B> b = (Tuple2<K, B>) f.apply(head());
            return (Traversable<Map, B>) new MapCons<>(b, (Map<K, B>) tail().map(f));
        }
    }

    @Override
    public <B> Monad<Map, B> flatMap(Fn1<Tuple2<K, V>, Monad<Map, B>> f) {
        return null;
    }
}

class EmptyMap extends Map<Comparable, Object> {
    private static EmptyMap emptyMap = null;

    public static Map<Comparable, Object> empty(){
        if(emptyMap == null){
            emptyMap = new EmptyMap();
        }
        return emptyMap;
    }

    private EmptyMap(){}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyMap;
    }

    @Override
    public Boolean isEmpty() {
        return true;
    }

    @Override
    public Tuple2<Comparable, Object> head() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Map<Comparable, Object> tail() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Map<Comparable, Object> init() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Tuple2<Comparable, Object> last() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Maybe<Tuple2<Comparable, Object>> maybeHead() {
        return (Maybe<Tuple2<Comparable, Object>>) Nothing();
    }

    @Override
    public Maybe<Tuple2<Comparable, Object>> maybeLast() {
        return (Maybe<Tuple2<Comparable, Object>>) Nothing();
    }
}

class MapCons<K extends Comparable, V> extends Map<K, V> {

    private final Tuple2<K, V> head_;
    private final Map<K, V> tail_;

    MapCons(Tuple2<K, V> head_, Map<K, V> tail_) {
        this.head_ = head_;
        this.tail_ = tail_;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MapCons){
            MapCons<K, V> m = (MapCons<K, V>) obj;
            return head_.equals(m.head()) && tail_.equals(m.tail());
        } else {
            return false;
        }
    }

    @Override
    public Boolean isEmpty() {
        return false;
    }

    @Override
    public Tuple2<K, V> head() {
        return head_;
    }

    @Override
    public Map<K, V> tail() {
        return tail_;
    }

    @Override
    public Map<K, V> init() {
        //TODO- Implemet
        return null;
    }

    @Override
    public Tuple2<K, V> last() {
        //TODO- Implement
        return null;
    }

    @Override
    public Maybe<Tuple2<K, V>> maybeHead() {
        return new Just<>(head_);
    }

    @Override
    public Maybe<Tuple2<K, V>> maybeLast() {
        return new Just<>(last());
    }
}

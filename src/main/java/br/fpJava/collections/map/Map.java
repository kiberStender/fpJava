package br.fpJava.collections.map;

import br.fpJava.collections.Traversable;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import br.fpJava.tuple.Tuple2;
import br.fpJava.typeclasses.Monad;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static br.fpJava.maybe.Nothing.nothing;
import static br.fpJava.collections.map.EmptyMap.empty;


/**
 * Created by sirkleber on 15/09/14.
 */

public abstract class Map<K, V> extends Traversable<Map, Tuple2<K, V>> {

    private final static <K, V> Tuple2<K, V>[] getTail(Tuple2<K, V>[] tp){
        Tuple2<K, V>[] tmp = new Tuple2[tp.length - 1];

        for(int i = 1; i < tp.length; i++){
            tmp[i - 1] = tp[i];
        }

        return tmp;
    }

    public final static <K, V> Map<K, V> Map(final Tuple2<K, V>... tp){
        if(tp.length == 0){
            return (Map<K, V>) empty();
        } else {
            return new MapCons<K, V>(tp[0], Map(getTail(tp)));
        }
    }

    @Override
    public Traversable<Map, Tuple2<K, V>> filter(Fn1<Tuple2<K, V>, Boolean> p) {
        return null;
    }

    @Override
    public Maybe<Tuple2<K, V>> find(Fn1<Tuple2<K, V>, Boolean> p) {
        return null;
    }

    @Override
    public <I extends Traversable<Map, Tuple2<K, V>>> Tuple2<I, I> splitAt(Integer n) {
        return null;
    }

    @Override
    public <B> B foldLeft(B acc, Fn1<B, Fn1<Tuple2<K, V>, B>> f) {
        return null;
    }

    @Override
    public <B> B foldRight(B acc, Fn1<Tuple2<K, V>, Fn1<B, B>> f) {
        return null;
    }

    @Override
    public <B> Monad<Map, B> map(Fn1<Tuple2<K, V>, B> f) {
        return null;
    }

    @Override
    public <B> Monad<Map, B> flatMap(Fn1<Tuple2<K, V>, Monad<Map, B>> f) {
        return null;
    }
}

class EmptyMap extends Map<Object, Object> {
    private static EmptyMap emptyMap = null;

    public static Map<Object, Object> empty(){
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
    public Tuple2<Object, Object> head() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public <I extends Traversable<Map, Tuple2<Object, Object>>> I tail() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public <I extends Traversable<Map, Tuple2<Object, Object>>> I init() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Tuple2<Object, Object> last() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Maybe<Tuple2<Object, Object>> maybeHead() {
        return (Maybe<Tuple2<Object, Object>>) nothing();
    }

    @Override
    public Maybe<Tuple2<Object, Object>> maybeLast() {
        return (Maybe<Tuple2<Object, Object>>) nothing();
    }
}

class MapCons<K, V> extends Map<K, V> {

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
    public Tuple2<K, V> last() throws NoSuchElementException {
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

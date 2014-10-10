package br.fpJava.collections.map;

import br.fpJava.collections.Traversable;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import br.fpJava.tuple.Tuple2;

import java.util.NoSuchElementException;

import static br.fpJava.maybe.Nothing.Nothing;
import static br.fpJava.collections.map.EmptyMap.emptyMap;
import static br.fpJava.tuple.Tuple2.tuple2;


/**
 * Created by sirkleber on 15/09/14.
 */

public abstract class Map<K extends Comparable<K>, V> extends Traversable<Map, Tuple2<K, V>> {

    private final static <K, V> Tuple2<K, V>[] getTail(Tuple2<K, V>[] tp){
        Tuple2<K, V>[] tmp = new Tuple2[tp.length - 1];

        for(int i = 1; i < tp.length; i++){
            tmp[i - 1] = tp[i];
        }

        return tmp;
    }

    public final static <K extends Comparable<K>, V> Map<K, V> Map(final Tuple2<K, V>... tp){
        if(tp.length == 0){
            return (Map<K, V>) emptyMap();
        } else {
            return Map(getTail(tp)).cons(tp[0]);
        }
    }

    @Override
    protected String prefix() {
        return "Map";
    }

    @Override
    protected String toStringFrmt(String acc, Tuple2<K, V> item) {
        if(acc.equals("")){
            return "(" + item._1 + " -> " + item._2 + ")";
        } else {
            return acc + "(" + item._1 + " -> " + item._2 + ")";
        }
    }

    protected Map<K, V> empty(){
        return (Map<K, V>) emptyMap();
    }

    protected Map<K, V> add(final Tuple2<K, V> item){
        return new MapCons<K, V>(item, this);
    }

    @Override
    public Map<K, V> cons(final Tuple2<K, V> item) {
        if(isEmpty()){
            return add(item);
        } else {
            MapCons<K, V> y =(MapCons<K, V>) this;

            switch(item._1.compareTo(y.head()._1)){
                case 1: return y.tail().cons(item).add(y.head());
                case 0:
                    if(item._2.equals(y.head()._2)){
                        return this;
                    } else {
                        return y.tail().cons(item);
                    }
                default: return y.tail().add(y.head()).add(item);
            }
        }
    }

    private Fn1<Map<K, V>, Map<K, V>> helper(final Map<K, V> acc){
        return new Fn1<Map<K, V>, Map<K, V>>() {
            @Override
            public Map<K, V> apply(final Map<K, V> other) {
                if(other.isEmpty()){
                    return acc;
                } else {
                    MapCons<K, V> c = (MapCons<K, V>) other;
                    return helper(acc.cons(c.head())).apply(c.tail());
                }
            }
        };
    }

    @Override
    public Traversable<Map, Tuple2<K, V>> concat(Traversable<Map, Tuple2<K, V>> prefix) {
        return helper(this).apply((Map<K, V>) prefix);
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

    private final Tuple2<Map<K, V>, Map<K, V>> splitR(Integer curN, Map<K, V> curL, Map<K, V> pre){
        if(curL.isEmpty()){
            return tuple2(pre, empty());
        } else {
            if(curN.equals(0)){
                return tuple2(pre, curL);
            } else {
                MapCons<K, V> c = (MapCons<K, V>) curL;
                return splitR(curN - 1, c.tail(), pre.cons(c.head()));
            }
        }
    }

    @Override
    public Tuple2<Map<K, V>, Map<K, V>> splitAt(Integer n) {
        return splitR(n, this, empty());
    }
}

class EmptyMap extends Map{
    private static EmptyMap emptyMp = null;

    public static Map<? extends Comparable<?>, ?> emptyMap(){
        if(emptyMp == null){
            emptyMp = new EmptyMap();
        }
        return emptyMp;
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
    public Tuple2<?, ?> head() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Map<?, ?> tail() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Map<?, ?> init() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Tuple2<?, ?> last() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Maybe<Tuple2<?, ?>> maybeHead() {
        return (Maybe<Tuple2<?, ?>>) Nothing();
    }

    @Override
    public Maybe<Tuple2<?, ?>> maybeLast() {
        return (Maybe<Tuple2<?, ?>>) Nothing();
    }
}

class MapCons<K extends Comparable<K>, V> extends Map<K, V> {

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
        if(tail_.isEmpty()){
            return empty();
        } else {
            return (Map<K, V>) tail_.init().cons(head_);
        }
    }

    @Override
    public Tuple2<K, V> last() {
        if(tail_.isEmpty()){
            return head_;
        } else {
            return tail_.last();
        }
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

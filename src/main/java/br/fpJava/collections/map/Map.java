package br.fpJava.collections.map;

import br.fpJava.collections.Traversable;
import br.fpJava.fn.Fn1;
import br.fpJava.maybe.Just;
import br.fpJava.maybe.Maybe;
import br.fpJava.tuple.Pair;

import java.util.NoSuchElementException;

import static br.fpJava.maybe.Nothing.nothing;
import static br.fpJava.maybe.Just.just;
import static br.fpJava.collections.map.EmptyMap.emptyMap;


/**
 * Created by sirkleber on 15/09/14.
 */

public abstract class Map<K extends Comparable<K>, V> extends Traversable<Map, Pair<K, V>> {

    private final static <K, V> Pair<K, V>[] getTail(Pair<K, V>[] tp){
        Pair<K, V>[] tmp = new Pair[tp.length - 1];

        for(int i = 1; i < tp.length; i++){
            tmp[i - 1] = tp[i];
        }

        return tmp;
    }

    public final static <K extends Comparable<K>, V> Map<K, V>map(final Pair<K, V>... tp){
        if(tp.length == 0){
            return (Map<K, V>) emptyMap();
        } else {
            return map(getTail(tp)).cons(tp[0]);
        }
    }

    @Override
    protected String prefix() {
        return "Map";
    }

    @Override
    protected String toStringFrmt(String acc, Pair<K, V> item) {
        if(acc.equals("")){
            return "(" + item.first + " -> " + item.second + ")";
        } else {
            return acc + "(" + item.first + " -> " + item.second+ ")";
        }
    }

    protected Map<K, V> empty(){
        return (Map<K, V>) emptyMap();
    }

    protected Map<K, V> add(final Pair<K, V> item){
        return new MapCons<K, V>(item, this);
    }

    @Override
    public Map<K, V> cons(final Pair<K, V> item) {
        if(isEmpty()){
            return add(item);
        } else {
            MapCons<K, V> y =(MapCons<K, V>) this;
            int compared = y.head().first.compareTo(item.first);

            if(compared < 0){
                return y.tail().cons(item).add(y.head());
            } else if(compared == 0){
                if(item.second.equals(y.head().second)){
                    return this;
                } else {
                    return y.tail().cons(item);
                }
            } else {
                return y.tail().add(y.head()).add(item);
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
    public Traversable<Map, Pair<K, V>> concat(Traversable<Map, Pair<K, V>> prefix) {
        return helper(this).apply((Map<K, V>) prefix);
    }

    public final Maybe<V> get(final K key){
        Integer n = length();

        if(n.equals(0)){
            return (Maybe<V>) nothing();
        } else if(n.equals(1)) {
          if(head().first.equals(key)){
              return new Just<V>(head().second);
          } else {
              return (Maybe<V>) nothing();
          }
        } else {
            final Pair<Map<K, V>, Map<K, V>> tp = splitAt(n / 2);
            final Map<K, V> x = tp.first;
            final Map<K, V> y = tp.second;

            if(y.head().first.compareTo(key) > 0){
                return x.get(key);
            } else {
                return y.get(key);
            }
        }
    }

    private final Pair<Map<K, V>, Map<K, V>> splitR(Integer curN, Map<K, V> curL, Map<K, V> pre){
        if(curL.isEmpty()){
            return Pair.of(pre, empty());
        } else {
            if(curN.equals(0)){
                return Pair.of(pre, curL);
            } else {
                MapCons<K, V> c = (MapCons<K, V>) curL;
                return splitR(curN - 1, c.tail(), pre.cons(c.head()));
            }
        }
    }

    @Override
    public Pair<Map<K, V>, Map<K, V>> splitAt(Integer n) {
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
    public Pair<?, ?> head() throws NoSuchElementException {
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
    public Pair<?, ?> last() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public Maybe<Pair<?, ?>> maybeHead() {
        return (Maybe<Pair<?, ?>>) nothing();
    }

    @Override
    public Maybe<Pair<?, ?>> maybeLast() {
        return (Maybe<Pair<?, ?>>) nothing();
    }
}

class MapCons<K extends Comparable<K>, V> extends Map<K, V> {

    private final Pair<K, V> head_;
    private final Map<K, V> tail_;

    MapCons(final Pair<K, V> head_, final Map<K, V> tail_) {
        this.head_ = head_;
        this.tail_ = tail_;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MapCons){
            final MapCons<K, V> m = (MapCons<K, V>) obj;
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
    public Pair<K, V> head() {
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
    public Pair<K, V> last() {
        if(tail_.isEmpty()){
            return head_;
        } else {
            return tail_.last();
        }
    }

    @Override
    public Maybe<Pair<K, V>> maybeHead() {
        return just(head_);
    }

    @Override
    public Maybe<Pair<K, V>> maybeLast() {
        return just(last());
    }
}

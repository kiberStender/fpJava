package br.fpJava.collections.seq;

/**
 * Created by sirkleber on 09/09/14.
 */
public class Cons<A> extends Seq<A>{
    public final A head;
    public final Seq<A> tail;

    public Cons(final A head, final Seq<A> tail){
        this.head = head;
        this.tail = tail;
    }

    public boolean equals(Object x){
        if(x instanceof Cons){
            Cons<A> s = (Cons<A>) x;
            return head.equals(s.head) && tail.equals(s.tail);
        } else {
            return false;
        }
    }
}

package br.fpJava.fn;

@FunctionalInterface
public interface Fn1<A, B>{
  B apply(final A a);

  default <C> Fn1<C, B> compose(final Fn1<C, A> g){
    return (final C c) -> apply(g.apply(c));
  }

  default <C> Fn1<A, C> andThen(final Fn1<B, C> g){
    return (final A a) -> g.apply(apply(a));
  }
}

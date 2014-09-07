package br.fpJava.typeclasses;

import br.fpJava.fn.Fn1;

public interface Functor<F, A>{
  <B> Functor<F, B> map(Fn1<A, B> f);
}

package br.fpJava.typeclasses;

import br.fpJava.fn.Fn1;

public interface Functor<F, A>{
    /**
     * Method to iterate over the container and produce a new container
     * applying the f anonymous function
     * @param f Function to transform the items inside the container
     * @param <B> The type of anonymous function return
     * @return A new container with type B
     */
  <B> Functor<F, B> map(Fn1<A, B> f);
}

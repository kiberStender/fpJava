package br.fpJava.eitherTest;

import br.fpJava.either.Either;
import br.fpJava.either.Left;
import br.fpJava.either.Right;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static br.fpJava.either.Right.Right;
import static br.fpJava.either.Left.Left;

/**
 * Created by sirkleber on 27/09/14.
 */
public class EitherTest {

    private Either<String, Integer> guessMagic(Integer n){
        if(n.equals(5)){
            return (Either<String, Integer>) Right(5);
        } else {
            return (Either<String, Integer>) Left("Nao foi desta vez");
        }
    }

    @Test
    public void testGuessMagic(){
        assertEquals(Left("Não foi desta vez"), guessMagic(2));
    }

    @Test
    public void testGuessMagic1(){
        assertEquals(Right(5), guessMagic(5));
    }
}

package ring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class RingsTest {

    @Test
    public void testIntegerRingSum() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Ring<Integer> integerRing = new IntegerRing();

        Integer result = Rings.sum(integers, integerRing);

        assertEquals(15, result);
    }

    @Test
    public void testIntegerRingProduct() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Ring<Integer> integerRing = new IntegerRing();

        Integer result = Rings.product(integers, integerRing);

        assertEquals(120, result);
    }

    @Test
    public void testDoubleRingSum() {
        List<Double> doubles = Arrays.asList(1.5, 2.5, 3.5, 4.5, 5.5);
        Ring<Double> doubleRing = new DoubleRing();

        Double result = Rings.sum(doubles, doubleRing);

        assertEquals(17.5, result, 0.001); // Using a delta for double comparison
    }

    @Test
    public void testDoubleRingProduct() {
        List<Double> doubles = Arrays.asList(1.5, 2.5, 3.5, 4.5, 5.5);
        Ring<Double> doubleRing = new DoubleRing();

        Double result = Rings.product(doubles, doubleRing);

        assertEquals(324.84375, result, 0.001); // Using a delta for double comparison
    }

    @Test
    public void testBigIntegerRingSum() {
        List<BigInteger> bigIntegers = Arrays.asList(
            new BigInteger("2"), new BigInteger("3"), new BigInteger("4"));
        Ring<BigInteger> bigIntegerRing = new BigIntegerRing();

        BigInteger result = Rings.sum(bigIntegers, bigIntegerRing);

        assertEquals(new BigInteger("9"), result);
    }

    @Test
    public void testBigIntegerRingProduct() {
        List<BigInteger> bigIntegers = Arrays.asList(
            new BigInteger("2"), new BigInteger("3"), new BigInteger("4"));
        Ring<BigInteger> bigIntegerRing = new BigIntegerRing();

        BigInteger result = Rings.product(bigIntegers, bigIntegerRing);

        assertEquals(new BigInteger("24"), result);
    }

    @Test
    public void testPolynomialCreation() {
        List<Integer> coefficients = Arrays.asList(1, 2, 3, 4);
        Polynomial<Integer> polynomial = Polynomial.from(coefficients);

        List<Integer> expectedCoefficients = Arrays.asList(1, 2, 3, 4);
        assertEquals(expectedCoefficients, polynomial.getCoefficients());
    }

    @Test
    public void testPolynomialAddition() {
        List<Integer> coefficients1 = Arrays.asList(1, 2);
        List<Integer> coefficients2 = Arrays.asList(4, 5, 6);

        Polynomial<Integer> polynomial1 = Polynomial.from(coefficients1);
        Polynomial<Integer> polynomial2 = Polynomial.from(coefficients2);

        Polynomial<Integer> result = polynomial1.plus(polynomial2, new IntegerRing());

        List<Integer> expectedCoefficients = Arrays.asList(5, 7, 6);
        assertEquals(expectedCoefficients, result.getCoefficients());
    }

    @Test
    public void testPolynomialAdditionWithShorterPolynomialLast() {
        List<Integer> coefficients1 = Arrays.asList(4, 5, 6);
        List<Integer> coefficients2 = Arrays.asList(1,2);

        Polynomial<Integer> polynomial1 = Polynomial.from(coefficients1);
        Polynomial<Integer> polynomial2 = Polynomial.from(coefficients2);

        Polynomial<Integer> result = polynomial2.plus(polynomial1, new IntegerRing());

        List<Integer> expectedCoefficients = Arrays.asList(5, 7, 6);
        assertEquals(expectedCoefficients, result.getCoefficients());
    }

    @Test
    public void testPolynomialMultiplication() {
        List<Integer> coefficients1 = Arrays.asList(1, 2);
        List<Integer> coefficients2 = Arrays.asList(4, 5, 6);

        Polynomial<Integer> polynomial1 = Polynomial.from(coefficients1);
        Polynomial<Integer> polynomial2 = Polynomial.from(coefficients2);

        Polynomial<Integer> result = polynomial1.times(polynomial2, new IntegerRing());

        List<Integer> expectedCoefficients = Arrays.asList(4, 13, 16, 12);
        assertEquals(expectedCoefficients, result.getCoefficients());
    }

    @Test
    public void testPolynomialMultiplicationWithShorterPolynomialLast() {
        List<Integer> coefficients1 = Arrays.asList(4, 5, 6);
        List<Integer> coefficients2 = Arrays.asList(1,2);

        Polynomial<Integer> polynomial1 = Polynomial.from(coefficients1);
        Polynomial<Integer> polynomial2 = Polynomial.from(coefficients2);

        Polynomial<Integer> result = polynomial2.times(polynomial1, new IntegerRing());

        List<Integer> expectedCoefficients = Arrays.asList(4, 13, 16, 12);
        assertEquals(expectedCoefficients, result.getCoefficients());
    }

    @Test
    public void testPolynomialRing() {
        PolynomialRing<Integer> polynomialRing = PolynomialRing.newInstance(new IntegerRing());

        Polynomial<Integer> zeroPolynomial = polynomialRing.zero();
        Polynomial<Integer> identityPolynomial = polynomialRing.identity();

        List<Integer> zeroCoefficients = Arrays.asList();
        List<Integer> identityCoefficients = Arrays.asList(1);

        assertEquals(zeroCoefficients, zeroPolynomial.getCoefficients());
        assertEquals(identityCoefficients, identityPolynomial.getCoefficients());
    }
}

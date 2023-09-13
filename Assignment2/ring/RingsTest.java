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
}

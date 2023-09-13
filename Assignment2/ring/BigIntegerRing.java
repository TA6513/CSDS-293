package ring;
import java.math.BigInteger;

//ring that supports the BigInteger type
public class BigIntegerRing implements Ring<BigInteger> {
    @Override
    public BigInteger zero() {
        return BigInteger.ZERO;
    }

    @Override
    public BigInteger identity() {
        return BigInteger.ONE;
    }

    @Override
    public BigInteger sum(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger product(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }
}
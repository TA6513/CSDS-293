package ring;
import java.math.BigInteger;
import java.util.Objects;

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
        Objects.requireNonNull(x, "input value cannot be null.");
        Objects.requireNonNull(y, "input value cannot be null.");   
        return x.add(y);
    }

    @Override
    public BigInteger product(BigInteger x, BigInteger y) {
        Objects.requireNonNull(x, "input value cannot be null.");
        Objects.requireNonNull(y, "input value cannot be null.");  
        return x.multiply(y);
    }
}
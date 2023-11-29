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
        if(x == null && y != null) {
            return y;
        }
        else if(y == null && x != null) {
            return x;
        }
        else if(x == null && y == null) {
            return BigInteger.ZERO;
        }
        else {
            return x.add(y);
        }
    }

    @Override
    public BigInteger product(BigInteger x, BigInteger y) {
        if(x == null || y == null) {
            return BigInteger.ZERO;
        }
        else {
            return x.multiply(y);
        }
    }
}
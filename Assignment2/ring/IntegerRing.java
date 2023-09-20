package ring;

import java.util.Objects;

//ring that supports the Integer type
public class IntegerRing implements Ring<Integer> {
    @Override
    public Integer zero() {
        return 0;
    }

    @Override
    public Integer identity() {
        return 1;
    }

    @Override
    public Integer sum(Integer x, Integer y) {
        Objects.requireNonNull(x, "input value cannot be null.");
        Objects.requireNonNull(y, "input value cannot be null.");   
        return x + y;
    }

    @Override
    public Integer product(Integer x, Integer y) {
        Objects.requireNonNull(x, "input value cannot be null.");
        Objects.requireNonNull(y, "input value cannot be null.");   
        return x * y;
    }
}

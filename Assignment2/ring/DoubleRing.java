package ring;

import java.util.Objects;

//ring that supports the Double type
public class DoubleRing implements Ring<Double> {
    @Override
    public Double zero() {
        return 0.0;
    }

    @Override
    public Double identity() {
        return 1.0;
    }

    @Override
    public Double sum(Double x, Double y) {
        Objects.requireNonNull(x, "input value cannot be null.");
        Objects.requireNonNull(y, "input value cannot be null.");   
        return x + y;
    }

    @Override
    public Double product(Double x, Double y) {
        Objects.requireNonNull(x, "input value cannot be null.");
        Objects.requireNonNull(y, "input value cannot be null.");   
        return x * y;
    }
}

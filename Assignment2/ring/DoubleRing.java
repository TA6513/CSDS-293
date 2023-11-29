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
        if(x == null && y != null) {
            return y;
        }
        else if(y == null && x != null) {
            return x;
        }
        else if(x == null && y == null) {
            return 0.0;
        }
        else {
            return x + y;
        }
    }

    @Override
    public Double product(Double x, Double y) {
        if(x == null || y == null) {
            return 0.0;
        }
        else {
            return x * y;
        }
    }
}

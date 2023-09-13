package ring;

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
        return x + y;
    }

    @Override
    public Double product(Double x, Double y) {
        return x * y;
    }
}

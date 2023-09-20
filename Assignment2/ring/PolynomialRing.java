package ring;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class PolynomialRing<T> implements Ring<Polynomial<T>> {
    private final Ring<T> baseRing;

    private PolynomialRing(Ring<T> baseRing) {
        this.baseRing = baseRing;
    }

    public static <S> PolynomialRing<S> newInstance(Ring<S> baseRing) {
        return new PolynomialRing<>(Objects.requireNonNull(baseRing, "Base ring cannot be null."));
    }

    @Override
    public Polynomial<T> zero() {
        List<T> zeroCoefficients = new ArrayList<>();
        return Polynomial.from(zeroCoefficients);
    }

    @Override
    public Polynomial<T> identity() {
        List<T> identityCoefficients = new ArrayList<>();
        identityCoefficients.add(baseRing.identity());
        return Polynomial.from(identityCoefficients);
    }

    @Override
    public Polynomial<T> sum(Polynomial<T> p, Polynomial<T> q) {
        Objects.requireNonNull(p, "Polynomial 'p' cannot be null.");
        Objects.requireNonNull(q, "Polynomial 'q' cannot be null.");
        return p.plus(q, baseRing);
    }

    @Override
    public Polynomial<T> product(Polynomial<T> p, Polynomial<T> q) {
        Objects.requireNonNull(p, "Polynomial 'p' cannot be null.");
        Objects.requireNonNull(q, "Polynomial 'q' cannot be null.");
        return p.times(q, baseRing);
    }
}

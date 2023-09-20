package ring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public final class Polynomial<T> implements Iterable<T> {
    private final List<T> coefficients;

    private Polynomial(List<T> coefficients) {
        this.coefficients = Objects.requireNonNull(coefficients, "Coefficients cannot be null.");
    }

    public static final <S> Polynomial<S> from(List<S> coefficients) {
        Objects.requireNonNull(coefficients, "Coefficients cannot be null.");
        return new Polynomial<>(coefficients);
    }

    public List<T> getCoefficients() {
        return new ArrayList<>(coefficients);
    }

    @Override
    public Iterator<T> iterator() {
        return coefficients.iterator();
    }

    public ListIterator<T> listIterator(int i) {
        return coefficients.listIterator(i);
    }

    @Override
    public String toString() {
        return coefficients.toString();
    }

    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {
        Objects.requireNonNull(other, "Polynomial 'other' cannot be null.");
        Objects.requireNonNull(ring, "input ring cannot be null.");
        int size1 = this.coefficients.size();
        int size2 = other.coefficients.size();
        int maxSize = Math.max(size1, size2);

        List<T> resultCoefficients = new ArrayList<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            T coeff1 = (i < size1) ? this.coefficients.get(i) : ring.zero();
            T coeff2 = (i < size2) ? other.coefficients.get(i) : ring.zero();

            T sum = ring.sum(coeff1, coeff2);
            resultCoefficients.add(sum);
        }

        return new Polynomial<>(resultCoefficients);
    }

    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        Objects.requireNonNull(other, "Polynomial 'other' cannot be null.");
        Objects.requireNonNull(ring, "input ring cannot be null.");
        int size1 = this.coefficients.size();
        int size2 = other.coefficients.size();
        int resultSize = size1 + size2 - 1;

        List<T> resultCoefficients = new ArrayList<>(resultSize);

        for (int i = 0; i < resultSize; i++) {
            T product = ring.zero();
            for (int j = 0; j <= i; j++) {
                if (j < size1 && (i - j) < size2) {
                    T coeff1 = this.coefficients.get(j);
                    T coeff2 = other.coefficients.get(i - j);
                    T termProduct = ring.product(coeff1, coeff2);
                    product = ring.sum(product, termProduct);
                }
            }
            resultCoefficients.add(product);
        }

        return new Polynomial<>(resultCoefficients);
    }
}

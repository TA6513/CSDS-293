package ring;

import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;

//class to provide ring operations
public final class Rings {
    public static <T> T reduce(List<T> args, T zero, BinaryOperator<T> accumulator) {
        Objects.requireNonNull(args, "args cannot be null.");

        boolean foundAny = false;
        T result = zero;

        for (T element : args) {
            if (!foundAny) {
                foundAny = true;
                result = element;
            } else {
                result = accumulator.apply(result, element);
            }
        }

        return result;
    }

    public static final <T> T sum(List<T> args, Ring<T> ring) {
        // Error checking for null input in public methods
        Objects.requireNonNull(args, "args cannot be null.");
        Objects.requireNonNull(ring, "input ring cannot be null.");

        return reduce(args, ring.zero(), ring::sum);
    }

    public static final <T> T product(List<T> args, Ring<T> ring) {
        // Error checking for null input in public methods
        Objects.requireNonNull(args, "args cannot be null.");
        Objects.requireNonNull(ring, "input ring cannot be null.");

        return reduce(args, ring.identity(), ring::product);
    }
}
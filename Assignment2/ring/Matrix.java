package ring;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface Matrix<T> {

    public static <T> Map<Indexes, T> createMatrix(int rows, int columns, Function<Indexes, T> valueMapper) {
        Map<Indexes, T> matrix = new HashMap<>();
        Indexes size = new Indexes(rows - 1, columns - 1);

        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.column(); col++) {
                Indexes index = new Indexes(row, col);
                T value = valueMapper.apply(index);

                // Replace null values with zero
                if (value == null) {
                    value = getDefaultValue(valueMapper.apply(index));
                }

                matrix.put(index, value);
            }
        }

        return matrix;
    }

    private static <T> T getDefaultValue(T value) {
        if (Integer.class.isAssignableFrom(value.getClass())) {
            return (T) Integer.valueOf(0);
        } else if (BigInteger.class.isAssignableFrom(value.getClass())) {
            return (T) BigInteger.valueOf(0);
        } else if (Double.class.isAssignableFrom(value.getClass())) {
            return (T) Double.valueOf(0);
        } else {
            throw new IllegalArgumentException("Unsupported numeric type");
        }
    }
    
    public <T> Matrix<T> instance(int rows, int columns, Function<Indexes, T> valueMapper);

    public <T> Matrix<T> instance(Indexes size, Function<Indexes, T> valueMapper);

    public <T> Matrix<T> constant(int size, T value);

    public <T> Matrix<T> identity(int size, T zero, T identity);

    public <T> Matrix<T> from(T[][] matrixArray);

    Indexes size();

    T value(Indexes indexes);

    T value(int row, int column);

    T value(Matrix<T> matrix, Indexes indexes);

    Matrix<T> plus(Matrix<T> other, Ring<T> ring);

    Matrix<T> times(Matrix<T> other, Ring<T> ring);

    String toString();
}
package ring;
import java.util.*;
import java.util.function.Function;

public final class MatrixMap<T> {
    private final Map<Indexes, T> matrix;
    private final Indexes size;

    private MatrixMap(Map<Indexes, T> matrix, Indexes size) {
        this.matrix = matrix;
        this.size = size;
    }

    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be greater than zero.");
        }

        Map<Indexes, S> matrix = new HashMap<>();
        Indexes size = new Indexes(rows - 1, columns - 1);

        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.column(); col++) {
                Indexes index = new Indexes(row, col);
                matrix.put(index, valueMapper.apply(index));
            }
        }

        return new MatrixMap<>(matrix, size);
    }

    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {
        return instance(size.row() + 1, size.column() + 1, valueMapper);
    }

    public static <S> MatrixMap<S> constant(int size, S value) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }

        Map<Indexes, S> matrix = new HashMap<>();
        Indexes sizeIndex = new Indexes(size - 1, size - 1);

        for (int row = 0; row <= sizeIndex.row(); row++) {
            for (int col = 0; col <= sizeIndex.column(); col++) {
                Indexes index = new Indexes(row, col);
                matrix.put(index, value);
            }
        }

        return new MatrixMap<>(matrix, sizeIndex);
    }

    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }

        Map<Indexes, S> matrix = new HashMap<>();
        Indexes sizeIndex = new Indexes(size - 1, size - 1);

        for (int row = 0; row <= sizeIndex.row(); row++) {
            for (int col = 0; col <= sizeIndex.column(); col++) {
                Indexes index = new Indexes(row, col);
                S value = (index.areDiagonal()) ? identity : zero;
                matrix.put(index, value);
            }
        }

        return new MatrixMap<>(matrix, sizeIndex);
    }

    public static <S> MatrixMap<S> from(S[][] matrixArray) {
        int rows = matrixArray.length;
        int columns = (rows > 0) ? matrixArray[0].length : 0;

        Map<Indexes, S> matrix = new HashMap<>();
        Indexes size = new Indexes(rows - 1, columns - 1);

        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.column(); col++) {
                Indexes index = new Indexes(row, col);
                if (row >= 0 && row < rows && col >= 0 && col < columns) {
                    matrix.put(index, matrixArray[row][col]);
                }
            }
        }

        return new MatrixMap<>(matrix, size);
    }

    public Indexes size() {
        return size;
    }

    public T value(Indexes indexes) {
        return matrix.getOrDefault(indexes, null);
    }

    public T value(int row, int column) {
        return value(new Indexes(row, column));
    }

    public static <S> S value(MatrixMap<S> matrix, Indexes indexes) {
        return matrix.value(indexes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.column(); col++) {
                Indexes index = new Indexes(row, col);
                T value = matrix.get(index);
                sb.append((value != null) ? value.toString() : "").append("\t");

                if (col == size.column()) {
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
    }

    public static class InvalidLengthException extends IllegalArgumentException {
        public enum Cause {
            ROW, COLUMN
        }

        private final Cause cause;
        private final int length;

        public InvalidLengthException(Cause cause, int length) {
            super();
            this.cause = cause;
            this.length = length;
        }

        public Cause getCauseType() {
            return cause;
        }

        public int getLength() {
            return length;
        }

        @Override
        public String getMessage() {
            return "Invalid length for " + cause.toString() + ": " + length;
        }
    }

}

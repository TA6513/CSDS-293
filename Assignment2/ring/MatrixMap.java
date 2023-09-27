package ring;

import java.util.*;
import java.util.function.Function;

// Represents a generic matrix. Provides methods for creating, accessing, and manipulating matrices of arbitrary types.
public final class MatrixMap<T> {
    private final Map<Indexes, T> matrix;
    private final Indexes size;

    private MatrixMap(Map<Indexes, T> matrix, Indexes size) {
        this.matrix = matrix;
        this.size = size;
    }

    // Creates a matrix using and initializes its elements based on the provided
    // valueMapper function.
    private static <S> Map<Indexes, S> createMatrix(int rows, int columns, Function<Indexes, S> valueMapper) {
        Map<Indexes, S> matrix = new HashMap<>();
        Indexes size = new Indexes(rows - 1, columns - 1);

        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.column(); col++) {
                Indexes index = new Indexes(row, col);
                matrix.put(index, valueMapper.apply(index));
            }
        }

        return matrix;
    }

    // Creates a matrix with specified dimensions and initializes values using a
    // valueMapper function.
    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be greater than zero.");
        }

        return new MatrixMap<>(createMatrix(rows, columns, valueMapper), new Indexes(rows - 1, columns - 1));
    }

    // Creates a matrix with specified dimensions and initializes values using a
    // valueMapper function.
    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {
        return instance(size.row() + 1, size.column() + 1, valueMapper);
    }

    // Creates a constant matrix of a given size with all elements initialized to a
    // specific value.
    public static <S> MatrixMap<S> constant(int size, S value) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }

        return new MatrixMap<>(createMatrix(size, size, index -> value), new Indexes(size - 1, size - 1));
    }

    // Creates an identity matrix of a given size with specified zero and identity
    // values.
    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }

        return new MatrixMap<>(createMatrix(size, size, index -> (index.areDiagonal()) ? identity : zero),
                new Indexes(size - 1, size - 1));
    }

    // Creates a matrix from a two-dimensional array of values.
    public static <S> MatrixMap<S> from(S[][] matrixArray) {
        int rows = matrixArray.length;
        int columns = (rows > 0) ? matrixArray[0].length : 0;

        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be greater than zero.");
        }

        Map<Indexes, S> matrix = new HashMap<>();
        Indexes size = new Indexes(rows - 1, columns - 1);

        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.column(); col++) {
                Indexes index = new Indexes(row, col);
                if (row < rows && col < columns) {
                    matrix.put(index, matrixArray[row][col]);
                }
            }
        }

        return new MatrixMap<>(matrix, size);
    }

    // Returns the size of the matrix.
    public Indexes size() {
        return size;
    }

    // Returns the value at the specified indexes in the matrix.
    public T value(Indexes indexes) {
        return matrix.getOrDefault(indexes, null);
    }

    // Returns the value at the specified row and column in the matrix.
    public T value(int row, int column) {
        return value(new Indexes(row, column));
    }

    // Returns the value at the specified indexes in a given matrix.
    public static <S> S value(MatrixMap<S> matrix, Indexes indexes) {
        return matrix.value(indexes);
    }

    // Generates a string representation of the matrix.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0, numRows = size.row(), numCols = size.column(); row <= numRows; row++) {
            for (int col = 0; col <= numCols; col++) {
                Indexes index = new Indexes(row, col);
                T value = matrix.getOrDefault(index, null);
                sb.append((value != null) ? value.toString() : "").append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // Custom exception class for invalid length with cause and length information.
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
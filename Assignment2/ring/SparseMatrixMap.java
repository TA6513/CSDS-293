package ring;

import java.util.*;
import java.util.function.Function;

// Represents a generic matrix. Provides methods for creating, accessing, and manipulating matrices of arbitrary types.
public final class SparseMatrixMap<S> implements Matrix<S> {
    private final Map<Indexes, S> matrix;
    private final Indexes size;

    public SparseMatrixMap(Map<Indexes, S> matrix, Indexes size) {
        this.matrix = matrix;
        this.size = size;
    }

    public static <S> Map<Indexes, S> createSparseMatrix(int rows, int columns, Function<Indexes, S> valueMapper) {
        Map<Indexes, S> sparseMatrix = new HashMap<>();
        Indexes size = new Indexes(rows - 1, columns - 1);

        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.column(); col++) {
                Indexes index = new Indexes(row, col);
                S value = valueMapper.apply(index);
                if (!value.equals(0)) {
                    sparseMatrix.put(index, value);
                }
            }
        }

        return sparseMatrix;
    }

    // Creates a matrix with specified dimensions and initializes values using a
    // valueMapper function.
    @Override
    public <S> SparseMatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);

        return new SparseMatrixMap<>(createSparseMatrix(rows, columns, valueMapper),
                new Indexes(rows - 1, columns - 1));
    }

    // Creates a matrix with specified dimensions and initializes values using a
    // valueMapper function.
    @Override
    public <S> SparseMatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {
        Objects.requireNonNull(size);
        Objects.requireNonNull(valueMapper);

        return instance(size.row() + 1, size.column() + 1, valueMapper);
    }

    // Creates a constant matrix of a given size with all elements initialized to a
    // specific value.
    @Override
    public <S> SparseMatrixMap<S> constant(int size, S value) {
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);
        Objects.requireNonNull(value);

        return new SparseMatrixMap<>(createSparseMatrix(size, size, index -> value), new Indexes(size - 1, size - 1));
    }

    // Creates an identity matrix of a given size with specified zero and identity
    // values.
    @Override
    public <S> SparseMatrixMap<S> identity(int size, S zero, S identity) {
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);

        return new SparseMatrixMap<>(createSparseMatrix(size, size, index -> (index.areDiagonal()) ? identity : zero),
                new Indexes(size - 1, size - 1));
    }

    // Creates a matrix from a two-dimensional array of values.
    @Override
    public <S> SparseMatrixMap<S> from(S[][] matrixArray) {
        Objects.requireNonNull(matrixArray);

        int rows = matrixArray.length;
        int columns = (rows > 0) ? matrixArray[0].length : 0;

        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);

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

        return new SparseMatrixMap<>(matrix, size);
    }

    // Returns the size of the matrix.
    @Override
    public Indexes size() {
        return size;
    }

    // Returns the value at the specified indexes in the matrix.
    @Override
    public S value(Indexes indexes) {
        Objects.requireNonNull(indexes);

        return matrix.getOrDefault(indexes, null);
    }

    // Returns the value at the specified row and column in the matrix.
    @Override
    public S value(int row, int column) {
        assert row > 0;
        assert column > 0;

        if (row < 0) {
            row = 0;
        }
        if (column < 0) {
            column = 0;
        }

        return value(new Indexes(row, column));
    }

    // Returns the value at the specified indexes in a given matrix.
    @Override
    public S value(Matrix<S> matrix, Indexes indexes) {
        Objects.requireNonNull(indexes);
        Objects.requireNonNull(matrix);

        return matrix.value(indexes);
    }

    // Generates a string representation of the matrix.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0, numRows = size.row(), numCols = size.column(); row <= numRows; row++) {
            for (int col = 0; col <= numCols; col++) {
                Indexes index = new Indexes(row, col);
                S value = matrix.getOrDefault(index, null);
                sb.append((value != null) ? value.toString() : "").append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // Perform matrix addition
    @Override
    public SparseMatrixMap<S> plus(Matrix<S> other, Ring<S> ring) {
        InconsistentSizeException.requireMatchingSize(this, other);

        Map<Indexes, S> resultMatrix = new HashMap<>();

        for (Indexes index : this.matrix.keySet()) {
            S sum;
            S thisValue = this.value(index); // Get the value from this.matrix
            S otherValue = other.value(index);
            if (thisValue == null || otherValue == ring.zero() || otherValue == null) {
                sum = null;
            }
            sum = ring.sum(thisValue, otherValue);
            resultMatrix.put(index, sum);
        }

        return new SparseMatrixMap<>(resultMatrix, size);
    }

    // Perform matrix multiplication
    @Override
    public SparseMatrixMap<S> times(Matrix<S> other, Ring<S> ring) {
        // Check for inconsistent sizes
        InconsistentSizeException.requireMatchingSize(this, other);
        NonSquareException.requireDiagonal(this.size);

        Map<Indexes, S> resultMatrix = new HashMap<>();
        Indexes size = this.size;

        for (int i = 0; i <= size.row(); i++) {
            for (int j = 0; j <= size.column(); j++) {
                Indexes currentIndex = new Indexes(i, j);
                S sum = ring.zero();

                for (int k = 0; k <= size.column(); k++) {
                    Indexes rowIndex = new Indexes(i, k);
                    Indexes colIndex = new Indexes(k, j);
                    S product;
                    if(this.value(rowIndex) == null || other.value(colIndex) == null || other.value(colIndex) == ring.zero()) {
                        product = null;
                    }
                    else {
                        product = ring.product(this.value(rowIndex), other.value(colIndex));
                    }
                    sum = ring.sum(sum, product);
                }
                
                if(sum == ring.zero()) {
                    resultMatrix.put(currentIndex, null);
                }
                else {
                    resultMatrix.put(currentIndex, sum);
                }
            }
        }

        return new SparseMatrixMap<>(resultMatrix, size);
    }

    public MatrixMap<S> makeNonSparse() {
        Map<Indexes, S> matrix = MatrixMap.createMatrix(size.row() + 1, size.column() + 1, this::value);
        return new MatrixMap<S>(matrix, size);
    }

    // Custom exception class for invalid length with cause and length information.
    public static class InvalidLengthException extends IllegalArgumentException {
        public enum Cause {
            ROW, COLUMN
        }

        private final Cause cause;
        private final int length;

        public InvalidLengthException(Cause cause, int length) {
            super("Length is invalid");
            this.cause = cause;
            this.length = length;
        }

        public Cause getCauseType() {
            return cause;
        }

        public int getLength() {
            return length;
        }

        public static int requireNonEmpty(Cause cause, int length) {
            if (length <= 0) {
                throw new IllegalArgumentException("Size must be greater than zero.",
                        new InvalidLengthException(cause, length));
            }
            return length;
        }

        @Override
        public String getMessage() {
            return "Invalid length for " + cause.toString() + ": " + length;
        }
    }

    public static class InconsistentSizeException extends IllegalArgumentException {
        private final Indexes thisIndexes;
        private final Indexes otherIndexes;

        public InconsistentSizeException(Indexes thisIndexes, Indexes otherIndexes) {
            super("Matrix sizes do not match");
            this.thisIndexes = thisIndexes;
            this.otherIndexes = otherIndexes;
        }

        public Indexes getThisIndexes() {
            return thisIndexes;
        }

        public Indexes getOtherIndexes() {
            return otherIndexes;
        }

        public static <S> Indexes requireMatchingSize(Matrix<S> thisMatrix, Matrix<S> otherMatrix) {
            Indexes thisSize = thisMatrix.size();
            Indexes otherSize = otherMatrix.size();

            if (!thisSize.equals(otherSize)) {
                throw new IllegalArgumentException("Matrix sizes do not match",
                        new InconsistentSizeException(thisSize, otherSize));
            }

            return thisSize;
        }

    }

    public static class NonSquareException extends IllegalStateException {
        private final Indexes indexes;

        public NonSquareException(Indexes indexes) {
            super("Matrix is not square");
            this.indexes = indexes;
        }

        public Indexes getIndexes() {
            return indexes;
        }

        public static Indexes requireDiagonal(Indexes indexes) {
            if (indexes.row() != indexes.column()) {
                throw new IllegalStateException("Indexes must be on the diagonal",
                        new NonSquareException(indexes));
            }
            return indexes;
        }
    }

}
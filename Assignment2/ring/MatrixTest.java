package ring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

public class MatrixTest {

    @Test
    void testPlusAndTimes() {
        Ring<Integer> integerRing = new IntegerRing();

        // Create dense matrices
        MatrixMap<Integer> denseMatrix1 = new MatrixMap<>(MatrixMap.createMatrix(3, 3, indexes -> indexes.row() * indexes.column()), new Indexes(2, 2) );
        MatrixMap<Integer> denseMatrix2 = new MatrixMap<>(MatrixMap.createMatrix(3, 3, indexes -> indexes.row() * indexes.column()), new Indexes(2, 2) );

        // Create sparse matrices
        SparseMatrixMap<Integer> sparseMatrix1 = new SparseMatrixMap<>(SparseMatrixMap.createSparseMatrix(3, 3, indexes -> indexes.row() * indexes.column()), new Indexes(2, 2) );
        SparseMatrixMap<Integer> sparseMatrix2 = new SparseMatrixMap<>(SparseMatrixMap.createSparseMatrix(3, 3, indexes -> indexes.row() * indexes.column()), new Indexes(2, 2) );

        // Test plus method
        MatrixMap<Integer> plusResultDense = denseMatrix1.plus(denseMatrix2, integerRing);
        SparseMatrixMap<Integer> plusResultSparse = sparseMatrix1.plus(sparseMatrix2, integerRing);
        MatrixMap<Integer> plusResultBothDense = denseMatrix1.plus(sparseMatrix2, integerRing);
        SparseMatrixMap<Integer> plusResultBothSparse = sparseMatrix1.plus(denseMatrix2, integerRing);

        for (int row = 0; row <= plusResultDense.size().row(); row++) {
            for (int col = 0; col <= plusResultDense.size().column(); col++) {
                Indexes index = new Indexes(row, col);
                Integer valueDense = plusResultDense.value(index);
                Integer valueSparse = plusResultSparse.value(index);

                // Handle the case where 0 in dense matrix is equivalent to null in sparse matrix
                if (valueDense == integerRing.zero()) {
                    valueDense =  null;
                }

                assertEquals(valueDense, valueSparse);
            }
        }

        for (int row = 0; row <= plusResultBothDense.size().row(); row++) {
            for (int col = 0; col <= plusResultBothDense.size().column(); col++) {
                Indexes index = new Indexes(row, col);
                Integer valueDense = plusResultBothDense.value(index);
                Integer valueSparse = plusResultBothSparse.value(index);

                // Handle the case where 0 in dense matrix is equivalent to null in sparse matrix
                if (valueDense == integerRing.zero()) {
                    valueDense =  null;
                }

                assertEquals(valueDense, valueSparse);
            }
        }

        // Test times method
        MatrixMap<Integer> timesResultDense = denseMatrix1.times(denseMatrix2, integerRing);
        SparseMatrixMap<Integer> timesResultSparse = sparseMatrix1.times(sparseMatrix2, integerRing);
        MatrixMap<Integer> timesResultBothDense = denseMatrix1.times(sparseMatrix2, integerRing);
        SparseMatrixMap<Integer> timesResultBothSparse = sparseMatrix1.times(denseMatrix2, integerRing);

        for (int row = 0; row <= timesResultDense.size().row(); row++) {
            for (int col = 0; col <= timesResultDense.size().column(); col++) {
                Indexes index = new Indexes(row, col);
                Integer valueDense = timesResultDense.value(index);
                Integer valueSparse = timesResultSparse.value(index);

                // Handle the case where 0 in dense matrix is equivalent to null in sparse matrix
                if (valueDense == 0) {
                    valueDense =  null;
                }

                assertEquals(valueDense, valueSparse);
            }
        }

        for (int row = 0; row <= timesResultBothDense.size().row(); row++) {
            for (int col = 0; col <= timesResultBothDense.size().column(); col++) {
                Indexes index = new Indexes(row, col);
                Integer valueDense = timesResultBothDense.value(index);
                Integer valueSparse = timesResultBothSparse.value(index);

                // Handle the case where 0 in dense matrix is equivalent to null in sparse matrix
                if (valueDense == 0) {
                    valueDense =  null;
                }

                assertEquals(valueDense, valueSparse);
            }
        }
    }

    @Test
    void testMakeSparseAndMakeNonSparse() {
        // Create a dense matrix with many zeroes
        MatrixMap<Integer> denseMatrix = new MatrixMap<>(MatrixMap.createMatrix(3, 3, indexes -> 0), new Indexes(2, 2));

        // Convert dense matrix to sparse
        SparseMatrixMap<Integer> sparseMatrix = denseMatrix.makeSparse();

        // Convert sparse matrix to dense
        MatrixMap<Integer> denseMatrixFromSparse = sparseMatrix.makeNonSparse();

        // Check if the original dense matrix is equal to the one obtained from converting sparse to dense
        assertEquals(denseMatrix.size(), denseMatrixFromSparse.size());

        for (int row = 0; row <= denseMatrix.size().row(); row++) {
            for (int col = 0; col <= denseMatrix.size().column(); col++) {
                Indexes index = new Indexes(row, col);
                Integer valueDense = denseMatrix.value(index);
                Integer valueSparse = denseMatrixFromSparse.value(index);

                // Handle the case where 0 in dense matrix is equivalent to null in sparse matrix
                if (valueDense == 0) {
                    valueDense =  null;
                }

                assertEquals(valueDense, valueSparse);
            }
        }
    }

    @Test
    void stressTest() {
        Ring<Integer> integerRing = new IntegerRing();

        MatrixMap<Integer> denseMatrix = new MatrixMap<>(MatrixMap.createMatrix(100, 100, indexes -> Integer.valueOf(indexes.row() * indexes.column())), new Indexes(999, 999));
        SparseMatrixMap<Integer> sparseMatrix = new SparseMatrixMap<>(MatrixMap.createMatrix(100, 100, indexes -> Integer.valueOf(indexes.row() * indexes.column())), new Indexes(999, 999));

        MatrixMap<Integer> timesResultBothDense = denseMatrix.times(sparseMatrix, integerRing);

        assertEquals(timesResultBothDense.size(), denseMatrix.size());
    }
}

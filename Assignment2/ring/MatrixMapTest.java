package ring;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MatrixMapTest {
    private MatrixMap<Integer> matrix;

    @Before
    public void setUp() {
        // Create a sample matrix for testing
        matrix = MatrixMap.instance(3, 3, (Indexes index) -> index.row() * 3 + index.column());
    }

    @Test
    public void testInstance() {
        // Test the size of the matrix
        assertEquals(new Indexes(2, 2), matrix.size());

        // Test specific values in the matrix
        assertEquals(Integer.valueOf(0), matrix.value(0, 0));
        assertEquals(Integer.valueOf(4), matrix.value(1, 1));
        assertEquals(Integer.valueOf(8), matrix.value(2, 2));
    }

    @Test
    public void testConstant() {
        MatrixMap<Integer> constantMatrix = MatrixMap.constant(4, 42);

        // Test the size of the constant matrix
        assertEquals(new Indexes(3, 3), constantMatrix.size());

        // Test specific values in the constant matrix
        assertEquals(Integer.valueOf(42), constantMatrix.value(0, 0));
        assertEquals(Integer.valueOf(42), constantMatrix.value(2, 2));
    }

    @Test
    public void testIdentity() {
        MatrixMap<Integer> identityMatrix = MatrixMap.identity(4, 0, 1);

        // Test the size of the identity matrix
        assertEquals(new Indexes(3, 3), identityMatrix.size());

        // Test specific values in the identity matrix
        assertEquals(Integer.valueOf(1), identityMatrix.value(0, 0));
        assertEquals(Integer.valueOf(0), identityMatrix.value(1, 2));
    }

    @Test
    public void testFrom() {
        Integer[][] array = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 }
        };

        MatrixMap<Integer> fromMatrix = MatrixMap.from(array);

        // Test the size of the matrix created from the array
        assertEquals(new Indexes(2, 2), fromMatrix.size());

        // Test specific values in the matrix created from the array
        assertEquals(Integer.valueOf(1), fromMatrix.value(0, 0));
        assertEquals(Integer.valueOf(5), fromMatrix.value(1, 1));
        assertEquals(Integer.valueOf(9), fromMatrix.value(2, 2));
    }

    @Test
    public void testBigIntegerMatrix() {
        // Test MatrixMap with BigInteger
        MatrixMap<BigInteger> matrix1 = MatrixMap.constant(2, BigInteger.ONE);
        MatrixMap<BigInteger> matrix2 = MatrixMap.constant(2, BigInteger.TEN);

        // Matrix addition
        MatrixMap<BigInteger> sumMatrix = matrix1.plus(matrix2, new BigIntegerRing());
        assertEquals(BigInteger.valueOf(11), sumMatrix.value(0, 0));
        assertEquals(BigInteger.valueOf(11), sumMatrix.value(0, 1));
        assertEquals(BigInteger.valueOf(11), sumMatrix.value(1, 0));
        assertEquals(BigInteger.valueOf(11), sumMatrix.value(1, 1));

        // Matrix multiplication
        MatrixMap<BigInteger> identityMatrix = MatrixMap.identity(2, BigInteger.ZERO, BigInteger.ONE);
        MatrixMap<BigInteger> productMatrix = sumMatrix.times(identityMatrix, new BigIntegerRing());
        assertEquals(BigInteger.valueOf(11), productMatrix.value(0, 0));
        assertEquals(BigInteger.valueOf(11), productMatrix.value(0, 1));
        assertEquals(BigInteger.valueOf(11), productMatrix.value(1, 0));
        assertEquals(BigInteger.valueOf(11), productMatrix.value(1, 1));
    }

    @Test
    public void testPolynomialMatrix() {
        // Create a PolynomialRing over BigInteger
        Ring<Polynomial<BigInteger>> polynomialRing = PolynomialRing.newInstance(new BigIntegerRing());
        Ring<BigInteger> bigIntegerRing = new BigIntegerRing();

        // Create two polynomials
        Polynomial<BigInteger> poly1 = Polynomial.from(List.of(BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE)); // x^2
                                                                                                                  // + 1
        Polynomial<BigInteger> poly2 = Polynomial.from(List.of(BigInteger.TEN, BigInteger.ONE)); // 10x + 10

        // Create two 2x2 identity matrices with Polynomial elements
        MatrixMap<Polynomial<BigInteger>> polyMatrix1 = MatrixMap.identity(2, poly1, polynomialRing.zero());
        MatrixMap<Polynomial<BigInteger>> polyMatrix2 = MatrixMap.identity(2, poly2, polynomialRing.zero());

        // Fill the matrices with polynomials
        polyMatrix1 = MatrixMap.instance(polyMatrix1.size(), indexes -> poly1);
        polyMatrix2 = MatrixMap.instance(polyMatrix2.size(), indexes -> poly2);

        // Matrix addition
        MatrixMap<Polynomial<BigInteger>> polySumMatrix = polyMatrix1.plus(polyMatrix2, polynomialRing);

        // Matrix multiplication
        MatrixMap<Polynomial<BigInteger>> polyProductMatrix = polyMatrix1.times(polyMatrix2, polynomialRing);

        // Expected result
        Polynomial<BigInteger> addResult = poly1.plus(poly2, bigIntegerRing);

        for (int row = 0; row <= polySumMatrix.size().row(); row++) {
            for (int col = 0; col <= polySumMatrix.size().column(); col++) {
                assertEquals(addResult, polySumMatrix.value(row, col));
            }
        }

        // Expected result
        Polynomial<BigInteger> multResult = poly1.times(poly2, bigIntegerRing);

        for (int row = 0; row <= polyProductMatrix.size().row(); row++) {
            for (int col = 0; col <= polyProductMatrix.size().column(); col++) {
                assertEquals(multResult, polyProductMatrix.value(row, col));
            }
        }
    }
}

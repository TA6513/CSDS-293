package ring;
import static org.junit.Assert.*;
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
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        MatrixMap<Integer> fromMatrix = MatrixMap.from(array);

        // Test the size of the matrix created from the array
        assertEquals(new Indexes(2, 2), fromMatrix.size());

        // Test specific values in the matrix created from the array
        assertEquals(Integer.valueOf(1), fromMatrix.value(0, 0));
        assertEquals(Integer.valueOf(5), fromMatrix.value(1, 1));
        assertEquals(Integer.valueOf(9), fromMatrix.value(2, 2));
    }
}

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class PolynomialInterpolatorTest {

    @Test
    public void testPolynomialWithSingleRoot() {
        List<Integer> roots = Arrays.asList(2);
        List<Integer> expectedCoefficients = Arrays.asList(1, -2);

        List<Integer> result = PolynomialInterpolator.findPolynomial(roots);

        assertEquals(expectedCoefficients, result);
    }

    @Test
    public void testPolynomialWithMultipleRoots() {
        List<Integer> roots = Arrays.asList(1, 2, 3);
        List<Integer> expectedCoefficients = Arrays.asList(1, -6, 11, -6);

        List<Integer> result = PolynomialInterpolator.findPolynomial(roots);

        assertEquals(expectedCoefficients, result);
    }

    @Test
    public void testPolynomialWithNullInput() {
        try {
            PolynomialInterpolator.findPolynomial(null);
            fail("Expected IllegalArgumentException for null input.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}

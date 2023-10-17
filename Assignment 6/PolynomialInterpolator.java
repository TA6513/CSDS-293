import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PolynomialInterpolator {

    public static List<Integer> multiplyPolynomials(List<Integer> poly1, List<Integer> poly2) {
        int m = poly1.size();
        int n = poly2.size();
        List<Integer> result = new ArrayList<>(m + n - 1);

        // Initialize result with zeros
        for (int i = 0; i < m + n - 1; i++) {
            result.add(0);
        }

        // Multiply the polynomials
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result.set(i + j, result.get(i + j) + poly1.get(i) * poly2.get(j));
            }
        }

        return result;
    }

    public static List<Integer> findPolynomial(List<Integer> xValues) {
        if (xValues == null) {
            throw new IllegalArgumentException("Input list of xValues cannot be null.");
        }

        int n = xValues.size();
        List<Integer> result = new ArrayList<>(Arrays.asList(1));

        for (int i = 0; i < n; i++) {
            List<Integer> factor = new ArrayList<>(Arrays.asList(-xValues.get(i), 1));
            result = multiplyPolynomials(result, factor);
        }

        // Reverse the coefficients list to get the correct order
        Collections.reverse(result);

        return result;
    }
}

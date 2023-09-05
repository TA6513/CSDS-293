import java.util.*;
import java.util.stream.*;

public class Assignment1 {
    public static <T extends Comparable<? super T>> List<T> longestHigherSuffix(
            List<T> a, List<T> b, Comparator<T> cmp) {

        int minLength = Math.min(a.size(), b.size());

        List<T> reversedA = new ArrayList<>(a);
        Collections.reverse(reversedA);

        List<T> reversedB = new ArrayList<>(b);
        Collections.reverse(reversedB);

        List<T> result = IntStream.range(0, minLength)
                .takeWhile(i -> cmp.compare(reversedA.get(i), reversedB.get(i)) >= 0)
                .mapToObj(i -> reversedA.get(i))
                .collect(Collectors.toList());

        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(6, 1, 3, 4, 8);
        List<Integer> list2 = Arrays.asList(9, 2, 3, 4, 5);

        List<Integer> longestSuffix = longestHigherSuffix(list1, list2, Comparator.naturalOrder());

        System.out.println(longestSuffix);
    }
}

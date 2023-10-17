import java.util.*;
import java.util.stream.*;

public class Assignment1 {
    public static <T extends Comparable<? super T>> List<T> longestHigherSuffix(
            List<T> a, List<T> b, Comparator<T> cmp) {

        int minLength = Math.min(a.size(), b.size()); //get the length of the shortest list
        List<T> reversedA = new ArrayList<>(a); //create a reversed version of list a for easier traversal
        Collections.reverse(reversedA);

        List<T> reversedB = new ArrayList<>(b); //create a reversed version of list b for easier traversal
        Collections.reverse(reversedB);

        List<T> result = IntStream.range(0, minLength) //create stream of indices from 0 to minLength
                .takeWhile(i -> cmp.compare(reversedA.get(i), reversedB.get(i)) >= 0) //filter stream to only include 
                                                                                      //indices where a >= b
                .mapToObj(i -> reversedA.get(i))                                      //converts elements at indices to stream
                .collect(Collectors.toList());                                        //combine elements of suffix in a list

        Collections.reverse(result); //reverse the list to undo the original reversal
        return result; //return the list
    }

    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(6, 1, 3, 4, 8);
        List<Integer> list2 = Arrays.asList(9, 2, 3, 4, 5);

        List<Integer> longestSuffix = longestHigherSuffix(list1, list2, Comparator.naturalOrder());

        System.out.println(longestSuffix);
    }
}

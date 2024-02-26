import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Integer[] integers = {5, 59, 987, 4, 12, 2, 4, 36, 7};
        Arrays.sort(integers, Comparator.reverseOrder());
        System.out.println(Arrays.toString(integers));
    }

}
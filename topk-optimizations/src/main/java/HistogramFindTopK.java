import java.util.Arrays;

public class HistogramFindTopK implements FindTopK {
    @Override
    public int[] findTopK(int[] ints, int k) {
        if (ints.length == 0) {
            throw new IllegalArgumentException("Array must be non-empty");
        } else if (k < 0 || k >= ints.length) {
            throw new IllegalArgumentException(String.format(
                    "k [%d] must be >= 0 and less than length of array [%d]",
                    k, ints.length
            ));
        } else {
            // Find the min and max values.
            int max = ints[0];
            int min = ints[0];
            for (int a: ints) {
                if (a > max) max = a;
                else if (a < min) min = a;
            }

            // Build a histogram for non-zero values.
            int[] hist = new int[max - min + 1];
            for (int a: ints) {
                hist[a - min] += 1;
            }
            System.out.println(Arrays.toString(hist));

            // Find the kth largest value by iterating from the end of the histogram.
            int numGreaterEqual = 0;
            int kthGreatest = max;
            while (kthGreatest >= min) {
                numGreaterEqual += hist[kthGreatest - min];;
                if (numGreaterEqual > k) break;
                else kthGreatest--;
            }

            int[] result = new int[k];
            int ri = 0;
            for (int ii = 0; ii < ints.length && ri < result.length; ii++) {
                if (ints[ii] > kthGreatest) {
                    result[ri] = ints[ii];
                    ri += 1;
                }
            }
            return result;
        }
    }
}

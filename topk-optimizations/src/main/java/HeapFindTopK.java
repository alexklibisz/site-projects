import java.util.Collections;
import java.util.PriorityQueue;

public class HeapFindTopK implements FindTopK {
    @Override
    public int[] findTopK(int[] ints, int k) {
        // Create a priority queue to store the k largest ints.
        PriorityQueue<Integer> largestInts = new PriorityQueue<>(k, Collections.reverseOrder());

        // Add the first k elements of the array to the priority queue.
        for (int i = 0; i < k; i++) {
            largestInts.add(ints[i]);
        }

        // Iterate over the remaining elements of the array and add them to the priority queue if they are larger than the smallest element in the priority queue.
        for (int i = k; i < ints.length; i++) {
            if (ints[i] > largestInts.peek()) {
                largestInts.poll();
                largestInts.add(ints[i]);
            }
        }

        // Create an array to store the k largest ints.
        int[] result = new int[k];

        // Iterate over the priority queue and add the k largest ints to the result array.
        for (int i = 0; i < k; i++) {
            result[i] = largestInts.poll();
        }

        // Return the result array.
        return result;
    }
}

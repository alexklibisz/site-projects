import java.util.Arrays;

final class SortingFindTopK implements FindTopK {
    public int[] findTopK(int[] ints, int k) {
        Arrays.sort(ints);
        int[] topK = new int[k];
        for (int i = 0; i < k; i++) {
            topK[i] = ints[ints.length - 1 - i];
        }
        return topK;
    }
}

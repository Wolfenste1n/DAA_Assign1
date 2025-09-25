import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class MergeSortTest {

    @Test
    void testSortSmallArrays() {
        int[] empty = {};
        MergeSort.mergeSort(empty);
        assertArrayEquals(new int[]{}, empty);

        int[] one = {5};
        MergeSort.mergeSort(one);
        assertArrayEquals(new int[]{5}, one);

        int[] two = {9, 3};
        MergeSort.mergeSort(two);
        assertArrayEquals(new int[]{3, 9}, two);

        int[] many = {5, 1, 4, 2, 8};
        MergeSort.mergeSort(many);
        assertArrayEquals(new int[]{1, 2, 4, 5, 8}, many);
    }

    @Test
    void testSortWithExternalBuffer() {
        int[] a = {10, -3, 7, 7, 0, 2};
        int[] b = { -3, 0, 2, 7, 7, 10 };
        MergeSort.mergeSort(a);
        assertArrayEquals(b, a);
    }

    
}



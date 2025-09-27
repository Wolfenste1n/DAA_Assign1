package algorithms.sort;

import java.util.Arrays;
import java.util.Objects;

public final class MergeSort {
    private static final int CUTOFF = 16;

    private MergeSort() { }

    public static void mergeSort(int[] array) {
        if (array == null || array.length < 2) return;
        int[] buffer = new int[array.length];
        mergeSort(array, buffer, 0, array.length - 1);
    }

    private static void mergeSort(int[] a, int[] buf, int left, int right) {
        int len = right - left + 1;
        if (len <= CUTOFF) {
            Arrays.sort(a, left, right + 1);
            return;
        }

        int mid = (left + right) >>> 1;
        mergeSort(a, buf, left, mid);
        mergeSort(a, buf, mid + 1, right);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else buf[k++] = a[j++];
        }
        if (i <= mid) {
            int remaining = mid - i + 1;
            System.arraycopy(a, i, buf, k, remaining);
            k += remaining;
        } else if (j <= right) {
            int remaining = right - j + 1;
            System.arraycopy(a, j, buf, k, remaining);
            k += remaining;
        }

        int mergedLen = right - left + 1;
        System.arraycopy(buf, left, a, left, mergedLen);
    }
}

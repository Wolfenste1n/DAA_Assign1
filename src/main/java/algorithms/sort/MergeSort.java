package algorithms.sort;

import java.util.Arrays;

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
        if (a[mid] <= a[mid + 1]) return;
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else buf[k++] = a[j++];
        }
        if (i <= mid) System.arraycopy(a, i, buf, k, mid - i + 1);
        else if (j <= right) System.arraycopy(a, j, buf, k, right - j + 1);
        System.arraycopy(buf, left, a, left, len);
    }
}

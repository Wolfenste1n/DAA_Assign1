package algorithms.sort;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }

    private static void quicksort(int[] arr, int low, int high) {
        while (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            if (pivotIndex - low < high - pivotIndex) {
                quicksort(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quicksort(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int randomizedPartition(int[] arr, int low, int high) {
        int pivotIndex = low + random.nextInt(high - low + 1);
        swap(arr, pivotIndex, high);
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

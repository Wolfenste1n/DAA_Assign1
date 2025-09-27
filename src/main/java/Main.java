import algorithms.closest.ClosestPair;
import algorithms.closest.ClosestPair.Point;
import algorithms.metrics.CsvWriter;
import algorithms.metrics.Metrics;
import algorithms.select.DeterministicSelect;
import algorithms.sort.MergeSort;
import algorithms.sort.QuickSort;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        CsvWriter writer = new CsvWriter("results/sorting.csv",
                "algo,n,time_ns,comparisons,allocations,max_recursion_depth");
        writer.writeHeaderIfNeeded();

        int[] sizes = {100, 1000, 5000, 10000};

        for (int n : sizes) {
            int[] arr = random.ints(n, 0, 100000).toArray();

            int[] arrCopy1 = arr.clone();
            Metrics m1 = new Metrics();
            m1.startTimer();
            QuickSort.sort(arrCopy1);
            m1.stopTimer();
            writer.writeRow(m1.csvRow("QuickSort", n));

            int[] arrCopy2 = arr.clone();
            Metrics m2 = new Metrics();
            m2.startTimer();
            MergeSort.mergeSort(arrCopy2);
            m2.stopTimer();
            writer.writeRow(m2.csvRow("MergeSort", n));
        }

        CsvWriter writerSelect = new CsvWriter("results/select.csv",
                "algo,n,k,value,time_ns,comparisons,allocations,max_recursion_depth");
        writerSelect.writeHeaderIfNeeded();

        for (int n : sizes) {
            int[] arr = random.ints(n, 0, 100000).toArray();
            int k = n / 2;

            Metrics m = new Metrics();
            m.startTimer();
            int value = DeterministicSelect.select(arr, k);
            m.stopTimer();

            writerSelect.writeRow("DeterministicSelect," + n + "," + k + "," + value + ","
                    + m.getElapsedNs() + "," + m.getComparisons() + ","
                    + m.getAllocations() + "," + m.getMaxRecursionDepth());
        }

        CsvWriter writerClosest = new CsvWriter("results/closest.csv",
                "algo,n,distance,time_ns");
        writerClosest.writeHeaderIfNeeded();

        for (int n : sizes) {
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
            }

            long start = System.nanoTime();
            double distance = ClosestPair.closestPair(points);
            long elapsed = System.nanoTime() - start;

            writerClosest.writeRow("ClosestPair," + n + "," + distance + "," + elapsed);
        }

    }
}

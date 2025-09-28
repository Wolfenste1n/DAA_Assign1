import algorithms.closest.ClosestPair;
import algorithms.closest.ClosestPair.Point;
import algorithms.metrics.CsvWriter;
import algorithms.metrics.Metrics;
import algorithms.select.DeterministicSelect;
import algorithms.sort.MergeSort;
import algorithms.sort.QuickSort;

import java.util.Random;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Random random = new Random();

        Metrics metrics = new Metrics();
        CsvWriter writer = new CsvWriter("results/sorting.csv", metrics.csvHeader());
        writer.writeHeaderIfNeeded();

        int[] sizes = {100, 1000, 5000, 10000};

        for (int n : sizes) {
            int[] arr = random.ints(n, 0, 100000).toArray();

            int[] arrCopy1 = arr.clone();
            metrics.reset();
            metrics.startTimer();
            QuickSort.sort(arrCopy1);
            metrics.stopTimer();
            writer.writeRow(metrics.csvRow(n));

            int[] arrCopy2 = arr.clone();
            metrics.reset();
            metrics.startTimer();
            MergeSort.mergeSort(arrCopy2);
            metrics.stopTimer();
            writer.writeRow(metrics.csvRow(n));
        }

        CsvWriter writerSelect = new CsvWriter("results/select.csv", metrics.csvHeader());
        writerSelect.writeHeaderIfNeeded();

        for (int n : sizes) {
            int[] arr = random.ints(n, 0, 100000).toArray();
            int k = n / 2;

            metrics.reset();
            metrics.startTimer();
            int value = DeterministicSelect.select(arr, k);
            metrics.stopTimer();

            writerSelect.writeRow(metrics.csvRow(n) + ";" + value);
        }

        CsvWriter writerClosest = new CsvWriter("results/closest.csv", "n;distance;time_ns");
        writerClosest.writeHeaderIfNeeded();

        for (int n : sizes) {
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);

            long start = System.nanoTime();
            double distance = ClosestPair.closestPair(points);
            long elapsed = System.nanoTime() - start;

            writerClosest.writeRow(n + ";" + distance + ";" + elapsed);
        }

        System.out.println("Completed experiments. Results in results/");
    }
}

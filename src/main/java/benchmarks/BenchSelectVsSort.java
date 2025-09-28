package benchmarks;

import algorithms.select.DeterministicSelect;
import algorithms.sort.MergeSort;
import algorithms.sort.QuickSort;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class BenchSelectVsSort {
    @Param({"1000", "10000", "50000"})
    private int n;

    private int[] arr;
    private int k;
    private Random random;

    @Setup(Level.Iteration)
    public void setup() {
        random = new Random();
        arr = random.ints(n, 0, 1_000_000).toArray();
        k = n / 2;
    }

    @Benchmark
    public int benchmarkSelect() {
        int[] copy = Arrays.copyOf(arr, arr.length);
        return DeterministicSelect.select(copy, k);
    }

    @Benchmark
    public int[] benchmarkQuickSort() {
        int[] copy = Arrays.copyOf(arr, arr.length);
        QuickSort.sort(copy);
        return copy;
    }

    @Benchmark
    public int[] benchmarkMergeSort() {
        int[] copy = Arrays.copyOf(arr, arr.length);
        MergeSort.mergeSort(copy);
        return copy;
    }
}

package algorithms.metrics;

public class Metrics {
    private long comparisons = 0;
    private long allocations = 0;
    private int recursionDepth = 0;
    private int maxRecursionDepth = 0;
    private long startNs = 0;
    private long elapsedNs = 0;

    public synchronized void startTimer() {
        if (startNs == 0) startNs = System.nanoTime();
    }

    public synchronized void stopTimer() {
        if (startNs != 0) {
            elapsedNs += System.nanoTime() - startNs;
            startNs = 0;
        }
    }

    public synchronized void incComparisons() { comparisons++; }
    public synchronized void incComparisons(long d) { comparisons += d; }

    public synchronized void incAllocations() { allocations++; }
    public synchronized void incAllocations(long d) { allocations += d; }

    public synchronized void enterRecursion() {
        recursionDepth++;
        if (recursionDepth > maxRecursionDepth) maxRecursionDepth = recursionDepth;
    }

    public synchronized void exitRecursion() {
        recursionDepth--;
        if (recursionDepth < 0) recursionDepth = 0;
    }

    public synchronized long getComparisons() { return comparisons; }
    public synchronized long getAllocations() { return allocations; }
    public synchronized int getMaxRecursionDepth() { return maxRecursionDepth; }
    public synchronized long getElapsedNs() { return elapsedNs; }

    public synchronized String csvHeader() {
        return "algo,n,time_ns,comparisons,allocations,max_recursion_depth";
    }

    public synchronized String csvRow(String algo, int n) {
        return algo + "," + n + "," + elapsedNs + "," + comparisons + "," + allocations + "," + maxRecursionDepth;
    }
}

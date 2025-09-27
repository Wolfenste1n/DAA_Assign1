package algorithms.metrics;

public class Metrics {
    private long comparisons = 0;
    private long assignments = 0;
    private int currDepth = 0;
    private int maxDepth = 0;
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
    public synchronized void incComparisons(long delta) { comparisons += delta; }

    public synchronized void incAssignments() { assignments++; }
    public synchronized void incAssignments(long delta) { assignments += delta; }

    public synchronized void enterRecursion() {
        currDepth++;
        if (currDepth > maxDepth) maxDepth = currDepth;
    }

    public synchronized void exitRecursion() {
        currDepth--;
        if (currDepth < 0) currDepth = 0;
    }

    public synchronized long getComparisons() { return comparisons; }
    public synchronized long getAssignments() { return assignments; }
    public synchronized int getMaxDepth() { return maxDepth; }
    public synchronized long getElapsedNs() { return elapsedNs; }

    public synchronized void reset() {
        comparisons = 0;
        assignments = 0;
        currDepth = 0;
        maxDepth = 0;
        startNs = 0;
        elapsedNs = 0;
    }

    public synchronized String csvHeader() {
        return "n;time_ns;comparisons;assignments;max_depth";
    }

    public synchronized String csvRow(int n) {
        return n + ";" + elapsedNs + ";" + comparisons + ";" + assignments + ";" + maxDepth;
    }
}
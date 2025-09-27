package algorithms.metrics;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class MetricsTest {

    @Test
    void testCountersAndDepth() {
        Metrics m = new Metrics();
        m.startTimer();
        m.enterRecursion();
        m.incComparisons(3);
        m.incAssignments(2);
        m.exitRecursion();
        m.stopTimer();

        assertEquals(3, m.getComparisons());
        assertEquals(2, m.getAssignments());
        assertEquals(1, m.getMaxDepth());
        assertTrue(m.getElapsedNs() >= 0);
    }

    @Test
    void testCsvWriterCreatesFile() throws Exception {
        Path out = Paths.get("target", "metrics-test.csv");
        Files.deleteIfExists(out);
        try (CsvWriter w = new CsvWriter(out, false)) {
            Metrics m = new Metrics();
            w.writeHeader(m.csvHeader());
            w.writeRow(m.csvRow(10));
        }
        assertTrue(Files.exists(out));
        String first = Files.readAllLines(out).get(0);
        assertTrue(first.contains("n;time_ns"));
    }
}
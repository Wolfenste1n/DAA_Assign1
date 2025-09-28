package algorithms.closest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    @Test
    void testSimplePoints() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4),
                new ClosestPair.Point(7, 7),
                new ClosestPair.Point(1, 1)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(Math.sqrt(2), result, 1e-6);
    }

    @Test
    void testTwoPoints() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(2, 3),
                new ClosestPair.Point(5, 6)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(Math.sqrt(18), result, 1e-6);
    }

    @Test
    void testDuplicates() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(1, 1),
                new ClosestPair.Point(1, 1),
                new ClosestPair.Point(2, 2)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(0.0, result, 1e-6);
    }
}

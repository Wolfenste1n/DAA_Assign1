package algorithms.closest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

public class ClosestPair {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points) {
        if (points == null || points.length < 2) return Double.POSITIVE_INFINITY;
        if (points.length == 2) return distance(points[0], points[1]);
        Point[] ptsSortedByX = points.clone();
        Arrays.sort(ptsSortedByX, Comparator.comparingDouble(p -> p.x));
        Point[] ptsSortedByY = points.clone();
        Arrays.sort(ptsSortedByY, Comparator.comparingDouble(p -> p.y));
        return closestUtil(ptsSortedByX, ptsSortedByY);
    }

    private static double closestUtil(Point[] ptsSortedByX, Point[] ptsSortedByY) {
        int n = ptsSortedByX.length;
        if (n <= 3) return bruteForce(ptsSortedByX);
        int mid = n / 2;
        Point midPoint = ptsSortedByX[mid];
        Point[] leftX = Arrays.copyOfRange(ptsSortedByX, 0, mid);
        Point[] rightX = Arrays.copyOfRange(ptsSortedByX, mid, n);
        ArrayList<Point> leftList = new ArrayList<>();
        ArrayList<Point> rightList = new ArrayList<>();
        for (Point p : ptsSortedByY) {
            if (p.x <= midPoint.x) leftList.add(p);
            else rightList.add(p);
        }
        Point[] leftY = leftList.toArray(new Point[0]);
        Point[] rightY = rightList.toArray(new Point[0]);
        double dl = closestUtil(leftX, leftY);
        double dr = closestUtil(rightX, rightY);
        double d = Math.min(dl, dr);
        return Math.min(d, stripClosest(ptsSortedByY, midPoint.x, d));
    }

    private static double bruteForce(Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dist = distance(pts[i], pts[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double stripClosest(Point[] ptsSortedByY, double midX, double d) {
        Point[] strip = Arrays.stream(ptsSortedByY)
                .filter(p -> Math.abs(p.x - midX) < d)
                .toArray(Point[]::new);
        double min = d;
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < min; j++) {
                double dist = distance(strip[i], strip[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

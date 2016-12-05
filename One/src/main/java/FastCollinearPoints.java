/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/23/2016
 *  Last updated:  11/25/2016
 *
 *  Compilation:   javac FastCollinearPoints.java
 *  Execution:     java FastCollinearPoints
 *
 *  Examines 4 points at a time and checks whether they all lie on the same line segment
 *  returning all such line segments
 *
 *
 *----------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private static final int THRESHOLD = 4; // threshold of points number to check collinear line
    private ArrayList<LineSegment> segs;
    private Point[] sortedPoints;

    /**
     * Finds all line segments containing 4 or more points
     * @param points {Point[]}
     */
    public FastCollinearPoints(Point[] points) {
        Point[] aux = checkInput(points);

        int len = aux.length;
        if (len < 4) return;

        segs = new ArrayList<LineSegment>();

        for (int i = 0; i < len; i++) {
            addLines(sortedPoints, aux, i);
        }

        sortedPoints = null;
    }

    /**
     * Check input
     * @param points {Point[]}
     * @return {Point[]} copy of points
     */
    private Point[] checkInput(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();

        int len = points.length;
        Point[] aux = new Point[len];
        for (int i = 0; i < len; i++) {
            Point p = points[i];
            if (p == null) throw new java.lang.NullPointerException(); // null point found
            aux[i] = p;
        }

        Arrays.sort(aux);
        sortedPoints = new Point[len];
        for (int i = 0; i < len - 1; i++) {
            Point p = aux[i];
            if (p.compareTo(aux[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException(); // repeated points found
            }
            sortedPoints[i] = p;
        }
        sortedPoints[len - 1] = aux[len - 1];
        return aux;
    }

    /**
     * Add collinear lines
     * @param points {Point[]}
     * @param aux {Point[]}
     * @param originIdx {int}
     */
    private void addLines(Point[] points, Point[] aux, int originIdx) {
        Point origin = points[originIdx];
        Comparator<Point> cpt = origin.slopeOrder();
        Arrays.sort(aux, cpt); // sort based on slope
        int start;

        for (int j = 1, len = aux.length; j < len;) {
            start = j;
            Point q = aux[j++];

            while (j < len && cpt.compare(q, aux[j]) == 0) {
                j++;
            }

            if (j - start + 1 >= THRESHOLD) {
                addLine(aux, origin, start, j - 1);
            }
        }
    }

    /**
     * Create line segment based on input points
     * @param points {ArrayList()}
     * @param origin {Point}
     * @param startIdx {int}
     * @param endIdx {int}
     */
    private void addLine(Point[] points, Point origin, int startIdx, int endIdx) {
        Arrays.sort(points, startIdx, endIdx + 1);
        Point p1 = points[startIdx];
        if (origin.compareTo(p1) < 0) { // not added yet
            segs.add(new LineSegment(origin, points[endIdx]));
        }
    }

    /**
     * The number of line segments
     * @return {int}
     */
    public int numberOfSegments() {
        return (segs != null) ? segs.size() : 0;
    }

    /**
     * The line segments
     * @return {LineSegment[]}
     */
    public LineSegment[] segments() {
        if (segs == null) {
            return new LineSegment[0];
        }

        LineSegment[] lines = new LineSegment[segs.size()];
        int i = 0;
        for (LineSegment line:segs) {
            lines[i++] = line;
        }
        return lines;
    }
}

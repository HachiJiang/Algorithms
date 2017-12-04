package com.assignment3;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/23/2016
 *  Last updated:  11/25/2016
 *
 *  Compilation:   javac com.assignment3.BruteCollinearPoints.java
 *  Execution:     java com.assignment3.BruteCollinearPoints
 *
 *  Examines 4 points at a time and checks whether they all lie on the same line segment
 *  returning all such line segments
 *
 *
 *----------------------------------------------------------------*/

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private static final int THRESHOLD = 4; // threshold of points number to check collinear line
    private ArrayList<LineSegment> segs;

    /**
     * Finds all line segments containing 4 points
     * @param points {com.assignment3.Point[]}
     */
    public BruteCollinearPoints(Point[] points) {
        checkInput(points);

        int len = points.length;
        if (len < 4) return;

        segs = new ArrayList<LineSegment>();
        ArrayList<Point> tmp;

        for (int i = 0; i < len; i++) {
            Point origin = points[i];
            Comparator<Point> cpt = origin.slopeOrder();

            for (int j = i + 1; j < len; j++) {
                tmp = new ArrayList<Point>();
                tmp.add(origin);

                mergeCollinearPoints(points, j, cpt, tmp);
                if (tmp.size() >= THRESHOLD) {
                    segs.add(createLine(tmp));
                }
            }
        }
    }

    /**
     * Check input
     * @param points {com.assignment3.Point[]}
     */
    private void checkInput(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();

        int len = points.length;
        Point[] aux = new Point[len];
        for (int i = 0; i < len; i++) {
            Point p = points[i];
            if (p == null) throw new java.lang.NullPointerException(); // null point found
            aux[i] = p;
        }

        Arrays.sort(aux);
        for (int i = 0; i < len - 1; i++) {
            if (aux[i].compareTo(aux[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException(); // repeated points found
            }
        }
    }

    /**
     * Merge collinear points to reference line seg
     * @param points {com.assignment3.Point[]}
     * @param refIdx {int}
     * @param cpt {Comparator}
     * @param pointsInLine {ArrayList}
     */
    private void mergeCollinearPoints(Point[] points, int refIdx, Comparator<Point> cpt, ArrayList<Point> pointsInLine) {
        Point ref = points[refIdx];
        Point q;

        pointsInLine.add(ref);
        for (int i = 0; i < refIdx; i++) {
            if (cpt.compare(ref, points[i]) == 0) return; // if the line contains points already checked, just skip
        }

        for (int i = refIdx + 1, len = points.length; i < len; i++) {
            q = points[i];
            if (cpt.compare(ref, q) == 0) { // check whether ref, q are in the same line segment
                pointsInLine.add(q);
            }
        }
    }

    /**
     * Create line segment based on input points
     * @param points {ArrayList()}
     * @return {com.assignment3.LineSegment}
     */
    private LineSegment createLine(ArrayList<Point> points) {
        Point start = points.get(0);
        Point end = points.get(0);
        Point p;
        int len = points.size();

        for (int i = 1; i < len; i++) {
            p = points.get(i);
            if (p.compareTo(start) < 0) start = p;
            else if (end.compareTo(p) < 0) end = p;
        }
        return new LineSegment(start, end);
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
     * @return {com.assignment3.LineSegment[]}
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

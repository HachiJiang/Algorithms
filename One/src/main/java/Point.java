/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/23/2016
 *  Last updated:  11/23/2016
 *
 *  Compilation:   javac Point.java
 *  Execution:     java Point
 *
 *  Point in plane
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x; // x-coordinate
    private final int y; // y-coordinate

    /**
     * constructs the point (x, y)
     * @param x {int}
     * @param y {int}
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw this point
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment from this point to that point
     * @param that {Point} end point of line
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * String representation
     * @return {String}
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     * @param that {Point} point to compare
     * @return {int}
     */
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        else if (this.x == that.x && this.y == that.y) return 0;
        else return 1;
    }

    /**
     * The slope between this point and that point
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     * @param that {Point} end point of line
     * @return {double}
     */
    public double slopeTo(Point that) {
        if (that == null) throw new java.lang.NullPointerException();

        if (this.x == that.x) {
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            else return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        } else {
            return 1.0 * (that.y - this.y) / (that.x - this.x);
        }
    }

    private class SlopeOrder implements Comparator<Point> {
        private Point p0;

        public SlopeOrder(Point p0) {
            this.p0 = p0;
        }

        /**
         * Compare two points
         * @param p1 {Point}
         * @param p2 {Point}
         * @return {int}
         */
        public int compare(Point p1, Point p2) {
            double s1 = p0.slopeTo(p1);
            double s2 = p0.slopeTo(p2);
            if (s1 < s2) return -1;
            else if (s1 == s2) return 0;
            else return 1;
        }
    }

    /**
     * Compare two points by slopes they make with this point
     * @return {Comparator}
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder(this);
    }
}

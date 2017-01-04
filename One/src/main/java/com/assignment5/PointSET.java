package com.assignment5;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       12/25/2016
 *  Last updated:  12/25/2016
 *
 *  Compilation:   javac PointSET.java
 *  Execution:     java PointSET
 *
 *  Brute-force implementation.
 *  A mutable data type PointSET.java that represents a set of points in the unit square.
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointTree; // store points in plane

    /**
     * Construct an empty set of points
     */
    public PointSET() {
        pointTree = new TreeSet<Point2D>();
    }

    /**
     * Is the set empty?
     * @return {boolean}
     */
    public boolean isEmpty() {
        return pointTree.isEmpty();
    }

    /**
     * Number of points in the set
     * @return {int}
     */
    public int size() {
        return pointTree.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p {Point2D}
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (contains(p)) return;
        pointTree.add(p);
    }

    /**
     * Does the set contain point p?
     * @param p {Point}
     * @return {boolean}
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return pointTree.contains(p);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        if (!isEmpty()) {
            for (Point2D p: pointTree) {
                p.draw();
            }
        }
    }

    /**
     * All points that are inside the rectangle
     * @param rect {ReactHV}
     * @return {Iterable<Point2d>}
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }

        Stack<Point2D> st = new Stack<Point2D>();
        for (Point2D p: pointTree) {
            if (rect.contains(p)) {
                st.push(p);
            }
        }
        return st;
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     * @param p {Point2D}
     * @return {Point2D}
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (isEmpty()) {
            return null;
        }

        Point2D target = pointTree.first();
        double disMin = p.distanceSquaredTo(target);
        double dis;
        for (Point2D that: pointTree) {
            dis = p.distanceSquaredTo(that);
            if (dis < disMin) {
                target = that;
                disMin = dis;
            }
        }
        return target;
    }
}

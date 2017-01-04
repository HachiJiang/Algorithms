package com.assignment5;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       12/25/2016
 *  Last updated:  1/3/2017
 *
 *  Compilation:   javac KdTree.java
 *  Execution:     java KdTree
 *
 *  2d-tree implementation.
 *  A mutable data type KdTree.java that represents a set of points in the unit square.
 *  A 2d-tree is a generalization of a BST to two-dimensional keys.
 *  The idea is to build a BST with points in the nodes,
 *  using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;   // root of the BST
    private int n;       // count of node

    /**
     * Construct an empty set of points
     */
    public KdTree() {
        root = null;
        n = 0;
    }

    /**
     * Node of a 2-d Tree
     */
    private static class Node {
        private Point2D p;    // the point
        private RectHV rect;  // the axis-aligned rectangle corresponding to this node
        private Node lb;      // the left/bottom subtree
        private Node rt;      // the right/top subtree
    }

    /**
     * Is the set empty?
     * @return {boolean}
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Number of points in the set
     * @return {int}
     */
    public int size() {
        return n;
    }

    /**
     * Create single node
     * @param p {Point2D}
     * @param parent {Node}
     * @param splitOrientation {boolean} true: vertical splitter
     * @param locOrientation {boolean}
     * @return {Node}
     */
    private Node create(Point2D p, Node parent, boolean splitOrientation, boolean locOrientation) {
        Node nd = new Node();
        nd.p = p;
        Point2D parentPoint = parent.p;
        RectHV parentRect = parent.rect;

        if (splitOrientation) { // vertical split
            if (locOrientation) { // left
                nd.rect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentPoint.x(), parentRect.ymax());
            } else { // right
                nd.rect = new RectHV(parentPoint.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
            }
        } else { // horizontal split
            if (locOrientation) { // bottom
                nd.rect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), parentPoint.y());
            } else { // top
                nd.rect = new RectHV(parentRect.xmin(), parentPoint.y(), parentRect.xmax(), parentRect.ymax());
            }
        }
        return nd;
    }

    /**
     * Insert node
     * @param current {Node}
     * @param p {Point2D}
     * @param parent {Node}
     * @param splitOrientation {boolean} true: vertical splitter
     * @param locOrientation {boolean}
     * @return {Node}
     */
    private Node insert(Node current, Point2D p, Node parent, boolean splitOrientation, boolean locOrientation) {
        if (current == null) {
            return create(p, parent, !splitOrientation, locOrientation);
        }

        double compared = splitOrientation ?
                Point2D.X_ORDER.compare(p, current.p) :  // vertical
                Point2D.Y_ORDER.compare(p, current.p);   // horizontal

        if (compared < 0) { // left/bottom
            current.lb = insert(current.lb, p, current, !splitOrientation, true);
        } else {            // right/top
            current.rt = insert(current.rt, p, current, !splitOrientation, false);
        }

        return current;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p {Point2D}
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (root == null) {
            root = new Node();
            root.p = p;
            root.rect = new RectHV(0, 0, 1, 1);
            n = 1;
            return;
        }

        if (contains(p)) return;
        insert(root, p, null, true, false);
        n++;
    }

    /**
     * Check whether current subtree contains p
     * @param current {Node}
     * @param p {Point}
     * @return {boolean}
     */
    private boolean contains(Node current, Point2D p) {
        return current != null && current.rect.contains(p) &&
                (current.p.equals(p) || contains(current.lb, p) || contains(current.rt, p));
    }

    /**
     * Does the set contain point p?
     * @param p {Point}
     * @return {boolean}
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        return contains(root, p);
    }

    /**
     * Draw single point
     * @param nd {Node}
     * @param splitOrientation {boolean} true: vertical splitter
     */
    private void drawPoint(Node nd, boolean splitOrientation) {
        RectHV rect = nd.rect;
        Point2D p = nd.p;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        p.draw();

        if (splitOrientation) { // vertical
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
        }
    }

    /**
     * Draw node
     * @param nd {Node}
     * @param splitOrientation {boolean} true: vertical splitter
     */
    private void draw(Node nd, boolean splitOrientation) {
        if (nd == null) return;
        if (nd.p != null) drawPoint(nd, splitOrientation);
        if (nd.lb != null) draw(nd.lb, !splitOrientation);
        if (nd.rt != null) draw(nd.rt, !splitOrientation);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        draw(root, true);
    }

    /**
     * Search point inside the specific rectangle
     * @param st {Stack<Point2D>}
     * @param rect {RectHV}
     * @param current {Node}
     */
    private void rangeSearch(Stack<Point2D> st, RectHV rect, Node current) {
        if (!current.rect.intersects(rect)) return;
        if (current.lb != null) rangeSearch(st, rect, current.lb);
        if (current.rt != null) rangeSearch(st, rect, current.rt);
        if (rect.contains(current.p)) st.push(current.p);
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
        rangeSearch(st, rect, root);
        return st;
    }

    /**
     * Search the nearest node to query point
     * @param p {Point2D} query point
     * @param current {Node} current root node
     * @param nearestNode {Node} current nearest node
     * @param minDis {double} current nearest distance
     */
    private double nearestSearch(Point2D p, Node current, Node nearestNode, double minDis) {
        if (current == null) return minDis;
        if (current.rect.distanceSquaredTo(p) >= minDis) return minDis;

        double dis = current.p.distanceSquaredTo(p);
        if (dis < minDis) {
            nearestNode.p = current.p;
            minDis = dis;
        }

        minDis = nearestSearch(p, current.lb, nearestNode, minDis);
        minDis = nearestSearch(p, current.rt, nearestNode, minDis);
        return minDis;
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

        Node nearestNode = new Node();
        nearestSearch(p, root, nearestNode, Double.POSITIVE_INFINITY);
        return nearestNode.p;
    }
}

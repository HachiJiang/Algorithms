package com.assignment3;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/23/2016
 *  Last updated:  11/23/2016
 *
 *  Compilation:   javac com.assignment3.LineSegment.java
 *  Execution:     java com.assignment3.LineSegment
 *
 *  Line segment in plane
 *
 *
 *----------------------------------------------------------------*/

import com.assignment3.Point;

public class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Constructs the line segment between points p and q
     * @param p {com.assignment3.Point}
     * @param q {com.assignment3.Point}
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }
        this.p = p;
        this.q = q;
    }

    /**
     * Draws this line segment
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * String representation
     * @return {String}
     */
    public String toString() {
        return p.toString() + " -> " + q.toString();
    }
}

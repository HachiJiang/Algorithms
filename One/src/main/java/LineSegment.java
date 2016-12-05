/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/23/2016
 *  Last updated:  11/23/2016
 *
 *  Compilation:   javac LineSegment.java
 *  Execution:     java LineSegment
 *
 *  Line segment in plane
 *
 *
 *----------------------------------------------------------------*/

public class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Constructs the line segment between points p and q
     * @param p {Point}
     * @param q {Point}
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

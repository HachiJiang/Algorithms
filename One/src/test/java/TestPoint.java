/**
 * Created by jianghong on 2016/11/23.
 */

import com.assignment3.Point;
import org.junit.Test;
import org.junit.Assert;
import java.util.Comparator;

public class TestPoint {

    @Test
    public void testToString() {
        Point p0 = new Point(1, 0);
        Assert.assertEquals(p0.toString(), "(1, 0)");
    }

    @Test
    public void testCompareTo() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(1, 0);
        Point p4 = new Point(1, 1);
        Assert.assertEquals(p1.compareTo(p2), 0);
        Assert.assertEquals(p1.compareTo(p3), -1);
        Assert.assertEquals(p1.compareTo(p4), -1);
    }

    @Test
    public void testSlopeTo() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(2, 4);
        Point p5 = new Point(2, 0);
        Point p6 = new Point(1, -2);
        Point p7 = new Point(-1, 2);
        Assert.assertEquals(p1.slopeTo(p1), Double.NEGATIVE_INFINITY, 0);
        Assert.assertEquals(p1.slopeTo(p2), Double.POSITIVE_INFINITY, 0);
        Assert.assertEquals(p1.slopeTo(p3), 0, 0);
        Assert.assertEquals(p1.slopeTo(p4), 2, 0);
        Assert.assertEquals(p1.slopeTo(p5), -2, 0);
        Assert.assertEquals(p1.slopeTo(p6), Double.POSITIVE_INFINITY, 0);
        Assert.assertEquals(p1.slopeTo(p7), 0, 0);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testSlopeToException() {
        Point p1 = new Point(1, 2);
        p1.slopeTo(null);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testSlopeOrderException() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(2, 2);
        Comparator<Point> cpt = p0.slopeOrder();
        Assert.assertEquals(-1, cpt.compare(p1, null));
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testSlopeOrderException1() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(2, 2);
        Comparator<Point> cpt = p0.slopeOrder();
        Assert.assertEquals(-1, cpt.compare(null, p1));
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testSlopeOrderException2() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(2, 2);
        Comparator<Point> cpt = p0.slopeOrder();
        Assert.assertEquals(-1, cpt.compare(null, null));
    }

    @Test
    public void testSlopeOrder() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(2, 2);
        Point p2 = new Point(2, 4);
        Point p3 = new Point(1, 2);
        Point p4 = new Point(1, 3);
        Comparator<Point> cpt = p0.slopeOrder();
        Assert.assertEquals(-1, cpt.compare(p1, p2));
        Assert.assertEquals(1, cpt.compare(p2, p1));
        Assert.assertEquals(0, cpt.compare(p0, p0));
        Assert.assertEquals(0, cpt.compare(p1, p1));
        Assert.assertEquals(0, cpt.compare(p3, p4));
        Assert.assertEquals(1, cpt.compare(p3, p1));
        Assert.assertEquals(-1, cpt.compare(p1, p3));
    }

}

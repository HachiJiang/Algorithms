/**
 * Created by jianghong on 2016/11/23.
 */

import org.junit.Assert;
import org.junit.After;
import org.junit.Test;

public class TestBruteCollinearPoints {
    private Point[] points;
    private int numSegs;

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testInputRepeat() {
        points = new Point[]{
                new Point(16000, 16000),
                new Point(10000, 10000),
                new Point(16000, 16000)
        };
        numSegs = 0;
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        Assert.assertEquals(numSegs, bcp.numberOfSegments());
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testInputNull() {
        points = new Point[]{
                new Point(16000, 16000),
                new Point(10000, 10000),
                null
        };
        numSegs = 0;
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        Assert.assertEquals(numSegs, bcp.numberOfSegments());
    }

    @Test
    public void testInput3() {
        points = new Point[]{
                new Point(16000, 16000),
                new Point(10000, 10000),
                new Point(20000, 20000)
        };
        numSegs = 0;
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        Assert.assertEquals(numSegs, bcp.numberOfSegments());
    }

    @Test
    public void testInput6() {
        points = new Point[]{
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };
        numSegs = 1;
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        Assert.assertEquals(numSegs, bcp.numberOfSegments());
    }

    @Test
    public void testInput8() {
        points = new Point[]{
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000)
        };
        numSegs = 2;
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        Assert.assertEquals(numSegs, bcp.numberOfSegments());
    }
}

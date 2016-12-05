import edu.princeton.cs.algs4.In;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class TestFastCollinearPoints {
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
        FastCollinearPoints bcp = new FastCollinearPoints(points);
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
        FastCollinearPoints bcp = new FastCollinearPoints(points);
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
        FastCollinearPoints bcp = new FastCollinearPoints(points);
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
        FastCollinearPoints bcp = new FastCollinearPoints(points);
        Assert.assertEquals(numSegs, bcp.numberOfSegments());
    }

    private Point[] loadPoints(String path) {
        In in = new In(path);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        return points;
    }

    @Test
    public void testInput40() {
        points = loadPoints("Practise/src/main/resources/collinear/input40.txt");
        numSegs = 4;
        FastCollinearPoints bcp = new FastCollinearPoints(points);
        Assert.assertEquals(numSegs, bcp.numberOfSegments());
    }
}

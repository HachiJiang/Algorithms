import com.assignment5.KdTree;
import com.assignment5.PointSET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jianghong on 2016/12/29.
 */
public class TestKdTree {
    @Test
    public void testSize() {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.921875, 0.27734375));
        Assert.assertTrue(kd.contains(new Point2D(0.921875, 0.27734375)));
        Assert.assertEquals(1, kd.size());

        kd.insert(new Point2D(0.921875, 0.27734375));
        Assert.assertEquals(1, kd.size()); // no need to insert the point already contained

        kd.insert(new Point2D(0, 0));
        Assert.assertEquals(2, kd.size());
    }

    private ArrayList<Point2D> buildPoints(String filename) {
        In in = new In(filename);

        // initialize the data structures with N points from standard input
        ArrayList<Point2D> pointList = new ArrayList<Point2D>();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            pointList.add(p);
        }
        return pointList;
    }

    @Test
    public void testContains() {
        Point2D[] points = new Point2D[] {
                new Point2D(0.206107, 0.095492),
                new Point2D(0.975528, 0.654508),
                new Point2D(0.024472, 0.345492),
                new Point2D(0.793893,0.095492),
                new Point2D(0.793893, 0.904508),
                new Point2D(0.975528, 0.345492),
                new Point2D(0.206107, 0.904508),
                new Point2D(0.500000, 0.000000),
                new Point2D(0.024472, 0.654508),
                new Point2D(0.500000, 1.000000)
        };

        KdTree kdtree = new KdTree();
        PointSET brute = new PointSET();
        for (Point2D p: points) {
            kdtree.insert(p);
            brute.insert(p);
        }

        Assert.assertEquals(points.length, kdtree.size());
        Assert.assertEquals(points.length, brute.size());

        for (Point2D p: points) {
            Assert.assertTrue(kdtree.contains(p));
            Assert.assertTrue(brute.contains(p));
        }
    }
}

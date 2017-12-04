/**
 * Created by jianghong on 2016/11/23.
 */

import com.assignment3.LineSegment;
import com.assignment3.Point;
import org.junit.Test;
import org.junit.Assert;

public class TestLineSegment {

    @Test
    public void testCompareTo() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        LineSegment l0 = new LineSegment(p1, p2);
        Assert.assertEquals("(0, 0) -> (1, 1)", l0.toString());
    }
}

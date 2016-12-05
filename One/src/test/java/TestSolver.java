/**
 * Created by jianghong on 2016/12/4.
 */

import org.junit.Assert;
import org.junit.Test;

public class TestSolver {

    @Test
    public void testClient() {
        int[][] blocks = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Solver sv = new Solver(new Board(blocks));
        Assert.assertEquals(4, sv.moves());
    }

    @Test
    public void testCompareTo() {

    }

}

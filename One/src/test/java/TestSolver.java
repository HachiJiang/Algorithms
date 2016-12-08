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
    public void testClient20() {
        int[][] blocks = new int[][]{{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};
        Solver sv = new Solver(new Board(blocks));
        Assert.assertEquals(20, sv.moves());
    }

    @Test
    public void testClient28() {
        int[][] blocks = new int[][]{{7, 8, 5}, {4, 0, 2}, {3, 6, 1}};
        Solver sv = new Solver(new Board(blocks));
        Assert.assertEquals(28, sv.moves());
    }

    @Test
    public void testClient3x327() {
        int[][] blocks = new int[][]{{1, 6, 4}, {0, 3, 5}, {8, 2, 7}};
        Solver sv = new Solver(new Board(blocks));
        Assert.assertEquals(27, sv.moves());
    }

}

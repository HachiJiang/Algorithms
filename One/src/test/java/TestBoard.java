/**
 * Created by jianghong on 2016/12/4.
 */

import org.junit.Test;
import org.junit.Assert;

public class TestBoard {

    @Test(expected = java.lang.NullPointerException.class)
    public void testBoardInvalid() {
        new Board(null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBoardInvalidItem() {
        int[][] blocks = new int[4][];
        new Board(blocks);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBoardInvalidItemValue() {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        new Board(blocks);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBoardInvalidItemValue2() {
        int[][] blocks = new int[][]{{-1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        new Board(blocks);
    }

    @Test
    public void testDimense() {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board b = new Board(blocks);
        Assert.assertEquals(3, b.dimension());
    }

    @Test
    public void testDimense2() {
        int[][] blocks = new int[][]{{0}};
        Board b = new Board(blocks);
        Assert.assertEquals(1, b.dimension());
    }

    @Test
    public void testHamming0() {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board b = new Board(blocks);
        Assert.assertEquals(0, b.hamming());
        Assert.assertEquals(0, b.manhattan());
        Assert.assertTrue(b.isGoal());
    }

    @Test
    public void testHamming04() {
        int[][] blocks = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board b = new Board(blocks);
        Assert.assertEquals(4, b.hamming());
        Assert.assertEquals(4, b.manhattan());
        Assert.assertFalse(b.isGoal());
    }

    @Test
    public void testHamming07() {
        int[][] blocks = new int[][]{{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};
        Board b = new Board(blocks);
        Assert.assertEquals(4, b.hamming());
        Assert.assertEquals(7, b.manhattan());
        Assert.assertFalse(b.isGoal());
    }

    @Test
    public void testHamming08() {
        int[][] blocks = new int[][]{{4, 1, 3}, {0, 2, 5}, {7, 8, 6}};
        Board b = new Board(blocks);
        Assert.assertEquals(5, b.hamming());
        Assert.assertEquals(5, b.manhattan());
        Assert.assertFalse(b.isGoal());
    }
}

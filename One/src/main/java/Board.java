/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       12/4/2016
 *  Last updated:  12/6/2016
 *
 *  Compilation:   javac Board.java
 *  Execution:     java Board
 *
 *  A board from an n-by-n array of blocks
 *  Corner cases:
 *  You may assume that the constructor receives an n-by-n array containing the n2 integers between 0 and n2 − 1,
 *  where 0 represents the blank square.
 *  Performance requirements:
 *  Your implementation should support all Board methods in time proportional to n2 (or better) in the worst case.
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[] tiles;
    private int n;                     // grid n x n
    private int blankPos = -1;         // position of blank block
    private int disHamming = -1;       // hamming distance
    private int disManhattan = -1;     // manhattan distance

    /**
     * Construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * @param blocks {int[][]}
     */
    public Board(int[][] blocks) {
        if (blocks == null) throw new java.lang.NullPointerException();

        n = blocks.length;
        int len = n * n;
        tiles = new int[len];

        for (int i = 0; i < n; i++) {
            int[] row = blocks[i];
            if (row == null || row.length != n)
                throw new java.lang.IllegalArgumentException();

            for (int j = 0; j < n; j++) {
                int value = row[j];
                int pos = xyTo1D(i, j);
                if (value < 0 || value >= len) throw new java.lang.IllegalArgumentException();
                if (value == 0) blankPos = pos;
                tiles[pos] = value;
            }
        }
        calDis();
    }

    /**
     * Get copy of tiles
     * @param arr {int[]}
     * @return {int[]}
     */
    private int[] tilesCopy(int[] arr) {
        int len = arr.length;
        int[] copy = new int[len];
        for (int i = 0; i < len; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    /**
     * Create copy of board
     * @param blank {int} position of blank block
     * @param pos1 {int} position to exchange
     * @param pos2 {int} position to exchange
     * @return {Board}
     */
    private Board boardCopy(int blank, int pos1, int pos2) {
        int[] copy = tilesCopy(tiles);
        int val1 = copy[pos1];
        int val2 = copy[pos2];
        int disH = disHamming + calDisHamming(val2, pos1) + calDisHamming(val1, pos2)
                - calDisHamming(val1, pos1) - calDisHamming(val2, pos2);
        int disM = disManhattan + calDisManhattan(val2, pos1) + calDisManhattan(val1, pos2)
                - calDisManhattan(val1, pos1) - calDisManhattan(val2, pos2);
        exch(copy, pos1, pos2);

        Board b = new Board(new int[0][0]);
        b.tiles = copy;
        b.n = n;
        b.blankPos = blank;
        b.disHamming = disH;
        b.disManhattan = disM;
        return b;
    }

    private int xyTo1D(int i, int j) {
        return i * n + j;
    }

    /**
     * Board dimension n
     * @return {int}
     */
    public int dimension() {
        return n;
    }

    /**
     * Distance between actual position and target position
     */
    private void calDis() {
        disHamming = 0;
        disManhattan = 0;
        for (int i = 0, len = tiles.length; i < len; i++) {
            int value = tiles[i];
            if (value == 0) continue;
            disHamming += calDisHamming(value, i);
            disManhattan += calDisManhattan(value, i);
        }
    }

    /**
     * Distance between actual position and target position
     * @param value {int}
     * @param idx {int}
     * @return {int}
     */
    private int calDisHamming(int value, int idx) {
        if (value == 0) return 0;
        return (value != idx + 1) ? 1 : 0;
    }

    /**
     * Distance between actual position and target position
     * @param value {int}
     * @param idx {int}
     * @return {int}
     */
    private int calDisManhattan(int value, int idx) {
        if (value == 0) return 0;
        value--;
        int expectedRow = value / n;
        int expectedCol = value % n;
        return Math.abs(expectedRow - idx / n) + Math.abs(expectedCol - idx % n);
    }

    /**
     * Number of blocks out of place
     * @return {int}
     */
    public int hamming() {
        return disHamming;
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     * @return {int}
     */
    public int manhattan() {
        return disManhattan;
    }

    /**
     * Is this board the goal board?
     * @return {boolean}
     */
    public boolean isGoal() {
        return disHamming == 0;
    }

    /**
     * Exchange specific pair of blocks
     * @param arr {int[]}
     * @param pos1 {int}
     * @param pos2 {int}
     */
    private void exch(int[] arr, int pos1, int pos2) {
        int tmp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = tmp;
    }

    /**
     * A board that is obtained by exchanging any pair of blocks
     * (the blank square is not a block)
     * @return {int}
     */
    public Board twin() {
        int len = tiles.length;
        int[] pos = new int[]{-1, -1};

        for (int i = 0; i < len; i++) {
            int value = tiles[i];
            if (value == 0 || value == i + 1) continue; // skip for blank block or block in correct position
            value--;
            if (tiles[value] == i + 1) {
                int expectedRow = value / n;
                int expectedCol = value % n;
                int actualRow = i / n;
                int actualCol = i % n;

                if (expectedRow == actualRow || expectedCol == actualCol) {
                    pos[0] = value;
                    pos[1] = i;
                    break;
                }
            }
        }

        if (pos[0] == -1) {
            for (int i = 0, j = 0; i < 2 && j < len; j++) {
                if (tiles[j] != 0) pos[i++] = j;
            }
        }

        return boardCopy(blankPos, pos[0], pos[1]);
    }

    /**
     * Does this board equal y?
     * @param y {Object}
     * @return {boolean}
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.blankPos != that.blankPos) return false;
        if (disHamming != that.disHamming) return false;
        if (disManhattan != that.disManhattan) return false;

        int[] tiles1 = this.tiles;
        int[] tiles2 = that.tiles;
        for (int i = 0, len = tiles1.length; i < len; i++) {
            if (tiles1[i] != tiles2[i]) return false;
        }

        return true;
    }

    /**
     * All neighboring boards
     * @return {Iterable<Board>}
     */
    public Iterable<Board> neighbors() {
        Stack<Board> st = new Stack<Board>();

        addNeighbor(st, blankPos - n);
        addNeighbor(st, blankPos + n);
        if (blankPos % n != 0) addNeighbor(st, blankPos - 1);
        if ((blankPos + 1) % n != 0) addNeighbor(st, blankPos + 1);

        return st;
    }

    /**
     * Add neighbor board
     * @param st {Stack<Board>}
     * @param pos {int}
     */
    private void addNeighbor(Stack<Board> st, int pos) {
        if (pos < 0 || pos >= tiles.length) return;
        st.push(boardCopy(pos, blankPos, pos));
    }

    /**
     * String representation of this board (in the output format specified below)
     * @return {String}
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[xyTo1D(i, j)]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}

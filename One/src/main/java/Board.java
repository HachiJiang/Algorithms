/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       12/4/2016
 *  Last updated:  12/4/2016
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

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] tiles;
    private int blankX = -1;    // position of blank block
    private int blankY = -1;    // position of blank block

    /**
     * Construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * @param blocks {int[][]}
     */
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.NullPointerException();

        int n = blocks.length;
        int max = n * n - 1;
        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            int[] row = blocks[i];
            if (row == null || row.length != n)
                throw new java.lang.IllegalArgumentException();

            int[] tileRow = this.tiles[i];
            for (int j = 0; j < n; j++) {
                int value = row[j];
                if (value < 0 || value > max) throw new java.lang.IllegalArgumentException();
                if (value == 0) {
                    blankX = i;
                    blankY = j;
                }
                tileRow[j] = value;
            }
        }
        // check whether blank block exists
        if (blankX == -1) throw new java.lang.IllegalArgumentException();
    }

    /**
     * Board dimension n
     * @return {int}
     */
    public int dimension() {
        return tiles.length;
    }

    /**
     * Distance between actual position and target position
     * @param size {int}
     * @param value {int}
     * @param i {int}
     * @param j {int}
     * @return {int}
     */
    private int disManhattan(int value, int size, int i, int j) {
        value--;
        int expectedRow = value / size;
        int expectedCol = value % size;
        return Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
    }

    /**
     * Number of blocks out of place
     * @return {int}
     */
    public int hamming() {
        int n = tiles.length;
        int dis = 0, startIdx;
        int[] row;

        for (int i = 0; i < n; i++) {
            row = tiles[i];
            startIdx = i * n;

            for (int j = 0; j < n; j++) {
                if (row[j] == 0) continue;
                if (row[j] != startIdx + j + 1) dis++;
            }
        }
        return dis;
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     * @return {int}
     */
    public int manhattan() {
        int n = tiles.length;
        int dis = 0;
        int[] row;

        for (int i = 0; i < n; i++) {
            row = tiles[i];
            for (int j = 0; j < n; j++) {
                if (row[j] == 0) continue;
                dis += disManhattan(row[j], n, i, j);
            }
        }
        return dis;
    }

    /**
     * Is this board the goal board?
     * @return {boolean}
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Exchange specific pair of blocks
     * @param i1 {int}
     * @param j1 {int}
     * @param i2 {int}
     * @param j2 {int}
     */
    private void exch(int i1, int j1, int i2, int j2) {
        int tmp = tiles[i1][j1];
        tiles[i1][j1] = tiles[i2][j2];
        tiles[i2][j2] = tmp;
        if (tmp == 0) {
            blankX = i2;
            blankY = j2;
        }
        else if (tiles[i1][j1] == 0) {
            blankX = i1;
            blankY = j1;
        }
    }

    /**
     * A board that is obtained by exchanging any pair of blocks
     * (the blank square is not a block)
     * @return {int}
     */
    public Board twin() {
        Board b = new Board(tiles);
        int n = tiles.length;
        int i1, j1, i2, j2;
        do {
            i1 = StdRandom.uniform(0, n);
            j1 = StdRandom.uniform(0, n);
            i2 = StdRandom.uniform(0, n);
            j2 = StdRandom.uniform(0, n);
        } while ((i1 == i2 && j1 == j2) || tiles[i1][j1] == 0 || tiles[i2][j2] == 0);
        b.exch(i1, j1, i2, j2);
        return b;
    }

    /**
     * Does this board equal y?
     * @param y {Object}
     * @return {boolean}
     */
    public boolean equals(Object y) {
        return (y != null) && toString().equals(y.toString());
    }

    /**
     * All neighboring boards
     * @return {Iterable<Board>}
     */
    public Iterable<Board> neighbors() {
        Stack<Board> st = new Stack<Board>();

        addNeighbor(st, blankX - 1, blankY);
        addNeighbor(st, blankX + 1, blankY);
        addNeighbor(st, blankX, blankY - 1);
        addNeighbor(st, blankX, blankY + 1);

        return st;
    }

    /**
     * Add neighbor board
     * @param st {Stack<Board>}
     * @param i {int}
     * @param j {int}
     */
    private void addNeighbor(Stack<Board> st, int i, int j) {
        int n = tiles.length;
        if (i < 0 || i >= n || j < 0 || j >= n) return;
        Board b = new Board(tiles);
        b.exch(blankX, blankY, i, j); // exchange values
        st.push(b);
    }

    /**
     * String representation of this board (in the output format specified below)
     * @return {String}
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = tiles.length;
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}

package com.assignment1;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/6/2016
 *  Last updated:  11/6/2016
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *
 *  Model a percolation system
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int BLOCKED = 0x0000;         // means blocked
    private static final int OPENED = 0x0001;          // means opened
    private static final int CONNECTTOP = 0x0010;      // means connected to top row
    private static final int CONNECTBOTTOM = 0x0100;   // means connected to bottom row
    private static final int PERCULATED = 0x0111;      // means connected to both top and bottom rows
    private boolean isPercolated = false;
    private int gridSize;                            // size of n-by-n grid
    private int[][] sites;                  // sites grid
    private WeightedQuickUnionUF uf;        // union find instance

    /**
     * Create n-by-n grid, with all sites blocked
     * @param n {int}
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid grid size");
        }

        this.gridSize = n;
        initSites(n);
        uf = new WeightedQuickUnionUF(n * n);
    }

    /**
     * Init each site as blocked
     * @param n {int} grid size
     */
    private void initSites(int n) {
        sites = new int[n][n];
        for (int i = 0; i < n; i++) {
            sites[0][i] = CONNECTTOP;
            sites[gridSize - 1][i] = sites[gridSize - 1][i] | CONNECTBOTTOM;
        }

        for (int i = 1; i < n - 1; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = BLOCKED;
            }
        }
    }

    /**
     * Convert a 2-dimensional (row, column) pair to a 1-dimensional union find object index
     * @param row {int} row index
     * @param col {int} column index
     * @return {int} 1-dimensional index
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * gridSize + col - 1;
    }

    /**
     * Convert a 1-dimensional index to row index in 2-dimensional pair
     * @param index {int}
     * @return {int}
     */
    private int oneDToRow(int index) {
        return index / gridSize;
    }

    /**
     * Convert a 1-dimensional index to column index in 2-dimensional pair
     * @param index {int}
     * @return {int}
     */
    private int oneDToCol(int index) {
        return index % gridSize;
    }

    /**
     * Union neighbors
     * @param row1 {int}
     * @param col1 {int}
     * @param row2 {int}
     * @param col2 {int}
     */
    private void unionNeighbor(int row1, int col1, int row2, int col2) {
        if (!validate(row2, col2) || !isOpen(row2, col2)) {
            return;
        }

        int rootPos1 = uf.find(xyTo1D(row1, col1));
        int rootPos2 = uf.find(xyTo1D(row2, col2));
        int rowIdx1 = oneDToRow(rootPos1);
        int colIdx1 = oneDToCol(rootPos1);
        int rowIdx2 = oneDToRow(rootPos2);
        int colIdx2 = oneDToCol(rootPos2);
        int status = sites[rowIdx1][colIdx1] | sites[rowIdx2][colIdx2];
        sites[rowIdx1][colIdx1] = sites[rowIdx2][colIdx2] = status;
        uf.union(rootPos1, rootPos2);

        if (status == PERCULATED) {
            isPercolated = true;
        }
    }

    /**
     * Validate indices of row and column
     * @param row {int} row index
     * @param col {int} column index
     * @return {boolean}
     */
    private boolean validate(int row, int col) {
        return row > 0 && row <= gridSize && col > 0 && col <= gridSize;
    }

    /**
     * Open site (row, col) if it is not open already
     * @param row {int} row index
     * @param col {int} column index
     */
    public void open(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException("row index or column index out of bounds");
        }

        if (!isOpen(row, col)) {
            sites[row - 1][col - 1] = sites[row - 1][col - 1] | OPENED;
            if (gridSize == 1) {
                isPercolated = true;
                return;
            }

            unionNeighbor(row, col, row - 1, col);
            unionNeighbor(row, col, row + 1, col);
            unionNeighbor(row, col, row, col - 1);
            unionNeighbor(row, col, row, col + 1);
        }
    }

    /**
     * Is site (row, col) open?
     * @param row {int} row index
     * @param col {int} column index
     * @return {boolean}
     */
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException("row index or column index out of bounds");
        }
        return (sites[row - 1][col - 1] & OPENED) == OPENED;
    }

    /**
     * Is site (row, col) full?
     * @param row {int} row index
     * @param col {int} column index
     * @return {boolean}
     */
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException("row index or column index out of bounds");
        }
        int root = uf.find(xyTo1D(row, col));
        int status = sites[oneDToRow(root)][oneDToCol(root)];
        return isOpen(row, col) && (status & CONNECTTOP) == CONNECTTOP;
    }

    /**
     * Does the system percolate?
     * @return {boolean}
     */
    public boolean percolates() {
        return isPercolated;
    }
}

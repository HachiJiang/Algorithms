/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       12/4/2016
 *  Last updated:  12/4/2016
 *
 *  Compilation:   javac Solver.java
 *  Execution:     java Solver
 *
 *  Find a solution to the initial board (using the A* algorithm)
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode last;             // last min search node

    /**
     * Find a solution to the initial board (using the A* algorithm)
     * @param initial {Board}
     */
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();
        search(initial, initial.twin());
    }

    /**
     * Define search node in Game Tree
     */
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prev;  // previous search node
        private int moves;

        public SearchNode(Board b, SearchNode prev, int moves) {
            this.board = b;
            this.prev = prev;
            this.moves = moves;
        }

        /**
         * Compare two search nodes by priority
         * @param that {SearchNode}
         * @return {int}
         */
        public int compareTo(SearchNode that) {
            int p1 = this.board.manhattan() + this.moves;
            int p2 = that.board.manhattan() + that.moves;
            if (p1 < p2) return -1;
            else if (p1 == p2) return 0;
            else return 1;
        }
    }

    /**
     * Search goal board with b and bSwap
     * @param b {Board} board in pq
     * @param bSwap {Board} board in pqSwap
     */
    private void search(Board b, Board bSwap) {
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();          // the priority queue
        MinPQ<SearchNode> pqSwap = new MinPQ<SearchNode>();      // the priority queue after swapping any pair of blocks
        SearchNode nd = new SearchNode(b, null, 0);
        SearchNode ndSw = new SearchNode(bSwap, null, 0);
        Board bPrev = null;
        Board bSwPrev = null;

        while (!b.isGoal() && !bSwap.isGoal()) {
            insertNeighbors(pq, b, bPrev, nd);
            bPrev = b;
            nd = pq.delMin();
            b = nd.board;

            insertNeighbors(pqSwap, bSwap, bSwPrev, ndSw);
            bSwPrev = bSwap;
            ndSw = pqSwap.delMin();
            bSwap = ndSw.board;
        }
        last = nd;
    }

    /**
     * Insert neighbors of board b to priority queue
     * @param pqTree {MinPQ<SearchNode>}
     * @param b {Board} current board
     * @param bPrev {Board} previous board
     * @param nd {Board}
     */
    private void insertNeighbors(MinPQ<SearchNode> pqTree, Board b, Board bPrev, SearchNode nd) {
        int moves = nd.moves + 1;
        for (Board nb: b.neighbors()) {
            if (!nb.equals(bPrev))
                pqTree.insert(new SearchNode(nb, nd, moves));
        }
    }

    /**
     * Is the initial board solvable?
     * @return {boolean}
     */
    public boolean isSolvable() {
        return last.board.isGoal();
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     * @return {int}
     */
    public int moves() {
        return !isSolvable() ? -1 : last.moves;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     * @return {Iterable<Board>}
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> st = new Stack<Board>();
        SearchNode nd = last;
        while (nd != null) {
            st.push(nd.board);
            nd = nd.prev;
        }

        return st;
    }
}

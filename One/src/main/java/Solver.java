/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       12/4/2016
 *  Last updated:  12/7/2016
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
    private SearchNode goal;             // goal search node

    /**
     * Find a solution to the initial board (using the A* algorithm)
     * @param initial {Board}
     */
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();          // the priority queue
        pq.insert(new SearchNode(initial, null,  0));
        pq.insert(new SearchNode(initial.twin(), null, 0));
        SearchNode nd = pq.delMin();
        int moves;

        Board b = nd.board;
        Board bPrev = null;
        while (!b.isGoal()) {
            moves = nd.moves + 1;
            if (nd.prev != null) bPrev = nd.prev.board;
            for (Board nb: b.neighbors()) {
                if (bPrev != null && nb.equals(bPrev)) continue; // b is previous board of nb
                pq.insert(new SearchNode(nb, nd, moves));
            }

            nd = pq.delMin();
            b = nd.board;
        }

        // check whether the solution is from initial
        goal = nd;
        while (nd.prev != null) {
            nd = nd.prev;
        }
        if (nd.board != initial) goal = null;
    }

    /**
     * Define search node in Game Tree
     */
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prev;  // previous search node
        private int priority;     // distance + moves
        private int distance;     // distance
        private int moves;        // moves

        public SearchNode(Board b, SearchNode prev, int m) {
            this.board = b;
            this.prev = prev;
            int dis = b.manhattan();
            this.distance = dis;
            this.priority = dis + m;
            this.moves = m;
        }

        /**
         * Compare two search nodes by priority
         * @param that {SearchNode}
         * @return {int}
         */
        public int compareTo(SearchNode that) {
            int p1 = this.priority;
            int p2 = that.priority;
            if (p1 < p2) return -1;
            else if (p1 == p2) {
                int p3 = this.distance;
                int p4 = that.distance;
                if (p3 < p4) return -1;
                else if (p3 == p4) return 0;
                else return 1;
            }
            else return 1;
        }
    }

    /**
     * Is the initial board solvable?
     * @return {boolean}
     */
    public boolean isSolvable() {
        return goal != null;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     * @return {int}
     */
    public int moves() {
        return goal == null ? -1 : goal.moves;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     * @return {Iterable<Board>}
     */
    public Iterable<Board> solution() {
        if (goal == null) return null;

        Stack<Board> st = new Stack<Board>();
        SearchNode nd = goal;
        while (nd != null) {
            st.push(nd.board);
            nd = nd.prev;
        }

        return st;
    }
}

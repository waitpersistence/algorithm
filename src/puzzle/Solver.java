//package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final boolean solvable;

    private final int moves;

    private final Node goal;
    //存储一个个节点，计算最小的f(n),并将其弹出，实现它.
    //一个节点要的数据，棋盘，他的曼哈顿距离，与到那个节点的moves
    private class Node implements Comparable<Node>{
        Board board;
        Node prev;
        int moves;
        private final int manhattanScore;

        Node(Board b,Node p,int m){
            this.board = b;
            prev = p;
            moves = m;
            manhattanScore = m+b.manhattan();
        }

        @Override
        public int compareTo(Node that) {
            return Integer.compare(this.manhattanScore, that.manhattanScore);
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial==null){
            throw new IllegalArgumentException();
        }
        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> minPQForTwin = new MinPQ<>();
        minPQ.insert(new Node(initial, null, 0));
        minPQForTwin.insert(new Node(initial.twin(),null,0));
        Node node = null;
        MinPQ<Node> executor = minPQ;
        while (!executor.isEmpty()) {
            node = executor.delMin();
            if (node.board.isGoal()) {
                break;
            }
            Iterable<Board> neighbors = node.board.neighbors();
            for (Board b : neighbors)
                if (node.prev == null || !b.equals(node.prev.board))
                    executor.insert(new Node(b, node, node.moves + 1));

            executor = executor == minPQ ? minPQForTwin : minPQ;
        }
        this.solvable = executor == minPQ;
        assert node != null;
        moves = node.moves;
        goal = node;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solvable;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if (!solvable) return -1;
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if (!solvable) return null;
        Stack<Board> p = new Stack<>();
        Node node = goal;
        while (node != null) {
            p.push(node.board);
            node = node.prev;
        }
        return p;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}

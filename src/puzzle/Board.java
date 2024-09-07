//package puzzle;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] board;
    private int n;//储存行数
    //hamming
    private int distance;//manhamton
    private int[] zrlc;
    private int[][] DIR=new int[][]{
            {0,1},
            {0,-1},
            {1,0},
            {-1,0},
    };
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        if(tiles.length<=0){
            throw new IllegalArgumentException();
        }
        n = tiles.length;;
        board = new int[tiles.length][tiles[0].length];
        for(int i = 0;i< tiles.length;i++){
            for(int j  = 0;j<tiles[0].length;j++){
                board[i][j] = tiles[i][j];
            }
        }
        zrlc = this.zero();
    }
    // string representation of this board
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(n+"\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        int ham1 = 0;
        for(int i = 0 ;i< board.length;i++){
            for(int j = 0; j< board.length;j++){
                if(board[i][j]!=0) {
                    if (!at(i, j)) {
                        ham1++;
                    }
                }
            }
        }
        return ham1;
    }

    // sum of Manhattan distances between tiles and goal
    private boolean at(int i , int j){
        if(i==board.length-1 && j == board.length-1){//最后一个格子是0
            if(board[i][j]==0){
                return true;
            }else
                return false;
        } else {//不是最后一个格子
            return board[i][j] == i * board.length + j + 1;
        }
    }
    public int manhattan(){
        distance=0;
        for(int i=0;i< board.length;i++){
            for(int j = 0;j< board.length;j++){
                if(board[i][j]!=0) {
                    if (!at(i, j)) {
                        int[] index1 = index(board[i][j]);
                        distance += Math.abs(index1[0] - i) + Math.abs(index1[1] - j);
                    }
                }
            }
        }
        return  distance;
    }
    private int[] index(int value){
        int[] location = new int[2];
        int i = (value-1) / board.length;
        int j = (value - 1 - i*board.length);
        location[0] = i;
        location[1] = j;
        return location;
    }
    // is this board the goal board?
    public boolean isGoal(){
        for(int i=0;i< board.length;i++){
            for(int j =0;j< board.length;j++){
                if(!at(i,j)){
                    return false;
                }
            }
        }
        return true;
    }

//     does this board equal y?
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        return Arrays.deepEquals(this.board, that.board);
    }

    // all neighboring boards
    //1.先找到0的位置
    private int[] zero(){
        int[] zrlc = new int[2];
        for(int i = 0;i< board.length;i++){
            for(int j = 0;j< board.length;j++){
                if(board[i][j]==0){
                    zrlc[0]=i;
                    zrlc[1]=j;
                }
            }
        }
        return zrlc;
    }
    // 复制棋盘的方法
    private int[][] copyBoard(int[][] board) {
        int n = board.length;
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    //开始找相邻的点
    public Iterable<Board> neighbors(){
        ArrayList<Board> p = new ArrayList<>();
        int[] zrlc = this.zero();
        // 尝试每个方向的移动
        int blankRow = zrlc[0];
        int blankCol = zrlc[1];
        for(int[] dir:DIR){
            int newRow = zrlc[0]+dir[0];
            int newCol = zrlc[0]+dir[1];
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                // 复制当前棋盘
                int[][] newBoard = copyBoard(board);

                // 交换空白方块和新位置的方块
                newBoard[blankRow][blankCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;

                // 添加新的棋盘到邻居列表
                p.add(new Board(newBoard));
            }
        }
        return p;
    }

//     a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int[][] newboard = copyBoard(board);
        int[] swap1 = new int[2];
        int[] swap2 = new int[2];
        //找到第一个
        for(int i = 0;i<n;i++){
            for(int j =0;j<n;j++){
                if(i!=zrlc[0]&&j!=zrlc[1]){
                    swap1[0] = i;
                    swap1[1] = j;
                }

            }
        }
        //找到第二个
        for(int i = 0;i<n;i++){
            for(int j =0;j<n;j++){
                if(i!=swap1[0]&&j!=swap1[1]){
                if(i!=zrlc[0]&&j!=zrlc[1]){
                    swap2[0] = i;
                    swap2[1] = j;
                }
                }
            }
        }
        //交换
        int value = board[swap1[0]][swap1[1]];
        newboard[swap1[0]][swap1[1]] = newboard[swap2[0]][swap2[1]];
        newboard[swap2[0]][swap2[1]] = value;
        Board newboard1 = new Board(newboard);
        return newboard1;

    }

    // unit testing (not graded)
    public static void main(String[] args){

        int[][] tiles = new int[][]{{1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};
        Board board = new Board(tiles);
        System.out.println(board.toString());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        int[][] newtiles = new int[][]{{1, 3, 2},
                {4, 5, 6},
                {7, 8, 0}};
        Board board1 = new Board(newtiles);
        System.out.println(board.equals(board1));
        System.out.println(board.neighbors());
        System.out.println(board.twin());
    }

}

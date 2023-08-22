package tp.isec.pa.tinypac.model.data;

import java.io.Serializable;

public final class Maze implements Serializable {
    private final IMazeElement[][] board;
    public Maze(int height, int width) {
        board = new IMazeElement[height][width];
    }
    public boolean set(int y, int x,IMazeElement element) {
        if (element==null)
            System.out.println("");
        if (y < 0 || y >= board.length || x < 0 || x >= board[0].length)
            return false;
        board[y][x] = element; // can be null
        return true;
    }
    public IMazeElement get(int y, int x) {

        if (y < 0 || y >= board.length || x < 0 || x >= board[0].length)
            return null;
        return board[y][x]; // can be null
    }
    public char[][] getMaze() {
        char[][] char_board = new char[board.length][board[0].length];
        for(int y=0;y<board.length;y++)
            for(int x=0;x<board[y].length;x++)
                if (board[y][x]==null)
                    char_board[y][x] = ' ';
                else
                    char_board[y][x] = board[y][x].getSymbol();
        return char_board;
    }
}

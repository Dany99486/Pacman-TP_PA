package tp.isec.pa.tinypac.model.data.BlinkyClyde;

import tp.isec.pa.tinypac.model.data.GameBWData;

import tp.isec.pa.tinypac.model.data.MazeElement;
import tp.isec.pa.tinypac.model.fsm.Direction;

public class Clyde extends Blinky {
    static final private char type='C';

    public Clyde(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return type;
    }

    @Override
    public void move(GameBWData game) {
        if (!onSite(game))
            super.move(game);

    }


    private boolean onSite(GameBWData game) {
        char[][] board = game.getBoard();
        int x = getX();
        int y = getY();



        // verifica a linha
        for (int j = 0; j < board[y].length; j++) {
            if (board[y][j] == 'c' && j != x) {
                // verifica se há 'x' entre a posição atual e a posição do 'c' na mesma linha
                if (isBlocked(x, j, y, y, board)) {

                    return false;
                }
                if (getOverItem()!='-'){
                    MazeElement a=new MazeElement();
                    a.setElemento(getOverItem());
                    game.getTrueBoard().set(getY(),getX(),a);
                }
                // se não há 'x', mova o personagem
                if (j > x) {
                    increaseX();
                    direction= Direction.RIGHT;
                } else {
                    decreaseX();
                    direction= Direction.LEFT;
                }
                addDirection(direction);
                return true;
            }
        }

        // verifica a coluna
        for (int i = 0; i < board.length; i++) {
            if (board[i][x] == 'c' && i != y) {
                // verifica se há 'x' entre a posição atual e a posição do 'c' na mesma coluna
                if (isBlocked(x, x, y, i, board)) {
                    return false;
                }
                if (getOverItem()!='-'){
                    MazeElement a=new MazeElement();
                    a.setElemento(getOverItem());
                    game.getTrueBoard().set(getY(),getX(),a);
                }
                // se não há 'x', mova o personagem
                if (i > y) {
                    increaseY();
                    direction= Direction.DOWN;
                } else {
                    decreaseY();
                    direction= Direction.UP;
                }
                addDirection(direction);
                return true;
            }
        }

        return false;
    }

    private boolean isBlocked(int startX, int endX, int startY, int endY, char[][] board) {

        int stepX,stepY;
        if (endX-startX<0) stepX=-1;
        else if(endX==startX)stepX=0;
        else stepX=1;

        if (endY-startY<0) stepY=-1;
        else if(endY==startY)stepY=0;
        else stepY=1;

        int currentX = startX + stepX;
        int currentY = startY + stepY;

        while (currentX != endX || currentY != endY) {
            if (board[currentY][currentX] == 'x') {
                return true;
            }

            currentX += stepX;
            currentY += stepY;
        }

        return false;
    }


}

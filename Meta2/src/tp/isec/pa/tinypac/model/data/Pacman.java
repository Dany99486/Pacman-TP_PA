package tp.isec.pa.tinypac.model.data;


import java.io.Serializable;


public class Pacman implements IMazeElement, Serializable {
    static final private char type='c';
    private int x,y;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {return x;}
    public int getY() {return y;}

    public void move(int keyType, GameData game){

        MazeElement a=new MazeElement();
        a.setElemento(' ');
        game.getTrueBoard().set(y,x,a);
        MazeElement a1=new MazeElement();
        a1.setElemento('W');
        game.getTrueBoard().set(game.getWarps()[0][0],game.getWarps()[0][1],a1);
        MazeElement a2=new MazeElement();
        a2.setElemento('W');
        game.getTrueBoard().set(game.getWarps()[1][0],game.getWarps()[1][1],a2);
        switch (keyType){
            case 4 -> {//d
                if (game.getCoord(x+1,y)=='o'||game.getCoord(x+1,y)=='O'||game.getCoord(x+1,y)=='F'||game.getCoord(x+1,y)=='M'||game.getCoord(x+1,y)=='W'||game.getCoord(x+1,y)==' '||game.getCoord(x+1,y)=='T') {
                    ++x;
                }
            }
            case 3 -> {//s
                if (game.getCoord(x,y+1)=='o'||game.getCoord(x,y+1)=='O'||game.getCoord(x,y+1)=='F'||game.getCoord(x,y+1)=='M'||game.getCoord(x,y+1)=='W'||game.getCoord(x,y+1)==' '||game.getCoord(x,y+1)=='T') {
                    ++y;
                }
            }
            case 2 -> {//w
                if (game.getCoord(x,y-1)=='o'||game.getCoord(x,y-1)=='O'||game.getCoord(x,y-1)=='F'||game.getCoord(x,y-1)=='M'||game.getCoord(x,y-1)=='W'||game.getCoord(x,y-1)==' '||game.getCoord(x,y-1)=='T') {
                    --y;
                }
            }
            case 1 -> {//a
                if (game.getCoord(x-1,y)=='o'||game.getCoord(x-1,y)=='O'||game.getCoord(x-1,y)=='F'||game.getCoord(x-1,y)=='M'||game.getCoord(x-1,y)=='W'||game.getCoord(x-1,y)==' '||game.getCoord(x-1,y)=='T') {
                    --x;
                }
            }


        }
        if (game.getCoord(x,y)=='o'){
            game.setCoord(x,y,' ');
            game.addPoints(1);
            game.addEatenFruit();
        }

    }



    public char getSymbol() {
        return type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

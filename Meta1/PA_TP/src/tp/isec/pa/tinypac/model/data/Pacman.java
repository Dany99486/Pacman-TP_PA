package tp.isec.pa.tinypac.model.data;


import com.googlecode.lanterna.input.KeyType;



public class Pacman {
    static final private char type='c';
    private int x,y;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {return x;}
    public int getY() {return y;}

    public void move(KeyType keyType,GameBWData game){
        if (game.getCoord(x,y)=='o'){
            game.setCoord(x,y,' ');
            game.addPoints(1);
            game.addEatenFruit();
        }
        switch (keyType){
            case ArrowRight -> {
                if (game.getCoord(x+1,y)=='o'||game.getCoord(x+1,y)=='O'||game.getCoord(x+1,y)=='F'||game.getCoord(x+1,y)=='M'||game.getCoord(x+1,y)=='W'||game.getCoord(x+1,y)==' '||game.getCoord(x+1,y)=='T') {
                    ++x;
                }
            }
            case ArrowDown -> {
                if (game.getCoord(x,y+1)=='o'||game.getCoord(x,y+1)=='O'||game.getCoord(x,y+1)=='F'||game.getCoord(x,y+1)=='M'||game.getCoord(x,y+1)=='W'||game.getCoord(x,y+1)==' '||game.getCoord(x,y+1)=='T') {
                    ++y;
                }
            }
            case ArrowUp -> {
                if (game.getCoord(x,y-1)=='o'||game.getCoord(x,y-1)=='O'||game.getCoord(x,y-1)=='F'||game.getCoord(x,y-1)=='M'||game.getCoord(x,y-1)=='W'||game.getCoord(x,y-1)==' '||game.getCoord(x,y-1)=='T') {
                    --y;
                }
            }
            case ArrowLeft -> {
                if (game.getCoord(x-1,y)=='o'||game.getCoord(x-1,y)=='O'||game.getCoord(x-1,y)=='F'||game.getCoord(x-1,y)=='M'||game.getCoord(x-1,y)=='W'||game.getCoord(x-1,y)==' '||game.getCoord(x-1,y)=='T') {
                    --x;
                }
            }


        }

    }



    public char getType() {
        return type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

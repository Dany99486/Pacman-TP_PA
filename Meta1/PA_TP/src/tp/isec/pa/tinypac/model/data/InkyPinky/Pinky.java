package tp.isec.pa.tinypac.model.data.InkyPinky;

import tp.isec.pa.tinypac.model.data.GameBWData;
import tp.isec.pa.tinypac.model.data.IGhost;
import tp.isec.pa.tinypac.model.fsm.Direction;

import java.util.ArrayList;
import java.util.Collections;

public class Pinky implements IGhost {
    static final private char type='P';
    static final private int cornerDistance=7;
    private int x,y;
    private final ArrayList<int[]>corners;
    private Direction blockedDirection;
    private ArrayList<Direction>directionsHistory;
    private boolean isVulnerable;

    public Pinky(int x, int y, int boarderWidth, int boarderHeight) {
        this.x = x;
        this.y = y;
        corners=new ArrayList<>();
        directionsHistory=new ArrayList<>();
        corners.add(new int[]{1,boarderHeight-2});//sup dir
        corners.add(new int[]{boarderWidth-2,boarderHeight-2});//inf dir
        corners.add(new int[]{1,1});//sup esq
        corners.add(new int[]{boarderWidth - 2, 1});//inf esq

    }
    protected void forInky(){
        Collections.swap(corners,2,3);//4ºbem      sd-id-ie-se
        Collections.swap(corners,1,2);//2ºbem       sd-ie-id-se
        Collections.swap(corners,0,2);
    }

    @Override
    public boolean isVulnerable() {
        return isVulnerable;
    }
    @Override
    public void setUnvulnerable() {
        isVulnerable=false;
    }


    @Override
    public int getX() {return x;}
    @Override
    public int getY() {return y;}

    @Override
    public char getType() {
        return type;
    }

    @Override
    public void move(GameBWData game) {
        char[][]maze= game.getBoard();
        int []getYlocation = new int[2];
        if (game.getCoord(x,y)=='y'||game.getCoord(x,y)=='Y'){
            for (int i = 0; i<maze.length; i++) {
                for (int j = 0; j < maze[1].length; j++) {
                    if (game.getCoord(i,j)=='Y')getYlocation= new int[]{i, j};
                }
            }
            if (x<getYlocation[0]) x++;
            else if (x>getYlocation[0])x--;
            else if (y>=getYlocation[1])y--;
            blockedDirection=Direction.DOWN;
            directionsHistory.clear();
            return;

        }
        if (calculateDistanceToNextCorner(x,y)<=cornerDistance) {
            int[] aux=corners.remove(0);
            corners.add(aux);
        }
        ArrayList<Direction> possibleDirections=new ArrayList<>();
        if (game.getBoard()[y-1][x] != 'x' && game.getBoard()[y-1][x] != 'W') possibleDirections.add(Direction.UP);
        if (game.getBoard()[y+1][x] != 'x' && game.getBoard()[y+1][x] != 'W') possibleDirections.add(Direction.DOWN);
        if (game.getBoard()[y][x-1] != 'x' && game.getBoard()[y][x-1] != 'W') possibleDirections.add(Direction.LEFT);
        if (game.getBoard()[y][x+1] != 'x' && game.getBoard()[y][x+1] != 'W') possibleDirections.add(Direction.RIGHT);

        if (possibleDirections.size()==0) return;

        Collections.shuffle(possibleDirections);

        ArrayList<int[]>nextPosicion=new ArrayList<>();

        int min_dist= game.getBoarderHeight()* game.getBoarderWidth();
        int[] selectedMove = new int[2];
        int dist;
        Direction direction = null;
        int[] move;
        for (Direction it:possibleDirections){
            if (it==blockedDirection)continue;
            switch (it){
                case UP -> {
                    direction=Direction.DOWN;
                    dist=calculateDistanceToNextCorner(x,y-1);
                    move= new int[]{x, y - 1};
                }
                case DOWN -> {
                    direction=Direction.UP;
                    dist=calculateDistanceToNextCorner(x, y+1);
                    move= new int[]{x, y + 1};
                }
                case LEFT -> {
                    direction=Direction.RIGHT;
                    dist=calculateDistanceToNextCorner(x-1, y);
                    move= new int[]{x-1, y};
                }
                case RIGHT -> {
                    direction=Direction.LEFT;
                    dist=calculateDistanceToNextCorner(x+1, y);
                    move= new int[]{x+1, y};

                }
                default -> {

                    return;
                }
            }
            if (dist<min_dist){

                blockedDirection=direction;
                min_dist=dist;
                selectedMove=move;
            }
        }

        for (int[] it:nextPosicion){
            dist=calculateDistanceToNextCorner(it[0],it[1]);
            if (dist<min_dist){
                blockedDirection=direction;
                min_dist=dist;
                selectedMove=it;
            }
        }
        directionsHistory.add(blockedDirection);
        x=selectedMove[0];
        y=selectedMove[1];
    }

    @Override
    public void invertMoves() {
        Collections.reverse(directionsHistory);
    }

    @Override
    public void reverseMove(GameBWData gameBWData) {
        if (directionsHistory.size()==0)
            isVulnerable=false;

        if (!isVulnerable){
            move(gameBWData);
            return;
        }
        switch (directionsHistory.get(0)) {
            case UP -> y--;
            case LEFT -> x--;
            case DOWN -> y++;
            case RIGHT -> x++;
        }
        directionsHistory.remove(0);
    }

    private int calculateDistanceToNextCorner(int x1,int y1){
        int n;
        n=Math.abs(x1-corners.get(0)[1])+Math.abs(y1-corners.get(0)[0]);
        return n;
    }

    public void setVulnerable() {
        isVulnerable = true;
    }
}

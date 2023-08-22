package tp.isec.pa.tinypac.model.data.InkyPinky;

import tp.isec.pa.tinypac.model.data.GameData;
import tp.isec.pa.tinypac.model.data.IGhost;

import tp.isec.pa.tinypac.model.data.MazeElement;
import tp.isec.pa.tinypac.model.fsm.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import static tp.isec.pa.tinypac.model.fsm.Direction.UP;

public class Pinky implements IGhost, Serializable {
    static final private char type='P';
    static final private int cornerDistance=7;
    private int x,y;
    private final ArrayList<int[]>corners;
    private Direction blockedDirection,direc;
    private ArrayList<Direction>directionsHistory;
    private boolean isVulnerable;
    private char overItem='y';

    @Override
    public void setOverItem(char overItem) {
        this.overItem = overItem;
    }

    @Override
    public char nextCell(GameData game) {
        char c;
        if (getDirection()==null) return 't';
        switch (getDirection()){
            case UP -> c = game.getCoord(x,y-1);
            case LEFT -> c = game.getCoord(x-1,y);
            case DOWN -> c = game.getCoord(x,y+1);
            case RIGHT -> c = game.getCoord(x+1,y);
            default -> c = 't';
        }

        return c;
    }

    @Override
    public char backCell(GameData game) {
        char c;

        switch (getDirection()){
            case DOWN -> c = game.getCoord(x,y-1);
            case RIGHT -> c = game.getCoord(x-1,y);
            case UP -> c = game.getCoord(x,y+1);
            case LEFT -> c = game.getCoord(x+1,y);
            default -> c = 't';
        }

        return c;    }

    @Override
    public Direction getDirection() {
        if (directionsHistory.isEmpty()) return null;

        Direction direction= directionsHistory.get(directionsHistory.size()-1);
        switch (direction){
            case DOWN ->{
                return UP;
            }
            case RIGHT -> {
                return UP;
            }
            case UP -> {
                return UP;
            }
            case LEFT -> {
                return UP;
            }
        }
        return null;
    }

    @Override
    public char getOverItem() {
        return overItem;
    }

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
    public Direction getDirec() {
        return direc;
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
    public void setOverItemBehind(char overItem, GameData data) {
        MazeElement a = new MazeElement();
        a.setElemento(overItem);
        if (!(data.getTrueBoard().get(y,x+1).getSymbol()=='x'||data.getTrueBoard().get(y,x+1).getSymbol()=='Y'||data.getTrueBoard().get(y,x+1).getSymbol()=='y'))
            data.getTrueBoard().set(y,x+1, a);

    }

    @Override
    public int getY() {return y;}

    @Override
    public char getSymbol() {
        return type;
    }

    @Override
    public void move(GameData game) {

        char[][]maze= game.getBoard();
        int []getYlocation = new int[2];

        if (getOverItem()=='y'||getOverItem()=='Y'){
            for (int i = 0; i<maze.length; i++) {
                for (int j = 0; j < maze[1].length; j++) {
                    if (game.getCoord(i,j)=='Y')getYlocation= new int[]{i, j};
                }
            }
            if (overItem=='Y'||overItem=='P'||overItem=='I'||overItem=='B'||overItem=='C') overItem='o';
            if (getOverItem() != '-') {
                MazeElement a = new MazeElement();
                a.setElemento(getOverItem());
                game.getTrueBoard().set(y,x, a);
            }



            if (x<game.getEntrance()[1]){
                if (overItem=='Y') overItem='m';

                setOverItem(game.getTrueBoard().get(y,x+1).getSymbol());
                x++;

            }
            else if (x>game.getEntrance()[1]){

                setOverItem(game.getTrueBoard().get(y,x-1).getSymbol());
                x--;


            }
            else if (y>=game.getEntrance()[0]){
                setOverItem(game.getTrueBoard().get(y-1,x).getSymbol());
                y--;


            }

            blockedDirection=Direction.DOWN;
            directionsHistory.clear();
            return;

        }
        if (overItem=='Y'||overItem=='P'||overItem=='I'||overItem=='B'||overItem=='C') overItem='o';
        if (getOverItem() != '-') {
            if (overItem=='Y') overItem='x';
            MazeElement a = new MazeElement();
            a.setElemento(getOverItem());
            game.getTrueBoard().set(y,x, a);
        }

        if (calculateDistanceToNextCorner(x,y)<=cornerDistance) {
            int[] aux=corners.remove(0);
            corners.add(aux);
        }
        ArrayList<Direction> possibleDirections=new ArrayList<>();
        if (game.getBoard()[y-1][x] != 'x' && game.getBoard()[y-1][x] != 'W'&& game.getBoard()[y-1][x] != 'Y') possibleDirections.add(UP);
        if (game.getBoard()[y+1][x] != 'x' && game.getBoard()[y+1][x] != 'W'&& game.getBoard()[y+1][x] != 'Y') possibleDirections.add(Direction.DOWN);
        if (game.getBoard()[y][x-1] != 'x' && game.getBoard()[y][x-1] != 'W'&& game.getBoard()[y][x-1] != 'Y') possibleDirections.add(Direction.LEFT);
        if (game.getBoard()[y][x+1] != 'x' && game.getBoard()[y][x+1] != 'W'&& game.getBoard()[y][x+1] != 'Y') possibleDirections.add(Direction.RIGHT);

        if (possibleDirections.size()==0) return;

        //System.out.println(overItem);

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
                    setOverItem(game.getTrueBoard().get(y-1,x).getSymbol());
                }
                case DOWN -> {
                    direction= UP;
                    dist=calculateDistanceToNextCorner(x, y+1);
                    move= new int[]{x, y + 1};
                    setOverItem(game.getTrueBoard().get(y+1,x).getSymbol());
                }
                case LEFT -> {
                    direction=Direction.RIGHT;
                    dist=calculateDistanceToNextCorner(x-1, y);
                    move= new int[]{x-1, y};
                    setOverItem(game.getTrueBoard().get(y,x-1).getSymbol());
                }
                case RIGHT -> {
                    direction=Direction.LEFT;
                    dist=calculateDistanceToNextCorner(x+1, y);
                    move= new int[]{x+1, y};
                    setOverItem(game.getTrueBoard().get(y,x+1).getSymbol());
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



        //setOverItemBehind(overItem,game);
        x=selectedMove[0];//move
        y=selectedMove[1];

    }

    public char nextCell(GameData game, Direction it){
        char c;

        switch (it){
            case UP -> c = game.getCoord(x,y-1);
            case LEFT -> c = game.getCoord(x-1,y);
            case DOWN -> c = game.getCoord(x,y+1);
            case RIGHT -> c = game.getCoord(x+1,y);
            default -> c = 't';
        }

        return c;
    }



    @Override
    public void invertMoves() {
        Collections.reverse(directionsHistory);
    }

    @Override
    public void reverseMove(GameData gameData) {
        if (directionsHistory.size()==0)
            isVulnerable=false;

        if (!isVulnerable){
            move(gameData);
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

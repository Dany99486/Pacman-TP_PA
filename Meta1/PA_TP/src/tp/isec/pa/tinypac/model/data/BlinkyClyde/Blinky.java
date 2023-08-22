package tp.isec.pa.tinypac.model.data.BlinkyClyde;

import tp.isec.pa.tinypac.model.data.GameBWData;
import tp.isec.pa.tinypac.model.data.IGhost;
import tp.isec.pa.tinypac.model.data.MazeElement;
import tp.isec.pa.tinypac.model.fsm.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blinky implements IGhost {
    static final private char type='B';
    private int x,y;
    Direction direction;
    private ArrayList<Direction>directionsHistory;
    private boolean isVulnerable;
    private char overItem='y';

    @Override
    public void setOverItem(char overItem) {
        this.overItem = overItem;
    }
    @Override
    public void setOverItemBehind(char overItem,GameBWData data) {
        MazeElement a = new MazeElement();
        a.setElemento(overItem);

        switch (direction){
            case DOWN -> {
                if (!(data.getTrueBoard().get(y-1,x).getSymbol()=='x'||data.getTrueBoard().get(y-1,x).getSymbol()=='Y'||data.getTrueBoard().get(y-1,x).getSymbol()=='y'))
                    data.getTrueBoard().set(y-1,x, a);
            }
            case RIGHT -> {
                if (!(data.getTrueBoard().get(y,x-1).getSymbol()=='x'||data.getTrueBoard().get(y,x-1).getSymbol()=='Y'||data.getTrueBoard().get(y,x-1).getSymbol()=='y'))
                    data.getTrueBoard().set(y,x-1, a);
            }
            case UP -> {
                if (!(data.getTrueBoard().get(y+1,x).getSymbol()=='x'||data.getTrueBoard().get(y+1,x).getSymbol()=='Y'||data.getTrueBoard().get(y+1,x).getSymbol()=='y'))
                    data.getTrueBoard().set(y+1,x, a);
            }
            case LEFT -> {
                if (!(data.getTrueBoard().get(y,x+1).getSymbol()=='x'||data.getTrueBoard().get(y,x+1).getSymbol()=='Y'||data.getTrueBoard().get(y,x+1).getSymbol()=='y'))
                    data.getTrueBoard().set(y,x+1, a);
            }
        }

        this.overItem = overItem;


    }
    @Override
    public char getOverItem() {
        return overItem;
    }

    public Blinky(int x, int y) {
        directionsHistory=new ArrayList<>();
        this.x = x;
        this.y = y;
        direction=Direction.UP;
    }
    @Override
    public boolean isVulnerable() {
        return isVulnerable;
    }

    @Override
    public int getX() {return x;}
    @Override
    public int getY() {return y;}

    public void addDirection(Direction direction){
        directionsHistory.add(direction);
    }

    public void increaseX(){++x;}
    public void increaseY(){++y;}
    public void decreaseX(){--x;}
    public void decreaseY(){--y;}

    //++x -> direita
    //++y -> baixo

    @Override
    public void move(GameBWData game){

        char nextCell = nextCell(game);
        char[][]maze= game.getBoard();
        int []getYlocation = new int[2];
        if (game.getCoord(x,y)=='y'||game.getCoord(x,y)=='Y'){
            for (int i = 0; i<maze.length; i++) {
                for (int j = 0; j < maze[1].length; j++) {
                    if (game.getCoord(i,j)=='Y')getYlocation= new int[]{i, j};
                }
            }
            setOverItem(game.getTrueBoard().get(y,x).getSymbol());
            if (x<getYlocation[0]) x++;
            else if (x>getYlocation[0])x--;
            else if (y>=getYlocation[1])y--;
            direction=Direction.UP;
            directionsHistory.clear();
            if (overItem=='Y'||overItem=='P'||overItem=='I'||overItem=='B'||overItem=='C') overItem='o';
            return;

        }

        //ver se bem ==========================================
        /*
        if (overItem!='-'){
            MazeElement a=new MazeElement();
            a.setElemento(overItem);
            game.getTrueBoard().set(y,x,a);
        }*/


        // Se a próxima célula não for uma parede, continue na mesma direção
        if (nextCell != 'x'&&nextCell!='Y') {/*
            if (nextCell=='P'||nextCell=='B'||nextCell=='I'||nextCell=='C')
                overItem='-';
            else                                    //overItem muda muito cedo
                overItem=nextCell;*/
            //System.out.println(overItem);
            switch (direction) {
                case UP -> y--;
                case LEFT -> x--;
                case DOWN -> y++;
                case RIGHT -> x++;
            }
            directionsHistory.add(direction);
            return;
        }

        // Se chegou aqui, é porque encontrou uma parede, então precisa escolher uma nova direção
        List<Direction> possibleDirections = new ArrayList<>();

        // Adiciona as direções possíveis (que não são parede)
        if (game.getBoard()[y-1][x] != 'x' && direction != Direction.DOWN) possibleDirections.add(Direction.UP);
        if (game.getBoard()[y+1][x] != 'x' && direction != Direction.UP) possibleDirections.add(Direction.DOWN);
        if (game.getBoard()[y][x-1] != 'x' && direction != Direction.RIGHT) possibleDirections.add(Direction.LEFT);
        if (game.getBoard()[y][x+1] != 'x' && direction != Direction.LEFT) possibleDirections.add(Direction.RIGHT);

        // Se não há direções possíveis, apenas volte na direção oposta
        if (possibleDirections.size() == 0) {
            switch (direction) {
                case UP -> {
                    y++;
                    direction = Direction.DOWN;
                }
                case LEFT -> {
                    x++;
                    direction = Direction.RIGHT;
                }
                case DOWN -> {
                    y--;
                    direction = Direction.UP;
                }
                case RIGHT -> {
                    x--;
                    direction = Direction.LEFT;
                }
            }
        } else {
            // Escolhe uma direção aleatória das possíveis
            int randomIndex = (int)(Math.random() * possibleDirections.size());
            direction = possibleDirections.get(randomIndex);

            // Move o fantasma na nova direção
            switch (direction) {
                case UP -> y--;
                case LEFT -> x--;
                case DOWN -> y++;
                case RIGHT -> x++;
            }
        }
        directionsHistory.add(direction);
    }

    @Override
    public void invertMoves() {
        Collections.reverse(directionsHistory);
        if (directionsHistory.size()<4) return;
        directionsHistory.remove(directionsHistory.size()-1);
        directionsHistory.remove(directionsHistory.size()-1);
        directionsHistory.remove(directionsHistory.size()-1);
        directionsHistory.remove(directionsHistory.size()-1);

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
            case DOWN -> y--;
            case RIGHT -> x--;
            case UP -> y++;
            case LEFT -> x++;
        }
        directionsHistory.remove(0);
    }

    @Override
    public void setVulnerable() {
        isVulnerable = true;
    }

    @Override
    public void setUnvulnerable() {
        isVulnerable=false;
    }


    public char nextCell(GameBWData game){
        char c;

        switch (direction){
            case UP -> c = game.getCoord(x,y-1);
            case LEFT -> c = game.getCoord(x-1,y);
            case DOWN -> c = game.getCoord(x,y+1);
            case RIGHT -> c = game.getCoord(x+1,y);
            default -> c = 't';
        }

        return c;
    }

    @Override
    public char backCell(GameBWData game) {
        char c;

        switch (direction){
            case DOWN -> c = game.getCoord(x,y-1);
            case RIGHT -> c = game.getCoord(x-1,y);
            case UP -> c = game.getCoord(x,y+1);
            case LEFT -> c = game.getCoord(x+1,y);
            default -> c = 't';
        }

        return c;
    }


    public Direction getDirection() {
        return direction;
    }

    @Override
    public char getSymbol() {
        return type;
    }

}

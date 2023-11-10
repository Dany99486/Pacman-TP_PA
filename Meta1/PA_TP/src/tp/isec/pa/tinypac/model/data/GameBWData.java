package tp.isec.pa.tinypac.model.data;

import com.googlecode.lanterna.input.KeyType;
import tp.isec.pa.tinypac.model.data.BlinkyClyde.Blinky;
import tp.isec.pa.tinypac.model.data.BlinkyClyde.Clyde;
import tp.isec.pa.tinypac.model.data.InkyPinky.Inky;
import tp.isec.pa.tinypac.model.data.InkyPinky.Pinky;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GameBWData {
    private static final int boarderWidth=29;
    private static final int boarderHeight=31;
    private static final int MaxLevel=20;
    private static final int initialTimer=10000;

    private Maze board;
    private int points;
    boolean venceu;
    private List<IGhost> ghosts;
    private Pacman pacman;
    private KeyType direction;
    private int coinsGot;
    private int[] fSpot;
    private int[][]warps;
    private int lastLevelMap;
    private int level;
    private int lifes;
    private int tic;
    private int velocidade;// [0,99] a zero anda o mesmo que o pacman ; a 50 andam o dobro
    private int timer;
    private boolean hauntFlag;



    public GameBWData()  {

        lifes=3;
        level=0;
        coinsGot=0;
        points=0;
        tic=0;
        velocidade=1;
        direction= KeyType.ArrowRight;
    }

    public int getTimer() {return timer;}
    public void normalMode() {
        hauntFlag=false;
    }

    public void initGame() {

        timer=initialTimer-(((level-1)*100)/2);
        if (level%4==0)
            velocidade=velocidade+(level/7)*8;

        hauntFlag=false;
        //quando morrer falta reiniciar o nivel
        board=new Maze(boarderHeight,boarderWidth);
        loadMazeFromFile(countMaps());

        ghosts=new ArrayList<>();
        ghosts.add(new Blinky(15,15));
        ghosts.add(new Clyde(14,15));
        ghosts.add(new Pinky(13,15,boarderWidth,boarderHeight));
        ghosts.add(new Inky(16,15,boarderWidth,boarderHeight));
        pacman=new Pacman(getSpawn()[1],getSpawn()[0]);
        setfSpot();
        setWarps();
    }
    public int getBoarderWidth(){return boarderWidth;}

    public int getBoarderHeight() {return boarderHeight;}

    public int[] getSpawn() {
        char[][] maze = board.getMaze();
        int[] coord=null;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'M') coord = new int[]{i, j};
            }

        }
        return coord;
    }
    public void setfSpot() {
        char[][] maze = board.getMaze();
        int[] coord=null;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'F') coord = new int[]{i, j};
            }

        }
        fSpot=coord;
    }
    public void setWarps() {
        char[][] maze = board.getMaze();
        int[][] coord = new int[2][];
        int n=0;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'W'){
                    coord[n] = new int[]{i, j};
                    ++n;
                }
            }

        }
        warps=coord;
    }

    private void loadMazeFromFile(String filename) {
        FileReader fr=null;
        try {
            fr=new FileReader(filename);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    MazeElement element = null;
                    if (c != ' ') {
                        element = new MazeElement();
                        element.setElemento(c);
                    }
                    board.set(y, x, element);
                }
                y++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {//sempre executado
            if (fr!=null){
                try {
                    fr.close();
                }catch (IOException e){
                    System.out.println("problema ao fechar");
                }
            }
        }
    }
    public boolean endOFLevel(){
        char[][]maze=board.getMaze();
        if (ghosts.size()==0)return true;
        for (int i = 0; i<maze.length; i++) {
            for (int j = 0; j < maze[1].length; j++) {
                if (maze[i][j]=='o'/*||maze[i][j]=='O'*/)return false;
            }
        }

        if (level>=20)
            venceu=true;
        return true;
    }


    private String countMaps(){
        File verifier=new File("maps/level01.txt");
        String path;

        if (!verifier.exists()){
            System.out.println("Falta o primeiro labirinto");
            System.exit(1);
        }


        for (int i=level+1;i<MaxLevel+1;i++){

            if (i<10)
                path="maps/level0"+i+".txt";
            else
                path="maps/level"+i+".txt";


            verifier=new File(path);
            if (verifier.exists()){

                level=i;
                lastLevelMap=i;

                return path;
            }
            else {

                if (lastLevelMap<10)
                    path="maps/level0" + lastLevelMap + ".txt";
                else
                    path="maps/level" + lastLevelMap + ".txt";

                level = i;
                return path;
            }
        }
        return "maps/level01.txt";
    }

    public int getPoints() {return points;}
    public void addPoints(int n){points+=n;}
    public char[][] getBoard() {
        char[][] game=board.getMaze();
        for (IGhost it:ghosts) {
            game[it.getY()][it.getX()]=it.getType();
        }
        game[pacman.getY()][pacman.getX()]= pacman.getType();
        return game;
    }

    public char getCoord(int x,int y){
        return board.get(y, x) != null ? board.get(y, x).getSymbol() : ' ';
    }
    public void setCoord(int x,int y,char c){
        MazeElement a=new MazeElement();
        a.setElemento(c);
        board.set(y,x,a);
    }
    public void warp(){
        if (pacman.getX()==warps[0][1]&&pacman.getY()==warps[0][0]){
            pacman.setX(warps[1][1]);
            pacman.setY(warps[1][0]);
        }else if (pacman.getX()==warps[1][1]&&pacman.getY()==warps[1][0]){
            pacman.setX(warps[0][1]);
            pacman.setY(warps[0][0]);
        }
    }
    public void frutaF(){
        if (getCoord(fSpot[1], fSpot[0])=='F') {
            if (pacman.getX()== fSpot[1]&&pacman.getY()== fSpot[0]) {
                setCoord(fSpot[1], fSpot[0], ' ');
                int a=0;
                int i=0;
                while (a>=0){
                    i++;
                    a= coinsGot -(25*i);

                }
                addPoints(i*25);
            }
            return;
        }
        if (coinsGot %25==0)setCoord(fSpot[1], fSpot[0],'F');


    }

    public boolean isHauntFlag() {
        return hauntFlag;
    }

    public void powerUP(){
        if (getCoord(pacman.getX(),pacman.getY())=='O'){
            addPoints(10);
            setCoord(pacman.getX(), pacman.getY(),' ');
            hauntFlag=true;
        }
    }
    public void action() {

        for (IGhost it:ghosts){
            if (tic%(100-velocidade)==0)
                it.move(this);
            if (it.getX()== pacman.getX()&&it.getY()== pacman.getY())
                pacmanDie();
        }
        if (tic%100==0){
            pacman.move(direction,this);
            for (IGhost it:ghosts){
                if (it.getX()== pacman.getX()&&it.getY()== pacman.getY())
                    pacmanDie();
            }
            frutaF();
            warp();
            powerUP();

        }
        tic++;
    }
    public boolean isBlinkyVulnerable(){
        for (IGhost it:ghosts)
            if (it.getType()=='B')
                return it.isVulnerable();
        return false;
    }
    public boolean isPinkyVulnerable(){
        for (IGhost it:ghosts)
            if (it.getType()=='P')
                return it.isVulnerable();
        return false;
    }
    public boolean isInkyVulnerable(){
        for (IGhost it:ghosts)
            if (it.getType()=='I')
                return it.isVulnerable();
        return false;
    }
    public boolean isClydeVulnerable(){
        for (IGhost it:ghosts)
            if (it.getType()=='C')
                return it.isVulnerable();
        return false;
    }
    public void invertMoves(){
        for (IGhost it:ghosts){
            it.invertMoves();
            it.setVulnerable();
        }
    }
    public void hauntMode(){
        hauntFlag=true;
        int countGhosts=0;
        IGhost a=null;
        for (IGhost it:ghosts){
            if (tic%(100-velocidade)==0)
                it.reverseMove(this);
            ++countGhosts;
            if (it.getX()== pacman.getX()&&it.getY()== pacman.getY()){
                if (it.isVulnerable()){
                    a=it;
                    addPoints((5-countGhosts)*50);
                }else {
                    pacmanDie();
                }
            }
        }
        if (tic%100==0){
            pacman.move(direction,this);
            warp();
        }
        for (IGhost it:ghosts){
            if (it.getX()== pacman.getX()&&it.getY()== pacman.getY()){
                if (it.isVulnerable()){
                    a=it;
                    addPoints((5-countGhosts)*50);
                }else
                    pacmanDie();


            }
        }
        if(ghosts.size()==0) {
            addPoints(200);
        }
        ghosts.remove(a);
        frutaF();
        tic++;

    }
    public void setKey(KeyType key) {

        if (key==KeyType.ArrowDown||key==KeyType.ArrowUp||key==KeyType.ArrowRight||key==KeyType.ArrowLeft)
            direction=key;
    }

    public void addEatenFruit() {
        ++coinsGot;
    }

    public void pacmanDie(){
        lifes--;
        pacman.setY(getSpawn()[0]);
        pacman.setX(getSpawn()[1]);
        level--;
        initGame();
    }

    public int getLifes() {
        return lifes;
    }




    public boolean getVenceu() {
        return venceu;
    }

    public void setUnvunerable() {
        for (IGhost it:ghosts)
            it.setUnvulnerable();
    }
}

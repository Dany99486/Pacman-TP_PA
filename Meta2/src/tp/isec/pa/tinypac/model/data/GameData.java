package tp.isec.pa.tinypac.model.data;


import javafx.scene.control.TextInputDialog;
import tp.isec.pa.tinypac.model.data.BlinkyClyde.Blinky;
import tp.isec.pa.tinypac.model.data.BlinkyClyde.Clyde;
import tp.isec.pa.tinypac.model.data.InkyPinky.Inky;
import tp.isec.pa.tinypac.model.data.InkyPinky.Pinky;


import java.io.*;
import java.util.*;


public class GameData implements Serializable {
    private final static long serialVersionUID=1L;


    private static final int boarderWidth=29;
    private static final int boarderHeight=31;
    private static final int MaxLevel=20;
    private static final int initialTimer=10000;

    private String name;
    private boolean load;
    private GameData save;
    private Maze board;
    private int points;
    boolean venceu;
    private List<IGhost> ghosts;
    private Pacman pacman;
    private int direction=9999;
    private int coinsGot;
    private int[] fSpot;
    private int[][]warps;
    private int[]spawn;
    private int[]entrance;
    private int lastLevelMap;
    private int level;
    private int lifes;
    private int tic;
    private int velocidade;// [0,99] a zero anda o mesmo que o pacman ; a 50 andam o dobro
    private int timer;
    private boolean hauntFlag;
    private long inicioJogo;



    public GameData()  {

        lifes=3;
        level=0;
        coinsGot=0;
        points=0;
        tic=0;
        velocidade=1;
        //loadTop5();

        //direction= KeyType.ArrowRight;
    }


    public int getTimer() {return timer;}
    public void normalMode() {
        hauntFlag=false;
    }


    public void setLoad(boolean load) {
        this.load = load;

    }

    /**
     * inicializa jogo, caso haja um save e o usuario queira pode começar um jogo antigo
     */
    public void initGame() {
        File arquivo=new File("files/save.dat");

        if (load){
            load();
            load=false;
            arquivo.delete();
            //loadTop5();
            return;

        }
        //loadTop5();
        arquivo.delete();
        timer=initialTimer-(((level-1)*100)/2);
        if (level%4==0)
            velocidade=velocidade+(level/7)*8;

        hauntFlag=false;
        //quando morrer falta reiniciar o nivel
        board=new Maze(boarderHeight,boarderWidth);
        loadMazeFromFile(countMaps());
        spawn=getInitialSpawn();
        entrance=getInitialEntrance();

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

    public int[] getSpawn(){
        return spawn;
    }
    public int[] getInitialSpawn() {
        char[][] maze = board.getMaze();
        int[] coord=null;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'M') coord = new int[]{i, j};
            }

        }
        return coord;
    }


    /**
     * Cria popup para que usuario possa colocar um novo record
     * Se nao quiser guardar record pode clicar no botao cancel, fechar janela ou nao preencher com o nome
     */
    private void openInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Novo Record!!");
        dialog.setHeaderText(null);
        dialog.setContentText("Nome:");
        //Button cancel=(Button)dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        //cancel.setText("Na");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> this.name = name);

        //System.out.println("Nome digitado: " + name);
    }

    /**
     * Se ainda nao houver um ficheiro de top 5 cria um e adiciona este recorde
     */
    public void createAndWriteTop5ClassToFile()  {
        if (name==null)return;
        Top5 top5 = new Top5();
        top5.addPlayer(name,points);
        // Defina os valores desejados para a classe Top5

        try (FileOutputStream fileOutputStream = new FileOutputStream("files/top5.dat");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(top5);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @return boolean que informa se se fez um novo record
     */
    public boolean isNewRecord(){
        File arquivo=new File("files/top5.dat");
        if (!arquivo.exists()) return true;
        Top5 top=Top5.loadTop5();
        return top.isNewRecord(points);
    }

    /**
     * Salva o top5 atual se o usuario quiser
     */
    public void saveTop(){

        File arquivo= new File("files/top5.dat");

        openInputDialog();
        if (name==null)return;

        if (!(arquivo.exists())){
            createAndWriteTop5ClassToFile();
            return;
        }

        Top5 a=Top5.loadTop5();
        a.addPlayer(name,points);
        try (FileOutputStream fileOut = new FileOutputStream("files/top5.dat");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(a);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int[] getEntrance(){
        return entrance;
    }
    public int[] getInitialEntrance() {
        char[][] maze = board.getMaze();
        int[] coord=null;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'Y') coord = new int[]{i, j};
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

    /**
     * le ficheiro para obter um mapa de jogo
     *
     * @param filename nome do ficheiro lido
     */
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

    /**
     * @return boolean que se for o final do nivel é true
     */
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


    /**
     * caso nao exista o primeiro nivel programa fecha
     *
     * @return nome do ficheiro com o mapa do proximo nivel
     */
    private String countMaps(){
        File verifier=new File("maps/level01.txt");
        String path;

        if (!verifier.exists()){
            System.out.println("Falta o primeiro labirinto");
            System.exit(1);
        }


        for (int i=level+1;i<MaxLevel+1;i++){//talvez +1

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
        return board.getMaze();
    }

    public char getCoord(int x,int y){
        return board.get(y, x) != null ? board.get(y, x).getSymbol() : ' ';
    }
    public void setCoord(int x,int y,char c){
        MazeElement a=new MazeElement();
        a.setElemento(c);
        board.set(y,x,a);
    }
    public int[][] getWarps(){
        return warps;
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

    /**
     * metodo responsavel pela fruta cujo caracter unico é o F
     */
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
        if (coinsGot %25==0) setCoord(fSpot[1], fSpot[0],'F');


    }

    public boolean isHauntFlag() {
        return hauntFlag;
    }

    /**
     * Coloca flag responsavel pelo HauntState a true
     */
    public void powerUP(){
        if (getCoord(pacman.getX(),pacman.getY())=='O'){
            addPoints(10);
            setCoord(pacman.getX(), pacman.getY(),' ');
            hauntFlag=true;
        }
    }
    public Maze getTrueBoard(){return board;}

    /**
     * @param ghost
     * @return ghost atras do ghost recebido nos parametros
     */
    public IGhost getGhostBehind(IGhost ghost){
        for (IGhost it:ghosts){
            switch (ghost.getDirection()){
                case DOWN ->{
                    if (it.getX()==ghost.getX()&&it.getY()==ghost.getY()-1) return it;
                }
                case RIGHT -> {
                    if (it.getX()==ghost.getX()-1&&it.getY()==ghost.getY()) return it;
                }
                case UP -> {
                    if (it.getX()==ghost.getX()&&it.getY()==ghost.getY()+1) return it;
                }
                case LEFT -> {
                    if (it.getX()==ghost.getX()+1&&it.getY()==ghost.getY()) return it;
                }
                default -> {return null;}
            }
        }
        return null;
    }

    /**
     * metodo que fazo jogo avançar
     *
     * @param timePassed tempo passado desde o começo do jogo
     */
    public void action(long timePassed) {
        MazeElement c=new MazeElement();
        c.setElemento('Y');
        if (!(board.get(getEntrance()[0],getEntrance()[1]).getSymbol()=='B'
                ||board.get(getEntrance()[0],getEntrance()[1]).getSymbol()=='C'
                ||board.get(getEntrance()[0],getEntrance()[1]).getSymbol()=='I'
                ||board.get(getEntrance()[0],getEntrance()[1]).getSymbol()=='P'))
            board.set(getEntrance()[0],getEntrance()[1],c);
        if (direction==9999) {
            //System.out.println("vewcf");
            inicioJogo= timePassed;
            return;
        }
        if (timePassed-inicioJogo>5) {

            for (IGhost it : ghosts) {
                if (it instanceof Blinky) {

                    if (tic % ((100 - velocidade)/10) == 0) {
                        if (it.getOverItem() != '-') {
                            MazeElement a = new MazeElement();
                            a.setElemento(it.getOverItem());
                            //System.out.println(it.getOverItem());
                            if (!(it.nextCell(this) == 'B' || it.nextCell(this) == 'C' || it.nextCell(this) == 'I' || it.nextCell(this) == 'P'))
                                board.set(it.getY(), it.getX(), a);
                        }
                        it.move(this);
                        //System.out.println(it.nextCell(this));

                        if (getGhostBehind(it) != null) {
                            getGhostBehind(it).setOverItemBehind(it.getOverItem(), this);
                        }
                        it.setOverItem(board.get(it.getY(), it.getX()).getSymbol());

                    }
                } else if (it instanceof Pinky) {
                    if (tic % ((100 - velocidade)/10) == 0) {

                        it.move(this);

                    }
                }

                if (it.getX() == pacman.getX() && it.getY() == pacman.getY()) {
                    pacmanDie();
                    inicioJogo = timePassed;
                }
            }
        }
        if (tic%10==0){
            pacman.move(direction,this);
            if (timePassed-inicioJogo>5)
                for (IGhost it:ghosts){

                    if (it.getX()== pacman.getX()&&it.getY()== pacman.getY()){
                        pacmanDie();
                        inicioJogo=timePassed;
                    }
                }
            frutaF();
            warp();
            powerUP();
            //colocar tudo no maze:


        }
        tic++;
    }
    public void putsToMaze(){
        for (IGhost it:ghosts){
            board.set(it.getY(),it.getX(),it);
        }
        board.set(pacman.getY(),pacman.getX(),pacman);


    }
    public boolean isBlinkyVulnerable(){
        for (IGhost it:ghosts)
            if (it.getSymbol()=='B')
                return it.isVulnerable();
        return false;
    }
    public boolean isPinkyVulnerable(){
        for (IGhost it:ghosts)
            if (it.getSymbol()=='P')
                return it.isVulnerable();
        return false;
    }
    public boolean isInkyVulnerable(){
        for (IGhost it:ghosts)
            if (it.getSymbol()=='I')
                return it.isVulnerable();
        return false;
    }
    public boolean isClydeVulnerable(){
        for (IGhost it:ghosts)
            if (it.getSymbol()=='C')
                return it.isVulnerable();
        return false;
    }

    /**
     * inverte historico de movimentos
     */
    public void invertMoves(){
        for (IGhost it:ghosts){
            it.invertMoves();
            it.setVulnerable();
        }
    }

    /**
     * permite que o jogo avance no HauntState
     *
     * @param timePassed tempo passado desde que o jogo começou
     */
    public void hauntMode(long timePassed){
        hauntFlag=true;
        int countGhosts=0;
        IGhost a=null;
        for (IGhost it:ghosts){
            if (tic%((100-velocidade)/10)==0){
                if (it.getOverItem()=='P'||it.getOverItem()=='I'||it.getOverItem()=='B'||it.getOverItem()=='C') it.setOverItem(' ');

                if (it.getOverItem()!='-') {
                    MazeElement b = new MazeElement();
                    b.setElemento(it.getOverItem());
                    //System.out.println(it.getOverItem());
                    if (!(it.nextCell(this)=='B'||it.nextCell(this)=='C'||it.nextCell(this)=='I'||it.nextCell(this)=='P'))
                        board.set(it.getY(), it.getX(), b);
                }
                it.reverseMove(this);
                if (getGhostBehind(it)!=null) {
                    getGhostBehind(it).setOverItemBehind(it.getOverItem(),this);

                }
                    it.setOverItem(board.get(it.getY(),it.getX()).getSymbol());
            }
            ++countGhosts;
            if (it.getX()== pacman.getX()&&it.getY()== pacman.getY()){
                if (it.isVulnerable()){
                    a=it;
                    addPoints((5-countGhosts)*50);
                }else {
                    pacmanDie();
                }
            }

            if (it.getOverItem()=='P'||it.getOverItem()=='I'||it.getOverItem()=='B'||it.getOverItem()=='C') it.setOverItem(' ');
        }
        if (tic%10==0){
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

    /**
     * Permite a alteração da direção do pacman
     *
     * @param key seta pressionada
     */
    public void setKey(int key) {

        if (key==1 || key==2 || key==3 || key==4)
            direction=key;
    }

    public void addEatenFruit() {
        ++coinsGot;
    }

    /**
     * Metodo responsavel pela morte do pacman
     */
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


    /**
     * Salva GameData atual num ficheiro binario(files/save.dat)
     */
    public void save() {
        try (FileOutputStream fileOut = new FileOutputStream("files/save.dat");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);  // jogo é o objeto que representa o estado do jogo
            //System.out.println("Jogo gravado em arquivo.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Le ficheiro binario files/save.dat, converte para um objeto GameData e restora save
     */
    public void load(){

        try (FileInputStream fileIn = new FileInputStream("files/save.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            save=(GameData) in.readObject();
            restore(save);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restora save
     *
     * @param m save
     */
    public void restore(GameData m) {



            board = m.board;
            points=m.points;
            lifes=m.lifes;
            ghosts=m.ghosts;
            pacman=m.pacman;
            direction=m.direction;
            coinsGot=m.coinsGot;
            fSpot=m.fSpot;
            warps= m.warps;
            spawn=m.spawn;
            entrance=m.entrance;
            lastLevelMap=m.lastLevelMap;
            level= m.level;
            tic=m.tic;
             velocidade=m.velocidade;
            timer=m.timer;
            hauntFlag=m.hauntFlag;
            inicioJogo=m.inicioJogo;



    }
}

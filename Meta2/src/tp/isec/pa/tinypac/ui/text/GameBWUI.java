package tp.isec.pa.tinypac.ui.text;


public class GameBWUI{/*
    GameBWContext fsm;
    private final TerminalScreen screen;

    public GameBWUI(GameBWContext fsm) throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(29, 33));
        Terminal terminal = terminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        this.fsm = fsm;

    }


    private void begin(boolean flag) {
        fsm.setload(flag);
        fsm.start();
    }

    public void menu(){
        startMenu();
    }

    private void hauntMode() {

            showResults();
            fsm.setkeyInput(keyInput());

    }

    private void waitEvent() {

            showResults();
            fsm.setkeyInput(keyInput());

        //screen.readInput();
    }
    private void pause() {

            pauseMenu();
            fsm.setkeyInput(keyInput());

    }

    private void pauseMenu() {
        try {
            Terminal terminal = screen.getTerminal();
            TextGraphics tg = terminal.newTextGraphics();

            tg.setForegroundColor(TextColor.ANSI.BLACK);
            tg.setBackgroundColor(TextColor.ANSI.RED);
            tg.putString(5, 15, "Fazer save prima: TAB");
            tg.putString(5, 16, " Sair pressione: ESC ");
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void start(boolean flag) {
        if (fsm.getState() == null) System.exit(-1);
        //while (keyInput()==null){
        //    ;
        //}
        while (true){
            switch (fsm.getState()) {
                case BEGIN -> begin(flag);
                //case START_MENU -> startMenu();
                case HAUNT_MODE -> hauntMode();
                case WAIT_EVENT -> waitEvent();
                case GAMEOVER -> gameOver();
                case PAUSE -> pause();
            }
        }


    }

    public void startMenu() {
        KeyType tecla;
        boolean flag=false;
        File arquivo=new File("files/save.dat");
        if (arquivo.exists()) {
            do {
                showMenuSave();
                tecla=keyInput();
                if (tecla==KeyType.Enter)  break;

                if(tecla == KeyType.Tab){
                    flag=true;
                    break;
                }
                if (tecla==KeyType.Home) {System.out.println(fsm.getTop());}


            } while (keyInput() != KeyType.Enter);
        }else {
            do {
                showMenu();
                tecla=keyInput();
                if (tecla==KeyType.Home) {System.out.println(fsm.getTop());}
                if (tecla==KeyType.Enter)  break;

            }while (keyInput()!=KeyType.Enter);
        }

        start(flag);

        //if (flag) fsm.load();
    }


    private void showMenu() {

        try {
            Terminal terminal = screen.getTerminal();

            TextGraphics tg = terminal.newTextGraphics();

            tg.setForegroundColor(TextColor.ANSI.BLACK);
            tg.setBackgroundColor(TextColor.ANSI.WHITE);
            tg.putString(4, 14, "                    ");
            tg.putString(4, 15, " ENTER para começar ");
            tg.putString(4, 16, " HOME para ver top5 ");
            tg.putString(4, 17, "                    ");

            screen.refresh();
            //System.out.println("wfefw");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMenuSave() {

        try {
            Terminal terminal = screen.getTerminal();

            TextGraphics tg = terminal.newTextGraphics();

            tg.setForegroundColor(TextColor.ANSI.BLACK);
            tg.setBackgroundColor(TextColor.ANSI.WHITE);
            tg.putString(4, 14, "                    ");
            tg.putString(4, 15, " ENTER para começar ");
            tg.putString(4, 16, "  TAB iniciar save  ");
            tg.putString(4, 17, " HOME para ver top5 ");
            tg.putString(4, 18, "                    ");

            screen.refresh();
            //System.out.println("wfefw");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void gameOver() {

        try {

            Terminal terminal = screen.getTerminal();
            TextGraphics tg = terminal.newTextGraphics();
            for (int i = 0; i<50; i++) {
                for (int j = 0; j < 50; j++){
                    tg.setBackgroundColor(TextColor.ANSI.BLACK);
                    tg.setForegroundColor(TextColor.ANSI.BLACK);
                    tg.putString(i,j, " ");
                }
            }

            tg.setForegroundColor(TextColor.ANSI.BLACK);
            tg.setBackgroundColor(TextColor.ANSI.RED);
            if (fsm.getVenceu()){
                tg.setForegroundColor(TextColor.ANSI.BLACK);
                tg.setBackgroundColor(TextColor.ANSI.GREEN);
                tg.putString(9, 15, "   Venceu!!   ");
            }
            else
                tg.putString(9, 15, "   GAMEOVER   ");
            tg.putString(9, 16, " Press enter! ");

            tg.setBackgroundColor(TextColor.ANSI.BLACK);
            tg.setForegroundColor(TextColor.ANSI.RED);
            tg.putString(8, 0, "DEIS-ISEC-IPC");

            screen.refresh();
            fsm.setkeyInput(keyInput());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void showResults() {

        try {
            showBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void showBoard() throws IOException {
        char[][] board = fsm.getBoard();
        Terminal terminal = screen.getTerminal();
        TextGraphics tg = terminal.newTextGraphics();

        String pontosStr = "Pontos: " + fsm.getPoints()+"      Vidas: "+fsm.getLifes();
        tg.putString(0, board.length, pontosStr);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                if (board[i][j] == 'x') {
                    tg.setForegroundColor(TextColor.ANSI.BLUE);
                    tg.setBackgroundColor(TextColor.ANSI.BLUE);

                } else if (board[i][j] == 'c') {
                    tg.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);

                } else if (board[i][j] == 'o') {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW);
                }
                else if (board[i][j] == 'P') {
                    if (fsm.isPinkyVulnerable()){
                        tg.setBackgroundColor(TextColor.ANSI.WHITE);
                        tg.setForegroundColor(TextColor.ANSI.WHITE);
                    }else {
                        tg.setBackgroundColor(TextColor.ANSI.RED);
                        tg.setForegroundColor(TextColor.ANSI.RED);
                    }

                }else if (board[i][j] == 'C') {

                    if (fsm.isClydeVulnerable()){
                        tg.setBackgroundColor(TextColor.ANSI.WHITE);
                        tg.setForegroundColor(TextColor.ANSI.WHITE);
                    }else {
                        tg.setBackgroundColor(TextColor.ANSI.CYAN);
                        tg.setForegroundColor(TextColor.ANSI.CYAN);
                    }
                }else if (board[i][j] == 'I') {

                    if (fsm.isInkyVulnerable()){
                        tg.setBackgroundColor(TextColor.ANSI.WHITE);
                        tg.setForegroundColor(TextColor.ANSI.WHITE);
                    }else {
                        tg.setBackgroundColor(TextColor.ANSI.MAGENTA);
                        tg.setForegroundColor(TextColor.ANSI.MAGENTA);
                    }
                }else if (board[i][j] == 'B') {

                    if (fsm.isBlinkyVulnerable()){
                        tg.setBackgroundColor(TextColor.ANSI.WHITE);
                        tg.setForegroundColor(TextColor.ANSI.WHITE);
                    }else {
                        tg.setBackgroundColor(TextColor.ANSI.GREEN);
                        tg.setForegroundColor(TextColor.ANSI.GREEN);
                    }
                }else if (board[i][j] == 'W') {
                    tg.setBackgroundColor(TextColor.ANSI.BLACK_BRIGHT);
                    tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
                }else if (board[i][j] == 'O') {
                    tg.setForegroundColor(TextColor.ANSI.RED);
                }else if (board[i][j] == 'F') {
                    tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                }
                tg.putString(j, i, String.valueOf(board[i][j]));
                tg.setBackgroundColor(TextColor.ANSI.BLACK);

            }
        }
        tg.setBackgroundColor(TextColor.ANSI.BLUE);
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(8, board.length+1, "DEIS-ISEC-IPC");

        screen.refresh();
    }
    private KeyCode keyInput()  {

        KeyStroke keyStroke = null;
        try {
            keyStroke = screen.pollInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (keyStroke==null)
            return null;



        return keyStroke.getKeyType();
    }*/
}

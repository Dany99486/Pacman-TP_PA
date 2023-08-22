package tp.isec.pa.tinypac;


import javafx.application.Application;
import tp.isec.pa.tinypac.gameengine.GameEngine;
import tp.isec.pa.tinypac.gameengine.IGameEngine;
import tp.isec.pa.tinypac.model.GameManager;
import tp.isec.pa.tinypac.ui.gui.MainJFX;

import java.io.IOException;

public class TinyPAcMain {
    /*public static void main(String[] args) throws IOException {

        IGameEngine gameEngine = new GameEngine();
        GameBWContext game = new GameBWContext();
        GameManager manager = new GameManager(game,gameEngine);
        GameBWUI ui=new GameBWUI(game);
        gameEngine.start(2);
        ui.startMenu();

        gameEngine.waitForTheEnd();

    }*/

    public static IGameEngine gameEngine;
    //public static GameManager gameBWManager;
    static {
        gameEngine = new GameEngine();
        /*
        try {
            gameEngine = new GameEngine();
            gameBWManager = new GameManager(gameEngine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
    public static void main(String[] args) {
        //gameEngine.registerClient((g,t) -> gameManager.evolve(t));
        Application.launch(MainJFX.class,args);
        //gameEngine.start(2);
        gameEngine.waitForTheEnd();
    }

}


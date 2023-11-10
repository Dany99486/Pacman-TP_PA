package tp.isec.pa.tinypac;


import tp.isec.pa.tinypac.gameengine.GameEngine;
import tp.isec.pa.tinypac.gameengine.IGameEngine;
import tp.isec.pa.tinypac.model.fsm.GameBWContext;
import tp.isec.pa.tinypac.ui.text.GameBWUI;

import java.io.IOException;

public class TinyPAcMain {
    public static void main(String[] args) throws IOException {
        IGameEngine gameEngine = new GameEngine();
        GameBWContext game = new GameBWContext(gameEngine);
        GameBWUI ui=new GameBWUI(game);
        gameEngine.start(2);
        ui.start();

        gameEngine.waitForTheEnd();
    }
}


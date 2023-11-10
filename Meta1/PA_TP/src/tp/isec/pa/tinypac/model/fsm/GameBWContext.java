package tp.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.input.KeyType;

import tp.isec.pa.tinypac.gameengine.IGameEngine;
import tp.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import tp.isec.pa.tinypac.model.data.GameBWData;


import java.io.IOException;


public class GameBWContext implements IGameEngineEvolve{
    private IGameBWState state;
    private GameBWData data;


    public GameBWContext(IGameEngine gameEngine) throws IOException {
        data = new GameBWData();
        state = new MenuState(this,data);
        gameEngine.registerClient(this);


    }
    void changeState(IGameBWState newState) {
        state = newState;
    }

    public void start() {
        state.start();
    }


    public boolean isBlinkyVulnerable(){return data.isBlinkyVulnerable();}
    public boolean isPinkyVulnerable(){return data.isPinkyVulnerable();}
    public boolean isInkyVulnerable(){return data.isInkyVulnerable();}
    public boolean isClydeVulnerable(){return data.isClydeVulnerable();}

    public GameBWState getState() {
        if (state == null)
            return null;
        return state.getState();
    }

    public boolean getVenceu(){return data.getVenceu();}
    public int getPoints() {return data.getPoints();}
    public char[][] getBoard(){return data.getBoard();}

    private void action(){state.action();}
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        action();
    }
    public void setkeyInput(KeyType key){
        state.setKey(key);

    }

    public int getLifes() {
        return data.getLifes();
    }

}

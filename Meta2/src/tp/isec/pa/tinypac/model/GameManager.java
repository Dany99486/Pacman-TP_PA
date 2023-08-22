package tp.isec.pa.tinypac.model;


import tp.isec.pa.tinypac.gameengine.IGameEngine;
import tp.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import tp.isec.pa.tinypac.model.fsm.GameContext;
import tp.isec.pa.tinypac.model.fsm.GameState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.TimeUnit;

/**
 * Classe responsavel por implementar uma game engine e permitir a alteração da gui
 */
public class GameManager implements IGameEngineEvolve {
    private GameContext gameContext;
    IGameEngine gameEngine;
    private final long initialTime;
    PropertyChangeSupport pcs;


    public GameManager(IGameEngine gameEngine,boolean save)  {

            gameContext = new GameContext();

        this.gameEngine=gameEngine;
        gameEngine.registerClient(this);//tirar
        gameEngine.start(20);
        pcs = new PropertyChangeSupport(this);

        initialTime = System.nanoTime();
        start(save);

    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        //if (gameContext.getState()==GameBWState.BEGIN)gameContext.start();

        gameContext.action(TimeUnit.SECONDS.convert(currentTime-initialTime,TimeUnit.NANOSECONDS));
        pcs.firePropertyChange(null,null,null);
        //System.out.println("nfe");
        //System.out.println(gameContext.getState());
    }

    //faltam os metodos restantes



    public void start(boolean save) {
        //gameEngine.start(20);
        gameContext.start(save);
        //gameContext.init();
        pcs.firePropertyChange(null,null,null);
    }
    public void setkeyInput(int key){
        gameContext.setkeyInput(key);
        pcs.firePropertyChange(null,null,null);

    }



    public boolean isBlinkyVulnerable(){return gameContext.isBlinkyVulnerable();}
    public boolean isPinkyVulnerable(){return gameContext.isPinkyVulnerable();}
    public boolean isInkyVulnerable(){return gameContext.isInkyVulnerable();}
    public boolean isClydeVulnerable(){return gameContext.isClydeVulnerable();}

    public boolean getVenceu(){return gameContext.getVenceu();}
    public int getPoints() {return gameContext.getPoints();}
    public char[][] getBoard(){
        //pcs.firePropertyChange(null,null,null);
        return gameContext.getBoard();
    }

    public int getLifes() {
        return gameContext.getLifes();
    }

    public GameState getState() {
        return gameContext.getState();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {pcs.addPropertyChangeListener(listener);}

}

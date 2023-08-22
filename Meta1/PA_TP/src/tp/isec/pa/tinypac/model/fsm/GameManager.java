package tp.isec.pa.tinypac.model.fsm;

import tp.isec.pa.tinypac.gameengine.IGameEngine;
import tp.isec.pa.tinypac.gameengine.IGameEngineEvolve;

import java.util.concurrent.TimeUnit;

public class GameManager implements IGameEngineEvolve {
    private final GameBWContext gameContext;
    private final long initialTime;

    public GameManager(GameBWContext gameContext,IGameEngine gameEngine) {
        this.gameContext = gameContext;
        gameEngine.registerClient(this);//tirar

        initialTime = System.nanoTime();


    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        gameContext.action(TimeUnit.SECONDS.convert(currentTime-initialTime,TimeUnit.NANOSECONDS));
        //System.out.println(TimeUnit.SECONDS.convert(currentTime-initialTime,TimeUnit.NANOSECONDS) );
    }
}

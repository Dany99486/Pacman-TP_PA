package tp.isec.pa.tinypac.model.fsm;


import tp.isec.pa.tinypac.model.data.GameData;

import java.util.Timer;
import java.util.TimerTask;

public class HauntState extends GameStateAdapter {
    /**
     * Passado x tempo(data.getTimer()) volta para o estado WaitEvent
     */
    public HauntState(GameContext context, GameData data) {
        super(context,data);
        Timer timer = new Timer();
        TimerTask tarefa = new TimerTask() {

            public void run() {
                changeState(GameState.WAIT_EVENT);
            }
        };
        timer.schedule(tarefa, data.getTimer());
    }

    /**
     * Vidas a 0 ou venceu muda estado para gameover
     * Avança de nível e inicializa o GameData para o nível seguinte
     *
     * @param timePassed Tempo passado desde o inicio do jogo
     */
    @Override
    public void action(long timePassed){
        data.putsToMaze();
        data.hauntMode(timePassed);
        if (data.getLifes()<=0)
            changeState(GameState.GAMEOVER);
        else if (data.endOFLevel()&&data.getVenceu())
            changeState(GameState.GAMEOVER);
        else if (data.endOFLevel())
            data.initGame();
            //changeState(GameBWState.BEGIN);
    }

    /**
     * Home - Coloca em pausa
     *
     * @param key Inteiro que representa uma tecla premida na UI
     */
    @Override
    public void setKey(int key){
        if (key==5){
            changeState(GameState.PAUSE);
        }
        data.setKey(key);
    }

    @Override
    public GameState getState() {
        return GameState.HAUNT_MODE;
    }


}

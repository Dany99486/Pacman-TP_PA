package tp.isec.pa.tinypac.model.fsm;


import com.googlecode.lanterna.input.KeyType;
import tp.isec.pa.tinypac.model.data.GameBWData;

import java.util.Timer;
import java.util.TimerTask;

public class HauntState extends GameBWStateAdapter {
    public HauntState(GameBWContext context, GameBWData data) {
        super(context,data);
        Timer timer = new Timer();
        TimerTask tarefa = new TimerTask() {

            public void run() {
                changeState(GameBWState.WAIT_EVENT);
            }
        };
        timer.schedule(tarefa, data.getTimer());
    }
    @Override
    public void action(){

        data.hauntMode();
        if (data.getLifes()<=0)
            changeState(GameBWState.GAMEOVER);
        else if (data.endOFLevel()&&data.getVenceu())
            changeState(GameBWState.GAMEOVER);
        else if (data.endOFLevel())
            changeState(GameBWState.BEGIN);
    }
    @Override
    public void setKey(KeyType key){
        if (key==KeyType.Home){
            changeState(GameBWState.PAUSE);
        }
        data.setKey(key);
    }

    @Override
    public GameBWState getState() {
        return GameBWState.HAUNT_MODE;
    }


}

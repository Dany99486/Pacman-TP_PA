package tp.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.input.KeyType;
import tp.isec.pa.tinypac.model.data.GameBWData;

public class WaitState extends GameBWStateAdapter {
    public WaitState(GameBWContext context, GameBWData data) {
        super(context, data);
        data.normalMode();
        data.setUnvunerable();
    }

    @Override
    public GameBWState getState() {
        return GameBWState.WAIT_EVENT;
    }
    @Override
    public void action(){
        data.action();
        if (data.getLifes()<=0)
            changeState(GameBWState.GAMEOVER);
        else if (data.endOFLevel()&&data.getVenceu())
            changeState(GameBWState.GAMEOVER);
        else if (data.endOFLevel())
            changeState(GameBWState.BEGIN);
        else if (data.isHauntFlag()) {
            data.invertMoves();
            changeState(GameBWState.HAUNT_MODE);
        }

    }
    @Override
    public void setKey(KeyType key){
        if (key==KeyType.Home){
            changeState(GameBWState.PAUSE);
        }
        data.setKey(key);
    }
}

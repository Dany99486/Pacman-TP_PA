package tp.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.input.KeyType;
import tp.isec.pa.tinypac.model.data.GameBWData;

public class PauseState extends GameBWStateAdapter {
    public PauseState(GameBWContext context, GameBWData data) {
        super(context, data);

    }

    @Override
    public void setKey(KeyType key){
        if (key==KeyType.Home){

            if (data.isHauntFlag())
                changeState(GameBWState.HAUNT_MODE);
            else
                changeState(GameBWState.WAIT_EVENT);
        } else if (key==KeyType.Escape){

            System.exit(0);
        }
    }

    @Override
    public GameBWState getState() {
        return GameBWState.PAUSE;
    }
}

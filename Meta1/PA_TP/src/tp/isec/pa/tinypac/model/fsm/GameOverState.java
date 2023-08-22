package tp.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.input.KeyType;
import tp.isec.pa.tinypac.model.data.GameBWData;



public class GameOverState extends GameBWStateAdapter {
    public GameOverState(GameBWContext context, GameBWData data) {
        super(context,data);


    }


    @Override
    public void setKey(KeyType key){

        if (key==KeyType.Enter){

            System.exit(0);
        }
    }

    @Override
    public GameBWState getState() {
        return GameBWState.GAMEOVER;
    }
}



package tp.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.input.KeyType;
import tp.isec.pa.tinypac.model.data.GameBWData;

abstract public class GameBWStateAdapter implements IGameBWState {//gameengine tem de ser aqui?
    GameBWContext context;
    GameBWData data;
    public GameBWStateAdapter(GameBWContext context, GameBWData data) {
    this.data=data;
    this.context=context;
    }
    protected void changeState(GameBWState newState) {
        context.changeState(newState.createState(context,data));
    }


    @Override
    public void start(){}



    @Override
    public void action(long timePassed){}

    @Override
    public void setKey(KeyType key) {}


}

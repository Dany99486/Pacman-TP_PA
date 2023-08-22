package tp.isec.pa.tinypac.model.fsm;


import tp.isec.pa.tinypac.model.data.GameBWData;



public class BeginState extends GameBWStateAdapter {
    public BeginState(GameBWContext context, GameBWData data) {
    super(context,data);
    }

    @Override
    public void start() {

        data.initGame();
        changeState(GameBWState.WAIT_EVENT);


    }


    @Override
    public GameBWState getState() {
        return GameBWState.BEGIN;
    }


}

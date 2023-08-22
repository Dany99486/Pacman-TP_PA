package tp.isec.pa.tinypac.model.fsm;


import tp.isec.pa.tinypac.model.data.GameData;

abstract public class GameStateAdapter implements IGameBWState {//gameengine tem de ser aqui?
    GameContext context;
    GameData data;
    public GameStateAdapter(GameContext context, GameData data) {
    this.data=data;
    this.context=context;
    }
    protected void changeState(GameState newState) {
        context.changeState(newState.createState(context,data));
    }


    @Override
    public void start(boolean save){}



    @Override
    public void action(long timePassed){}

    @Override
    public void setKey(int key) {}


}

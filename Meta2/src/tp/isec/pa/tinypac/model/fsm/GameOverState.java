package tp.isec.pa.tinypac.model.fsm;

import tp.isec.pa.tinypac.model.data.GameData;



public class GameOverState extends GameStateAdapter {
    public GameOverState(GameContext context, GameData data) {
        super(context,data);


    }


    @Override
    public void setKey(int key){

        if (key==1){
            if (data.isNewRecord()){

                data.saveTop();
            }
            System.exit(0);
        }
    }

    @Override
    public GameState getState() {
        return GameState.GAMEOVER;
    }
}



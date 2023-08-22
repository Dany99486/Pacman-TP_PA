package tp.isec.pa.tinypac.model.fsm;

import tp.isec.pa.tinypac.model.data.GameData;



public class PauseState extends GameStateAdapter {
    public PauseState(GameContext context, GameData data) {
        super(context, data);

    }

    /**
     * HOME - Retorna ao jogo
     * ESC - Sai do jogo e se for record grava na classe TOP5
     * TAB - Salva Jogo naquele momento(GameData)
     *
     * @param key Inteiro que representa uma tecla premida na UI
     */
    @Override
    public void setKey(int key){
        if (key==5){

            if (data.isHauntFlag())
                changeState(GameState.HAUNT_MODE);
            else
                changeState(GameState.WAIT_EVENT);
        } else if (key==3){

            if (data.isNewRecord()){

                data.saveTop();
            }
            System.exit(0);
        } else if (key==1) {
            data.save();
        }
    }

    @Override
    public GameState getState() {
        return GameState.PAUSE;
    }
}

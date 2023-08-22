package tp.isec.pa.tinypac.model.fsm;


import tp.isec.pa.tinypac.model.data.GameData;

import java.io.File;


public class BeginState extends GameStateAdapter {
    public BeginState(GameContext context, GameData data) {
        super(context,data);

    }

    /**
     * Inicializa a classe GameData
     *
     * @param save Determina se se deve iniciar com o save ou nao
     */
    @Override
    public void start(boolean save) {
        if (save){
            File arquivo=new File("files/save.dat");
            if (arquivo.exists()){
                data.setLoad(save);

                data.load();
            }
        }
        data.initGame();
        changeState(GameState.WAIT_EVENT);




    }


    @Override
    public GameState getState() {
        return GameState.BEGIN;
    }


}

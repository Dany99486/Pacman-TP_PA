package tp.isec.pa.tinypac.model.fsm;

import tp.isec.pa.tinypac.model.data.GameData;

public class WaitState extends GameStateAdapter {
    public WaitState(GameContext context, GameData data) {
        super(context, data);
        //System.out.println("waitstate");

        data.normalMode();
        data.setUnvunerable();

    }

    @Override
    public GameState getState() {
        return GameState.WAIT_EVENT;
    }

    /**
     * Vidas a 0 ou venceu muda estado para gameover
     * Avança de nível e inicializa o GameData para o nível seguinte
     *
     * @param timePassed Tempo passado desde o inicio do jogo
     */
    @Override
    public void action(long timePassed){
        data.putsToMaze();
        data.action(timePassed);
        if (data.getLifes()<=0)
            changeState(GameState.GAMEOVER);
        else if (data.endOFLevel()&&data.getVenceu())
            changeState(GameState.GAMEOVER);
        else if (data.endOFLevel()){
            //changeState(GameBWState.BEGIN);  desta forma deixou de dar
            data.initGame();

        }
        else if (data.isHauntFlag()) {
            data.invertMoves();
            changeState(GameState.HAUNT_MODE);
        }

    }

    /**
     * Home - Coloca em pausa
     *
     * @param key Inteiro que representa uma tecla premida na UI
     */
    @Override
    public void setKey(int key){
        System.out.println(key);

        if (key==5){
            changeState(GameState.PAUSE);
        }
        data.setKey(key);

    }
}

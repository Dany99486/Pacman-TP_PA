package tp.isec.pa.tinypac.model.fsm;


import tp.isec.pa.tinypac.model.data.GameBWData;

public enum GameBWState {
    BEGIN,WAIT_EVENT,HAUNT_MODE, GAMEOVER, PAUSE, /*START_MENU*/;

    public IGameBWState createState(GameBWContext context, GameBWData data){
        return switch (this){
            case BEGIN -> new BeginState(context,data) ;
            //case START_MENU -> new MenuState(context,data) ;
            case WAIT_EVENT -> new WaitState(context,data);
            case HAUNT_MODE -> new HauntState(context,data);
            case GAMEOVER -> new GameOverState(context,data);
            case PAUSE -> new PauseState(context,data);
        };
    }
}

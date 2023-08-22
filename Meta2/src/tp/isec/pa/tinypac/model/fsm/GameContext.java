package tp.isec.pa.tinypac.model.fsm;


import tp.isec.pa.tinypac.model.data.GameData;


public class GameContext {
    private IGameBWState state;
    private GameData data;


    /**
     * Classe acedida pelo GameManager que altera os dados e os estados
     */
    public GameContext(){
        data = new GameData();
        //state = null;
        state=new BeginState(this,data);
        //state.start();
    }
    void changeState(IGameBWState newState) {
        state = newState;
    }

    public void start(boolean save) {

        //state=new BeginState(this,data);
        state.start(save);//nao alterar

    }



    public void action(long timePassed){

        //if (state==null) return;
        //if (state.getState()==GameBWState.BEGIN) state.start();
        state.action(timePassed);
    }

    public void setkeyInput(int key){

        state.setKey(key);

    }

    public boolean isBlinkyVulnerable(){return data.isBlinkyVulnerable();}
    public boolean isPinkyVulnerable(){return data.isPinkyVulnerable();}
    public boolean isInkyVulnerable(){return data.isInkyVulnerable();}
    public boolean isClydeVulnerable(){return data.isClydeVulnerable();}
    public GameState getState() {
        if (state == null)
            return null;
        return state.getState();
    }
    public boolean getVenceu(){return data.getVenceu();}
    public int getPoints() {return data.getPoints();}
    public char[][] getBoard(){return data.getBoard();}

    public int getLifes() {
        return data.getLifes();
    }



}

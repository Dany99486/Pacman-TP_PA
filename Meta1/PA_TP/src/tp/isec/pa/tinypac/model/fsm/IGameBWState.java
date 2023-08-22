package tp.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.input.KeyType;//tirar

public interface IGameBWState {
    void start();
    GameBWState getState();
    void action(long timePassed);
    void setKey(KeyType key);
}

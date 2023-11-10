package tp.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.input.KeyType;

public interface IGameBWState {
    void start();
    GameBWState getState();
    void action();
    void setKey(KeyType key);
}

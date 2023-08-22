package tp.isec.pa.tinypac.model.data;

import tp.isec.pa.tinypac.model.fsm.Direction;

public interface IGhost extends IMazeElement {
    boolean isVulnerable();


    int getX();
    void setOverItemBehind(char overItem, GameData data);
    void setOverItem(char overItem);
     char nextCell(GameData game);
     char backCell(GameData game);
     Direction getDirection();
    char getOverItem();
    int getY();

    char getSymbol();
    void move(GameData game);

    void invertMoves();

    void reverseMove(GameData gameData);
    void setVulnerable();

    void setUnvulnerable();
}

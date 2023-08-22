package tp.isec.pa.tinypac.model.data;

import tp.isec.pa.tinypac.model.fsm.Direction;

public interface IGhost extends IMazeElement {
    boolean isVulnerable();


    int getX();
    void setOverItemBehind(char overItem,GameBWData data);
    void setOverItem(char overItem);
     char nextCell(GameBWData game);
     char backCell(GameBWData game);
     Direction getDirection();
    char getOverItem();
    int getY();

    char getSymbol();
    void move(GameBWData game);

    void invertMoves();

    void reverseMove(GameBWData gameBWData);
    void setVulnerable();

    void setUnvulnerable();
}

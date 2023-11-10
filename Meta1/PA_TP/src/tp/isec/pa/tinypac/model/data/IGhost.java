package tp.isec.pa.tinypac.model.data;

public interface IGhost {
    boolean isVulnerable();


    int getX();

    int getY();

    char getType();
    void move(GameBWData game);

    void invertMoves();

    void reverseMove(GameBWData gameBWData);
    void setVulnerable();

    void setUnvulnerable();
}

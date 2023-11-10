package tp.isec.pa.tinypac.model.data.InkyPinky;



public class Inky extends Pinky {
    static final private char type='I';

    public Inky(int x, int y, int boarderWidth, int boarderHeight) {
        super(x,y,boarderWidth,boarderHeight);
        super.forInky();
    }

    @Override
    public char getType() {
        return type;
    }

}

package tp.isec.pa.tinypac.model.data;

public class MazeElement implements IMazeElement{
    private char elemento;



    @Override
    public char getSymbol() {
        return elemento;
    }

    public void setElemento(char elemento) {
        this.elemento = elemento;
    }
}

package tp.isec.pa.tinypac.model.data;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int points;
    public String toString() {
        return "\nname=" + name +
                ", points=" + points;
    }

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}


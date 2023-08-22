package tp.isec.pa.tinypac.model.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Top5 implements Serializable {
    private List<Player> players;

    public Top5() {
        this.players = new ArrayList<Player>();
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(String name,int points){
        int minPoints=Integer.MAX_VALUE;
        Player lessPointPlayer=null;
        players.add(new Player(name,points));
        for (Player it:players)
            if (it.getPoints()<minPoints) {
                lessPointPlayer=it;
                minPoints=it.getPoints();
            }
        if (players.size()>5)
            players.remove(lessPointPlayer);
    }

    public boolean isNewRecord(int points){
        if (players.size()<5) return true;
        for (Player it:players)
            if (it.getPoints()<points) return true;
        return false;
    }


    /**
     * Le top5 de ficheiro binario e returna uma copia
     *
     * @return copia do top5
     */
    public static Top5 loadTop5() {

        File arquivo=new File("files/top5.dat");
        if (!arquivo.exists()) return null;
        Top5 top5 = null;
        try (FileInputStream fileIn = new FileInputStream("files/top5.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            top5 = (Top5) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return top5;
        //getTop();
    }

    @Override
    public String toString() {
        return "Top5:\n" +
                players ;
    }
}

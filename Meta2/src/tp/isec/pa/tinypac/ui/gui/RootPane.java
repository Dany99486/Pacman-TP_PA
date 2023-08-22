package tp.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import tp.isec.pa.tinypac.TinyPAcMain;
import tp.isec.pa.tinypac.model.GameManager;

import tp.isec.pa.tinypac.ui.gui.resources.CSSManager;
import tp.isec.pa.tinypac.ui.gui.uistates.*;

/**
 * Cria no inicio do jogo a ui de cada um dos estados e apenas coloca visivel quando a maquina se encontra no respetivo estado
 */
public class RootPane extends BorderPane {
    GameManager gameManager;
    StackPane center;
    PointsLifes pointsLifes;

/*
    File arquivo=new File("files/save.dat");
            if (arquivo.exists()){
        gameBWManager.setload(true);
        gameBWManager.load();
    }
            gameBWManager.start();*/
    public RootPane(boolean save) {
        //this.gameManager = TinyPAcMain.gameBWManager;
        this.gameManager=new GameManager(TinyPAcMain.gameEngine,save);

        //gameManager.start(save);
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        CSSManager.applyCSS(this,"styles.css");

        //ballsWon = new BallsWon(gameBWManager);
        //pointsLifes = new PointsLifes(gameManager);

        center = new StackPane(
                //new StartMenuUI(gameManager),
                new BeginUI(gameManager),
                new WaitUI(gameManager),
                new GameOverUI(gameManager),
                new HauntUI(gameManager),
                new PauseUI(gameManager)
        );
        center.setBackground(
                new Background(
                        new BackgroundFill(
                                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                                        new Stop(0, Color.rgb(23,67,100)),
                                        new Stop(1, Color.rgb(20,20,20))),
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );
        this.setCenter(center);
        //center.requestFocus();



        pointsLifes=new PointsLifes(gameManager);




        //System.out.println(gameManager.getState());
        /*this.setBottom(
                pointsLifes=new PointsLifes(gameManager)
        );*/

    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> Platform.runLater(() -> update()));

    }

    /**
     * Coloca o pane pointsLifes em baixo dependendo do estado
     */
    private void update() {
        //System.out.println("["+gameManager.getState()+"]");
        //System.out.println(gameManager.getState());
        //System.out.println("wcw");
        if (gameManager.getState()==null) return;
        switch (gameManager.getState()) {
            case WAIT_EVENT -> setBottom(pointsLifes);
            case HAUNT_MODE -> setBottom(pointsLifes);
            default -> {
                //System.out.println("sr");
                setRight(null);
                setBottom(null);
            }
        }


        /*
        if (gameManager.getState()==null) center=new StartMenuUI(gameManager);
        else
            center = switch (gameManager.getState()) {
                case BEGIN -> new BeginUI(gameManager);
                case WAIT_EVENT -> new WaitUI(gameManager);
                case HAUNT_MODE -> new HauntUI(gameManager);
                case GAMEOVER -> new GameOverUI(gameManager);
                case PAUSE -> new PauseUI(gameManager);
            };*/


        //center.requestFocus();
    }
}

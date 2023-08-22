package tp.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import tp.isec.pa.tinypac.model.GameManager;
import tp.isec.pa.tinypac.model.fsm.GameState;

public class BeginUI extends AnchorPane {
        GameManager gameManager;
    public BeginUI(GameManager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }
    private void createViews() {
        this.setBackground(
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


    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> Platform.runLater(() -> update()));
    }

    private void update() {

        if (gameManager.getState() != GameState.BEGIN) {

            this.setVisible(false);
            return;
        }

        this.setVisible(true);



    }
}




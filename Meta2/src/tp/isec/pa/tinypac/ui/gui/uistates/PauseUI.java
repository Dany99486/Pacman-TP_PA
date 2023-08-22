package tp.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tp.isec.pa.tinypac.model.GameManager;
import tp.isec.pa.tinypac.model.fsm.GameState;

public class PauseUI extends AnchorPane {
    GameManager gameManager;
    Label lbnStart, lbnExit, lbnSave;

    public PauseUI(GameManager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        lbnStart = new Label(" HOME - to continue ");
        lbnStart.setMinWidth(200);
        lbnStart.setId("pauseLabel");
        lbnExit = new Label(" ESC - to exit ");
        lbnExit.setMinWidth(200);
        lbnExit.setId("pauseLabel");
        lbnSave = new Label(" TAB - to save ");
        lbnSave.setMinWidth(200);
        lbnSave.setId("pauseLabel");

        VBox vBox = new VBox(lbnStart, lbnExit, lbnSave);
        AnchorPane.setTopAnchor(vBox,0.0);
        AnchorPane.setBottomAnchor(vBox,0.0);
        AnchorPane.setLeftAnchor(vBox,0.0);
        AnchorPane.setRightAnchor(vBox,0.0);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        this.getChildren().add(vBox);

    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> Platform.runLater(() -> update()));
        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            int a=99999999;
            switch (keyCode){
                case TAB -> a=1;
                case ESCAPE -> a=3;
                case HOME -> a=5;
            }
            gameManager.setkeyInput(a);
        });

    }

    private void update() {
        requestFocus();
        if (gameManager.getState() != GameState.PAUSE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}

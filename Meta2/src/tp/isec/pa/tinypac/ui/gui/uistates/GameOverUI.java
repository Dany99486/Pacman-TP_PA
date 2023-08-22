package tp.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tp.isec.pa.tinypac.model.GameManager;
import tp.isec.pa.tinypac.model.fsm.GameState;

public class GameOverUI extends AnchorPane {
    GameManager gameManager;
    Label lbnResult, lbnExit;
    public GameOverUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    /**
     * Dependendo se venceu ou perdeu diz que venceu ou se é o fim do jogo
     */
    private void createViews() {
        String result;
        if (gameManager.getVenceu()){
            result=" Venceu ";
        }else
            result=" GameOver ";

        lbnResult = new Label(result);
        lbnResult.setMinWidth(200);
        lbnResult.setStyle("-fx-text-fill: white;");
        lbnResult.setMaxWidth(250);
        lbnResult.setMaxHeight(75);

        lbnExit = new Label(" Press enter to exit! ");
        lbnExit.setMinWidth(200);
        lbnExit.setStyle("-fx-text-fill: white;");
        lbnExit.setMaxWidth(250);
        lbnExit.setMaxHeight(75);


        VBox vBox = new VBox(lbnResult, lbnExit);
        AnchorPane.setTopAnchor(vBox,0.0);
        AnchorPane.setBottomAnchor(vBox,0.0);
        AnchorPane.setLeftAnchor(vBox,0.0);
        AnchorPane.setRightAnchor(vBox,0.0);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(250);
        vBox.setSpacing(20);
        vBox.setBorder(new Border(new BorderStroke(Color.rgb(31, 31, 31), BorderStrokeStyle.SOLID, null,new BorderWidths(4))));
        vBox.setBackground(new Background( new BackgroundFill(Color.rgb(0, 0, 0), null, null)));
        this.getChildren().add(vBox);


    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> Platform.runLater(() -> update()));

        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            int a=99999999;
            if (keyCode == KeyCode.ENTER) {
                a = 1;
            }
            gameManager.setkeyInput(a);
        });
    }

    private void update() {
        requestFocus();
        if (gameManager.getState() != GameState.GAMEOVER) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

    }
}

package tp.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tp.isec.pa.tinypac.model.GameManager;

/**
 * Caixa que dependendo do estado fica visivel e mostra as vidas e os pontos atuais
 */
public class PointsLifes extends HBox{
    GameManager gameBWManager;
    HBox hbLifes,hbPoints;
    Label lbPoints,lbLifes;

    public PointsLifes(GameManager gameBWManager) {
        this.gameBWManager = gameBWManager;
        this.setId("info");
        createViews();
        registerHandlers();
        update();

    }

    private void createViews() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));

        //setSpacing(400);


        hbPoints = new HBox();
        hbPoints.setAlignment(Pos.CENTER_LEFT);
        hbPoints.setPrefWidth(Integer.MAX_VALUE);
        hbPoints.setPadding(new Insets(0,50,0,50));


        hbLifes = new HBox();
        hbLifes.setAlignment(Pos.CENTER_RIGHT);
        hbLifes.setPrefWidth(Integer.MAX_VALUE);
        hbLifes.setPadding(new Insets(0,50,0,50));
        int points = gameBWManager.getPoints();
        lbPoints=new Label("Points: "+points);
        hbPoints.getChildren().add(lbPoints);
        hbPoints.getChildren().get(0).setId("labelnone");

        lbLifes=new Label("Lifes: "+points);
        hbLifes.getChildren().clear();
        int lifes = gameBWManager.getLifes();

        hbLifes.getChildren().add(lbLifes);
        hbLifes.getChildren().get(0).setId("labelnone");


        this.getChildren().addAll(hbPoints,hbLifes);
    }

    private void registerHandlers() {
        gameBWManager.addPropertyChangeListener(evt -> Platform.runLater(() -> update()));
    }

    private void update() {
        //hbPoints.getChildren().clear();
        lbLifes.setText("Lifes: "+gameBWManager.getLifes());
        lbPoints.setText("Points: "+gameBWManager.getPoints());


    }
}

package tp.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import tp.isec.pa.tinypac.model.GameManager;
import tp.isec.pa.tinypac.model.fsm.GameState;
import tp.isec.pa.tinypac.ui.gui.resources.ImageManager;



public class WaitUI extends AnchorPane {
    GameManager gameManager;
    TilePane tilePane;
    FlowPane auxPane;
    public WaitUI(GameManager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }
    private void createViews() {
        //gameManager.init();
        //char[][] board=gameManager.getBoard();
        //int numLines= board[1].length;
        tilePane = new TilePane(Orientation.HORIZONTAL);
        //tilePane.setPrefColumns(numLines);
        tilePane.setPrefTileHeight(15);
        tilePane.setPrefTileWidth(15);
        tilePane.setId("board");
        auxPane = new FlowPane(tilePane);


        auxPane.setAlignment(Pos.CENTER);

        //TilePane.setMargin(tilePane, new Insets(30, 0, 0, 0));
        AnchorPane.setTopAnchor(auxPane,0.0);
        AnchorPane.setBottomAnchor(auxPane,0.0);
        AnchorPane.setLeftAnchor(auxPane,0.0);
        AnchorPane.setRightAnchor(auxPane,0.0);
        //auxPane.setBackground(Background.fill(Color.ORANGE));
        tilePane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 1.0), null, null)));

        this.getChildren().addAll(auxPane);
    }


    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> Platform.runLater(() -> update()));
        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            int a=99999999;
            switch (keyCode){
                case A -> a=1;
                case W -> a=2;
                case S -> a=3;
                case D -> a=4;
                case HOME -> a=5;
            }

            gameManager.setkeyInput(a);

        });

    }

    private void update() {

        requestFocus();
        //this.getChildren().remove(tilePane);
        //createViews();

        if (gameManager.getState() != GameState.WAIT_EVENT) {

            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        char[][] board=gameManager.getBoard();

        TilePane tilePane1=new TilePane();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Rectangle rect = new Rectangle(15,15);
                if (board[i][j]=='x') {

                    rect.setFill(Color.BLUE);
                    rect.setStroke(Color.BLUE);
                }
                else if(board[i][j]==' ') {
                    rect.setFill(Color.BLACK);
                    rect.setStroke(Color.BLACK);
                }else if(board[i][j]=='o') {

                    rect.setFill(Color.BLACK);
                    rect.setStroke(Color.BLACK);
                    Rectangle circle = new Rectangle(4,4, Color.GOLD);
                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().addAll(rect, circle);

                    StackPane.setMargin(circle, new Insets(50));
                    tilePane1.getChildren().add(stackPane);
                    continue;
                }else if(board[i][j]=='O') {
                    ImageView imageView = new ImageView(ImageManager.getImage("fruit.png"));
                    imageView.setFitHeight(15);
                    imageView.setFitWidth(15);
                    tilePane1.getChildren().add(imageView);
                    continue;
                }else if(board[i][j]=='F') {
                    ImageView imageView = new ImageView(ImageManager.getImage("greenFruit.png"));
                    imageView.setFitHeight(15);
                    imageView.setFitWidth(15);
                    tilePane1.getChildren().add(imageView);
                    continue;
                }else if(board[i][j]=='W') {
                    rect.setFill(Color.PURPLE);
                    rect.setStroke(Color.PURPLE);
                }
                else if(board[i][j]=='Y') {
                    rect.setFill(Color.GRAY);
                    rect.setStroke(Color.GRAY);
                }else if(board[i][j]=='C') {
                    ImageView imageView = new ImageView(ImageManager.getImage("blueGhost.png"));
                    imageView.setFitHeight(15);
                    imageView.setFitWidth(15);
                    tilePane1.getChildren().add(imageView);
                    continue;
                }else if(board[i][j]=='I') {
                    ImageView imageView = new ImageView(ImageManager.getImage("redGhost.png"));
                    imageView.setFitHeight(15);
                    imageView.setFitWidth(15);
                    tilePane1.getChildren().add(imageView);
                    continue;
                }else if(board[i][j]=='B') {
                    ImageView imageView = new ImageView(ImageManager.getImage("orangeGhost.png"));
                    imageView.setFitHeight(15);
                    imageView.setFitWidth(15);
                    tilePane1.getChildren().add(imageView);
                    continue;
                }else if(board[i][j]=='P') {
                    ImageView imageView = new ImageView(ImageManager.getImage("pinkGhost.png"));
                    imageView.setFitHeight(15);
                    imageView.setFitWidth(15);
                    tilePane1.getChildren().add(imageView);
                    continue;
                }else if(board[i][j]=='c') {
                    ImageView imageView = new ImageView(ImageManager.getImage("pacman.png"));
                    imageView.setFitHeight(15);
                    imageView.setFitWidth(15);
                    tilePane1.getChildren().add(imageView);
                    continue;
                }
                else{
                    rect.setFill(Color.BLACK);
                    rect.setStroke(Color.BLACK);
                }
                tilePane1.getChildren().add(rect);
            }
        }
        tilePane=tilePane1;
        tilePane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 1.0), null, null)));

        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPrefColumns(board[1].length);
        tilePane.setPrefTileHeight(15);
        tilePane.setPrefTileWidth(15);
        tilePane.setId("board");
        //TilePane.setMargin(tilePane, new Insets(30, 0, 0, 0));
        AnchorPane.setTopAnchor(auxPane,0.0);
        AnchorPane.setBottomAnchor(auxPane,0.0);
        AnchorPane.setLeftAnchor(auxPane,0.0);
        AnchorPane.setRightAnchor(auxPane,0.0);
        auxPane.getChildren().clear();
        auxPane.getChildren().add(tilePane);



    }
}

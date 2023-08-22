package tp.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import tp.isec.pa.tinypac.model.GameManager;
import tp.isec.pa.tinypac.model.data.Player;
import tp.isec.pa.tinypac.model.data.Top5;
import tp.isec.pa.tinypac.ui.gui.RootPane;
import tp.isec.pa.tinypac.ui.gui.resources.CSSManager;

import java.io.File;
import java.util.List;

/**
 * Nao tem qualquer relação com a máquina de estados permitindo apenas que o usuário possa escolher começar o jogo ou ver o TOP5
 */
public class StartMenuUI extends BorderPane {
    //GameManager gameBWManager;
    Button btnStart,btnExit,btnTop5,btnSave;
    VBox vBox;
    BorderPane center;


    public StartMenuUI() {
        center=new BorderPane();
        //this.gameBWManager = gameBWManager;
        createViews();
        registerHandlers();
        update();

    }

    private void createViews() {
        CSSManager.applyCSS(this,"styles.css");
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
        //center=new RootPane(gameBWManager);
        File arquivo=new File("files/save.dat");

        btnStart = new Button("Start");
        btnStart.setMinWidth(200);
        btnTop5 = new Button("Top5");
        btnTop5.setMinWidth(200);
        btnExit  = new Button("Exit");
        btnExit.setMinWidth(200);
        btnSave = new Button("Start Save");
        btnSave.setMinWidth(200);
        if (arquivo.exists()){
            if (Top5.loadTop5()==null)vBox = new VBox(btnSave,btnStart,btnExit);
            else vBox = new VBox(btnSave,btnStart,btnTop5,btnExit);
        }else {
            if (Top5.loadTop5()==null)vBox = new VBox(btnStart,btnExit);
            else vBox = new VBox(btnStart,btnTop5,btnExit);

        }
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        center.setCenter(vBox);
        this.setCenter(center);
        registerHandlers();
    }

    private void registerHandlers() {

        //gameBWManager.addPropertyChangeListener(evt -> Platform.runLater(() -> update()));
        btnStart.setOnAction( event -> {

            center=new RootPane(false);
            this.setCenter(center);
            //gameBWManager.start();//tirar os gamemanager
        });
        btnSave.setOnAction( event -> {

            center=new RootPane(true);
            this.setCenter(center);



        });
        btnTop5.setOnAction(actionEvent -> {
            vBox.getChildren().clear();
            TableView<Player> tableView = new TableView<>();

            TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


            TableColumn<Player, Integer> pointsColumn = new TableColumn<>("Points");
            pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

            tableView.setStyle("-fx-focus-color: transparent;");
            tableView.getColumns().addAll(nameColumn, pointsColumn);


            List<Player> topPlayers = Top5.loadTop5().getPlayers();

            tableView.setFixedCellSize(30); // Defina a altura desejada para cada linha
            tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));
            tableView.getItems().setAll(topPlayers);
            tableView.getStyleClass().add("custom-table");

            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Ajustar automaticamente o tamanho das colunas

            Label back=new Label("[Esc] to go Back");
            back.setPrefWidth(150);
            Label top5=new Label("TOP5");
            back.setPrefWidth(150);
            top5.setStyle("-fx-text-fill: white");
            vBox.getChildren().addAll(top5,tableView,back);


        });
        btnExit.setOnAction( event -> {
            Platform.exit();
            System.exit(0);
        });
        this.setOnKeyPressed(event -> {
            File arquivo=new File("files/save.dat");
            KeyCode keyCode = event.getCode();
            if (keyCode==KeyCode.ESCAPE) createViews();



        });
    }

    private void update() {
        /*if (gameBWManager.getState() != null) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);*/

    }
}

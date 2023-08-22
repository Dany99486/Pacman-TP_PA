package tp.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import tp.isec.pa.tinypac.model.GameManager;

public class MainJFX extends Application {
    GameManager gameBWManager;

    @Override
    public void init() throws Exception {
        super.init();
        //gameBWManager = TinyPAcMain.gameBWManager;
    }

    @Override
    public void start(Stage stage) throws Exception {
        newStageForTesting(stage,"TP_PA");

        //newStageForTesting(new Stage(),"Game BW#clone");
    }

//    public void startMenu(Stage stage)

    private void newStageForTesting(Stage stage, String title) {
        //começar o startmenu aqui e o startmenu e que coloca o rootpane
        StartMenuUI menu=new StartMenuUI();
        //RootPane root = new RootPane(gameBWManager);
        Scene scene = new Scene(menu,700,400);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }
}

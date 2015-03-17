

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

//        AppCore banerApp=new AppCore(primaryStage);
//        banerApp.showBaner();

        AppCore.showBaner(primaryStage);
    }


    public static void main(String[] args) {

        launch(args);
    }
}

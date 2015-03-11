import com.lime.zeromvc.Zero;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppCore extends Zero<VoideCommandKey,VoideMediatorKey> {


    public StackPane root = new StackPane();
    public AppCore zero;
    public Stage stage;

    public AppCore(Stage primaryStage){
        stage=primaryStage;
        zero=this;
    }

    public void showBaner() {
        stage.setTitle("WelCome");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
        stage.setMinHeight(200);
        stage.setMinWidth(300);

        addMediator(VoideMediatorKey.BANER,BanerView.class);
        addCommand(VoideCommandKey.CREATE_File, FileCommand.class, "newFile");
        addCommand(VoideCommandKey.OPEN_File,FileCommand.class,"openFile");
        addCommand(VoideCommandKey.SAVE_FILE,FileCommand.class,"saveFile");
        activate(VoideMediatorKey.BANER);
    }
    public void showHome() {
        stage.setTitle("Value Object Development");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        stage.setMinHeight(200);
        stage.setMinWidth(300);


        addMediator(VoideMediatorKey.HOME,HomeView.class);
        addCommand(VoideCommandKey.SAVE_FILE, FileCommand.class, "saveFile");
        addCommand(VoideCommandKey.ADD_CLASS, AppCommand.class, "addClass");
        addCommand(VoideCommandKey.ADD_ITEM, AppCommand.class, "addItem");
        addCommand(VoideCommandKey.ADD_INSTANCE, AppCommand.class, "addInstance");
        addCommand(VoideCommandKey.DELETE_SELECTED, AppCommand.class, "deleteSelected");
        addCommand(VoideCommandKey.SELECT_CLASS, AppCommand.class, "selectClass");
        addCommand(VoideCommandKey.SELECT_ITEM, AppCommand.class, "selectItem");

        activate(VoideMediatorKey.HOME);
    }
}

import com.lime.zeromvc.Zero;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class AppCore extends Zero<VoideCommandKey,VoideMediatorKey> {


    private static AppCore banerApp;
    public StackPane root = new StackPane();
    public AppCore zero;
    public Stage stage;

    public AppCore(Stage primaryStage){
        stage=primaryStage;
        zero=this;
    }

    public static void showBaner(Stage stage) {
        banerApp=new AppCore(stage);
        banerApp.showBaner();
    }

    public void showBaner() {
        stage.setTitle("WelCome");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
        stage.setMinHeight(200);
        stage.setMinWidth(300);

        addMediator(VoideMediatorKey.BANER, BanerView.class);
        addCommand(VoideCommandKey.LOAD_SET, BanerCommand.class, "loadSet");
        addCommand(VoideCommandKey.CREATE_File, BanerCommand.class, "newFile");
        addCommand(VoideCommandKey.OPEN_File,BanerCommand.class,"openFile");
        addCommand(VoideCommandKey.SAVE_FILE,BanerCommand.class,"saveFile");
        addCommand(VoideCommandKey.START,BanerCommand.class,"start");
        addCommand(VoideCommandKey.IMPORT,BanerCommand.class,"importFile");
        activate(VoideMediatorKey.BANER);
        command(VoideCommandKey.LOAD_SET);


    }
    public void showHome() {
        stage.setTitle("Value Object Development");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        stage.setMinHeight(200);
        stage.setMinWidth(300);


        addMediator(VoideMediatorKey.HOME,HomeView.class);
        addCommand(VoideCommandKey.SAVE_FILE, FileCommand.class, "saveFile");
        addCommand(VoideCommandKey.START, FileCommand.class, "start");
        addCommand(VoideCommandKey.ADD_CLASS, AppCommand.class, "addClass");
        addCommand(VoideCommandKey.ADD_ITEM, AppCommand.class, "addItem");
        addCommand(VoideCommandKey.ADD_INSTANCE, AppCommand.class, "addInstance");
        addCommand(VoideCommandKey.DELETE_SELECTED, AppCommand.class, "deleteSelected");
        addCommand(VoideCommandKey.SELECT_CLASS, AppCommand.class, "selectClass");
        addCommand(VoideCommandKey.SELECT_ITEM, AppCommand.class, "selectItem");
        addCommand(VoideCommandKey.RENAME, AppCommand.class, "reName");
        addCommand(VoideCommandKey.SELECT, AppCommand.class, "select");
        addCommand(VoideCommandKey.CHOICE_CLASS_TYPE, AppCommand.class, "selectItemClass");
        addCommand(VoideCommandKey.SELECT_MODEL, AppCommand.class, "selectItemModel");

        addCommand(VoideCommandKey.UP_CLASS_POOL, AppCommand.class, "upClassVoPool");


        addCommand(VoideCommandKey.CHANGE_ID, AppCommand.class, "changeId");
        addCommand(VoideCommandKey.CHANGE_VALUE, AppCommand.class, "changeValue");
        addCommand(VoideCommandKey.CHANGE_ANNOTATION, AppCommand.class, "changeAnnotation");


        addCommand(VoideCommandKey.EXPORT_EXCEL, ExportCommand.class, "excel");
        addCommand(VoideCommandKey.IMPORT_EXCEL, ImportCommand.class, "importExcel");
        addCommand(VoideCommandKey.IMPORT, ImportCommand.class);



        addCommand(VoideCommandKey.EXPORT_JSON, LuaCommand.class,"exportJSON");
        addCommand(VoideCommandKey.EXPORT_LUA, LuaCommand.class,"exportLua");




        activate(VoideMediatorKey.HOME);
    }

    public static void newFile(Stage stage) {

        AppCore appCore = new AppCore(stage);
        appCore.showHome();
        appCore.command(VoideCommandKey.SAVE_FILE);
    }

    public static void openFile(Stage stage ,File file) {
        AppCore appCore=new AppCore(stage);
        appCore.showHome();
        appCore.command(VoideCommandKey.START,file);
    }

    public static void addRecent(File file) {
        banerApp.command(VoideCommandKey.SAVE_FILE, file);
    }


    public static void importFile(Stage stage, File file) {

        AppCore appCore = new AppCore(stage);
        appCore.showHome();
        appCore.command(VoideCommandKey.SAVE_FILE);
        appCore.command(VoideCommandKey.IMPORT,file);

    }
}


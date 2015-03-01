import com.lime.zeromvc.Zero;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppCore extends Zero<VoideCommandKey,VoideMediatorKey> {


    static StackPane root = new StackPane();
    static AppCore zero;
    static Stage stage;

    public AppCore(Stage primaryStage){
        stage=primaryStage;
        zero=this;

        addMediator(VoideMediatorKey.BANER,BanerView.class);
        addMediator(VoideMediatorKey.HOME,HomeView.class);
//        addMediator(VoideMediatorKey.TEST,TestView.class);
//
        addCommand(VoideCommandKey.MAKE_VO,FileCommand.class,"newFile");
        addCommand(VoideCommandKey.OPEN_VO,FileCommand.class,"openFile");
//        addCommand(VoideCommandKey.ADD_NODE,AddNodeCommand.class);
//        addCommand(VoideCommandKey.ADD_TYPE,AddTypeCommand.class);
//        addCommand(VoideCommandKey.TYPE_CHOOSE,AppCommand.class,"typeChoose");
//        addCommand(VoideCommandKey.TYPE_RENAME,AppCommand.class,"reName");
//        addCommand(VoideCommandKey.NODE_CHOOSE,AppCommand.class,"nodeChoose");
//        addCommand(VoideCommandKey.RE_MODE,AppCommand.class,"reMode");
//
//
//        addCommand(VoideCommandKey.SAVE,FileCommand.class,"save");
//
//
//
//
//
//
//        addCommand(VoideCommandKey.CHOICE_TYPE,AppCommand.class,"choiceType");




        primaryStage.setTitle("Value Object Development");
        primaryStage.setScene(new Scene(root, 700, 430));
        primaryStage.show();
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(300);


        activate(VoideMediatorKey.BANER);
//        activate(VoideMediatorKey.TEST);

    }
}


import com.lime.zeromvc.Proxy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by lime on 15/2/25.
 */
public class BanerView extends VoideMediator{
    @Override
    protected void init() {

    }

    @Override
    protected void activate() {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent box = null;
        try {
            box=fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        AppCore.root.getChildren().add(box);

//        Button openVoButton= new Button("打开");
//        openVoButton.setPrefSize(100,100);
//        openVoButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                command(VoideCommandKey.OPEN_VO);
//            }
//        });
//
//        Button newVoButton= new Button("新建");
//        newVoButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                command(VoideCommandKey.MAKE_VO);
//            }
//        });
//        newVoButton.setPrefSize(100,100);
//
//        HBox box = new HBox(10,openVoButton,newVoButton);
//        box.setMaxSize(210,100);
//
//
//
//
//        AppCore.root.getChildren().add(box);

        //dispose();
    }

    @Override
    protected void update(Proxy proxy) {

    }

    @Override
    protected void inactivate() {
        AppCore.root.getChildren().clear();
    }
}

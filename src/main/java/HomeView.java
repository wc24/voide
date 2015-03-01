import com.lime.zeromvc.Proxy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by linming on 15-3-1.
 */
public class HomeView extends VoideMediator {

    @Override
    protected void init() {

    }

    @Override
    protected void activate() {
        Parent root1 = null;
//        //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/home.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../voide/src/main/resources/home.fxml"));
////
        try {
            root1=fxmlLoader.load();
////            homeui=fxmlLoader.getController();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
//
//
//        AppCore.root.getChildren().add(root1);

    }

    @Override
    protected void update(Proxy proxy) {

    }

    @Override
    protected void inactivate() {

    }
}

import com.lime.zeromvc.Mediator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by lime on 15/2/25.
 */
public abstract class VoideMediator extends Mediator<VoideCommandKey,VoideMediatorKey> {

    public Parent box = null;
    public VoideMediator (){
        //super("one");
    }

    public AppCore getApp(){
        return (AppCore) zero;
    }

    public void loadFxml(String fxmlName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
        fxmlLoader.setController(this);

        try {
            box=fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        getApp().root.getChildren().add(box);
    }
}

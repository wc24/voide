
import com.lime.zeromvc.Proxy;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by lime on 15/2/25.
 */
public class BanerView extends VoideMediator {

    public BanerView(){
        //System.out.print("xxx");
    }

    @Override
    protected void init() {

    }

    @Override
    protected void activate() {
        loadFxml("baner.fxml");
    }
    @Override
    protected void update(Proxy proxy) {

    }

    @Override
    protected void inactivate() {
        getApp().root.getChildren().clear();
    }
    //-----------------------------------
    @FXML
    private void createHd(Event event){
        command(VoideCommandKey.CREATE_File);
    }
    @FXML
    private void importHd(Event event){
        System.out.print("2");
    }
    @FXML
    private void openHd(Event event){
        command(VoideCommandKey.OPEN_File);
    }
    //-----------------------------------

}

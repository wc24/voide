
import com.lime.zeromvc.Proxy;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.ResourceBundle;

/**
 * Created by lime on 15/2/25.
 */
public class BanerView extends VoideMediator {

    public BanerProxy banerProxy;
    public BanerView(){
        //System.out.print("xxx");
    }

    @Override
    protected void init() {

        banerProxy=getProxy(BanerProxy.class);
        addProxy(banerProxy);

    }

    @Override
    protected void activate() {
        loadFxml("baner.fxml");
        recentUI.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                command(VoideCommandKey.START,new File((String) recentUI.getSelectionModel().getSelectedItem()));
            }
        });

    }
    @Override
    protected void update(Proxy proxy) {

        recentUI.getItems().clear();
        for (String recent :banerProxy.recentList){
            recentUI.getItems().add(recent);
        }

//                recentUI.getItems().add(classesJSON.getString(i));
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
        command(VoideCommandKey.IMPORT);
    }
    @FXML
    private void openHd(Event event){
        command(VoideCommandKey.OPEN_File);
    }
    @FXML
    private ListView recentUI;

    //-----------------------------------

}

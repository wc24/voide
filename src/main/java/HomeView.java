import com.lime.zeromvc.Proxy;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

/**
 * Created by linming on 15-3-1.
 */
public class HomeView extends VoideMediator {
    public AppProxy appProxy;

    @Override
    protected void init() {
        appProxy = getProxy(AppProxy.class);
        addProxy(appProxy);

    }

    @Override
    protected void activate() {
        loadFxml("home.fxml");
        //box.addEventHandler();
        box.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.S && event.isMetaDown()) {
                    command(VoideCommandKey.SAVE_FILE);
                }
            }
        });


        classData = FXCollections.observableArrayList();
        itemData = FXCollections.observableArrayList();
        classListView.setItems(classData);
        itemListView.setItems(itemData);


        classListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<ClassVo>() {
            @Override
            public void onChanged(Change<? extends ClassVo> c) {
                System.out.print(c.getList().get(0));
                command(VoideCommandKey.SELECT_CLASS,c.getList().get(0));
            }
        });

    }

    public ObservableList<ClassVo> classData;
    public ObservableList<ItemVo> itemData;

    @FXML
    private ListView classListView;

    @FXML
    private ListView itemListView;



    @FXML
    private TextField nameUI;

    @FXML
    private TextField idUI;

    @FXML
    private TextField valueUI;

    @FXML
    private ChoiceBox choiceBox;


    @FXML
    private void addClassHd() {
        command(VoideCommandKey.ADD_CLASS);
    }

    @FXML
    private void addItemHd() {
        command(VoideCommandKey.ADD_ITEM);
    }

    @FXML
    private void addInstanceHd() {
        command(VoideCommandKey.ADD_INSTANCE);
    }

    @FXML
    private void deleteSelectedHd() {
        command(VoideCommandKey.DELETE_SELECTED);
    }

    @Override
    protected void update(Proxy proxy) {

        // System.out.print(itemListView.getItems());


        for (int i = 0; i < classData.size(); i++) {
            ClassVo classVo=classData.get(i);
            if (!appProxy.classVoList.contains(classVo)) {
                classListView.getSelectionModel().select(-1);
                classData.remove(classVo);
            }
        }
        for (int i = 0; i < appProxy.classVoList.size(); i++) {
            ClassVo classVo=appProxy.classVoList.get(i);
            if (!classData.contains(classVo)) {
                classData.add(classVo);
            }
        }


        if (appProxy.selectClass!=null){
            nameUI.setText(appProxy.selectClass.className);
        }

        System.out.print("123");

    }

    @Override
    protected void inactivate() {

    }
}

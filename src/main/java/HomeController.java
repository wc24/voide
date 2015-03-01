
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    public ListView l1;

    @FXML
    public ListView l2;

    @FXML
    public Separator s1;

    @FXML
    public Separator s2;

    @FXML
    public TextField pName;
    @FXML
    public TextField tName;


    @FXML
    public ChoiceBox typeChoice;

    @FXML
    public Label typeTxt;
    @FXML
    public Label nameTxt;

    @FXML
    public HBox nodeSet;


    @FXML
    ToggleGroup g1;

    @FXML
    RadioButton r1;
    @FXML
    RadioButton r2;
    @FXML
    RadioButton r3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        typeChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                AppCore.zero.command(VoideCommandKey.CHOICE_TYPE, typeChoice.getItems().get((Integer) newValue).toString());

            }
        });
        r1.setUserData(0);
        r2.setUserData(1);
        r3.setUserData(2);
        g1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue != null) {
//                    AppCore.zero.command(VoideCommandKey.RE_MODE, newValue);
                }
            }
        });


    }


    @FXML
    private void addTypeHd(Event event) {
//        AppCore.zero.command(VoideCommandKey.ADD_TYPE, "newType");
    }

    @FXML
    private void addNodeHd(Event event) {
//        AppCore.zero.command(VoideCommandKey.ADD_NODE, "newNode");
    }

    @FXML
    private void typeListHd(Event event) {

        l2.getSelectionModel().select(-1);
//        AppCore.zero.command(VoideCommandKey.TYPE_CHOOSE, l1.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void nodeListHd(Event event) {
//        AppCore.zero.command(VoideCommandKey.NODE_CHOOSE, l2.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void pNameHd(ActionEvent event) {
//        AppCore.zero.command(VoideCommandKey.TYPE_RENAME, pName.getText());
    }

    @FXML
    private void tNameHd(ActionEvent event) {
//        AppCore.zero.command(VoideCommandKey.CHOICE_TYPE, tName.getText());
    }

    @FXML
    private void saveHd(ActionEvent event) {
//        AppCore.zero.command(VoideCommandKey.SAVE);
    }

}

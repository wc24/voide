import com.lime.zeromvc.Proxy;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

/**
 * Created by linming on 15-3-1.
 */
public class HomeView extends VoideMediator {


    public ObservableList<ViewListData> classData;
    public ObservableList<ClassVo> classTypeData;
    public ObservableList<ViewListData> itemData;
    @FXML
    private ListView classListView;
    @FXML
    private ListView itemListView;
    @FXML
    private TextField nameUI;
    @FXML
    private TextField idUI;
    @FXML
    private TextArea valueUI;
    @FXML
    private ChoiceBox classUI;
    @FXML
    private TextArea annotationUI;
    @FXML
    private VBox itemBox;
    @FXML
    private ToggleGroup typeGroup;
    @FXML
    private MenuButton exportUI;

    private boolean isClass;

    @Override
    protected void init() {
        //addProxy(getProxy(StateProxy.class));
        addProxy(getProxy(AppProxy.class));

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
                checkKey(event);

            }
        });


        classData = FXCollections.observableArrayList();
        itemData = FXCollections.observableArrayList();
        classTypeData = FXCollections.observableArrayList();


        classListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (isClass){
                    command(VoideCommandKey.SELECT_CLASS, classListView.getSelectionModel().getSelectedItem());
                }
            }
        });

        itemListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (!isClass){
                    command(VoideCommandKey.SELECT_ITEM, itemListView.getSelectionModel().getSelectedItem());
                }
            }
        });


        classListView.setItems(classData);
        itemListView.setItems(itemData);
        classUI.setItems(classTypeData);


        classUI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ClassVo>() {
            @Override
            public void changed(ObservableValue<? extends ClassVo> observable, ClassVo oldValue, ClassVo newValue) {
                command(VoideCommandKey.CHOICE_CLASS_TYPE, newValue);
            }
        });
        typeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                ItemModel itemModel;
                if (newValue.equals(typeGroup.getToggles().get(2))) {
                    itemModel = ItemModel.REPEATED;
                } else if (newValue.equals(typeGroup.getToggles().get(1))) {
                    itemModel = ItemModel.OPTIONAL;
                } else {
                    itemModel = ItemModel.REQUIRED;
                }
                command(VoideCommandKey.SELECT_MODEL, itemModel);
            }
        });


        nameUI.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                command(VoideCommandKey.RENAME, nameUI.getText());
                //checkKey(event);
            }
        });
        idUI.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                command(VoideCommandKey.CHANGE_ID, idUI.getText());
               // checkKey(event);
            }
        });
        valueUI.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                command(VoideCommandKey.CHANGE_VALUE, valueUI.getText());
                checkKey(event);
            }
        });
        annotationUI.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                command(VoideCommandKey.CHANGE_ANNOTATION, annotationUI.getText());
                checkKey(event);
            }
        });




    }

    private void checkKey(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN && event.isMetaDown()){
            if (isClass){
                classListView.getSelectionModel().selectNext();
            }else{
                itemListView.getSelectionModel().selectNext();
            }
        }else if (event.getCode() == KeyCode.UP && event.isMetaDown()){
            if (isClass){
                classListView.getSelectionModel().selectPrevious();
            }else{
                itemListView.getSelectionModel().selectPrevious();
            }
        }
    }

    @FXML
    private void addClassHd() {
        command(VoideCommandKey.ADD_CLASS);
    }

    @FXML
    private void addItemHd() {
        command(VoideCommandKey.ADD_ITEM);
    }

    @FXML
    private void saveHd() {
        command(VoideCommandKey.SAVE_FILE);
    }

    @FXML
    private void addInstanceHd() {
        command(VoideCommandKey.ADD_INSTANCE);
    }

    @FXML
    private void deleteSelectedHd() {
        command(VoideCommandKey.DELETE_SELECTED);
    }

    @FXML
    private void classListViewHd() {
        isClass=true;
        command(VoideCommandKey.SELECT_CLASS, classListView.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void itemListViewHd() {
        isClass=false;
        command(VoideCommandKey.SELECT_ITEM, itemListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void exportExcelHd() {
        command(VoideCommandKey.EXPORT_EXCEL);
    }

    @FXML
    private void exportLuaHd() {
        command(VoideCommandKey.EXPORT_LUA);
    }

    @FXML
    private void exportJsonHd() {
        command(VoideCommandKey.EXPORT_JSON);
    }


    @FXML
    private void importExcelHd() {
        command(VoideCommandKey.IMPORT_EXCEL);
    }

    @Override
    protected void update(Proxy proxy) {
    }


    public void update(AppProxy proxy, AppProxyEnum type) {
        switch (type) {
            case CALSS_LIST:
            case INSTANCE_LIST:
                classData.clear();
                for (int i = 0; i < proxy.classVoList.size(); i++) {
                    ClassVo classVo = proxy.classVoList.get(i);
                    classData.add(classVo);
                }
                for (int i = 0; i < proxy.instanceVoList.size(); i++) {
                    InstanceVo instanceVo = proxy.instanceVoList.get(i);
                    classData.add(instanceVo);
                }
                if (proxy.selectClass != null) {
                    int selectId = classData.indexOf(proxy.selectClass);
                    if (selectId != -1) {
                        classListView.getSelectionModel().select(selectId);
                    }
                }

                break;
            case ITEM_LIST:
                if (proxy.selectClass != null) {
                    itemData.clear();
                    for (int i = 0; i < proxy.selectClass.itemVoList.size(); i++) {
                        ItemVo itemVo = proxy.selectClass.itemVoList.get(i);
                        itemData.add(itemVo);
                    }
                }
                break;
            case CALSS_POOL:
                classTypeData.clear();
                for (Map.Entry<String, ClassVo> entry : proxy.classVoPool.entrySet()) {
                    classTypeData.add(entry.getValue());
                }
                break;
            case CALSS_NAME:
                classData.set(classData.indexOf(proxy.selectClass), proxy.selectClass);
                break;
            case ITEM_NAME:
                itemData.set(itemData.indexOf(proxy.selectItem), proxy.selectItem);
                break;
            case INSTANCE_NAME:
                classData.set(classData.indexOf(proxy.selectInstance), proxy.selectInstance);
                break;
            case ARRTIBUTE_LIST:
                if (proxy.selectInstance != null) {
                    itemData.clear();
                    listInstanceData(proxy.selectInstance,null,proxy.selectInstance.classType, 0);

                    proxy.selectInstance.attributePool.clear();
                    for (ViewListData viewListData : itemData){
                        AttributeVo attributeVO=(AttributeVo)viewListData;
                        proxy.selectInstance.attributePool.put(attributeVO.name,attributeVO);
                    }
                }
                break;
            case PREFERENCES:
                nameUI.setDisable(false);
                switch (proxy.editTarget) {
                    case ITEM:
                        if (proxy.selectItem != null) {
                            itemBox.setDisable(false);
                            if (!nameUI.getText().equals(proxy.selectItem.name)) {
                                nameUI.setText(proxy.selectItem.name);
                            }
                            classUI.getSelectionModel().select(proxy.selectItem.classType);
                            typeGroup.getToggles().get(proxy.selectItem.getModel()).setSelected(true);
                            valueUI.setText(proxy.selectItem.value);
                            annotationUI.setText(proxy.selectItem.annotation);
                            idUI.setText(proxy.selectItem.id);
                        } else {
                            itemBox.setDisable(true);
                        }
                        break;
                    case INSTANCE:
                        itemBox.setDisable(true);
                        if (proxy.selectInstance != null) {
                            nameUI.setText(proxy.selectInstance.name);
                            valueUI.setText(proxy.selectInstance.value);
                            annotationUI.setText(proxy.selectInstance.annotation);
                            idUI.setText(proxy.selectInstance.id);
                        }

                        break;
                    case ATTRIBUTE:
                        itemBox.setDisable(true);
                        if (proxy.selectAttribute != null) {
                            nameUI.setText(proxy.selectAttribute.name);
                            nameUI.setDisable(true);
                            classUI.getSelectionModel().select(proxy.selectAttribute.itemVo.classType);
                            typeGroup.getToggles().get(proxy.selectAttribute.itemVo.getModel()).setSelected(true);
                            valueUI.setText(proxy.selectAttribute.value);
                            annotationUI.setText(proxy.selectAttribute.annotation);
                            idUI.setText(proxy.selectAttribute.id);
                        }
                        break;
                    case CLASS:
                        itemBox.setDisable(true);
                        if (proxy.selectClass != null) {
                            if (!nameUI.getText().equals(proxy.selectClass.name)) {
                                nameUI.setText(proxy.selectClass.name);
                            }
                            valueUI.setText(proxy.selectClass.value);
                            annotationUI.setText(proxy.selectClass.annotation);
                            idUI.setText(proxy.selectClass.id);
                        }
                        break;
                }
                break;
        }
    }

    private void listInstanceData(InstanceVo instanceVo,String keys,ClassVo classType, int depth) {

        if (depth < 10) {
            for (int i = 0; i < classType.itemVoList.size(); i++) {
                ItemVo itemVo = classType.itemVoList.get(i);
                String nextKeys=null;
                if (keys==null){
                    nextKeys=itemVo.name;
                }else{
                    nextKeys=keys+"."+itemVo.name;
                }
                if (itemVo.model==ItemModel.REPEATED){
                    //addInstanceData(instanceVo,nextKeys+".length",itemVo,depth+1);
                    AttributeVo attributeVO=instanceVo.attributePool.get(nextKeys+".length");

                    if (attributeVO!=null){
                        attributeVO.itemVo=itemVo;
                        itemData.add(attributeVO);
                        int len=0;
                        try {
                            len=Integer.parseInt(attributeVO.value);
                            if (len>100){
                                len=100;
                            }
                        }catch (NumberFormatException e){
                        }

                        if (len>0){
                            AttributeVo subAttributeVo;
                            for (int j = 0; j < len; j++) {
                                addAttributeData(instanceVo,nextKeys+"["+j+"]",itemVo,depth);
                            }
                        }


                    }else{
                        attributeVO=new AttributeVo();
                        attributeVO.name=nextKeys+".length";
                        attributeVO.itemVo=itemVo;
                        itemData.add(attributeVO);
                    }


                }else{

                    addAttributeData(instanceVo,nextKeys,itemVo,depth);
                }
            }
        }
//        for (int i = 0; i < proxy.selectClass.itemVoList.size(); i++) {
//                        ViewListData itemVo = proxy.selectClass.itemVoList.get(i);
//                        itemData.add(itemVo);
//                    }
    }

    private void addAttributeData(InstanceVo instanceVo, String nextKeys, ItemVo itemVo, int depth) {

        AttributeVo attributeVO=instanceVo.attributePool.get(nextKeys);
        if (itemVo.classType!=null && itemVo.classType.itemVoList.size()>0){
            listInstanceData(instanceVo,nextKeys,itemVo.classType,depth+1);
        }else if (attributeVO!=null){
            attributeVO.itemVo=itemVo;
            itemData.add(attributeVO);
        }else{
            attributeVO=new AttributeVo();
            attributeVO.name=nextKeys;
            attributeVO.itemVo=itemVo;
            itemData.add(attributeVO);
        }
    }

//    private void addInstanceData(InstanceVo instanceVo, String nextKeys, ItemVo itemVo, int depth) {
//        AttributeVo attributeVO=instanceVo.attributePool.get(nextKeys);
//        if (itemVo.classType!=null && itemVo.classType.itemVoList.size()>0){
//            listInstanceData(instanceVo,nextKeys,itemVo.classType,depth+1);
//        }else if (attributeVO!=null){
//            attributeVO.itemVo=itemVo;
//            itemData.add(attributeVO);
//        }else{
//            attributeVO=new AttributeVo();
//            attributeVO.name=nextKeys;
//            attributeVO.itemVo=itemVo;
//            itemData.add(attributeVO);
//        }
//    }


    @Override
    protected void inactivate() {

    }
}

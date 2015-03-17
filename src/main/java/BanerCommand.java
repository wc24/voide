import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.CharBuffer;

/**
 * Created by lime on 15/3/16.
 */
public class BanerCommand extends VoideCommand {
    public BanerProxy banerProxy;

    @Override
    protected void init() {
        banerProxy=getProxy(BanerProxy.class);
    }

    public void loadSet() {

        try {
            FileReader fileReader;
            fileReader = new FileReader(banerProxy.recentFile);
            CharBuffer loadBuffer = CharBuffer.allocate((int) banerProxy.recentFile.length());
            fileReader.read(loadBuffer);
            String loadData = new String(loadBuffer.array());
            JSONObject loadJSON = new JSONObject(loadData);
            JSONArray classesJSON = loadJSON.getJSONArray("fileList");
            for (int i = 0; i < classesJSON.length(); i++) {
                banerProxy.recentList.add(classesJSON.getString(i));
//                recentUI.getItems().add(classesJSON.getString(i));
            }
        } catch (FileNotFoundException e) {
           // e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        banerProxy.update();

    }
    public void newFile() {
        Stage stage = new Stage();
        AppCore.newFile(stage);
    }

    public void openFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialFileName("vo.vojson");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("vojson files (*.vojson)", "*.vojson"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            AppCore.openFile(stage,file);
        }
    }
    public void importFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("import Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            AppCore.importFile(stage,file);
        }
    }



    public void start(File file) {
        Stage stage = new Stage();
        AppCore.openFile(stage,file);
    }

    public void saveFile(File file) {

        if (!banerProxy.recentList.contains(file.getPath())){
            banerProxy.recentList.add(file.getPath());
        }

        FileWriter fileWriter = null;
        if(!banerProxy.recentFile.isDirectory()){
            new File(banerProxy.recentFile.getParent()).mkdirs();
        }

        if(!banerProxy.recentFile.isFile()){
            try {
                banerProxy.recentFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            fileWriter = new FileWriter(banerProxy.recentFile);
            JSONObject nodeJo = new JSONObject();
            nodeJo.put("fileList",JSONObject.wrap(banerProxy.recentList));
            if (nodeJo != null && nodeJo.toString().length() > 2) {
                fileWriter.write(nodeJo.toString());
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        banerProxy.update();
    }
}
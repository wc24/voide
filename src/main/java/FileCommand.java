import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Created by linming on 15-3-1.
 */
public class FileCommand extends VoideCommand {
    public FileProxy fileProxy;
    public AppProxy appProxy;
    @Override
    protected void init() {
    }
    public void newFile(){
        Stage stage=new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialFileName("vo.vojson");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("vojson files (*.vojson)", "*.vojson"));
        File file=  fileChooser.showSaveDialog(stage);
        if (file!=null){
            AppCore banerApp=new AppCore(stage);
            fileProxy=banerApp.zero.model.getProxy(FileProxy.class);
            fileProxy.filePath=file.getPath();
            banerApp.zero.command(VoideCommandKey.SAVE_FILE);
            banerApp.showHome();
        }
    }
    public void openFile(){
        Stage stage=new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialFileName("vo.vojson");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("vojson files (*.vojson)", "*.vojson"));
        File file=  fileChooser.showOpenDialog(stage);
        if (file!=null){
            AppCore banerApp=new AppCore(stage);

            fileProxy=banerApp.zero.model.getProxy(FileProxy.class);
            appProxy =banerApp.zero.model.getProxy(AppProxy.class);
            fileProxy.filePath=file.getPath();
            banerApp.zero.command(VoideCommandKey.SAVE_FILE);
            banerApp.showHome();




            FileReader fileReader= null;
            CharBuffer loadBuffer = CharBuffer.allocate((int) file.length());
            try {
                fileReader = new FileReader(fileProxy.filePath);
                fileReader.read(loadBuffer);
                String loadData=new String(loadBuffer.array());
//                System.out.print(loadData);
                JSONArray loadJSON =new JSONArray(loadData);

                for (int i = 0; i < loadJSON.length(); i++) {
                    JSONObject classJson= loadJSON.getJSONObject(i);
                    JSONArray itemsJson= classJson.getJSONArray("itemVoList");
                    ClassVo classVo=new ClassVo(classJson.getString("className"));
                    for (int j = 0; j <itemsJson.length() ; j++) {
                        JSONObject itemJson= itemsJson.getJSONObject(i);
                        ItemVo itemVo=new ItemVo(itemJson.getString("itemName"),itemJson.getString("type"),itemJson.getInt("id"),itemJson.getString("value"));
                    }
                    appProxy.classVoList.add(classVo);
                }
                appProxy.update();
                //JSONArray nodeJo = new JSONArray(appProxy.classVoList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    public void saveFile(){
        fileProxy =this.getProxy(FileProxy.class);
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(fileProxy.filePath);
            JSONArray nodeJo = new JSONArray(appProxy.classVoList);
            System.out.print(nodeJo.toString());
            fileWriter.write(nodeJo.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

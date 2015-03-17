import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Iterator;

/**
 * Created by linming on 15-3-1.
 */
public class FileCommand extends VoideCommand {
    public FileProxy fileProxy;
    public AppProxy appProxy;

    @Override
    protected void init() {
        fileProxy = getProxy(FileProxy.class);
        appProxy = getProxy(AppProxy.class);
    }






    public void start(File file) {
        fileProxy.file=file;
        FileReader fileReader = null;
        CharBuffer loadBuffer = CharBuffer.allocate((int) fileProxy.file.length());
        try {
            fileReader = new FileReader(fileProxy.file.getPath());
            fileReader.read(loadBuffer);
            String loadData = new String(loadBuffer.array());
//                System.out.print(loadData);
            JSONObject loadJSON = new JSONObject(loadData);

            JSONArray classesJSON = loadJSON.getJSONArray("classList");


            for (int i = 0; i < classesJSON.length(); i++) {
                JSONObject classJson = classesJSON.getJSONObject(i);
                JSONArray itemsJson = classJson.getJSONArray("itemVoList");
                ClassVo classVo = new ClassVo(classJson.getString("name"));
                if (classJson.has("annotation")) {
                    classVo.annotation = classJson.getString("annotation");
                }
                if (classJson.has("value")) {
                    classVo.value = classJson.getString("value");
                }
                if (classJson.has("id")) {
                    classVo.id = classJson.getString("id");
                }
                for (int j = 0; j < itemsJson.length(); j++) {
                    JSONObject itemJson = itemsJson.getJSONObject(j);
                    ItemVo itemVo = new ItemVo(itemJson.getString("name"));

                    if (itemJson.has("className")) {
                        itemVo.className = itemJson.getString("className");
                    }
                    itemVo.model = ItemModel.values()[itemJson.getInt("model")];
                    if (itemJson.has("annotation")) {
                        itemVo.annotation = itemJson.getString("annotation");
                    }
                    if (itemJson.has("value")) {
                        itemVo.value = itemJson.getString("value");
                    }
                    if (itemJson.has("id")) {
                        itemVo.id = itemJson.getString("id");
                    }
                    classVo.itemVoList.add(itemVo);
                }
                appProxy.classVoList.add(classVo);
            }

            command(VoideCommandKey.UP_CLASS_POOL);


            for (ClassVo classVo : appProxy.classVoList) {
                for (ItemVo itemVo : classVo.itemVoList) {
                    itemVo.classType = appProxy.classVoPool.get(itemVo.className);
                }
            }


            JSONArray instanceListJSON = loadJSON.getJSONArray("instanceList");

            for (int i = 0; i < instanceListJSON.length(); i++) {
                JSONObject instanceJSON = instanceListJSON.getJSONObject(i);
                InstanceVo instanceVo = new InstanceVo(appProxy.classVoPool.get(instanceJSON.getString("className")), instanceJSON.getString("name"));
                JSONObject attributesJson = instanceJSON.getJSONObject("attributePool");
                Iterator iter = attributesJson.keys();
                while (iter.hasNext()) {
                    String str = (String) iter.next();
                    JSONObject attributeJson = attributesJson.getJSONObject(str);
                    AttributeVo attributeVo = new AttributeVo();
                    if (attributeJson.has("annotation")) {
                        attributeVo.annotation = attributeJson.getString("annotation");
                    }
                    if (attributeJson.has("value")) {
                        attributeVo.value = attributeJson.getString("value");
                    }
                    if (attributeJson.has("id")) {
                        attributeVo.id = attributeJson.getString("id");
                    }
                    if (attributeJson.has("name")) {
                        attributeVo.name = attributeJson.getString("name");
                    }
                    instanceVo.attributePool.put(str, attributeVo);
                }
                appProxy.instanceVoList.add(instanceVo);
            }
            appProxy.update(AppProxyEnum.CALSS_LIST);
            //JSONArray nodeJo = new JSONArray(appProxy.classVoList);

            if (loadJSON.has("file") && loadJSON.getJSONObject("file").has("excelFile")){
                fileProxy.excelFile=new File(loadJSON.getJSONObject("file").getString("excelFile"));
            }
            if (loadJSON.has("file") && loadJSON.getJSONObject("file").has("luaFile")){
                fileProxy.luaFile=new File(loadJSON.getJSONObject("file").getString("luaFile"));
            }
            if (loadJSON.has("file") && loadJSON.getJSONObject("file").has("jsonFile")){
                fileProxy.jsonFile=new File(loadJSON.getJSONObject("file").getString("jsonFile"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        if (fileProxy.file == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setInitialFileName("vo.vojson");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("vojson files (*.vojson)", "*.vojson"));
            fileProxy.file = fileChooser.showSaveDialog(((AppCore) zero).stage);
            if (fileProxy.file != null) {
                AppCore.addRecent(fileProxy.file);

            } else {
                return;
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileProxy.file);
            JSONObject nodeJo = new JSONObject(appProxy);
            nodeJo.put("file",JSONObject.wrap(fileProxy));
            if (nodeJo != null && nodeJo.toString().length() > 2) {
                fileWriter.write(nodeJo.toString());
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

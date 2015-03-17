import javafx.stage.FileChooser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lime on 15/3/17.
 */
public class LuaCommand extends VoideCommand {
    private FileProxy fileProxy;
    private AppProxy appProxy;

    @Override
    protected void init() {
        fileProxy = getProxy(FileProxy.class);
        appProxy = getProxy(AppProxy.class);
    }


    public void exportJSON() {
        if (fileProxy.jsonFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("export Json File");
            fileChooser.setInitialFileName(".json");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("json files (*.json)", "*.json"));
            fileProxy.jsonFile = fileChooser.showSaveDialog(((AppCore) zero).stage);
        }
        if (fileProxy.jsonFile == null) {
            return;
        }

        JSONObject out = new JSONObject();
        for (InstanceVo instanceVo : appProxy.instanceVoList) {
            out.put(instanceVo.name, classVoToJSON(instanceVo, null, instanceVo.classType));
        }
        writeFile(fileProxy.jsonFile, out.toString(1));

    }

    public void exportLua() {

        if (fileProxy.luaFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("export Lua File");
            fileChooser.setInitialFileName(".lua");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("lua files (*.lua)", "*.lua"));
            fileProxy.luaFile = fileChooser.showSaveDialog(((AppCore) zero).stage);
        }
        if (fileProxy.luaFile == null) {
            return;
        }

        JSONObject out = new JSONObject();
        for (InstanceVo instanceVo : appProxy.instanceVoList) {
            out.put(instanceVo.name, classVoToJSON(instanceVo, null, instanceVo.classType));
        }
        writeFile(fileProxy.luaFile, Tool.JsonToLua(out.toString(1)));

    }

    private void writeFile(File file, String out) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(out);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object classVoToJSON(InstanceVo instanceVo, String keys, ClassVo classType) {
        AttributeVo attributeVO = instanceVo.attributePool.get(keys);
        if (classType.getName().equals("String")) {
            if (attributeVO == null) {
                return "";
            } else {
                return attributeVO.value;
            }

        } else if (classType.getName().equals("Number")) {
            if (attributeVO == null) {
                return 0;
            } else {
                return Tool.parseInt(attributeVO.value);
            }

        } else if (classType.getName().equals("Boolean")) {
            if (attributeVO == null) {
                return false;
            } else {
                return attributeVO.value != "0" || attributeVO.value != "false";
            }
        } else {
            JSONObject instanceJSONO = new JSONObject();
            for (ItemVo itemVo : classType.itemVoList) {
                String nextKeys = null;
                if (keys == null) {
                    nextKeys = itemVo.name;
                } else {
                    nextKeys = keys + "." + itemVo.name;
                }
                if (itemVo.model == ItemModel.REPEATED) {
                    JSONArray itemJSONArray = new JSONArray();
                    attributeVO = instanceVo.attributePool.get(nextKeys + ".length");

                    if (attributeVO != null) {
                        for (int i = 0; i < Tool.parseInt(attributeVO.value); i++) {
                            itemJSONArray.put(classVoToJSON(instanceVo, nextKeys + "[" + i + "]", itemVo.classType));
                        }
                    }
                    instanceJSONO.put(itemVo.name, itemJSONArray);
                } else {
                    instanceJSONO.put(itemVo.name, classVoToJSON(instanceVo, nextKeys, itemVo.classType));
                }
            }
            return instanceJSONO;
        }
    }
}

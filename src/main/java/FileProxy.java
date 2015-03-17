import com.lime.zeromvc.Proxy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileProxy extends Proxy {
    public File file;
    public File excelFile;
    public File luaFile;
    public File jsonFile;


    public String getExcelFile() {
        return excelFile.getPath();
    }
    public String getLuaFile() {
        return luaFile.getPath();
    }
    public String getJsonFile() {
        return jsonFile.getPath();
    }
}
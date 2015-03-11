import java.util.ArrayList;
import java.util.List;

/**
 * Created by lime on 15/3/11.
 */
public class ClassVo {
    public String className;
    public List<ItemVo> itemVoList;

    public ClassVo(String className) {
        this.className = className;
        itemVoList = new ArrayList<ItemVo>();
    }

    public List<ItemVo> getItemVoList() {
        return itemVoList;
    }

    public String getClassName() {
        return className;
    }
    public String toString(){
        return className;
    }
}

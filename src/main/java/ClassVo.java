import java.util.ArrayList;
import java.util.List;

/**
 * Created by lime on 15/3/11.
 */
public class ClassVo  extends ViewListData {
    public List<ItemVo> itemVoList;

    public ClassVo(String className) {
        this.name = className;
        itemVoList = new ArrayList<ItemVo>();
    }

    public List<ItemVo> getItemVoList() {
        return itemVoList;
    }
    public String toString(){
        return name;
    }
}

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lime on 15/3/13.
 */
public class InstanceVo extends ViewListData {
    public ClassVo classType;
    public Map<String,AttributeVo> attributePool;

    public InstanceVo(ClassVo classType,String instanceName) {
        this.classType = classType;
        this.name = instanceName;
        attributePool=new HashMap<String, AttributeVo>();
    }
    public String toString(){
        return classType.name+"@"+name;
    }
    public String getClassName (){
        return classType.name;
    }

    public Map<String,AttributeVo> getAttributePool() {
        return attributePool;
    }
}

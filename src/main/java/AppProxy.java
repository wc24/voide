import com.lime.zeromvc.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linming on 15-3-1.
 */
public class AppProxy extends Proxy {
    public List<ClassVo> classVoList;
    public List<InstanceVo> instanceVoList;
    public Map<String,ClassVo> classVoPool;
    public Map<String,InstanceVo> instanceVoPool;
    public ItemVo selectItem;
    public ClassVo selectClass;
    public InstanceVo selectInstance;
    public int classIndex;
    public int itemIndex;
    public int instanceIndex;

    public ClassVo numberClassVo;
    public ClassVo stringClassVo;
    public ClassVo booleanClassVo;
    public AttributeVo selectAttribute;
    public EditTarget editTarget;

    public AppProxy() {
        classVoList=new ArrayList<ClassVo>();
        instanceVoList=new ArrayList<InstanceVo>();
        classIndex=0;
        itemIndex=0;
        instanceIndex=0;
        numberClassVo=new ClassVo("Number");
        stringClassVo=new ClassVo("String");
        booleanClassVo=new ClassVo("Boolean");
        classVoPool=new HashMap<String, ClassVo>();
        instanceVoPool=new HashMap<String, InstanceVo>();

    }

    public List<ClassVo> getClassList(){
        return  classVoList;
    }
    public List<InstanceVo> getInstanceList(){
        return  instanceVoList;
    }
}

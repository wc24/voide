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
    public ItemVo selectItem;
    public ClassVo selectClass;
    public Integer classIndex;

    public AppProxy() {
        classVoList=new ArrayList<ClassVo>();
    }
}

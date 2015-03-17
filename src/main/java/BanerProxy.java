import com.lime.zeromvc.Proxy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lime on 15/3/16.
 */
public class BanerProxy extends Proxy {
    public List<String> recentList;
    public File recentFile;

    public BanerProxy(){
        recentFile=new File(System.getProperty("user.home")+"/voide/set.josn");
        recentList=new ArrayList<String>();
    }
}

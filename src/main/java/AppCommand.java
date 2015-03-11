import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by linming on 15-3-1.
 */
public class AppCommand extends VoideCommand {

    private AppProxy appProxy;

    @Override
    protected void init() {
        appProxy =this.getProxy(AppProxy.class);

    }

    public void addClass(){
        appProxy.classIndex++;
        appProxy.classVoList.add(new ClassVo("CLASS"+ appProxy.classIndex));
        appProxy.update();
    }
    public void addItem(){
//        appProxy.classVoList.add(new ClassVo("CLASS"+ appProxy.classVoList.size()));
//        appProxy.update();
    }

    public void addInstance(){
//        appProxy.classVoList.add(new ClassVo("CLASS"+ appProxy.classVoList.size()));
//        appProxy.update();
    }
    public void deleteSelected(){

        if (appProxy.classVoList.contains(appProxy.selectClass)){
            appProxy.classVoList.remove(appProxy.selectClass);
        }
        appProxy.update();
    }
    public void selectClass(ClassVo classVo){
        appProxy.selectClass=classVo;
        appProxy.update();
    }
    public void selectItem(ItemVo itemVo){
        appProxy.selectItem=itemVo;
        appProxy.update();
    }


}

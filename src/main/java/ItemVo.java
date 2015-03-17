/**
 * Created by lime on 15/3/10.
 */
public class ItemVo extends ViewListData {
    public String className;
    public ItemModel model;
    public ClassVo classType;

    public ItemVo(String itemName){
        this.name = itemName;
    }
    public Integer getModel (){
        return model.ordinal();
    }
    public String getClassName (){
        return classType.name;
    }

    public String toString(){
        return name;
    }
}

/**
 * Created by lime on 15/3/10.
 */
public class ItemVo {
    public String type;
    public ItemModel model;
    public Integer id;
    public String value;
    public String itemName;

    public ItemVo(String itemName,String type, Integer id,String value){
        this.model=ItemModel.NONE;
        this.type=type;
        this.id=0;
        this.value="";
    }
    public ItemVo(String itemName,String type, Integer id,String value,ItemModel model){
        this.type=type;
        this.model=model;
        this.id=0;
        this.value="";
    }
    public Integer getModel (){
        return model.ordinal();
    }
    public String getType (){
        return type;
    }
    public String getItemName (){
        return itemName;
    }
    public Integer getId (){
        return id;
    }
    public String getValue (){
        return value;
    }


    public String toString(){
        return itemName;
    }
}

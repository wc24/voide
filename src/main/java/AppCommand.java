/**
 * Created by linming on 15-3-1.
 */
public class AppCommand extends VoideCommand {

    private AppProxy appProxy;
//    private StateProxy stateProxy;

    @Override
    protected void init() {
        appProxy = this.getProxy(AppProxy.class);
    }

    public void addClass() {
        appProxy.classIndex++;
        appProxy.classVoList.add(new ClassVo("CLASS" + appProxy.classIndex));
        command(VoideCommandKey.UP_CLASS_POOL);
        appProxy.update(AppProxyEnum.CALSS_LIST);
    }

    public void addItem() {
        if (appProxy.selectClass != null && (appProxy.editTarget==EditTarget.CLASS || appProxy.editTarget==EditTarget.ITEM ) ) {
            appProxy.itemIndex++;
            ItemVo itemVo = new ItemVo("item" + appProxy.itemIndex);
            itemVo.classType = appProxy.stringClassVo;
            itemVo.model = ItemModel.REQUIRED;
            appProxy.selectClass.itemVoList.add(itemVo);
            appProxy.update(AppProxyEnum.ITEM_LIST);
        }
    }

    public void addInstance() {

        if (appProxy.selectClass != null) {
            InstanceVo instanceVo = new InstanceVo(appProxy.selectClass,"instance" + appProxy.instanceIndex);
            appProxy.instanceVoList.add(instanceVo);
        }
        appProxy.update(AppProxyEnum.INSTANCE_LIST);
//        appProxy.classVoList.add(new ClassVo("CLASS"+ appProxy.classVoList.size()));
//        appProxy.update();
    }

    public void deleteSelected() {


        switch (appProxy.editTarget) {

            case ITEM:
                if (appProxy.selectClass.itemVoList.contains(appProxy.selectItem)) {
                    appProxy.selectClass.itemVoList.remove(appProxy.selectItem);
                    appProxy.selectItem = null;
                }
                appProxy.update(AppProxyEnum.ITEM_LIST);
                break;
            case INSTANCE:
                if (appProxy.instanceVoList.contains(appProxy.selectInstance)) {
                    appProxy.instanceVoList.remove(appProxy.selectInstance);
                    appProxy.selectInstance = null;
                }
                appProxy.update(AppProxyEnum.INSTANCE_LIST);
                break;
            case CLASS:
                if (appProxy.classVoList.contains(appProxy.selectClass)) {
                    appProxy.classVoList.remove(appProxy.selectClass);
                    appProxy.selectClass = null;
                }
                command(VoideCommandKey.UP_CLASS_POOL);
                appProxy.update(AppProxyEnum.CALSS_LIST);
                break;
        }
    }

    public void selectClass(ViewListData viewListData) {
        if (ClassVo.class.isInstance(viewListData)) {
            appProxy.editTarget = EditTarget.CLASS;
            appProxy.selectClass = (ClassVo) viewListData;
            appProxy.update(AppProxyEnum.ITEM_LIST);
            appProxy.update(AppProxyEnum.PREFERENCES);
        } else if (InstanceVo.class.isInstance(viewListData)) {
            appProxy.editTarget = EditTarget.INSTANCE;
            appProxy.selectInstance = (InstanceVo) viewListData;
            appProxy.update(AppProxyEnum.ARRTIBUTE_LIST);
            appProxy.update(AppProxyEnum.PREFERENCES);
        }
    }

    public void selectItem(ViewListData viewListData) {
        if (ItemVo.class.isInstance(viewListData)) {
            appProxy.editTarget = EditTarget.ITEM;
            appProxy.selectItem = (ItemVo) viewListData;
            appProxy.update(AppProxyEnum.PREFERENCES);
        } else if (AttributeVo.class.isInstance(viewListData)) {
            appProxy.editTarget = EditTarget.ATTRIBUTE;
            appProxy.selectAttribute = (AttributeVo) viewListData;
            appProxy.update(AppProxyEnum.PREFERENCES);

        }
    }

    public void select(Boolean isSelectClass) {
//        appProxy.isSelectClass=isSelectClass;
//        appProxy.update(AppProxyEnum.PREFERENCES);
    }

    public void selectItemClass(ClassVo classVo) {
        switch (appProxy.editTarget) {
            case ITEM:
                if (appProxy.selectItem != null) {
                    appProxy.selectItem.classType = classVo;
                }
                break;
            case INSTANCE:
                break;
            case ATTRIBUTE:
                break;
            case CLASS:
                break;
        }
    }




    public void upClassVoPool() {
        appProxy.classVoPool.clear();
        appProxy.classVoPool.put(appProxy.numberClassVo.name, appProxy.numberClassVo);
        appProxy.classVoPool.put(appProxy.stringClassVo.name, appProxy.stringClassVo);
        appProxy.classVoPool.put(appProxy.booleanClassVo.name, appProxy.booleanClassVo);
        for (ClassVo classVo : appProxy.classVoList) {
            appProxy.classVoPool.put(classVo.name, classVo);
        }
        appProxy.update(AppProxyEnum.CALSS_POOL);
    }

    public void selectItemModel(ItemModel itemModel) {
        switch (appProxy.editTarget) {
            case ITEM:
                appProxy.selectItem.model = itemModel;
                break;
            case INSTANCE:
                break;
            case ATTRIBUTE:
                break;
            case CLASS:
                break;
        }
    }


    public void reName(String value) {

        switch (appProxy.editTarget) {

            case ITEM:
                appProxy.selectItem.name = value;
                appProxy.update(AppProxyEnum.ITEM_NAME);
                break;
            case INSTANCE:
                appProxy.selectInstance.name = value;
                appProxy.update(AppProxyEnum.INSTANCE_NAME);
                break;
            case CLASS:
                appProxy.selectClass.name = value;
                command(VoideCommandKey.UP_CLASS_POOL);
                appProxy.update(AppProxyEnum.CALSS_NAME);
                break;
        }
    }

    public void changeAnnotation(String value) {

        switch (appProxy.editTarget) {

            case ITEM:
                appProxy.selectItem.annotation = value;
                break;
            case INSTANCE:
                appProxy.selectInstance.annotation = value;
                break;
            case ATTRIBUTE:
                appProxy.selectAttribute.annotation = value;
                break;
            case CLASS:
                appProxy.selectClass.annotation = value;
                break;
        }
    }

    public void changeId(String value) {
        switch (appProxy.editTarget) {
            case ITEM:
                appProxy.selectItem.id = value;
                break;
            case INSTANCE:
                appProxy.selectInstance.id = value;
                break;
            case ATTRIBUTE:
                appProxy.selectAttribute.id = value;
                break;
            case CLASS:
                appProxy.selectClass.id = value;
                break;
        }
    }

    public void changeValue(String value) {
        switch (appProxy.editTarget) {
            case ITEM:
                appProxy.selectItem.value = value;
                break;
            case INSTANCE:
                appProxy.selectInstance.value = value;
                break;
            case ATTRIBUTE:
                appProxy.selectAttribute.value = value;
                break;
            case CLASS:
                appProxy.selectClass.value = value;
                break;
        }

    }
}

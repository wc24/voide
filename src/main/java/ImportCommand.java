import javafx.stage.FileChooser;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lime on 15/3/16.
 */
public class ImportCommand extends VoideCommand {
    private AppProxy appProxy;
    private FileProxy fileProxy;

    @Override
    protected void init() {
        appProxy = getProxy(AppProxy.class);
        fileProxy = getProxy(FileProxy.class);
    }

    public void importExcel() {
        if (fileProxy.excelFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("import excel File");
            fileChooser.setInitialFileName(".xls");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("excel files (*.xls)", "*.xls"));
            File file = fileChooser.showOpenDialog(((AppCore) zero).stage);
            if (file != null) {
                fileProxy.excelFile = file;
            } else {
                return;
            }
        }
        command(VoideCommandKey.IMPORT, fileProxy.excelFile);

    }

    public void execute(File file) {
        if (file.isFile()) {

            String secondName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (secondName.equals("txt")) {

            } else if (secondName.equals("xml")) {

            } else if (secondName.equals("xls")) {
                importExcel(file);
            } else if (secondName.equals("vojson")) {

            }
        }
    }

    private void importExcel(File file) {


        fileProxy.excelFile = file;

        Workbook book;
        Sheet sheet;
        Cell cell;
        ClassVo classVo;
        ItemVo itemVo;
        InstanceVo instanceVo;
        AttributeVo attributeVo;
        InstanceItemVo instanceItemVo;
        try {
            book = Workbook.getWorkbook(file);

            //-----解类名
            appProxy.classVoPool.clear();
            appProxy.classVoPool.put(appProxy.numberClassVo.name, appProxy.numberClassVo);
            appProxy.classVoPool.put(appProxy.stringClassVo.name, appProxy.stringClassVo);
            appProxy.classVoPool.put(appProxy.booleanClassVo.name, appProxy.booleanClassVo);
            for (int sheetId = 1; sheetId < book.getNumberOfSheets(); sheetId++) {
                sheet = book.getSheet(sheetId);
                classVo = new ClassVo(sheet.getName());
                appProxy.classVoPool.put(classVo.name, new ClassVo(classVo.name));
            }

//-----解实例名
            appProxy.instanceVoPool.clear();
            appProxy.instanceVoList.clear();
            Sheet instanceSheet = book.getSheet(0);
            for (int rowId = 1; rowId < instanceSheet.getRows(); rowId++) {
                classVo = appProxy.classVoPool.get(instanceSheet.getCell(1, rowId).getContents());
                instanceVo = new InstanceVo(classVo, instanceSheet.getCell(0, rowId).getContents());
                appProxy.instanceVoPool.put(instanceVo.name, instanceVo);
            }

            for (Map.Entry<String, InstanceVo> entry : appProxy.instanceVoPool.entrySet()) {
                appProxy.instanceVoList.add(entry.getValue());
            }

//-----解属性名
            appProxy.classVoList.clear();
            for (int sheetId = 1; sheetId < book.getNumberOfSheets(); sheetId++) {
                sheet = book.getSheet(sheetId);
                classVo = appProxy.classVoPool.get(sheet.getName());
                classVo.annotation = sheet.getCell(1, 3).getContents();
                classVo.id = sheet.getCell(1, 4).getContents();
                classVo.value = sheet.getCell(1, 5).getContents();
                appProxy.classVoList.add(classVo);

                int startRowId = 8;
                Map<String, InstanceItemVo> indexMap = new HashMap<String, InstanceItemVo>();
                for (int rowId = startRowId + 1; rowId < sheet.getRows(); rowId++) {
                    if (!indexMap.containsKey(sheet.getCell(0, rowId).getContents())) {
                        instanceItemVo = new InstanceItemVo();
                        instanceItemVo.length = 1;
                        instanceItemVo.index = 0;
                        instanceItemVo.name = sheet.getCell(0, rowId).getContents();
                        instanceItemVo.instance = appProxy.instanceVoPool.get(sheet.getCell(1, rowId).getContents());
                        indexMap.put(sheet.getCell(0, rowId).getContents(), instanceItemVo);
                    } else {
                        indexMap.get(sheet.getCell(0, rowId).getContents()).length++;
                    }
                }
                for (Map.Entry<String, InstanceItemVo> entry : indexMap.entrySet()) {
                    attributeVo = new AttributeVo();
                    attributeVo.value = String.valueOf(entry.getValue().length);
                    attributeVo.name = entry.getKey() + ".length";
                    entry.getValue().instance.attributePool.put(attributeVo.name, attributeVo);

                }

                for (int columnId = 2; columnId < sheet.getColumns(); columnId++) {
                    itemVo = new ItemVo(sheet.getCell(columnId, 0).getContents());
                    itemVo.classType = appProxy.classVoPool.get(sheet.getCell(columnId, 1).getContents());
                    itemVo.model = ItemModel.valueOf(sheet.getCell(columnId, 2).getContents());
                    itemVo.annotation = sheet.getCell(columnId, 3).getContents();
                    itemVo.id = sheet.getCell(columnId, 4).getContents();
                    itemVo.value = sheet.getCell(columnId, 5).getContents();
                    classVo.itemVoList.add(itemVo);

                    if (itemVo.getClassName().equals("String") || itemVo.getClassName().equals("Number") || itemVo.getClassName().equals("Boolean")) {
                        for (int rowId = startRowId + 1; rowId < sheet.getRows(); rowId++) {
                            attributeVo = new AttributeVo();
                            instanceItemVo = indexMap.get(sheet.getCell(0, rowId).getContents());
                            if (instanceItemVo.length > 1) {
                                attributeVo.name = instanceItemVo.name + "[" + instanceItemVo.index + "]." + sheet.getCell(columnId, startRowId).getContents();
                                instanceItemVo.index++;
                                if (instanceItemVo.index == instanceItemVo.length) {
                                    instanceItemVo.index = 0;
                                }
                            } else {
                                attributeVo.name = instanceItemVo.name + "." + sheet.getCell(columnId, startRowId).getContents();
                            }
                            attributeVo.itemVo = itemVo;
                            attributeVo.value = sheet.getCell(columnId, rowId).getContents();
                            instanceVo = appProxy.instanceVoPool.get(sheet.getCell(1, rowId).getContents());
                            instanceVo.attributePool.put(attributeVo.name, attributeVo);
                        }
                    }
                }
            }

            appProxy.update(AppProxyEnum.CALSS_POOL);
            appProxy.update(AppProxyEnum.CALSS_LIST);


            //instanceSheet

//            instanceVoPool


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }


    }
}

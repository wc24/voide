import javafx.stage.FileChooser;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by lime on 15/3/14.
 */
public class ExportCommand extends VoideCommand {
    private AppProxy appProxy;
    private FileProxy fileProxy;
    private Map<String, Integer> instanceIndex;

    private WritableWorkbook book;

    @Override
    protected void init() {
        appProxy = this.getProxy(AppProxy.class);
        fileProxy = this.getProxy(FileProxy.class);

        instanceIndex = new HashMap<String, Integer>();
    }

    public void excel() {
        if (fileProxy.excelFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setInitialFileName(".xls");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("excel files (*.xls)", "*.xls"));
            File file = fileChooser.showSaveDialog(((AppCore) zero).stage);
            if (file != null) {
                fileProxy.excelFile = file;
            } else {
                return;
            }
        }

        try {
            book = Workbook.createWorkbook(fileProxy.excelFile);

            int sheetId = 0;
            int labelX = 0;
            int labelY = 0;


            WritableSheet instanceSheet = book.createSheet("instance", sheetId);

            for (ClassVo classVo : appProxy.classVoList) {
                sheetId++;
                instanceIndex.put(classVo.name, 9);
                WritableSheet classSheet = book.createSheet(classVo.name, sheetId);
                labelX = 0;
                classSheet.addCell(new Label(labelX, 0, "KEY"));
                classSheet.addCell(new Label(labelX, 1, "CLASS"));
                classSheet.addCell(new Label(labelX, 2, "MODEL"));
                classSheet.addCell(new Label(labelX, 3, "注解"));
                classSheet.addCell(new Label(labelX, 4, "ID"));
                classSheet.addCell(new Label(labelX, 5, "默认值"));
                labelX++;

                classSheet.addCell(new Label(labelX, 0, "类说明"));
                classSheet.addCell(new Label(labelX, 1, classVo.name));
//                classSheet.addCell(new Label(labelX, 2, "MODEL"));
                classSheet.addCell(new Label(labelX, 3, classVo.annotation));
                classSheet.addCell(new Label(labelX, 4, classVo.id));
                classSheet.addCell(new Label(labelX, 5, classVo.value));
                //classSheet.addCell(new Label(labelX, 8, "以下是数据实例"));
                for (ItemVo itemVo : classVo.itemVoList) {
                    labelX++;
                    classSheet.addCell(new Label(labelX, 0, itemVo.name));
                    classSheet.addCell(new Label(labelX, 8, itemVo.name));
                    classSheet.addCell(new Label(labelX, 1, itemVo.getClassName()));
                    classSheet.addCell(new Label(labelX, 2, itemVo.model.toString()));
                    classSheet.addCell(new Label(labelX, 3, itemVo.annotation));
                    classSheet.addCell(new Label(labelX, 4, itemVo.id));
                    classSheet.addCell(new Label(labelX, 5, itemVo.value));
                }


                classSheet.addCell(new Label(0, 8, "KEY"));
                classSheet.addCell(new Label(1, 8, "所属实例名"));
            }
            //book.getSheet()
            labelY = 0;
            instanceSheet.addCell(new Label(0, 0, "key"));
            instanceSheet.addCell(new Label(1, 0, "sheet"));
            instanceSheet.addCell(new Label(2, 0, "value"));
            for (InstanceVo instanceVo : appProxy.instanceVoList) {
                labelY++;
                instanceSheet.addCell(new Label(0, labelY, instanceVo.name));
                instanceSheet.addCell(new Label(1, labelY, instanceVo.classType.name));
                instanceSheet.addCell(new Label(2, labelY, instanceVo.name));
                addInstance(instanceVo, null, instanceVo.classType,-1);
            }
            book.write();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
    private void addInstance(InstanceVo instanceVo, String key, ClassVo classVo , int attIndex) throws WriteException {
        int labelY = 0;
        WritableSheet sheet = book.getSheet(classVo.name);
        if (sheet != null) {
            labelY = instanceIndex.get(classVo.name);
            instanceIndex.put(classVo.name, labelY + 1);
            int labelX = 1;
            String nextKey;
            if (key == null) {
//                sheet.addCell(new Label(0, labelY, instanceVo.name));
            } else {
                sheet.addCell(new Label(0, labelY, key));
            }
            sheet.addCell(new Label(1, labelY, instanceVo.name));

            for (ItemVo itemVo : classVo.itemVoList) {
                labelX++;
                if (itemVo.classType != null) {
                    if (key == null) {
                        nextKey = itemVo.name;
                    } else {
                        if (attIndex==-1){
                            nextKey = key + "." + itemVo.name;
                        }else {
                            nextKey = key+"["+attIndex + "]." + itemVo.name;
                        }
                    }
                    if (itemVo.model == ItemModel.REPEATED) {
                        sheet.addCell(new Label(labelX, labelY, nextKey));
                        AttributeVo attributeVo = instanceVo.attributePool.get(nextKey + ".length");
                        if (attributeVo != null && attributeVo.value != null) {
                            for (int i = 0; i < Tool.parseInt(attributeVo.value); i++) {
                                addInstance(instanceVo, nextKey, itemVo.classType,i);
                            }
                        }
                    } else {
                        AttributeVo attributeVo = instanceVo.attributePool.get(nextKey);

                        if (itemVo.getClassName() == "Boolean") {
                            if (attributeVo == null) {
                            } else {
                                sheet.addCell(new Label(labelX, labelY, attributeVo.value));
                            }
                        } else if (itemVo.getClassName() == "Number") {
                            if (attributeVo == null) {
                                sheet.addCell(new Number(labelX, labelY,0));
                            } else {
                                sheet.addCell(new Number(labelX, labelY, Tool.parseInt(attributeVo.value)));
                            }
                        } else if (itemVo.getClassName() == "String") {
                            if (attributeVo == null) {
                            } else {
                                sheet.addCell(new Label(labelX, labelY, attributeVo.value));
                            }
                        } else {
                            sheet.addCell(new Label(labelX, labelY, nextKey));
                        }
                        addInstance(instanceVo, nextKey, itemVo.classType,-1);
                    }
                }
            }
        }
    }
}

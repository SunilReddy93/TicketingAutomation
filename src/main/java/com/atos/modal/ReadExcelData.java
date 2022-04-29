package com.atos.modal;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadExcelData {

    private static File source;
    private FileInputStream fileInputStream;
    private XSSFSheet sheet1;
    private String path;

    public void setFilePath(File file){
        source = file;
    }

    public File getSource(){
        return source;
    }

    public XSSFWorkbook getWorkbook() throws Exception{
        fileInputStream = new FileInputStream(source);
        return new XSSFWorkbook(fileInputStream);
    }

    public void closeInputStream() throws IOException {
        fileInputStream.close();
    }

    public int getRowcount(XSSFSheet sheet1) {
        return sheet1.getLastRowNum();
    }

}

package com.atos.main;

import com.atos.gui.Complete;
import com.atos.gui.FileChooser;
import com.atos.gui.Login;
import com.atos.modal.AutomateLogic;
import com.atos.modal.ReadCSV;
import com.atos.modal.ReadExcelData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {


    public static void main(String[] args) throws Exception {



        FileChooser fileChooser = new FileChooser();
        ReadExcelData data = new ReadExcelData();
        Login login = new Login();
        AutomateLogic logic = new AutomateLogic();
        ReadCSV readCSV = new ReadCSV();


        if (!logic.getUsername().isEmpty() && !logic.getPassword().isEmpty()){

            if (data.getSource().toString().toLowerCase().contains(".csv")){
                readCSV.loadChrome();
                readCSV.login(logic.getUsername(), logic.getPassword());
                readCSV.readCSVData();
                readCSV.closeBrowser();
                //readCSV.closeFileOutputStream();
                Complete complete = new Complete();
            }else if (data.getSource().toString().toLowerCase().contains(".xls")){
                XSSFWorkbook workbook = data.getWorkbook();
                XSSFSheet sheet = workbook.getSheetAt(0);
                logic.loadChrome();
                logic.login(logic.getUsername(), logic.getPassword());
                logic.businessLogic(sheet, workbook);
                logic.closeFileOutputStream();
                data.closeInputStream();
                logic.closeBrowser();
                Complete complete = new Complete();
            }


        }

    }
}

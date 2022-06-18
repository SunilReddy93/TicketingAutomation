package com.atos.modal;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AutomateLogic {

    private WebDriver driver;
    private ReadExcelData getData = new ReadExcelData();
    private static String username;
    private static String password;
    private FileOutputStream fout;

    public void setUsername(String username1){
        username = username1;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password1){
        password = password1;
    }

    public String getPassword(){
        return password;
    }

    public void loadChrome(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    public void login(String username, String password){
        driver.get("#");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"fm1\"]/div[3]/input[4]")).click();


            //ServiceDesk
        driver.findElement(By.xpath("//div[starts-with(@id, 'txt') and contains(@title, '#')]")).click();


    }

    public void businessLogic(XSSFSheet sheet, XSSFWorkbook workbook) throws Exception {

        for (int i = 1; i <=getData.getRowcount(sheet) ; i++) {


            //Create
            driver.findElement(By.xpath("//*[@id=\"tabbar\"]/ul/li[2]/a")).click();
            //Keyword
            driver.findElement(By.xpath("/html/body/center/table[4]/tbody/tr[2]/td/div[1]/form/input")).sendKeys("LCH_SIEM_PTF");
            //Search
            driver.findElement(By.xpath("//*[@id=\"butt\"]/span/span")).click();
            //Click on create incident
            driver.findElement(By.xpath("/html/body/center/form[1]/table[5]/tbody/tr[3]/td[7]/div/a")).click();
            //Select Security Entity
            driver.findElement(By.xpath("/html/body/center[2]/div[1]/table[2]/tbody/tr[2]/td[2]/div/img")).click();

            //Description
            String desc = sheet.getRow(i).getCell(1).getStringCellValue();
            driver.findElement(By.xpath("/html/body/form[1]/center/div[6]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div[1]/textarea")).sendKeys(desc);

            //Subject
            String subject = sheet.getRow(i).getCell(0).getStringCellValue();
            driver.findElement(By.xpath("/html/body/form[1]/center/div[7]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div[1]/input")).sendKeys(subject);

            //Charging to
            WebElement element = driver.findElement(By.xpath("/html/body/form[1]/center/div[8]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td[2]/div[1]/select"));
            Select select = new Select(element);
            select.selectByValue("FM_CS");

            //Classification
            WebElement element1 = driver.findElement(By.xpath("/html/body/form[1]/center/div[8]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td[2]/div[1]/select"));
            Select select1 = new Select(element1);
            select1.selectByValue("05 - Security - Logical security");

            //Impact
            WebElement element2 = driver.findElement(By.xpath("/html/body/form[1]/center/div[8]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[5]/td[2]/div[1]/select"));
            Select select2 = new Select(element2);
            select2.selectByValue("Acceptable Degradation");

            //Urgency
            String urgency = sheet.getRow(i).getCell(6).getStringCellValue();
            WebElement element3 = driver.findElement(By.xpath("/html/body/form[1]/center/div[8]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[5]/td[4]/div[1]/select"));
            Select select3 = new Select(element3);
            switch (urgency.toLowerCase()) {
                case "high critical":
                    select3.selectByValue("High Critical");
                    break;
                case "mission critical":
                    select3.selectByValue("Mission Critical");
                    break;
                case "low critical":
                    select3.selectByValue("Low Critical");
                    break;
                case "medium critical":
                    select3.selectByValue("Medium Critical");
                    break;
            }

            //FLS Action
            WebElement element4 = driver.findElement(By.xpath("/html/body/form[1]/center/div[8]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td[4]/div[1]/select"));
            Select select4 = new Select(element4);
            select4.selectByValue("Incident initialized by another entity");

            //Category
            WebElement element5 = driver.findElement(By.xpath("/html/body/form[1]/center/div[8]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[2]/td[6]/div[1]/select"));
            Select select5 = new Select(element5);
            select5.selectByValue("Event");

            WebElement element6 = driver.findElement(By.xpath("/html/body/form[1]/center/div[8]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[2]/td[8]/div[1]/input"));
            if (!element6.isSelected())
                element6.click();

            //Get ISMP ID
            String id = driver.findElement(By.xpath("/html/body/form[1]/center/table[1]/tbody/tr[1]/td[1]")).getText();
            String toremove = "Incident Management n° ";

            sheet.getRow(i).createCell(7).setCellValue(Integer.parseInt(id.replace(toremove, "")));
            fout = new FileOutputStream(getData.getSource());
            workbook.write(fout);
            driver.findElement(By.xpath("/html/body/form[1]/center/div[2]/table/tbody/tr/td/button/span/span")).click();



        }

    }

    public void closeBrowser(){
        driver.close();
    }

    public void closeFileOutputStream() throws IOException {
        fout.close();
    }

}

package com.atos.modal;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReadCSV {

    private File file = new File(String.valueOf(new ReadExcelData().getSource()));
    private WebDriver driver;
    private FileOutputStream outputStream;
    private List<String[]> csvBody;

    public void loadChrome(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    public void login(String username, String password){
        driver.get("https://cas.itsm.worldline.com/login");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"fm1\"]/div[3]/input[4]")).click();


        //ServiceDesk
        driver.findElement(By.xpath("//div[starts-with(@id, 'txt') and contains(@title, 'https://servicedesk.itsm.worldline.com/cas.jsp?lang=en')]")).click();

    }

    public void readCSVData()  {


// Read existing file
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            csvBody = reader.readAll();
            // get CSV row column  and replace with by using row and column
            String data = csvBody.get(1)[1];


            for (int i = 1; i < csvBody.size(); i++) {

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
                String desc = csvBody.get(i)[1];
                driver.findElement(By.xpath("/html/body/form[1]/center/div[6]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div[1]/textarea")).sendKeys(desc);

                //Subject
                String subject = csvBody.get(i)[0];
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
                String urgency = csvBody.get(i)[6];
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
                csvBody.get(i)[7] = id.replace(toremove, "");


                driver.findElement(By.xpath("/html/body/form[1]/center/div[2]/table/tbody/tr/td/button/span/span")).click();


            }

            reader.close();

            // Write to CSV file which is open
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();



        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public void closeBrowser(){
        driver.close();
    }


}

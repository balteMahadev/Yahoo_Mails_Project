package com.mails.summery;

import com.opencsv.CSVWriter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Actions_Class implements InterfaceMethods {
    public static Properties properties;
    public static WebDriver driver;
    public static CSVWriter write;
    public static Logger logger = LogManager.getLogger("Actions_Class");
    int mail_start_index = 3;
    int Enter_Data_To_Csv_Start_Index = 0;
    List<String> mails_from = new ArrayList<>();
    List<String> Subjects = new ArrayList<>();
    List<String> date_and_time_of_mails = new ArrayList<>();
    Utilities validations = new Utilities();

    @Override
    public String File_Path(String file_Name) {
        String userDirectory = System.getProperty("user.dir");
        String pathSeparator = System.getProperty("file.separator");
        String filePaths = userDirectory + pathSeparator + "files" + pathSeparator + file_Name;
        String filePath = filePaths + "." + "properties";
        return filePath;
    }

    @Override
    @BeforeTest
    public void Read_Properties() {
        Actions_Class actions_class = new Actions_Class();
        properties = new Properties();
        String file = actions_class.File_Path("Properties");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            System.out.println(properties.getProperty("base_url"));
        } catch (FileNotFoundException e) {
            logger.error("File Not found:");
        } catch (IOException e) {
            logger.error("found null value");
        }
    }

    @Override
    public void Open_Browser(String url) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Override
    public void Read_Mails(WebDriver driver, String mails_Xpath) {
        List<WebElement> emails = driver.findElements(By.xpath(mails_Xpath));
        Boolean listResult =  validations.ValuesInList(emails);
        Assert.assertTrue(listResult);
        for (int i = mail_start_index; i <= emails.size(); i++) {
            System.out.println("-------------------------------------------------");
            try {
                String path = properties.getProperty("individual_mail_xpath_partial_1") + i + properties.getProperty("individual_mail_xpath_partial_2");
                driver.findElement(By.xpath(path)).click();
                String from = driver.findElement(By.xpath(properties.getProperty("mail_from"))).getText();
                mails_from.add(from);
               logger.info("From :" + from);
                String Subject = driver.findElement(By.xpath(properties.getProperty("mail_subject"))).getText();
                logger.info("Subject :" + Subject);
                Subjects.add(Subject);
                String DateAndTime = driver.findElement(By.xpath(properties.getProperty("mail_date_and_time"))).getText();
                logger.info("Time  :" + DateAndTime.split("at")[1]);
                date_and_time_of_mails.add(DateAndTime);
                WebElement back = driver.findElement(By.xpath(properties.getProperty("back_button_xpath")));
                back.click();

            } catch (InvalidSelectorException e)
            {
                logger.error("Please Check Element of list");
                Assert.assertTrue(false);
            } catch (NoSuchElementException e) {
                logger.info("Go Next");
            }
        }
    }

    @Override
    public void Close_Browser(WebDriver driver) {
        driver.quit();
    }

    @Override
    public void Write_Into_Csv() {
        String userDirectory = System.getProperty("user.dir");
        String pathSeparator = System.getProperty("file.separator");
        String filePaths = userDirectory + pathSeparator + "files" + pathSeparator + "sample";
        String filePath = filePaths + "." + "csv";

        {
            try {
                write = new CSVWriter(new FileWriter(filePath));
                for (int i = Enter_Data_To_Csv_Start_Index; i < mails_from.size(); i++) {
//                    String set[] = {"From  :-"+mails_from.get(i),"Subject  :-"+Subjects.get(i), "Date and Time  :-"+date_and_time_of_mails.get(i)};
                    String set1[] = {"From  :-" + mails_from.get(i), "Subject  :-" + Subjects.get(i), "Time  :-" + date_and_time_of_mails.get(i).split("at")[1]};
                    write.writeNext(set1);
                }
                write.flush();
            } catch (IOException e) {
                logger.error("CSV file not found");
            }
        }

    }
    public void takeScreenShot(String testCaseName,WebDriver driver)
    {

        String userDirectory = System.getProperty("user.dir");
        String pathSeparator = System.getProperty("file.separator");
        String filePaths = userDirectory + pathSeparator + "ScreenShots" + pathSeparator + testCaseName;
        String filePath = filePaths + "." + "png";
        TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source,new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

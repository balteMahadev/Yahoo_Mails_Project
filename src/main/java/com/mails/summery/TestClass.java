package com.mails.summery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestClass extends Actions_Class {
    Actions_Class actions_class = new Actions_Class();
    Utilities validations = new Utilities();
    public static Logger logger = LogManager.getLogger(TestClass.class);
    public static String userName = "tchmahadev@yahoo.com";
    public static String password = "*******";

    @Test(priority = 0)
    public void runUrlAndCheckPageTitle() {
        actions_class.Open_Browser(properties.getProperty("base_url"));
        Boolean getTitle = validations.PageTitle(driver.getTitle(), properties.getProperty("PageTitle"));
        if(getTitle)
        {
            logger.info("url lunched");
            logger.info("Title Matched");
        }
        else {
            logger.error("Title not matched");
            Assert.assertTrue(false);
        }
    }

    @Test(dependsOnMethods = {"runUrlAndCheckPageTitle"})
    public void EnterUserName() {
        String UserNameXpath = properties.getProperty("user_name_box_Xpath");
        try {
            Boolean UserNameBoxElement = validations.isElementPresent(driver.findElement(By.xpath(UserNameXpath)));
            Boolean EnterName = validations.valueInTextBox(driver.findElement(By.xpath(UserNameXpath)), userName);
            logger.info("User Name Entered is  :" + userName);
            Assert.assertTrue(UserNameBoxElement && EnterName);
        } catch (NoSuchElementException e) {
            logger.info("Test failed due to No Such Text User Text Box Element is Present");
            Assert.assertTrue(false);
        }catch (IllegalArgumentException e)
        {
            logger.error("Test failed due to illegal argument for user text box locator ");
        }
    }
    @Test(dependsOnMethods = {"EnterUserName"})
    public void CheckUserIsValidOrNot()
    {
        try {
            String UserValidXpath = properties.getProperty("UserValid");
            Boolean userNameValid = validations.WaitForElementIntractable(driver,By.xpath(UserValidXpath));
            if (userNameValid){
                logger.error("In valid User");
                Assert.assertFalse(userNameValid);
            }
        }
        catch (TimeoutException exception)
        {
           logger.info("Valid User"); Assert.assertFalse(false);
        }
    }
    @Test(dependsOnMethods = {"CheckUserIsValidOrNot"})
    public void EnterPassword() {
        try {
            String UserNameXpath = properties.getProperty("password_box_Xpath");
            Boolean UserNameBoxElement = validations.isElementPresent(driver.findElement(By.xpath(UserNameXpath)));
            Boolean EnterPassword = validations.valueInTextBox(driver.findElement(By.xpath(UserNameXpath)), password);
            logger.info("User Name Password is  :" + password);
            Assert.assertTrue(UserNameBoxElement && EnterPassword);
        } catch (NoSuchElementException e)
        {
                logger.error("Test failed due to No Such Password Text Box Element is  Present");
                Assert.assertTrue(false);
        }
        catch (IllegalArgumentException e)
        {
            logger.error("Test failed due to illegal argument for Password Text Box locator ");
        }
    }
    @Test(dependsOnMethods = {"EnterPassword"})
    public void CheckPasswordValidOrNot()
    {
        try {
            String passwordValidXpath = properties.getProperty("PasswordValid");
            Boolean  passwordValid = validations.WaitForElementIntractable(driver,By.xpath(passwordValidXpath));
            if (passwordValid)
            {
                logger.error("In valid password");
                Assert.assertFalse(passwordValid);
            }
        }
        catch (TimeoutException exception)
        {
            logger.info("Valid password"); Assert.assertFalse(false);
        }

    }
    @Test(dependsOnMethods = {"CheckPasswordValidOrNot"})
    public void ClickOnMailsButton() {
        String MailButtonXpathButton = properties.getProperty("go_to_mails");
        try {
            Boolean MailsButtonPresent = validations.WaitForElementIntractable(driver,By.xpath(MailButtonXpathButton));
            logger.info("Clicked on Mail icon");
            Assert.assertTrue(MailsButtonPresent);
        }
        catch (IllegalArgumentException e)
        {
            logger.error("Test failed due to illegal argument for Mail box locator ");
            Assert.assertTrue(false);
        }
        catch (Exception e) {
            logger.error("Test failed due No Such Mails Button Element is  Present");
            Assert.assertTrue(false);
        }
    }
    @Test(dependsOnMethods = {"ClickOnMailsButton"})
    public void Read_All_Mails()
    {
        actions_class.Read_Mails(driver,properties.getProperty("list_of_mails_xpath"));
        logger.info("Entered into all in inbox mails");
    }
    @Test(dependsOnMethods = {"Read_All_Mails"})
    public void EnterIntoCsvFile()
    {
        actions_class.Write_Into_Csv();
        logger.info("Data entered into csv file");
    }
    @AfterTest
    public void CloseBrowser()
    {
        actions_class.Close_Browser(driver);
        logger.info("Browser closed");
    }
}

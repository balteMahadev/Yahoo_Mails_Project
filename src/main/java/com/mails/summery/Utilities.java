package com.mails.summery;
/*     User Mahadev
       Date 25-jan-2022
       Des Utility class reusable methods
       */

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Utilities
{
    public Boolean isElementPresent(WebElement element)
    {

        if(element.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean  valueInTextBox(WebElement element,String text)
    {
        Boolean entered;
        if(isElementPresent(element) && text!=null)
        {
            element.click();
            element.sendKeys(text, Keys.ENTER);
            entered = true;
        }
        else
        {
            entered = false;
        }
        return entered;
    }
    public Boolean ValuesInList(List<WebElement> list)
    {
        if(list.size()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean PageTitle(String ExpectedTitle,String FoundTitle)
    {
        if(ExpectedTitle.equals(FoundTitle))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean WaitForElementIntractable(WebDriver driver , By by)
    {
        WebDriverWait wait = new WebDriverWait(driver,10);
        WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        if(element.isDisplayed())
        {
            element.click();
            return true;
        }
        else
        {
            return false;
        }
    }

}

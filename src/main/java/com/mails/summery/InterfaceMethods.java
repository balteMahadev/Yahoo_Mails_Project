package com.mails.summery;
/*User Mahadev
        #Date 25-jan-2022
        #Des Methods which has to be using in reading mails summery*/

import org.openqa.selenium.WebDriver;

public interface InterfaceMethods
{
    public String File_Path(String file_Name);
    public void Read_Properties();
    public void Open_Browser(String url);
    public void Read_Mails(WebDriver driver, String mails_Xpath);
    public void Close_Browser(WebDriver driver);
    public void Write_Into_Csv();

}

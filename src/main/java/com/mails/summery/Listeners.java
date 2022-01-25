package com.mails.summery;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners extends Actions_Class implements ITestListener
{
    @Override
    public void onTestFailure(ITestResult result)
    {
        String testMethodName =  result.getMethod().getMethodName();
         takeScreenShot(testMethodName,driver);

    }
}

package com.autotest.piugins.extent;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

/**
 * Created by youyong.li on 8/11/2017.
 */
public class ExtentReportsBase {
    private String sharedPath;
    private ExtentReports extent;
    private ExtentTest test;


    public String getSharedPath() {
        return sharedPath;
    }

    public void setSharedPath(String sharedPath) {
        this.sharedPath = sharedPath;
    }

    @BeforeSuite
    public void beforeSuite(){
    }

    @AfterSuite
    public void afterSuite(){
        extent.close();
    }

    @BeforeClass
    public void beforeClass(){
        extent = new ExtentReports(sharedPath + "extent.html", true, NetworkMode.OFFLINE);
        extent.loadConfig(ExtentReportsBase.class,  "extent-config.xml");
        extent
                .addSystemInfo("Host Name", "Anshoo")
                .addSystemInfo("Environment", "QA");
    }

    @AfterClass
    public void afterClass(){
        extent.flush();
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        test = extent.startTest(method.getAnnotation(Test.class).description());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            test.log(LogStatus.FAIL,result.getThrowable());
        }
        extent.endTest(test);
    }

    protected void setPassTest(String datails){
         test.log(LogStatus.PASS,datails);
    }

    protected void setFailTest(String datails){
        test.log(LogStatus.FAIL,datails);
    }

    protected void setInfoTest(String datails){
        test.log(LogStatus.INFO,datails);
    }

}

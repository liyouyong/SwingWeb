package com.autotest.test;


import com.autotest.driver.impl.WebAPI;
import com.autotest.utils.FileUtil;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class webDemo extends WebAPI {

    webDemo() {
        super();
    }

    @BeforeTest
    public void setUp(){
        String sharedPath = FileUtil.createDir("reports");
        setSharedPath(sharedPath);
    }

    @Test(priority =1, description ="搜索功能")
    public void test0(){
        startBrowser("chrome");
        get("https://www.baidu.com"); //
        maxWindow(); //
        sendKeys("//*[@id='kw']","0","java"); //
        click("//*[@id='su']","0"); //
        sleep("2");
        screenshotFull("searchjava"); //
        getText("//*[@id=\"u\"]/a[1]","0","baidu");
        quit(); //
    }

}
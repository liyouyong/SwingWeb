package com.autotest.test;

import com.autotest.driver.impl.WebAPI;
import com.autotest.utils.FileUtil;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by z1245 on 11/1/2017.
 */
public class getJenKinsUnitData extends WebAPI {
    public getJenKinsUnitData() {
        super();
    }
    @BeforeTest
    public void setUp(){
        String sharedPath = FileUtil.createDir("reports");
        setSharedPath(sharedPath);
    }

    @Test(priority =1, description ="Login server Jenkins")
    public void test0(){
        startBrowser("chromedriver.exe"); //
        get("http://10.58.1.46:8080"); //
        sendKeys("//input[@id='j_username']","0","techrefresh_readonly"); //
        sendKeys("//input[@name='j_password']","0","1234pass"); //
        click("//button[@id='yui-gen1-button']","0"); //
    }

    @Test(priority = 2, description = "get the data")
    public void test1() {
        get("http://10.58.1.46:8080/job/techrefresh-voucher-service-pipeline/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[2]/descendant::a","0");
        click("//a[contains(text(),'Coverage Report')]","0"); //
        sleep("5"); //
        screenshot("cucumber_report"); //
        String Packages = getText("//*[@id=\"main-panel\"]/table[1]/tbody/tr[2]/td[2]/table/tbody/tr/td[1]", "0", "Packages");
        String Files = getText("//*[@id=\"main-panel\"]/table[1]/tbody/tr[2]/td[3]/table/tbody/tr/td[1]", "0", "Files");
        String Classes = getText("//*[@id=\"main-panel\"]/table[1]/tbody/tr[2]/td[4]/table/tbody/tr/td[1]", "0", "Classes");
        String Methods = getText("//*[@id=\"main-panel\"]/table[1]/tbody/tr[2]/td[5]/table/tbody/tr/td[1]", "0", "Methods");
        String Lines = getText("//*[@id=\"main-panel\"]/table[1]/tbody/tr[2]/td[6]/table/tbody/tr/td[1]", "0", "Lines");
        String Conditionals = getText("//*[@id=\"main-panel\"]/table[1]/tbody/tr[2]/td[7]/table/tbody/tr/td[1]", "0", "Conditionals");
        System.out.println("Packages:" + Packages + "Files:" + Files + "Classes:" +Classes + "Methods:" + Methods + "Lines:" + Lines + "Conditionals:" +Conditionals);
    }

    @Test(priority =3, description ="Logout server Jenkins")
    public void test2(){
        quit(); //
    }

}

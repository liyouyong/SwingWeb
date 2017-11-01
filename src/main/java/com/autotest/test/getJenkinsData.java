package com.autotest.test;

import com.autotest.driver.impl.WebAPI;
import com.autotest.piugins.email.SendEmailByUI;
import com.autotest.utils.ExcelUtil;
import com.autotest.utils.FileUtil;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by youyong.li on 10/26/2017.
 */
public class getJenkinsData extends WebAPI{
    private String patronRating ="0%";
    private String itamdinRating ="0%";
    private String loyaltyRating ="0%";
    private String creditControlRating ="0%";
    private String promotionRating = "0%";
    private String someUI ="0%";
    private String allUI ="0%";
    private String all = "0%";
    private String filePath = "D:\\Users\\z1115\\Track 3 QA Dashboard.xlsx";

    getJenkinsData() {
        super();
    }

    @BeforeTest
    public void setUp(){
        String sharedPath = FileUtil.createDir("reports");
        setSharedPath(sharedPath);
    }

    @Test(priority =1, description ="Login local Jenkins")
    public void test0(){
        startBrowser("chrome"); //
        get("http://localhost:8089"); //
        sendKeys("//input[@id='j_username']","0","liyouyong"); //
        sendKeys("//input[@name='j_password']","0","123456"); //
        click("//button[@id='yui-gen1-button']","0"); //
    }

    @Test(priority =2, description ="UI Automation Some")
    public void test1(){
        get("http://localhost:8089/job/sit-some-ui-automation/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]","0"); //
        sleep("5"); //
        someUI = getText("//*[@id='tablesorter']/tfoot/tr[2]/td[12]","0","cucumber_result"); //
        screenshot("cucumber_report"); //
    }

    @Test(priority =3, description ="UI Automation All")
    public void test2(){
        get("http://localhost:8089/job/sit-all-ui-automation/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]","0"); //
        sleep("5"); //
        allUI = getText("//*[@id='tablesorter']/tfoot/tr[2]/td[12]","0","cucumber_result"); //
        screenshot("cucumber_report"); //
    }

    @Test(priority =4, description ="Logout local Jenkins")
    public void test3(){
        quit(); //
    }

    @Test(priority =5, description ="Login Server Jenkins")
    public void test4(){
        startBrowser("chrome"); //
        get("http://10.58.1.46:8080"); //
        sendKeys("//input[@id='j_username']","0","techrefresh_readonly"); //
        sendKeys("//input[@name='j_password']","0","1234pass"); //
        click("//button[@id='yui-gen1-button']","0"); //
    }

    @Test(priority =6, description ="API Automation Patron")
    public void test5(){
        get("http://10.58.1.46:8080/job/dev-techrefresh-api-testing-Patron/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]","0"); //
        sleep("5"); //
        patronRating = getText("//*[@id='tablesorter']/tfoot/tr[2]/td[12]","0","cucumber_result"); //
        screenshot("cucumber_report"); //
    }

    @Test(priority =7, description ="API Automation Itadmin")
    public void test6(){
        get("http://10.58.1.46:8080/job/dev-techrefresh-api-testing-Itadmin/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]","0"); //
        sleep("5"); //
        itamdinRating = getText("//*[@id='tablesorter']/tfoot/tr[2]/td[12]","0","cucumber_result"); //
        screenshot("cucumber_report"); //
    }

    @Test(priority =8, description ="API Automation Loyalty")
    public void test7(){
        get("http://10.58.1.46:8080/job/dev-techrefresh-api-testing-Loyalty/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]","0"); //
        sleep("5"); //
        loyaltyRating = getText("//*[@id='tablesorter']/tfoot/tr[2]/td[12]","0","cucumber_result"); //
        screenshot("cucumber_report"); //
    }

    @Test(priority =9, description ="API Automation CreditControl")
    public void test8(){
        get("http://10.58.1.46:8080/job/dev-techrefresh-api-testing-CreditControl/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]","0"); //
        sleep("5"); //
        creditControlRating = getText("//*[@id='tablesorter']/tfoot/tr[2]/td[12]","0","cucumber_result"); //
        screenshot("cucumber_report"); //
    }

    @Test(priority =10, description ="API Automation Promotion")
    public void test9(){
        get("http://10.58.1.46:8080/job/dev-techrefresh-api-testing-Promotion-new/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]","0"); //
        sleep("5"); //
        promotionRating = getText("//*[@id='tablesorter']/tfoot/tr[2]/td[12]","0","cucumber_result"); //
        screenshot("cucumber_report"); //
    }

    @Test(priority =11, description ="logout")
    public void test10(){
        quit(); //
    }

    @Test(priority = 12, description ="export excel")
    public void test11() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(new Date());
        all = String.valueOf((int) Math.round((Double.valueOf(patronRating.replace("%","")) +
                Double.valueOf(itamdinRating.replace("%","")) +
                Double.valueOf(loyaltyRating.replace("%","")) +
                Double.valueOf(creditControlRating.replace("%","")) +
                Double.valueOf(promotionRating.replace("%","")))/5)) + "%";
        String[] ages = {date,patronRating,itamdinRating,loyaltyRating,creditControlRating,promotionRating,all,someUI,allUI};
        ExcelUtil.createTrackQA(filePath,ages);
    }

    @Test(priority =13, description ="send email")
    public void test12(){
        SendEmailByUI.send(filePath);
    }
}

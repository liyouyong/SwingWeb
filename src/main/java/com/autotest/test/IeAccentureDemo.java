package com.autotest.test;

import com.autotest.driver.impl.WebAPI;
import com.autotest.utils.FileUtil;
import com.autotest.utils.WinUtil;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by z1245 on 11/17/2017.
 */
public class IeAccentureDemo extends WebAPI {

    public IeAccentureDemo() {
        super();
    }

    @BeforeTest
    public void setUp() {
        String sharedPath = FileUtil.createDir("reports");
        setSharedPath(sharedPath);
    }

    @Test(priority =1, description ="go to page")
    public void test1() {
        startBrowser("ie");
        get("https://emtpm-internal1.accenture.com/SaaSEM02WebClientLogin/cpwerx.aspx?formslogin=true&DebugWindow=true&DebugWindowChannelID=Consolidation");
        sleep("10");
    }

    @Test(priority =2, description ="...")
    public void test2() throws Exception {
        Robot robot = new Robot();
        Dimension dimension =  WinUtil.getResolutionRatio();
        System.out.println(dimension.getHeight());
        System.out.println(dimension.getWidth());
        //Superuser1
        /*int s[] = {KeyEvent.VK_CAPS_LOCK,KeyEvent.VK_S,KeyEvent.VK_CAPS_LOCK,KeyEvent.VK_U,KeyEvent.VK_P,
                KeyEvent.VK_E,KeyEvent.VK_R,KeyEvent.VK_U,KeyEvent.VK_S,KeyEvent.VK_E
                ,KeyEvent.VK_R,KeyEvent.VK_1};*/
//        WinUtil.clickLMouse(robot, Integer.parseInt(String.valueOf(dimension.getWidth()*1/2)), Integer.parseInt(String.valueOf(dimension.getHeight()*0.56)), 2);
        int tab[] = {KeyEvent.VK_TAB};
        int enter[] = {KeyEvent.VK_ENTER};
        WinUtil.clickLMouse(robot, Integer.parseInt(String.valueOf(Math.round(dimension.getWidth()*0.5))), Integer.parseInt(String.valueOf(Math.round(dimension.getHeight()*0.56))), 200);
        WinUtil.copyDataToClipBoard("Superuser1");
        WinUtil.doParse(robot);
        /*WinUtil.pressKeys(robot,tab, 200);*/
        WinUtil.clickLMouse(robot, Integer.parseInt(String.valueOf(Math.round(dimension.getWidth()*0.5))), Integer.parseInt(String.valueOf(Math.round(dimension.getHeight()*0.6))), 200);
        WinUtil.copyDataToClipBoard("Demo2018!");
        WinUtil.doParse(robot);
        //click the "login" button
        WinUtil.clickLMouse(robot, Integer.parseInt(String.valueOf(Math.round(dimension.getWidth()*0.57))), Integer.parseInt(String.valueOf(Math.round(dimension.getHeight()*0.65))), 200);
        /*WinUtil.pressKeys(robot,a, 200);
        WinUtil.pressKeys(robot,a, 200);
        WinUtil.pressKeys(robot,a, 200);
        WinUtil.pressKeys(robot,a, 200);
        WinUtil.pressKeys(robot,a, 200);
        WinUtil.pressKeys(robot,enter,200);*/

    }

}

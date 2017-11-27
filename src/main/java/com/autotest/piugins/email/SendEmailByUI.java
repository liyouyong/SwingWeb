package com.autotest.piugins.email;

import com.autotest.utils.WinUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by youyong.li on 11/1/2017.
 */
public class SendEmailByUI {

    public static void send(String filePath){
        try{
            final Robot rb = new Robot();
            // 设置开始菜单的大概位置
            int x = 40;
            int y = Toolkit.getDefaultToolkit().getScreenSize().height - 10;

            // 鼠标移动到开始菜单
            rb.mouseMove(x, y);
            rb.delay(500);

            // 单击开始菜单
            WinUtil.clickLMouse(rb, x, y, 500);
            rb.delay(1000);

            int[] enter = {KeyEvent.VK_ENTER};
//        int[] ctrlA = {KeyEvent.VK_CONTROL, KeyEvent.VK_A};
//        int[] ctrlC = {KeyEvent.VK_CONTROL, KeyEvent.VK_V};

            // 启动outlook
            int[] ks = { KeyEvent.VK_O, KeyEvent.VK_U,KeyEvent.VK_T,KeyEvent.VK_L,KeyEvent.VK_O,KeyEvent.VK_O,
                    KeyEvent.VK_K, KeyEvent.VK_ENTER};
            WinUtil.pressKeys(rb, ks, 500);
            rb.delay(10000);

            // 单击新建邮件
            WinUtil.clickLMouse(rb, 40, 100, 500);
            rb.delay(3000);

            // 输入邮件标题
            WinUtil.clickLMouse(rb, 159, 225, 500);
            WinUtil.copyDataToClipBoard("Track 3 QA Dashboard");
            WinUtil.doParse(rb);

            WinUtil.clickLMouse(rb, 166, 166, 500);
//            WinUtil.copyDataToClipBoard("youyong.li@accenture.com");
            WinUtil.copyDataToClipBoard("leo.k.yung@accenture.com; daniel.w.leung@accenture.com; youyong.li@accenture.com");
            WinUtil.doParse(rb);

            WinUtil.clickLMouse(rb, 265, 198, 500);
//            WinUtil.copyDataToClipBoard("youyong.li@accenture.com");
            WinUtil.copyDataToClipBoard("jack.guangjing.chen@accenture.com; dennis.k.lau@accenture.com; boriswong@melco-resorts.com; gary.k.leung@accenture.com; yong.tan@accenture.com; ruoli.chen@accenture.com");
            WinUtil.doParse(rb);

            WinUtil.clickLMouse(rb,300,300,500);
            WinUtil.copyDataToClipBoard("Hi Team,");
            WinUtil.doParse(rb);
            WinUtil.pressKeys(rb, enter, 500);
            WinUtil.pressKeys(rb, enter, 500);
            WinUtil.copyDataToClipBoard("The Track3 QA Dashboard as below, please check. Thanks!");
            WinUtil.doParse(rb);
            WinUtil.pressKeys(rb, enter, 500);
            WinUtil.pressKeys(rb, enter, 500);

            //click the menu
            rb.mouseMove(x, y);
            rb.delay(500);
            WinUtil.clickLMouse(rb, x, y, 500);
            rb.delay(1000);
            WinUtil.copyDataToClipBoard(filePath);
            WinUtil.doParse(rb);
            WinUtil.pressKeys(rb, enter, 500);
            rb.delay(3000);
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_A);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_A);
            WinUtil.doCopy(rb);
            WinUtil.altTab(rb,1000);
            WinUtil.clickLMouse(rb,1328, 608, 500);
            WinUtil.doParse(rb);
            WinUtil.pressKeys(rb, enter, 500);
            WinUtil.copyDataToClipBoard("Best Regards,");
            WinUtil.doParse(rb);
            WinUtil.pressKeys(rb, enter, 500);
            WinUtil.copyDataToClipBoard("Youyong");
            WinUtil.doParse(rb);

            //add file
//            WinUtil.clickLMouse(rb, 562, 90, 500);
//            WinUtil.copyDataToClipBoard(filePath);
//            WinUtil.doParse(rb);
//            rb.keyPress(KeyEvent.VK_ENTER);
//            rb.keyRelease(KeyEvent.VK_ENTER);
//            rb.delay(2000);
//            send email
//            WinUtil.clickLMouse(rb, 32, 200, 500);  //
//            //close excel
//            WinUtil.altTab(rb,1000);
//            rb.delay(1000);
//            WinUtil.clickLMouse(rb, 1520, 10, 500);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        String filePath = "D:\\Users\\z1115\\Track 3 QA Dashboard.xlsx";
        SendEmailByUI.send(filePath);
//        try{
//            Robot rb = new Robot();
////            int[] ks = { KeyEvent.VK_CONTROL, KeyEvent.VK_ALT,KeyEvent.VK_DELETE};
////            WinUtil.pressKeys(rb, ks, 500);
//            int x = 40;
//            int y = Toolkit.getDefaultToolkit().getScreenSize().height - 10;
//
//            // 鼠标移动到开始菜单
//            rb.mouseMove(x, y);
//            rb.delay(500);
//
//            // 单击开始菜单
//            WinUtil.clickLMouse(rb, x, y, 500);
//            rb.delay(1000);
////
//            WinUtil.clickLMouse(rb, 343, 706, 500);
//            rb.delay(1000);
//
//            WinUtil.clickLMouse(rb, 403, 654, 500);
//            rb.delay(1000);
//            rb.setAutoDelay(500);
//            Thread.sleep(2000);
//            rb.keyPress(KeyEvent.VK_CONTROL);
//            rb.keyPress(KeyEvent.VK_ALT);
//            rb.keyPress(KeyEvent.VK_INSERT);
//            rb.keyRelease(KeyEvent.VK_CONTROL);
//            rb.keyRelease(KeyEvent.VK_ALT);
//            rb.keyRelease(KeyEvent.VK_INSERT);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }
}

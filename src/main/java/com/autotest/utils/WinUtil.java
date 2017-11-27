package com.autotest.utils;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by youyong.li on 11/1/2017.
 */
public class WinUtil {
    /**
     * 鼠标单击（左击）
     */
    public static void clickLMouse(Robot r, int x, int y, int delay) {
        r.mouseMove(x, y);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.delay(10);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(delay);
    }

    public static void altTab(Robot r, int delay) throws Exception {
        Thread.sleep(3000);
        r.setAutoDelay(200);
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(KeyEvent.VK_TAB);
        r.keyRelease(KeyEvent.VK_ALT);
        r.keyRelease(KeyEvent.VK_TAB);
    }

    /**
     * 鼠标单击（右击）
     *
     */
    public static void clickRMouse(Robot r, int x, int y, int delay) {
        r.mouseMove(x, y);
        r.mousePress(InputEvent.BUTTON3_MASK);
        r.delay(10);
        r.mouseRelease(InputEvent.BUTTON3_MASK);
        r.delay(delay);
    }

    /**
     * 键盘输入（一次只能输入一个字符）
     *
     */
    public static void pressKeys(Robot r, int[] ks, int delay) {
        for (int i = 0; i < ks.length; i++) {
            r.keyPress(ks[i]);
            r.delay(10);
            r.keyRelease(ks[i]);
            r.delay(delay);
        }
    }

    /**
     * 将数据复制到剪切板
     *
     */
    public static void copyDataToClipBoard(Object obj) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection ss = new StringSelection(obj.toString() );
        clipboard.setContents(ss, null);
    }

    /**
     * 复制
     *
     */
    public static void doCopy(Robot r) throws InterruptedException {
        Thread.sleep(3000);
        r.setAutoDelay(1000);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_C);
        r.keyRelease(KeyEvent.VK_C);
        r.keyRelease(KeyEvent.VK_CONTROL);
    }

    /**
     * 粘贴
     *
     */
    public static void doParse(Robot r) throws InterruptedException {
        r.setAutoDelay(500);
        Thread.sleep(2000);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
    }

    /**
     * 捕捉全屏慕
     *
     */
    public Icon captureFullScreen(Robot r) {
        BufferedImage fullScreenImage = r.createScreenCapture(new Rectangle(
                Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIcon icon = new ImageIcon(fullScreenImage);
        return icon;
    }

    /**
     * 捕捉屏幕的一个矫形区域
     *
     */
    public Icon capturePartScreen(Robot r, int x, int y, int width, int height) {
        r.mouseMove(x, y);
        BufferedImage fullScreenImage = r.createScreenCapture(new Rectangle(
                width, height));
        ImageIcon icon = new ImageIcon(fullScreenImage);
        return icon;
    }

    public static Dimension getResolutionRatio() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        return screenSize;
    }


}

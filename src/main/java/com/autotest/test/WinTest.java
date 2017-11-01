package com.autotest.test;

import com.autotest.utils.WinUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by youyong.li on 11/1/2017.
 */
public class WinTest {
    public static void main(String[] args) throws Exception {

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

        // 启动outlook
        int[] ks = { KeyEvent.VK_O, KeyEvent.VK_U,KeyEvent.VK_T,KeyEvent.VK_L,KeyEvent.VK_O,KeyEvent.VK_O,
                KeyEvent.VK_K, KeyEvent.VK_ENTER};
        WinUtil.pressKeys(rb, ks, 500);
        rb.delay(10000);

        // 单击新建邮件
        WinUtil.clickLMouse(rb, 40, 100, 500);
        rb.delay(3000);

        // 输入邮件标题
        WinUtil.clickLMouse(rb, 1030, 306, 500);
        WinUtil.copyDataToClipBoard("outlook");
        WinUtil.doParse(rb);

        JOptionPane.showMessageDialog(null, "演示完毕!");
    }
}

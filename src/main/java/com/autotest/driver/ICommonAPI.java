package com.autotest.driver;

import org.openqa.selenium.WebElement;

import java.sql.Connection;

/**
 * Created by youyong.li on 9/8/2017.
 */
public interface ICommonAPI {

    /**
     * 查找元素
     * @param element
     * @param index
     * @return
     */
    WebElement findElement(String element, int index);

    /**
     * 查找元素
     * @param element
     * @param index
     * @return
     */
    WebElement getElement(String element, int index);


    /**
     * 获取参数化数据
     * @param key
     * @return
     */
    String getValue(String key);

    /**
     * 关键字
     * 加载URL
     * @param url
     * @return
     */
    void get(String url);

    /**
     * 关键字
     * 关闭浏览器但不清理session
     */
    void close();

    /**
     * 关键字
     * 关闭浏览器并清理session
     */
    void quit();

    /**
     * 关键字
     * selemnium自带隐性等待时间
     * @param second 秒
     */
    void wait(String second);

    /**
     * 关键字
     * java自带强制等待时间
     * @param second 秒
     */
    void sleep(String second);

    /**
     * 关键字
     * 获取url
     * @return
     */
    String getCurrentUrl(String key);

    /**
     * 关键字
     * 获取title
     * @return
     */
    String getTitle(String key);

    /**
     * 关键字
     * 获取text
     * @param element
     * @param index
     * @return
     */
    String getText(String element, String index, String key);

    /**
     * 关键字
     * 获取attribute
     * @param element
     * @param index
     * @param attributeName
     * @return
     */
    String getAttribute(String element, String index, String attributeName, String key);

    /**
     * 关键字
     * 普通输入操作
     * @param element
     * @param index
     * @param value
     */
    void sendKeys(String element, String index, String value);

    /**
     * 关键字
     * 键盘输入操作
     * @param element
     * @param index
     * @param keys
     */
    void sendKeysAction(String element, String index, String keys);

    /**
     * 关键字
     * 普通点击操作
     * @param element
     * @param index
     */
    void click(String element, String index);

    /**
     * 关键字
     * 双击操作
     * @param element
     * @param index
     */
    void clickDouble(String element, String index);

    /**
     * 关键字
     * 清除数据
     * @param element
     * @param index
     */
    void clear(String element, String index);

    /**
     * 关键字
     * 浏览器最大化
     */
    void maxWindow();

    /**
     * 关键字
     * 根据value值选择select
     * @param element
     * @param index
     * @param value
     */
    void selectByValue(String element, String index, String value);

    /**
     * 关键字
     * 根据value值选择select
     * @param element
     * @param index
     * @param text
     */
    void selectByText(String element, String index, String text);

    /**
     * 关键字
     * 切换到特定frame
     * @param element
     * @param index
     */
    void switchToFrame(String element, String index);

    /**
     * 关键字
     * 切换到默认特定frame
     */
    void switchToDefaultFrame();

    /**
     * 关键字
     * 切换到指定window
     */
    void switchToWindow(int i);

    /**
     * 关键字
     * 手动全屏截图
     * @param name
     */
    String screenshotFull(String name);

    /**
     * 关键字
     * 手动部分截图
     * @param element
     * @param index
     * @param name
     */
    String screenshotPart(String element, String index, String name);

    /**
     * 关键字
     * 手动部分截图
     * @param name
     */
    void screenshot(String name);

    /**
     * 关键字
     * 断言是否部分包含
     * @param except
     * @param actual
     */
    void verifyContainEquals(String except, String actual);

    /**
     * 关键字
     * 断言是否相等
     * @param except
     * @param actual
     */
    void verifyEquals(String except, String actual);

    /**
     * 关键字
     * 断言是否不等
     * @param except
     * @param actual
     */
    void verifyNotEquals(String except, String actual);

    /**
     * 关键字
     * 自定义变量
     * @param value
     * @param key
     */
    void defineVariable(String value, String key);

    /**
     * 获取图片名称
     * @return
     */
    String getPngName();

    /**
     * 截图
     * @return
     */
    String snapshot();

    /**
     * 设置详细信息
     * @return
     */
    String setDetails(String keyword, String[] args);

    /**
     * 图片对比
     * @return
     */
    void compareImage(String source, String target);

    /**
     * 关键字
     * mysql连接数据库
     * @param host
     * @param database
     * @param user
     * @param password
     * @return
     */
    Connection mysqlConnect(String type, String host, String database, String user, String password);

    /**
     * 关键字
     * mysql断开数据库
     */
    void mysqlDisconnect();

    /**
     * 关键字
     * mysql数据操作
     */
    void mysqlOperation(String sql);
}

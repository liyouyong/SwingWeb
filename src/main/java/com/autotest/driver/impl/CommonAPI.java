package com.autotest.driver.impl;

import com.autotest.driver.ICommonAPI;
import com.autotest.piugins.extent.ExtentReportsBase;
import com.autotest.utils.DateUtil;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by youyong.li on 9/8/2017.
 */
public class CommonAPI extends ExtentReportsBase implements ICommonAPI {
    private Map<String,String> map = new HashMap<>();
    private WebDriver driver;
    private Connection conn = null;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * 查找元素
     * @param element
     * @param index
     * @return
     */
    public WebElement findElement(final String element, final int index) {
        WebElement webElement=null;
        try {
            webElement=(new WebDriverWait(driver, 30,3000)).until(
                    new ExpectedCondition<WebElement>() {

                        @Override
                        public WebElement apply(WebDriver driver) {
                            return getElement(element,index);
                        }
                    });
        } catch (Exception e) {
            throw e;
        }
        return webElement;
    }

    @Override
    public WebElement getElement(String element, int index) {
        WebElement webElement=null;
        try{
            webElement= (WebElement) driver.findElements(By.xpath(element)).get(index);
        }catch (Exception e){
        }
        return webElement;
    }

    @Override
    public String getValue(String key) {
        String pattern = "\\$\\{(.*)\\}";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(key);
        if(mat.find()){
            String value = map.get(mat.group(1));
            if(value == null){
                return key;
            }
            else {
                return value;
            }
        }
        else {
            return key;
        }
    }

    @Override
    public void get(String url) {
        String datails = setDetails("get", new String[]{url});
        try {
            driver.get(getValue(url));
            setPassTest(datails);
        }catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void close() {
        String datails = setDetails("close", new String[]{});
        try{
            driver.close();
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+e.getMessage());
            throw e;
        }
    }

    @Override
    public void quit() {
        String datails = setDetails("quit", new String[]{});
        try{
            driver.quit();
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+e.getMessage());
            throw e;
        }
    }

    @Override
    public void wait(String second) {
        String datails = setDetails("wait", new String[]{String.valueOf(second)});
        try{
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(second), TimeUnit.SECONDS);
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void sleep(String second) {
        String datails = setDetails("sleep", new String[]{String.valueOf(second)});
        try {
            Thread.sleep(Integer.parseInt(second)*1000);
            setPassTest(datails);
        } catch (InterruptedException e) {
            setFailTest(datails+ snapshot());
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentUrl(String key) {
        String datails = setDetails("getCurrentUrl", new String[]{key});
        try {
            String url=driver.getCurrentUrl();
            map.put(key,url);
            setPassTest(datails+"<br><span style=\"font-weight:bold;color: green\">Url:"+url+"</span>");
            return url;
        }catch (Exception e){
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public String getTitle(String key) {
        String datails = setDetails("getTitle", new String[]{key});
        try {
            String title=driver.getTitle();
            map.put(key,title);
            setPassTest(datails+"<br><span style=\"font-weight:bold;color: green\">Title:"+title+"</span>");
            return title;
        }catch (Exception e){
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public String getText(String element, String index, String key) {
        String datails = setDetails("getText", new String[]{element,index,key});
        String text;
        sleep("2");
        try {
            WebElement webElement=findElement(element, Integer.parseInt(index));
            text = webElement.getText();
            map.put(key,text);
            setPassTest(datails+"<br><span style=\"font-weight:bold;color: green\">Text:"+text+"</span>");
            return text;
        } catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public String getAttribute(String element, String index, String attributeName, String key) {
        String datails = setDetails("getAttribute", new String[]{element,index,attributeName,key});
        String text;
        sleep("2");
        try {
            WebElement webElement=findElement(element, Integer.parseInt(index));
            text = webElement.getAttribute(attributeName);
            map.put(key,text);
            setPassTest(datails+"<br><span style=\"font-weight:bold;color: green\">Attribute:"+text+"</span>");
            return text;
        } catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void sendKeys(String element, String index, String value) {
        String datails = setDetails("sendKeys", new String[]{element,index,value});
        try {
            WebElement webElement=findElement(element, Integer.parseInt(index));
            String result = getValue(value);
            webElement.sendKeys(result);
            setPassTest(datails);
        } catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void sendKeysAction(String element, String index, String keys) {
        String datails = setDetails("sendKeysAction", new String[]{element,index, String.valueOf(keys)});
        try {
            WebElement weElement = findElement(element, Integer.parseInt(index));
            Actions actions = new Actions(driver);
            actions.sendKeys(weElement, changeKyes(keys)).perform();
            setPassTest(datails);
        } catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void click(String element, String index) {
        String datails = setDetails("click", new String[]{element,index});
        try {
            WebElement webElement=findElement(element, Integer.parseInt(index));
            webElement.click();
            setPassTest(datails);
        } catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void clickDouble(String element, String index) {
        String datails = setDetails("clickDouble", new String[]{element,index});
        try {
            WebElement weElement = findElement(element, Integer.parseInt(index));
            Actions action = new Actions(driver);
            action.doubleClick(weElement).perform();
            setPassTest(datails);
        } catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void clear(String element, String index) {
        String datails = setDetails("clear", new String[]{element,index});
        try {
            WebElement webElement=findElement(element, Integer.parseInt(index));
            webElement.clear();
            setPassTest(datails);
        } catch (Exception e) {
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void maxWindow() {
        String datails = setDetails("maxWindow", new String[]{});
        try{
            driver.manage().window().maximize();
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void selectByValue(String element, String index, String value) {

    }

    @Override
    public void selectByText(String element, String index, String text) {

    }

    @Override
    public void switchToFrame(String element, String index) {
        String datails = setDetails("switchToFrame", new String[]{element,index});
        try{
            WebElement frameElement=findElement(element,Integer.parseInt(index));
            driver.switchTo().frame(frameElement);
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void switchToDefaultFrame() {
        String datails = setDetails("switchToDefaultFrame", new String[]{});
        try {
            driver.switchTo().defaultContent();
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public void switchToWindow(int i) {
        String datails = setDetails("switchToWindow", new String[]{String.valueOf(i)});
        try{
            String[] handls=new String[driver.getWindowHandles().size()];
            driver.getWindowHandles().toArray(handls);
            driver.switchTo().window(handls[i]);
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+ snapshot());
            throw e;
        }
    }

    @Override
    public String screenshotFull(String name) {
        String datails = setDetails("screenshotFull", new String[]{name});
        String pngName = getPngName();
        String file = getSharedPath() + pngName;
        try {
            Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(fpScreenshot.getImage(), "PNG", new File(file));
            map.put(name,file);
            setPassTest(datails+"<br><a href='"+pngName+"' target='_black'><img src='"+pngName+"' width='500px' height='300px' alt='截图信息'></a>");
        } catch (IOException e) {
            setFailTest(datails+e.getMessage());
        }
        return file;
    }

    @Override
    public String screenshotPart(String element, String index,String name) {
        String datails = setDetails("screenshotPart", new String[]{element,index,name});
        String pngName = getPngName();
        String file = getSharedPath() + pngName;
        try {
            Screenshot fpScreenshot = new AShot().takeScreenshot(driver,findElement(element, Integer.parseInt(index)));
            ImageIO.write(fpScreenshot.getImage(), "PNG", new File(file));
            map.put(name,file);
            setPassTest(datails+"<br><a href='"+pngName+"' target='_black'><img src='"+pngName+"' width='500px' height='300px' alt='截图信息'></a>");
        } catch (IOException e) {
            setFailTest(datails+e.getMessage());
        }
        return file;
    }

    @Override
    public void screenshot(String name) {
        String datails = setDetails("screenshot", new String[]{name});
        String pngName = getPngName();
        String file = getSharedPath() + pngName;
        try {
            WebDriver augmentDriver = new Augmenter().augment(driver);
            File srcFile = ((TakesScreenshot) augmentDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(file));
            setPassTest(datails+"<br><a href='"+pngName+"' target='_black'><img src='"+pngName+"' width='500px' height='300px' alt='截图信息'></a>");
        } catch (IOException e) {
            setFailTest(datails+e.getMessage());
        }
    }

    @Override
    public void verifyContainEquals(String except, String actual) {
        String datails = setDetails("verifyContainEquals", new String[]{except,actual});
        try {
            Boolean flagBoolean = getValue(except).contains(getValue(actual));
            Assert.assertTrue(flagBoolean);
            setPassTest(datails+"<br><span style=\"font-weight:bold;color: green\">Except:"+getValue(except)+";Actual:"+getValue(actual)+"</span>");
        } catch (Error e) {
            setFailTest(datails+e.getMessage());
            throw e;
        }
    }

    @Override
    public void verifyEquals(String except, String actual) {
        String datails = setDetails("verifyEquals", new String[]{except,actual});
        try {
            Assert.assertEquals(getValue(except),getValue(actual));
            setPassTest(datails+"<br><span style=\"font-weight:bold;color: green\">Except:"+getValue(except)+";Actual:"+getValue(actual)+"</span>");
        } catch (Error e) {
            setFailTest(datails+e.getMessage());
            throw e;
        }
    }

    @Override
    public void verifyNotEquals(String except, String actual) {
        String datails = setDetails("verifyNotEquals", new String[]{except,actual});
        try {
            Assert.assertNotEquals(getValue(except),getValue(actual));
            setPassTest(datails+"<br><span style=\"font-weight:bold;color: green\">Except:"+getValue(except)+";Actual:"+getValue(actual)+"</span>");
        } catch (Error e) {
            setFailTest(datails+e.getMessage());
            throw e;
        }
    }

    @Override
    public void defineVariable(String value, String key) {
        String datails = setDetails("defineVariable", new String[]{value,key});
        try {
            map.put(key,value);
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+e.getMessage());
            throw e;
        }
    }

    @Override
    public String getPngName() {
        return DateUtil.getTimestamp() + ".png";
    }

    @Override
    public String snapshot() {
        String pngName = getPngName();
        String file = getSharedPath() + pngName;
        try {
            File scrFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(scrFile, new File(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<br><a href='"+pngName+"' target='_black'><img src='"+pngName+"' width='500px' height='300px' alt='截图信息'></a>";
    }

    @Override
    public String setDetails(String keyword, String[] args) {
        String deatils = "<h6><b>"+keyword+"</b></h6>";
        StringBuilder params = new StringBuilder();
        for(int i=0; i<args.length;i++){
            params.append("<span class=\"label info\">参数").append(i + 1).append(":</span> ").append(args[i]).append(" ");
        }
        deatils = deatils + params;
        return deatils;
    }

    @Override
    public void compareImage(String source,String target) {
        String datails = setDetails("compareImage", new String[]{source,target});
        try {
            File sourceFile = new File(getValue(source));
            File targetFile = new File(getValue(target));
            String[] images = {getValue(source),getValue(target)};
            String[][] list1 = getPX(images[0]);
            String[][] list2 = getPX(images[1]);
            int xiangsi = 0;
            int busi = 0;
            int i = 0, j = 0;
            for (String[] strings : list1) {
                if ((i + 1) == list1.length) {
                    continue;
                }
                for (String string : strings) {
                    try {
                        String[] value1 = list1[i][j].split(",");
                        String[] value2 = list2[i][j].split(",");
                        int k = 0;
                        for (String val : value2) {
                            if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
                                xiangsi++;
                            } else {
                                busi++;
                            }
                        }
                    } catch (RuntimeException e) {
                        continue;
                    }
                    j++;
                }
                i++;
            }
            String baifen = "";
            try {
                baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");
                baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);
            } catch (Exception e) {
                baifen = "0";
            }
            if (baifen.length() <= 0) {
                baifen = "0";
            }
            if(busi == 0){
                baifen="100";
            }
            String message = "<br>相似像素：" + xiangsi + " 不相似像素：" + busi + " 相似度：" + Integer.parseInt(baifen) + "%<br>" +
                    "<a href='"+sourceFile.getName()+"' target='_black'><img src='"+sourceFile.getName()+"' width='500px' height='300px' alt='基准图'>"+source+"</a>" +
                    "<a href='"+targetFile.getName()+"' target='_black'><img src='"+targetFile.getName()+"' width='500px' height='300px' alt='目标图'>"+target+"</a>";
            if(baifen.equals("100") ){
                setPassTest(datails+message);
            }else {
                setFailTest(datails+message);
            }
        }catch (Exception e){
            e.printStackTrace();
            setFailTest(datails+e.getMessage());
        }

    }

    @Override
    public Connection mysqlConnect(String type, String host, String database, String user, String password) {
        String datails = setDetails("mysqlConnect", new String[]{type,host,database,user,password});
        String className;
        String url;
        try {
            switch (type.toUpperCase()) {
                case "MYSQL":
                    className = "com.mysql.jdbc.Driver";
                    url = "jdbc:mysql://" + host + "/" + database;
                    break;
                case "ORACLE":
                    className = "oracle.jdbc.OracleDriver";
                    url = "jdbc:oracle:thin:@" + host + ":" + database;
                    break;
                case "SQLSERVER":
                    className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                    url = "jdbc:sqlserver://" + host + ";databaseName=" + database;
                    break;
                default:
                    className = "com.mysql.jdbc.Driver";
                    url = "jdbc:mysql://" + host + "/" + database;
                    break;
            }
            Class.forName(className);
            conn = DriverManager.getConnection(url, user, password);
            setPassTest(datails);
        } catch (Exception e) {
            setFailTest(datails+e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void mysqlDisconnect() {
        String datails = setDetails("mysqlDisconnect", new String[]{});
        try {
            if (conn != null)
                conn.close();
            setPassTest(datails);
        } catch (Exception e) {
            setFailTest(datails+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void mysqlOperation(String sql) {
        String datails = setDetails("mysqlOperation", new String[]{sql});
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            setPassTest(datails);
        } catch (Exception e) {{
            setFailTest(datails+e.getMessage());
            e.printStackTrace();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                setFailTest(datails+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String[][] getPX(String args) {
        int[] rgb = new int[3];

        File file = new File(args);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        String[][] list = new String[width][height];
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];

            }
        }
        return list;
    }

    private Keys changeKyes(String key){
        Keys keys = null;
        switch (key.toUpperCase())
        {
            case "ENTER" :
                keys = Keys.ENTER;
                break;
            case "TAB" :
                keys = Keys.TAB;
                break;
            case "SPACE" :
                keys = Keys.SPACE;
                break;
            case "PAGE_UP" :
                keys = Keys.PAGE_UP;
                break;
            case "PAGE_DOWN" :
                keys = Keys.PAGE_DOWN;
                break;
            case "F1" :
                keys = Keys.F1;
                break;
            case "F2" :
                keys = Keys.F2;
                break;
            case "F3" :
                keys = Keys.F3;
                break;
            case "F4" :
                keys = Keys.F4;
                break;
            case "F6" :
                keys = Keys.F6;
                break;
            case "F7" :
                keys = Keys.F7;
                break;
            case "F8" :
                keys = Keys.F8;
                break;
            case "F9" :
                keys = Keys.F9;
                break;
            case "F10" :
                keys = Keys.F10;
                break;
            case "F11" :
                keys = Keys.F11;
                break;
            case "F12" :
                keys = Keys.F12;
                break;
        }
        return keys;
    }
}

package com.autotest.driver.impl;

import com.autotest.utils.SeleniumGridUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by youyong.li on 6/22/2017.
 */
public class WebAPI extends CommonAPI {
    private Map<String,String> map;
    private WebDriver driver;

    public WebAPI(){
        this.driver = getDriver();
        this.map = getMap();
    }

    /**
     * 关键字
     * 启动服务器浏览器
     * @param browser
     * @return
     */
    public WebDriver startBrowser(String browser) {
        String datails = setDetails("startBrowser", new String[]{browser});
        String driverPath = Thread.currentThread().getContextClassLoader ().getResource("").getPath().replace("%20", " ")+"driver/";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try{
            switch (browser.toLowerCase()) {
                case "firefox" :
                    System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                case "ie":
                    System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
                    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    capabilities.setCapability("ignoreProtectedModeSettings", true);
                    driver = new InternetExplorerDriver(capabilities);
                    break;
                case "phantomjs":
                    System.setProperty("phantomjs.binary.path", driverPath + "phantomjs.exe");
                    capabilities.setCapability("acceptSslCerts", true);
                    capabilities.setCapability("takesScreenshot", true);
                    capabilities.setCapability("cssSelectorsEnabled", true);
                    capabilities.setJavascriptEnabled(true);
                    driver = new PhantomJSDriver(capabilities);
                    break;
                default:
                    System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
            }
            setDriver(driver);
            setPassTest(datails);
        }catch (Exception e){
            setFailTest(datails+e.getMessage());
        }
        return driver;
    }

    /**
     * 关键字
     * 启动远程浏览器
     * @param browser
     * @param host
     * @return
     */
    public WebDriver startRemoteBrowser(String browser, String host) {
        String nodeURL = "http://"+host+"/wd/hub";
        Boolean hubService = SeleniumGridUtil.startHub();
        if(hubService){
            String datails = setDetails("startRemoteBrowser", new String[]{browser,nodeURL});
            DesiredCapabilities capabilities = new DesiredCapabilities();
            try{
                switch (browser.toLowerCase()) {
                    case "firefox" :
                        capabilities.setBrowserName("firefox");
                        capabilities.setCapability("firefox_binary",
                                "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
                        driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
                        break;
                    case "chrome":
                        capabilities.setBrowserName("chrome");
                        driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
                        break;
                    case "ie":
                        capabilities.setBrowserName("internet explorer");
                        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                        capabilities.setCapability("ignoreProtectedModeSettings", true);
                        driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
                        break;
                    case "phantomjs":
                        capabilities.setBrowserName("phantomjs");
                        capabilities.setCapability("acceptSslCerts", true);
                        capabilities.setCapability("takesScreenshot", true);
                        capabilities.setCapability("cssSelectorsEnabled", true);
                        capabilities.setJavascriptEnabled(true);
                        driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
                        break;
                    default:
                        capabilities.setBrowserName("chrome");
                        driver = new RemoteWebDriver(new URL(nodeURL), capabilities);
                        break;
                }
                setPassTest(datails);
                setDriver(driver);
            }catch (MalformedURLException e){
                setFailTest(datails+ "\n<span style=\"font-weight:bold;color: red\">Node Service启动失败，请检查服务!</span>");
            }
        }else {
            setFailTest("<span style=\"font-weight:bold;color: red\">Hub Service启动失败，请找系统管理员!</span>");
        }
        return driver;
    }

    public WebDriver startBrowserStack(String username, String key, String browser, String browserVersion, String os, String osVersion) {
        String datails = setDetails("startBrowserStack", new String[]{username,key,browser,browserVersion,os,osVersion});
        DesiredCapabilities caps;
        try{
            switch (browser.toLowerCase()) {
                case "firefox":
                    caps = DesiredCapabilities.firefox();
                    break;
                case "chrome":
                    caps = DesiredCapabilities.chrome();
                    break;
                case "ie":
                    caps = DesiredCapabilities.internetExplorer();
                    break;
                case "edge":
                    caps = DesiredCapabilities.edge();
                    break;
                case "andriod":
                    caps = DesiredCapabilities.android();
                    break;
                case "ipad":
                    caps = DesiredCapabilities.ipad();
                    break;
                case "iphone":
                    caps = DesiredCapabilities.iphone();
                    break;
                default:
                    caps = DesiredCapabilities.chrome();
                    break;
            }
            caps.setCapability("browser", browser);
            caps.setCapability("browser_version", browserVersion);
            caps.setCapability("os", os);
            caps.setCapability("os_version", osVersion);
            caps.setCapability("browserstack.debug", "true");
            String URL = "https://"+username+":"+key+"@hub-cloud.browserstack.com/wd/hub";
            driver = new RemoteWebDriver(new URL(URL), caps);
            String sessionId =((RemoteWebDriver) driver).getSessionId().toString();
            System.out.println("SessionId is" + sessionId);
            setPassTest(datails);
            setDriver(driver);
        } catch (MalformedURLException e) {
            setFailTest(datails+e.getMessage());
        }
        return driver;
    }

}

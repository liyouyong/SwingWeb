package com.autotest.utils;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by youyong.li on 8/17/2017.
 */
public class SeleniumGridUtil {
    private static final String STANDALONE_PAth = System.getProperty("user.dir") + "\\src\\main\\resources\\driver\\selenium-server-standalone-2.44.0.jar";
    private static final String IP = "127.0.0.1";
    private static final int HUB_HOST = 4444;
    private static final int NODE_HOST = 5555;

    private static Boolean checkService(String ip, int host,String cmd){
        Socket client;
        try{
            client = new Socket(ip, host);
            client.close();
            System.out.println("HUB已经启动");
            return true;
        }catch(Exception e){
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(cmd);
                System.out.println("HUB启动");
                return true;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("HUB未启动");
            return false;
        }
    }

    public static Boolean startHub(){
        String cmd = "java -jar " + STANDALONE_PAth + " -role hub";
        return checkService(IP,HUB_HOST,cmd);
    }

    public static Boolean startNode(){
        String cmd = "java -Dwebdriver.chrome.driver=\"chromedriver.exe\" " + STANDALONE_PAth + " -port 5555 -role node";
        return checkService(IP,NODE_HOST,cmd);
    }

    public static void main(String[] args){
        SeleniumGridUtil.startHub();
    }
}

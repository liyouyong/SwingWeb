package com.autotest.utils;

import java.io.File;

/**
 * Created by youyong.li on 6/22/2017.
 */
public class FileUtil {


	public static boolean deleteDir(File dir) {
        if(dir.isFile()){  
        	dir.delete();       
        }else{
        	File[] files = dir.listFiles();
        	for (int i = 0; i < files.length; i++) {  
        		deleteDir(files[i]);  
                files[i].delete();      
            } 
        }
        if(! dir.exists()) dir.mkdir();
        return dir.exists() && dir.list().length == 0;
    }
	
	public static boolean removeDir(File dir) {
		if (dir.isDirectory()) {
	      	String[] children = dir.list();
	      	for (int i=0; i<children.length; i++) {
	          	boolean success = deleteDir(new File(dir, children[i]));
	          	if (!success) {
	              	return false;
	          	}
	      	}
	  	}
	  	return dir.delete();
	}

	public static String createDir(String basePath){
		String date = DateUtil.getNow("yyyyMMdd");
		String time = DateUtil.getTimestamp();
		String dir = basePath + "/" + date + "/" + time + "/";
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println(dir);
		return dir;
	}

	public static String getFileName(String basePath){
		removeFolder(basePath, -7);
		String name = DateUtil.getNow("yyyyMMdd");
		File file = new File(basePath + File.separator + name);
		if(! file.exists()){
//			return file.getAbsolutePath();
			file.mkdirs();
		}
		File[] tempList = new File(basePath).listFiles();
		int index = 1;
		int flag = 0;
		for (int i = 0; i < tempList.length; i++) {
			if(tempList[i].isDirectory() && tempList[i].getName().startsWith(name)){
				if(tempList[i].getName().contains("_")){
					int pos = tempList[i].getName().indexOf("_");
					pos = Integer.parseInt(tempList[i].getName().substring(pos+1));
					if(pos > index){
						index = pos;
					}
					flag++;
				}
			}
		}
		if(flag > 0) index++;
		return basePath + File.separator + name + "_" + index;
	}
	
	public static void removeFolder(String basePath, int day){
		String endDate = DateUtil.getNewDate(day);
		File baseFile = new File(basePath);
		if(baseFile.exists()){
			for (File file : baseFile.listFiles()) {
				if(file.isDirectory() && file.getName().startsWith(endDate)){
					removeDir(file);
				}
			}
		}
	}

	public static void main(String[] args){
		System.out.println(createDir("C:/reports"));
//		System.out.println(StringUtils.substringAfter(createDir(), "WEB-INF"));
	}
}

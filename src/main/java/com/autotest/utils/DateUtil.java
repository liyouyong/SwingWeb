package com.autotest.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yangangui on 2017/1/5.
 * @author yangangui
 *
 */
public class DateUtil {
	
	public static void main(String[] args){
		System.out.println(getNewDate(-7));
	}
	
	public static String getNow(String format){
		Date date = new Date();
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String getNewDate(int days){
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, days); 
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
	}
	
	public static String getTimestamp(){
		Calendar curCalendar = Calendar.getInstance();
		int hour = curCalendar.get(Calendar.HOUR_OF_DAY);//24H
		int minute = curCalendar.get(Calendar.MINUTE);
		int second = curCalendar.get(Calendar.SECOND);
		int millisecond = curCalendar.get(Calendar.MILLISECOND);
		return  hour + "_" + minute + "_" + second + "_" + millisecond+(int)((Math.random()*9+1)*100000);
	}

}

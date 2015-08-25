package net.kadirderer.btc.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	
	public static Date parse(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String format(Date date) {
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
	}

}

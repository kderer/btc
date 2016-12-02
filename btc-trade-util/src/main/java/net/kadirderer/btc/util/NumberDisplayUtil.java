package net.kadirderer.btc.util;

import java.text.NumberFormat;

public class NumberDisplayUtil {
	
	private static NumberFormat numberFormat;	
	
	static {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(3);
	}
	
	public static String dailyReportFormat(double value) {
		return numberFormat.format(value);
	}
	
	public static Double parse(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return null;
		}		
	}

}

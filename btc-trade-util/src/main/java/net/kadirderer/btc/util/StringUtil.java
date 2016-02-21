package net.kadirderer.btc.util;



public class StringUtil {
	
	public static boolean isNullOrEmpty(String string) {
		if (string == null) {
			return true;
		}
		
		if (string.length() > 0) {
			return false;
		}
		
		return true;
	}

}

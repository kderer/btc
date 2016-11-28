package net.kadirderer.btc.util;

import java.util.StringTokenizer;

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
	
	public static String generateDeliminatedString(char deliminator, String[] array) {
		if (array == null || array.length == 0) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (String word : array) {
			sb.append(word);
			sb.append(deliminator);
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	public static String[] generateArrayFromDeliminatedString(char deliminator, String deliminatedString) {
		if (deliminatedString == null || deliminatedString.length() == 0) {
			return null;
		}
		
		StringTokenizer st = new StringTokenizer(deliminatedString, String.valueOf(deliminator));
		String[] array = new String[st.countTokens()];
		
		int i = 0;
		while (st.hasMoreTokens()) {
			array[i] = st.nextToken();
			i += 1;
		}
		
		return array;
	}

}

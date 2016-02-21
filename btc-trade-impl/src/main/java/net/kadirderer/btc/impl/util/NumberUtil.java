package net.kadirderer.btc.impl.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {
	
	private static DecimalFormat df = new DecimalFormat("#.####"); 
	
	public static double format(double number) {
		df.setRoundingMode(RoundingMode.DOWN);
		return Double.valueOf(df.format(number)).doubleValue();
	}

}

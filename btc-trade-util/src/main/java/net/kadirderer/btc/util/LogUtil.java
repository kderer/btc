package net.kadirderer.btc.util;

import org.slf4j.Logger;

public class LogUtil {
	
	public static void printExceptionLog(Logger logger, Exception e) {		
		StackTraceElement stackTraceElement = e.getStackTrace()[0];
		String exceptionMessage = e.getMessage();
		String className = e.getClass().getName();
		
		if(!StringUtil.isNullOrEmpty(exceptionMessage)) {
			logger.error("{} exception occured at {}.{}[{}]: {}", className, 
					stackTraceElement.getClassName(), stackTraceElement.getMethodName(),
					stackTraceElement.getLineNumber(), exceptionMessage);
		} else {
			logger.error("{} exception occured at {}.{}[{}].", className,
					stackTraceElement.getClassName(), stackTraceElement.getMethodName(),
					stackTraceElement.getLineNumber());
		}
	}

}

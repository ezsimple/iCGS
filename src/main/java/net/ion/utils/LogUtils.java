package net.ion.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// 로그 사용방식을 통일하기 위해서 .... 
// protected Log logger = LogFactory.getLog(this.getClass());
public class LogUtils {
	static Log getLog(Class<?> clazz) {
		return LogFactory.getLog(clazz);
	}
}
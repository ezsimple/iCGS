package net.ion.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// 로그 사용방식을 통일하기 위해서 .... 
// protected Log logger = LogFactory.getLog(this.getClass());
// 음 ... 이걸 어떻게 써먹어야 하지?
public abstract class LogUtils {
	static Log getLog(Class<?> clazz) {
		return LogFactory.getLog(clazz);
	}
}
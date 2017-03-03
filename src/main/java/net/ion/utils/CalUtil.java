package net.ion.utils;

import java.util.GregorianCalendar;

public class CalUtil {

	public final static long gmtTime(){
		return GregorianCalendar.getInstance().getTimeInMillis() ;
	}
}

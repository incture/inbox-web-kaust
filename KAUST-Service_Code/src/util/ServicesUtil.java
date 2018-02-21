package com.incture.pmc.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Contains utility functions to be used by Services
 * 
 * @version R1
 */
public class ServicesUtil {

	public static final String NOT_APPLICABLE = "N/A";
	public static final String SPECIAL_CHAR = "∅";
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Collection<?> o) {
		if (o == null || o.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(StringBuffer sb) {
		if (sb == null || sb.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(StringBuilder sb) {
		if (sb == null || sb.length() == 0) {
			return true;
		}
		return false;
	}

	public static String getCSV(Object... objs) {
		if (!isEmpty(objs)) {
			if (objs[0] instanceof Collection<?>) {
				return getCSVArr(((Collection<?>) objs[0]).toArray());
			} else {
				return getCSVArr(objs);
			}

		} else {
			return "";
		}
	}

	private static String getCSVArr(Object[] objs) {
		if (!isEmpty(objs)) {
			StringBuffer sb = new StringBuffer();
			for (Object obj : objs) {
				sb.append(',');
				if (obj instanceof Field) {
					sb.append(extractFieldName((Field) obj));
				} else {
					sb.append(extractStr(obj));
				}
			}
			sb.deleteCharAt(0);
			return sb.toString();
		} else {
			return "";
		}
	}

	public static String extractStr(Object o) {
		return o == null ? "" : o.toString();
	}

	public static String extractFieldName(Field o) {
		return o == null ? "" : o.getName();
	}

	public static String buildNoRecordMessage(String queryName, Object... parameters) {
		StringBuffer sb = new StringBuffer("No Record found for query: ");
		sb.append(queryName);
		if (!isEmpty(parameters)) {
			sb.append(" for params:");
			sb.append(getCSV(parameters));
		}
		return sb.toString();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static Date resultAsDate(Object o) {
		Date date = null;
		if(!isEmpty(o)){
			String template = "";
			if (o instanceof Object[]) {
				template = Arrays.asList((Object[]) o).toString();
			} else {
				template = String.valueOf(o);
			}
			try {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				date = formatter.parse(template);
				//		System.err.println("[PMC][WorkBoxFacade][resultAsDate][o]"+o+"[template]"+template+"[date]"+date+"yyyy-MM-dd hh:mm:ss.SSS");
			} catch (ParseException e) {
				System.err.println("resultAsString ParseException" + e.getMessage());
			}
		}
		return date;
	}

	public static Date resultTAsDate(Object o)
	{
		Date date = null;
		if(!isEmpty(o) && o.toString() != ""){
			String template = "";
			if (o instanceof Object[]) {
				template = Arrays.asList((Object[]) o).toString();
			} else {
				template = String.valueOf(o);
			}
			try {
				DateFormat formatterT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				date = formatter.parse(formatter.format(formatterT.parse(template)));
				//		System.err.println("[PMC][WorkBoxFacade][resultAsDate][o]"+o+"[template]"+template+"[date]"+date+"yyyy-MM-dd hh:mm:ss.SSS");
			} catch (Exception e) {
				System.err.println("resultTAsDate " + e.getMessage());
			}
		}
		return date;
	}

	public static long getEarlierDateInMillis(int noOfDays , int hours ,int minutes ,int seconds ,int millis) {
		Calendar calendar = Calendar.getInstance();
		System.err.println("calender start"+calendar.getTimeInMillis());		
//		calendar.add(Calendar.DAY_OF_MONTH, -noOfDays);
//		calendar.set(Calendar.HOUR_OF_DAY, -hours);
		calendar.set(Calendar.MINUTE, -minutes);
//		calendar.set(Calendar.SECOND, -seconds);
//		calendar.set(Calendar.MILLISECOND, -millis);
		System.err.println("calender end"+calendar.getTimeInMillis());
		return calendar.getTimeInMillis();
	} 
	
	public static String calendarFormat(GregorianCalendar calendar){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		return dateFormatted;
	}
	
	public static String getDecryptedText(String encryptedText){
		if(!isEmpty(encryptedText)){
			byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
			return new String(decodedBytes);
		}
		return null;
	}
	public static String getStringFromList(List<String> stringList) {
		String returnString = "";
		for(String st : stringList){
			returnString = returnString + "'"+st+"',";
		}
		return returnString.substring(0,returnString.length()-1);
	}
	public static String getStringFromList(String[] stringList) {
		String returnString = "";
		for(String st : stringList){
			returnString = returnString + "'"+st+"',";
		}
		return returnString.substring(0,returnString.length()-1);
	}
	
	public static String getStringFromList(Object[] stringList) {
		String returnString = "";
		for(Object st : stringList){
			returnString = returnString + "'"+st.toString()+"',";
		}
		return returnString.substring(0,returnString.length()-1);
	}

}

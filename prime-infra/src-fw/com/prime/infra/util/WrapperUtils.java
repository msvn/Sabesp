package com.prime.infra.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.prime.infra.record.adapters.ObjectUtils;
import com.prime.infra.record.adapters.flatfile.annotation.FixedLenghtField;

public class WrapperUtils {
	
	public static final DecimalFormat df = new DecimalFormat("#,##0.00");
	public static final Date SABESP_MES_REFERENCIA = toDate("1-12-79", "dd-MM-yy");
	public static final Date SABESP_DIA_REFERENCIA = toDate("1-1-57", "dd-MM-yy");
	public static final String DATA_POR_EXTENSO = "dd 'de' MMMM 'de' yyyy";
	public static final long AMOUNT_MONTH_IN_MILLISECONDS = 2629743000L;
	public static final long AMOUNT_DAY_IN_MILLISECONDS = 86400000L; 
	
	public static Integer toInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}
	
    public static Character toChar(String str) {
    	if(str!=null && str.trim().length()>0){
    		return str.charAt(0);
    	}
   		return null;	
	}
    
    public static String getNextChar(String str) {
		if (isNull(str)) {
			return "";
		}
//		char c = str.charAt(0);
//		int next = Character.getNumericValue(c+1);
//		char ca = (char) next;
		return "";

	}
    
	public static String removeLastCaracter(String str) {
		if (isNull(str)) {
			return null;
		}
		return str.substring(0, str.length()-1);
	}
	
	public static String toSubst(String str, int len) {
		if (str != null && str.length() > 0) {
			if (str.length() < len) {
				len = str.length();
			}
			str = str.substring(0, len);
		}
		return str;
	}

	public static Long toLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	public static Long toLong(Object o) {
		if(isNotNull(o)){
			try {
				if(o instanceof BigDecimal){
					return Long.parseLong(o.toString());
				}else if(o instanceof String){
					return Long.parseLong((String)o);
				}
			}catch (Exception e) {
//			e.printStackTrace();				
			}
		}	
		return null;
	}

	
	public static BigDecimal toBigDecimal(String value) {
		Locale.setDefault(new Locale("pt", "BR"));
		try {
			df.setParseBigDecimal(true);
			BigDecimal bd = (BigDecimal) df.parse(value);
			return bd;
		} catch (ParseException pe) {
//			pe.printStackTrace();
		}
		return BigDecimal.ZERO;
	}
	
	public static Double toDouble(String value) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		try {
			return Double.valueOf(decimalFormat.parse(value).doubleValue());
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return new Double(0);
	}
	
	public static String parseDouble(Double value) {
		if (value!= null && value> 0) {
			try {
				df.setParseBigDecimal(true);
				return (df.format(value).replace(".", "").replace(",", ""));		
			} catch (Exception pe) {
//				pe.printStackTrace();
			}
		}
		return "0";
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String parseMoney(String value) {
		double retorno = 0;
		if (isNull(value)) {
			return null;
		}
		try {		
			retorno = Double.parseDouble(value);						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(retorno); 
	}
	
	public static Date getToday() {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
        TimeZone.setDefault(tz);  
        Calendar fromDate = GregorianCalendar.getInstance(tz);  
		return fromDate.getTime();
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String parseMoney(Double value) {
		double retorno = 0;
		if (isNull(value)) {
			return null;
		}
		try {		
			retorno = value.doubleValue();						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(retorno); 
	}
	
	/**
	 * Converte uma String para um objeto java.sql.Date. Caso a String seja
	 * vazia ou nula, retorna null - para facilitar em casos onde formulários
	 * podem ter campos de datas vazios.
	 * 
	 * @param strData
	 *            String no formato dd/MM/yyyy a ser formatada
	 * @return java.sql.Date
	 * @throws Exception
	 *             Caso a String esteja no formato errado
	 */
	public static Date toDate(String data) {
		if (data == null || data.equals("")) {
			return null;
		}
		java.util.Date date = null;
		try {
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			date = (java.util.Date) f.parse(data);
		} catch (ParseException e) {
//			e.printStackTrace();
			// throw new Exception("Data com formato inválido");
		}
		return date;
	}
	
	public static Date addMonthsToDate(Date data,int months) {
		if (isNull(data)) {
			return null;
		}
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTime(data);
		fromDate.add(Calendar.MONTH, months);		
		return fromDate.getTime();
	}
	
	public static Date getFirstDayFromNextMonth() {
		Calendar fromDate = Calendar.getInstance();		
		return getFirstDayFromNextMonth(fromDate.getTime());
	}	
	
	public static Date getFirstDayFromNextMonth(Date data) {
		if (isNull(data)) {
			return null;
		}
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTime(data);
		fromDate.add(Calendar.MONTH, 1);
		fromDate.set(Calendar.DAY_OF_MONTH, 1);
		fromDate.set(Calendar.HOUR_OF_DAY, 1);
		return fromDate.getTime();
	}
	
	public static Date addDaysToDate(Date data,int days) {
		if (isNull(data)) {
			return null;
		}
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTime(data);
		fromDate.add(Calendar.DAY_OF_YEAR, days);		
		return fromDate.getTime();
	}
	
	public static Date removeDaysToNow(int days) {
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_YEAR, -days);		
		return fromDate.getTime();
	}
	
	public static Date removeMonthsFromNow(int months) {
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.MONTH, -months);		
		return fromDate.getTime();
	}
	
	public static Date removeMonthsFromDate(Date data,int months) {			
		return addMonthsToDate(data, -months);
	}
	
	/**
	 * Utilizada para buscar data, usando como data base: 12/01/1979
	 * 
	 * @param months
	 * @return
	 */
	public static Date addMonths(int months) {
		return addMonthsToDate(SABESP_MES_REFERENCIA,months);
	}
	
	/**
	 * Utilizada para buscar data, usando como data base: 1/01/1957
	 * 
	 * @param days
	 * @return
	 */
	public static Date addDays(int days) {
		return addDaysToDate(SABESP_DIA_REFERENCIA,days);
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static long getMonthsFromDate(Date data){
		if (isNull(data)) {
			return 0;
		}
		return (data.getTime()- SABESP_MES_REFERENCIA.getTime())/AMOUNT_MONTH_IN_MILLISECONDS;
	}
	
	public static long getDaysFromDate(Date data){
		if (isNull(data)) {
			return 0;
		}
		return (data.getTime()- SABESP_DIA_REFERENCIA.getTime())/AMOUNT_DAY_IN_MILLISECONDS;
	}
	
	public static Date toDate(String data, String pattern) {
		if (data == null || data.equals("")) {
			return null;
		}
		java.util.Date date = null;
		try {
			DateFormat f = new SimpleDateFormat(pattern);			
			date = (java.util.Date) f.parse(data);
		} catch (ParseException e) {
//			e.printStackTrace();
			// throw new Exception("Data com formato inválido");
		}
		return date;
	}
	
	public static String parseDate(Date data){
		return parseDate(data,"dd/MM/yyyy");
	}
	
	public static String parseDate(Date data,String pattern) {	
		String retorno = "";
		if (data == null || data.equals("")) {
			retorno = null;
		}
		try {
			SimpleDateFormat f = new SimpleDateFormat(pattern);
			retorno = f.format(data);
		} catch (Exception e) {
		}
		return retorno;
	}
	
	public static String formatValue(String value, String format) {
		return format(value,format).replace(",", ".");
	}

	public static String format(String value, String format) {
		String output = format;
		if (value!=null && value.trim().length()>0){
			Object obj = new java.math.BigDecimal(value);
			java.text.DecimalFormat myFormatter = null;			
			
			try {
				myFormatter = new java.text.DecimalFormat(format);
				output = myFormatter.format(obj);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}						
		return output;

	}
	
	public static boolean isNotNull(Object o) {	
		return !isNull(o);
	}
	
	public static boolean isNull(Object o) {		
		if(o!=null){
			if(o instanceof String){
				if (((String)o).trim().length()>0){
					return false;	
				}else{
					return true;
				}					
			}else if(o instanceof Collection){
				if (((Collection) o).size()>0){
					for(Object child : (Collection) o){						
						if(child instanceof String){
							if (((String)child).trim().length()>0){
								return false;	
							}
						}else{
							return false;
						}
					}
				}else{
					return true;
				}			
			}else if(o instanceof String[]){
				if(((String[])o).length>0){
					for (int i = 0; i < ((String[])o).length; i++) {
						if(((String[])o)[i].trim().length()>0){
							return false;
						}
					}					
				}
			}else{
				return false;
			}						
		}
		return true;
	}
	
    public static Object populate(Object object)  {		
		int i = 0;
		Field[] fields = object.getClass().getDeclaredFields();
	     for (Field field : fields) {	  
	    	 i++;
	    	 FixedLenghtField annAEFixedLenghtField = (FixedLenghtField)field.getAnnotation(FixedLenghtField.class);;
	    	 try {
	    		 populateFields(object,field,i,annAEFixedLenghtField.lenght());	
			} catch (Exception e) {
				e.printStackTrace();
			}
	     }
	     return object;
	}
    
	private static Object populateFields(Object object, Field field, int index, int lenght) throws Exception{
		Object returnValue = null; 
		String methodName = "set" + String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
		
		Class[] parameterTypes = new Class[1];
		parameterTypes[0] = field.getType();
		Method method = object.getClass().getMethod(methodName, parameterTypes);

		if(field.getType().toString().indexOf("String")>0){
			returnValue = StringUtils.rightPad(decorate(index,lenght), lenght,"-");
		}else if(field.getType().toString().indexOf("Date")>0){
			returnValue = WrapperUtils.toDate("31/12/9999");
		}else if(field.getType().toString().indexOf("Double")>0){			
			returnValue = new Double(StringUtils.rightPad(index+"", lenght,"0"));
		}
        return method.invoke(object,returnValue ); 
	}
	
	private static String decorate(int index, int len){
		if(len+"".length()>=2 || index==1){
			return index+""; 
		}else{
			return "*";	
		}
	}

	public static String toString(Object object) {
		String returnValue = "";
		Field[] fields = object.getClass().getDeclaredFields();
	     for (Field field : fields) {
	    	 FixedLenghtField annAEFixedLenghtField = (FixedLenghtField)field.getAnnotation(FixedLenghtField.class);;
	    	 try {	    		 
	    		 Object value = ObjectUtils.invokeGetterMethod(field, object);
	    			if(field.getType().toString().indexOf("String")>0){
	    				returnValue = returnValue.concat(value!=null ? StringUtils.rightPad(value.toString(),annAEFixedLenghtField.lenght(),"") : StringUtils.leftPad("",annAEFixedLenghtField.lenght(),""));
	    			}else if(field.getType().toString().indexOf("Date")>0){
	    				returnValue = returnValue.concat(value!=null ? parseDate((Date)value, "yyyy-MM-dd") : StringUtils.leftPad("",annAEFixedLenghtField.lenght(),""));
	    			}else if(field.getType().toString().indexOf("Double")>0){	
	    				String s  = parseDouble((Double)value);
	    				returnValue = returnValue.concat(StringUtils.leftPad(s,annAEFixedLenghtField.lenght(),"0"));
	    			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	     }
		return returnValue;
	}

	public static List<String> toArrayOfString(Object object) {
		List<String> returnValue = new ArrayList<String>();
		Field[] fields = object.getClass().getDeclaredFields();
	     for (Field field : fields) {
	    	 try {	    		 
	    		 Object value = ObjectUtils.invokeGetterMethod(field, object);
	    			if(field.getType().toString().indexOf("String")>0){
	    				returnValue.add(value.toString());
	    			}else if(field.getType().toString().indexOf("Date")>0){
	    				returnValue.add(parseDate((Date)value, "yyyy-MM-dd"));
	    			}else if(field.getType().toString().indexOf("Double")>0){			
	    				returnValue.add(parseDouble((Double)value));
	    			}
	    		 
			} catch (Exception e) {
				e.printStackTrace();
			}
	     }
		return returnValue;
	}
	
	/**
	  * Returns the Julian day number that begins at noon of
	  * this day, Positive year signifies A.D., negative year B.C.
	  * Remember that the year after 1 B.C. was 1 A.D.
	  *
	  * ref :
	  *  Numerical Recipes in C, 2nd ed., Cambridge University Press 1992
	  */
	  // Gregorian Calendar adopted Oct. 15, 1582 (2299161)
	  public static int JGREG= 15 + 31*(10+12*1582);
//	public static int JGREG = 1 + 31 * (1 + 12 * 1957);
	public static double HALFSECOND = 0.5;

	public static double toJulian(int[] ymd) {
		int year = ymd[0];
		int month = ymd[1]; // jan=1, feb=2,...
		int day = ymd[2];
		int julianYear = year;
		if (year < 0)
			julianYear++;
		int julianMonth = month;
		if (month > 2) {
			julianMonth++;
		} else {
			julianYear--;
			julianMonth += 13;
		}

		double julian = (java.lang.Math.floor(365.25 * julianYear)
				+ java.lang.Math.floor(30.6001 * julianMonth) + day + 1720995.0);
		if (day + 31 * (month + 12 * year) >= JGREG) {
			// change over to Gregorian calendar
			int ja = (int) (0.01 * julianYear);
			julian += 2 - ja + (0.25 * ja);
		}
		return java.lang.Math.floor(julian);
	}

	/**
	 * Converts a Julian day to a calendar date ref : Numerical Recipes in C,
	 * 2nd ed., Cambridge University Press 1992
	 */
	public static int[] fromJulian(double injulian) {
		int jalpha, ja, jb, jc, jd, je, year, month, day;
		double julian = injulian + HALFSECOND / 86400.0;
		ja = (int) julian;
		if (ja >= JGREG) {
			jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
			ja = ja + 1 + jalpha - jalpha / 4;
		}

		jb = ja + 1524;
		jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
		jd = 365 * jc + jc / 4;
		je = (int) ((jb - jd) / 30.6001);
		day = jb - jd - (int) (30.6001 * je);
		month = je - 1;
		if (month > 12)
			month = month - 12;
		year = jc - 4715;
		if (month > 2)
			year--;
		if (year <= 0)
			year--;

		return new int[] { year, month, day };
	}

	public static String replaceHTML(String value){
		return value.replaceAll("\\<.*?>","");
	}
	
	
	public static void main1(String args[]) {
//		// FIRST TEST reference point
//		System.out.println("Julian date for "
//				+ toJulian(new int[] { 1979, 12, 01 }));
//		// output : 2440000
//		int results[] = fromJulian(toJulian(new int[] { 1979, 12, 1 }));
//		System.out.println("... back to calendar : " + results[0] + " "
//				+ results[1] + " " + results[2]);
//
//		// SECOND TEST today
//		Calendar today = Calendar.getInstance();
//		double todayJulian = toJulian(new int[] { today.get(Calendar.YEAR),
//				today.get(Calendar.MONTH) + 1, today.get(Calendar.DATE) });
//		System.out.println("Julian date for today : " + todayJulian);
//		results = fromJulian(todayJulian);
//		System.out.println("... back to calendar : " + results[0] + " "
//				+ results[1] + " " + results[2]);

		// THIRD TEST
		double date1 = toJulian(new int[] { 1957, 1, 1 });
		double date2 = toJulian(new int[] { 2010, 9, 6 });
		System.out.println("Between 2005-01-01 and 2005-01-31 : "
				+ (date2 - date1) + " days");

//		System.out.println(date1);

//		int ii[] = fromJulian((date2) - date1);
//		for (int i = 0; i < ii.length; i++) {
//			System.out.println(ii[i]);
//		}
		/*
		 * expected output : Julian date for May 23, 1968 : 2440000.0 ... back
		 * to calendar 1968 5 23 Julian date for today : 2453487.0 ... back to
		 * calendar 2005 4 26 Between 2005-01-01 and 2005-01-31 : 30.0 days
		 */
		
		

		
	}

	public static void main2(String[] args) {
        Date data = com.prime.infra.util.WrapperUtils.toDate("1-12-79", "dd-MM-yy");

        data = com.prime.infra.util.WrapperUtils.addMonthsToDate(data, +350);

        System.out.println("Current date : " + data.toString());         

        System.out.println("Current month : " + com.prime.infra.util.WrapperUtils.toJulian(new int[]{1957, 1, 1}));

        data = com.prime.infra.util.WrapperUtils.toDate("1-1-57", "dd-MM-yy");

        data = com.prime.infra.util.WrapperUtils.addDaysToDate(data, +19606);

        System.out.println("Current day : " + data.toString());                 
        
        
//        com.prime.infra.util.WrapperUtils.fromJulian(0D);  19606

	}
	
	public static void main(String[] args) {
//		System.out.println("Current month -14: " +WrapperUtils.removeMonthsFromNow(12));
//		System.out.println(toDate("11-09-2010", "dd-MM-yyyy").getTime()-toDate("1-12-1979", "dd-MM-yyyy").getTime());
//		
//		System.out.println(getMonthsFromDate( removeMonthsFromNow(9) ));
//		System.out.println(addMonths(-226).toString());
		
//		System.out.println(getNextChar("JOSE"));
		
//		char c = 'J';
//		for(int i=65;i<=91;i++){
//			if(c==(char)i){
//				i++; char d =(char)i; break;
//			}
//				
//			System.out.println(i+"  "+(char)i);
//		} 
	
//		System.out.println(WrapperUtils.getDaysFromDate(WrapperUtils.getFirstDayFromNextMonth()));
		
//		System.out.println(WrapperUtils.parseMoney(("13000.51")));
		
//		System.out.println(WrapperUtils.addMonths(249));

		java.math.BigDecimal b = new java.math.BigDecimal(2);
		Long l = toLong("2");
		System.out.println(l);
	}	
	
	
	public static int daysInMonth() {
		GregorianCalendar c = new GregorianCalendar();
		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		daysInMonths[1] += c.isLeapYear(c.get(GregorianCalendar.YEAR)) ? 1 : 0;
		return daysInMonths[c.get(GregorianCalendar.MONTH)];
	}
	
}




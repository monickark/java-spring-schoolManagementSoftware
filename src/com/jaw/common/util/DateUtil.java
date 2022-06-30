package com.jaw.common.util;



import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.util.dao.ICurrentDateDao;

//Utility class Date operations
@Component
public class DateUtil {

	// Logging
	Logger logger = Logger.getLogger(DateUtil.class);

	@Autowired
	ICurrentDateDao currentDateDao;

	public String addDaysToCurrentDate(int days) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		String dt = sdf.format(d);
		c.add(Calendar.DATE, 30); // number of days to add
		dt = sdf.format(c.getTime());
		return dt;
	}

	public Boolean checkDate(String date) {

		Boolean result = false;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = new Date();
			if (date1.compareTo((Date) formatter.parse(date)) > 0) {
				result = true;
			} else {
				result = false;
			}

		} catch (ParseException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	// method to get the current date with time
	public String getCurrentDateWithTime() {

		logger.debug("inside getCurrentJavaSqlDate method");

		Date dNow = currentDateDao.getServerDateTime();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateTime = ft.format(dNow);

		getCurrentDate();
		return dateTime;
	}

	// method to get the current date with time
	public String getCurrentDate() {

		logger.debug("inside getCurrentJavaSqlDate method");

		Date dNow = currentDateDao.getServerDateTime();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = ft.format(dNow);
System.out.println("currnt date"+dateTime);
		return dateTime;
	}

	public Date getCurrentDateInDateDataType() {

		logger.debug("inside getCurrentJavaSqlDate method");

		Date dNow = currentDateDao.getServerDateTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		Date today;
		try {
			today = dateFormat.parse(dateFormat.format(dNow));

		} catch (ParseException e) {

		}

		return dNow;
	}

	public static String getFormattedDateTimeFromDB(String dBDate) {

		String dateWithoutMilliSec = dBDate.substring(0, 19);
		return dateWithoutMilliSec;

	}

	public Date getDateFormatByString(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.SIMPLE_DATE_FORMAT);
		Date resDate = null;
		try {
			resDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("current dateeeeeeeeeeeeee ddd"+sdf.format(resDate));
		return resDate;
	}

	public long getDiffBetweenTwoDates(Calendar startDate, Calendar toDate) {

		long miliSecondForDate1 = startDate.getTimeInMillis();
		long miliSecondForDate2 = toDate.getTimeInMillis();

		// Calculate the difference in millisecond between two dates
		long diffInMilis = miliSecondForDate2 - miliSecondForDate1;

		long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
		return diffInDays;
	}
	public List<String> getDateBetweenTwoDates(String startDate,
			String endDate){
		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.SIMPLE_DATE_FORMAT);
		calendar.setTime(getDateFormatByString(startDate));
		try {
			while (calendar.getTime().before(
					sdf.parse(endDate))
					|| calendar.getTime().equals(
							sdf.parse(endDate))) {
				Date resultado = calendar.getTime();
				
				dates.add(sdf.format(resultado));
				calendar.add(Calendar.DATE, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}
	public List<String> getMonthBetweenTwoDates(String startDate,
			String endDate){
		List<String> months = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.SIMPLE_DATE_FORMAT);
		calendar.setTime(getDateFormatByString(startDate));
		try {
			while (calendar.getTime().before(
					sdf.parse(endDate))
					|| calendar.getTime().equals(
							sdf.parse(endDate))) {
				  System.out.println("month letterrrrrrrrrr"+new SimpleDateFormat("MMM").format(calendar.getTime()));
				
				  int year = calendar.get(Calendar.YEAR);
				  System.out.println("year letterrrrrrrrrr"+year);
				  months.add(new SimpleDateFormat("MMM").format(calendar.getTime())+"-"+Integer.toString(year));
				calendar.add(Calendar.MONTH, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return months;
	}
	
	
	
	public List<String> getMonthStartAndEndDate(String monthAndYear){
		List<String> months = new ArrayList<String>();
		String[] my=monthAndYear.split("-");
		
		
		 Calendar cal = Calendar.getInstance();
		    try {
				cal.setTime(new SimpleDateFormat("MMM").parse(my[0]));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    int monthInt = cal.get(Calendar.MONTH) + 1;
	    //Start Date\
		    String startDate=Integer.toString(Integer.parseInt(my[1]))+"-"+Integer.toString(monthInt)+"-01";
		    months.add(startDate);
	    Calendar c = Calendar.getInstance();      
	    c.set(Integer.parseInt(my[1]),monthInt-1,1); 
	    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  

		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.SIMPLE_DATE_FORMAT);  
	    System.out.println("end dateeeeeeeeeeeeeee"+sdf.format(c.getTime()));
	    //end Date\
	    months.add(sdf.format(c.getTime()));
		return months;
	}
}

package com.kremski.android.calendar;

public class CalendarData {
	
	private String calendarID = null;
	private String calendarName  = null;
	private String calendarDisplayName = null;
	private boolean isCalendarSelected = false;
	
	public CalendarData(String calendarID, String calendarName,
			String calendarDisplayName, boolean isCalendarSelected) {
		this.calendarID = calendarID;
		this.calendarName = calendarName;
		this.calendarDisplayName = calendarDisplayName;
		this.isCalendarSelected = isCalendarSelected;
	}

	public String getCalendarID() {
		return calendarID;
	}
	
	public String getCalendarName() {
		return calendarName;
	}
	
	public String getCalendarDisplayName() {
		return calendarDisplayName;
	}
	
	public boolean isCalendarSelected() {
		return isCalendarSelected;
	}
	
	
}

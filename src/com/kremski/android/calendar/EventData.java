package com.kremski.android.calendar;

import java.util.Date;

public class EventData {

	private String title = null;
	private String eventLocation = null;
	private Date startDate = null;
	private Date endDate = null;

	public EventData(String title, String eventLocation, Date startDate, Date endDate) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.eventLocation = eventLocation;
	}

	public String getTitle() {
		return title;
	}
	
	public String getEventLocation() {
		return eventLocation;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return "Event [title=" + title + ", eventLocation=" + eventLocation
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	
	
	
}

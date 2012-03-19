package com.kremski.android.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class CalendarManager {
	
	private Context context = null;
	
	
	public CalendarManager(Context context) {
		this.context = context;
	}
	
	public List<CalendarData> getCalendars() {
    	final Cursor cursor = getCursorWithCalendars();
    	 
    	List<CalendarData> calendars = new LinkedList<CalendarData>();
    	
    	if (cursor != null) {
    		calendars = getAllCalendarsFromCursor(cursor);
	    	cursor.close();
    	}
    	
    	return calendars;
    }
	
	public Map<String, List<EventData>> getEventsFromCalendarsInDateRange(Date start, Date end, List<String> calendarIDs) {
		Map<String, List<EventData>> calendarsEvents = new HashMap<String, List<EventData>>();
		
		for (String calendarID : calendarIDs) {
			calendarsEvents.put(calendarID, getEventsFromCalendar(start, end, calendarID));
		}
		
		return calendarsEvents;
	}
	
	public void addEventToCalendarWithSpecifiedID(EventData event, Integer calendarID) {
		ContentValues cv = new ContentValues();
		
		cv.put("calendar_id", calendarID);
		cv.put("title", event.getTitle());
		cv.put("dtstart", event.getStartDate().getTime());
		cv.put("dtend", event.getEndDate().getTime());
		cv.put("eventLocation", event.getEventLocation());
		
		context.getContentResolver().insert(Uri.parse("content://com.android.calendar/events"), cv);
	}

    private Cursor getCursorWithCalendars() {
    	ContentResolver contentResolver = context.getContentResolver();
   	 
    	return contentResolver.query(Uri.parse("content://com.android.calendar/calendars"),
    	        new String[]{"_id", "name", "displayName", "selected"}, null, null, null);
    }
    
    private List<CalendarData> getAllCalendarsFromCursor(Cursor calendarsCursor) {
    	List<CalendarData> calendars = new LinkedList<CalendarData>();
    	
		while (calendarsCursor.moveToNext()) {
	    	calendars.add(getCalendarFromCursor(calendarsCursor));
    	}
    	
    	return calendars;
    }
    
    private CalendarData getCalendarFromCursor(Cursor calendarCursor) {
    	String calendarID = calendarCursor.getString(0);
    	String calendarName  = calendarCursor.getString(1);
    	String calendarDisplayName = calendarCursor.getString(2);
    	boolean isCalendarSelected = !calendarCursor.getString(3).equals("0");
    	
    	return new CalendarData(calendarID, calendarName, calendarDisplayName, isCalendarSelected);
    }
    
    private List<EventData> getEventsFromCalendar(Date start, Date end, String calendarID) {
        Cursor calendarCursor = getCursorPointedAtSpecifiedCalendar(start, end, calendarID);
        List<EventData> retrievedEvents = null;
        
        if (calendarCursor != null) {
        	retrievedEvents = getEventsFromCalendarCursor(calendarCursor);
        	calendarCursor.close();
        }
        
    	return retrievedEvents;
    }
    
    private Cursor getCursorPointedAtSpecifiedCalendar(Date start, Date end, String calendarID) {
    	Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
        ContentUris.appendId(builder, start.getTime());
        ContentUris.appendId(builder, end.getTime());
         
        return context.getContentResolver().query(builder.build(),
                new String[]{"title", "eventLocation", "dtstart", "dtend" },
                			"Calendars._id=" + calendarID,
                null, "startDay ASC, startMinute ASC");
    }
    
    private List<EventData> getEventsFromCalendarCursor(Cursor calendarCursor) {
    	List<EventData> retrievedEvents = new LinkedList<EventData>();
    	
    	while (calendarCursor.moveToNext()) {
            retrievedEvents.add(getEventFromCursor(calendarCursor));
        }
    	
    	return retrievedEvents;
    }
    
    private EventData getEventFromCursor(Cursor calendarCursor) {
    	return new EventData(calendarCursor.getString(0), calendarCursor.getString(1),
    			new Date(calendarCursor.getLong(2)),
    			new Date(calendarCursor.getLong(3)));
    }
}

package com.kremski.android.calendar;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

public class CalendarTestsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        CalendarManager gcm = new CalendarManager(this);
        List<String> calendarIDs = new LinkedList<String>();
        
        for(CalendarData c : gcm.getCalendars()) {
        	calendarIDs.add(c.getCalendarID());
        }
        
        Date eventStartDate = new Date(new Date().getTime()-DateUtils.DAY_IN_MILLIS);
        Date eventEndDate = new Date(new Date().getTime()+DateUtils.DAY_IN_MILLIS);
        
        Map<String, List<EventData>> calendarsEvents = gcm.getEventsFromCalendarsInDateRange(
        				eventStartDate, eventEndDate, calendarIDs);
        
        for(String calendarID : calendarsEvents.keySet()) {
        	Log.i(calendarID, calendarsEvents.get(calendarID).toString());
        }
       
        EventData e = new EventData("Some new EvENT", "EVERyWHERe", eventStartDate,  eventEndDate);
        
        for(String calendarID : calendarsEvents.keySet()) {
        	gcm.addEventToCalendarWithSpecifiedID(e, Integer.parseInt(calendarID));
        }
    }
    
}
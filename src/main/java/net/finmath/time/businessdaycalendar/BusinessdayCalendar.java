/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 15.09.2013
 */

package net.finmath.time.businessdaycalendar;

import java.util.Calendar;

/**
 * Base class for all business day calendars.
 * Implements date rolling.
 * 
 * @author Christian Fries
 */
public abstract class BusinessdayCalendar implements BusinessdayCalendarInterface {

	/* (non-Javadoc)
	 * @see net.finmath.time.BusinessdayCalendarInterface#getAdjustedDate(java.util.Calendar, net.finmath.time.BusinessdayCalendarInterface.DateRollConvention)
	 */
	@Override
	public Calendar getAdjustedDate(Calendar date, DateRollConvention dateRollConvention) {
		if(dateRollConvention == DateRollConvention.UNADJUSTED) {
			return date;
		}
		else if(dateRollConvention == DateRollConvention.MODIFIED_FOLLOWING) {
			Calendar adjustedDate = getAdjustedDate(date, DateRollConvention.FOLLOWING);
			if(adjustedDate.get(Calendar.MONTH) != date.get(Calendar.MONTH)) {
				return getAdjustedDate(date, DateRollConvention.PRECEDING);
			}
			else return adjustedDate;
		}
		else if(dateRollConvention == DateRollConvention.MODIFIED_PRECEDING) {
			Calendar adjustedDate = getAdjustedDate(date, DateRollConvention.PRECEDING);
			if(adjustedDate.get(Calendar.MONTH) != date.get(Calendar.MONTH)) {
				return getAdjustedDate(date, DateRollConvention.FOLLOWING);
			}
			else return adjustedDate;
		}
		else if(dateRollConvention == DateRollConvention.FOLLOWING || dateRollConvention == DateRollConvention.PRECEDING) {
			int adjustment = dateRollConvention == DateRollConvention.FOLLOWING ? 1 : -1;
			Calendar adjustedDate = (Calendar)date.clone();
			while(!isBusinessday(adjustedDate)) {
				adjustedDate.add(Calendar.DAY_OF_YEAR, adjustment);
			}
			return adjustedDate;
		}

		throw new IllegalArgumentException("Unknown date roll convention.");
	}
}
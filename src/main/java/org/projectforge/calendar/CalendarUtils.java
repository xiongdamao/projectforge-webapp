/////////////////////////////////////////////////////////////////////////////
//
// Project ProjectForge Community Edition
//         www.projectforge.org
//
// Copyright (C) 2001-2012 Kai Reinhard (k.reinhard@micromata.de)
//
// ProjectForge is dual-licensed.
//
// This community edition is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as published
// by the Free Software Foundation; version 3 of the License.
//
// This community edition is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
// Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, see http://www.gnu.org/licenses/.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.calendar;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.projectforge.common.DateHelper;

/**
 * @author Kai Reinhard (k.reinhard@micromata.de)
 */
public class CalendarUtils
{
  /**
   * Converts a given date (in user's timeZone) to midnight of UTC timeZone.
   * @param date
   * @return
   */
  public static Date getUTCMidnightDate(final Date date)
  {
    final Calendar utcCal = getUTCMidnightCalendar(date);
    return utcCal.getTime();
  }

  /**
   * Converts a given date (in user's timeZone) to midnight of UTC timeZone.
   * @param date
   * @return
   */
  public static Timestamp getUTCMidnightTimestamp(final Date date)
  {
    final Calendar cal = getUTCMidnightCalendar(date);
    return new Timestamp(cal.getTimeInMillis());
  }

  /**
   * Converts a given date (in user's timeZone) to midnight of UTC timeZone.
   * @param date
   * @return
   */
  public static Calendar getUTCMidnightCalendar(final Date date)
  {
    final Calendar usersCal = DateHelper.getCalendar();
    usersCal.setTime(date);
    final Calendar utcCal = DateHelper.getUTCCalendar();
    copyCalendarDay(usersCal, utcCal);
    return utcCal;
  }

  /**
   * Converts a given date (in UTC) to midnight of user's timeZone.
   * @param date
   * @return
   */
  public static Timestamp getMidnightTimestampFromUTC(final Date date)
  {
    final Calendar cal = getMidnightCalendarFromUTC(date);
    return new Timestamp(cal.getTimeInMillis());
  }

  /**
   * Converts a given date (in user's timeZone) to midnight of UTC timeZone.
   * @param date
   * @return
   */
  public static Calendar getMidnightCalendarFromUTC(final Date date)
  {
    final Calendar utcCal = DateHelper.getUTCCalendar();
    utcCal.setTime(date);
    final Calendar usersCal = DateHelper.getCalendar();
    copyCalendarDay(utcCal, usersCal);
    return usersCal;
  }

  private static void copyCalendarDay(final Calendar src, final Calendar dest)
  {
    copyCalField(src, dest, Calendar.YEAR);
    copyCalField(src, dest, Calendar.MONTH);
    copyCalField(src, dest, Calendar.DAY_OF_MONTH);
    dest.set(Calendar.HOUR_OF_DAY, 0);
    dest.set(Calendar.MINUTE, 0);
    dest.set(Calendar.SECOND, 0);
    dest.set(Calendar.MILLISECOND, 0);
  }

  private static void copyCalField(final Calendar src, final Calendar dest, final int field)
  {
    dest.set(field, src.get(field));
  }
}
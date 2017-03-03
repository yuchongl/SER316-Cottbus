package net.sf.memoranda.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.io.*;

import net.sf.memoranda.date.CalendarDate;

/**
 * Provides locale info.
 */
/* $Id: Local.java,v 1.6 2004/10/11 08:48:21 alexeya Exp $ */
public class Local {

  static Locale currentLocale = Locale.getDefault();
  static LoadableProperties messages = new LoadableProperties();
  static boolean disabled = false;

  // changed Jan to January and Feb to February
  static String monthnames[] = { "January", "February", "March", "April", "May", "June", "July",
      "August", "September", "October", "November", "December" };

  static String weekdaynames[] = { "S", "M", "T", "W", "T", "F", "S" };

  static {
    if (!Configuration.get("DISABLE_L10N").equals("yes")) {
      String fn = "messages_" + currentLocale.getLanguage() + ".properties";
      if (Configuration.get("LOCALES_DIR") != "") {
        System.out.print("Look " + fn + " at: " + Configuration.get("LOCALES_DIR") + " ");
        try {
          messages.load(new FileInputStream(Configuration.get("LOCALES_DIR") 
              + File.separator + fn));
          System.out.println(" - found");
        } catch (IOException ex) {
          // Do nothing ...
          System.out.println(" - not found");
          ex.printStackTrace();
        }
      }
      if (messages.size() == 0) {
        try {
          messages.load(Local.class.getResourceAsStream("localmessages/" + fn));
        } catch (Exception e) {
          // Do nothing ...
        }
      }
    } else {
      currentLocale = new Locale("en", "US");
      /* DEBUG */
      System.out.println("* DEBUG: Locales are disabled");
    }
    if (messages.size() == 0) {
      messages = null;
    }

    /*** DEBUG PURPOSES ***/
    System.out.println("Default locale: " + currentLocale.getDisplayName());
    if (messages != null) {
      System.out.println("Use local messages: messages_" 
          + currentLocale.getLanguage() + ".properties");
    } else {
      System.out.println("* DEBUG: Locales are disabled or not found: messages_"
          + currentLocale.getLanguage() + ".properties");
    }
    /**********************/
  }

  public static Hashtable<?, ?> getMessages() {
    return messages;
  }

  public static Locale getCurrentLocale() {
    return currentLocale;
  }

  public static String getString(String key) {
    if ((messages == null) || (disabled)) {
      return key;
    }
    String msg = (String) messages.get(key.trim().toUpperCase());
    if ((msg != null) && (msg.length() > 0)) {
      return msg;
    } else {
      return key;
    }
  }

  public static String[] getMonthNames() {
    String[] localmonthnames = new String[12];
    for (int i = 0; i < 12; i++) {
      localmonthnames[i] = getString(monthnames[i]);
    }
    return localmonthnames;
  }

  public static String[] getWeekdayNames() {
    String[] localwdnames = new String[7];
    String[] localnames = weekdaynames;

    if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
      localnames = new String[] { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
    }

    for (int i = 0; i < 7; i++) {
      localwdnames[i] = getString(localnames[i]);
    }
    return localwdnames;
  }

  public static String getMonthName(int mnt) {
    return getString(monthnames[mnt]);
  }

  public static String getWeekdayName(int wd) {
    return getString(weekdaynames[wd - 1]);
  }

  public static String getDateString(Date dte, int fmt) {
    DateFormat dateFormat = DateFormat.getDateInstance(fmt, currentLocale);
    return dateFormat.format(dte);
  }

  public static String getDateString(Calendar cal, int fmt) {
    /* @todo: Get date string format from locale */
    /*
     * String s = getMonthName(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.DAY_OF_MONTH) +
     * ", " + cal.get(Calendar.YEAR) + " (" + getWeekdayName(cal.get(Calendar.DAY_OF_WEEK)) + ")";
     * return s;
     */
    return getDateString(cal.getTime(), fmt);
  }

  public static String getDateString(CalendarDate date, int fmt) {
    return getDateString(date.getDate(), fmt);
  }

  public static String getDateString(int mnt, int day, int yr, int fmt) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, mnt);
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.YEAR, yr);

    // String s = getMonthName(m) + " " + d + ", "
    // + y + " (" + getWeekdayName(cal.get(Calendar.DAY_OF_WEEK)) + ")";
    return getDateString(cal.getTime(), fmt);
  }

  public static String getTimeString(Date date) {
    DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, currentLocale);
    return dateFormat.format(date);
  }

  public static String getTimeString(Calendar cal) {
    /*
     * String h = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)); if (h.length() < 2) { h = "0" + h;
     * } String m = String.valueOf(cal.get(Calendar.MINUTE)); if (m.length() < 2) { m = "0" + m; }
     * return h + ":" + m;
     */
    return getTimeString(cal.getTime());
  }

  public static String getTimeString(int hh, int mm) {
    /*
     * String h = String.valueOf(hh); if (h.length() < 2) { h = "0" + h; } String m =
     * String.valueOf(mm); if (m.length() < 2) { m = "0" + m; } return h + ":" + m;
     */
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, hh);
    cal.set(Calendar.MINUTE, mm);
    return getTimeString(cal.getTime());
  }

  public static int[] parseTimeString(String str) {
    str = str.trim();
    String hr = "";
    String mn = "";
    if (str.indexOf(":") > 0) {
      hr = str.substring(0, str.indexOf(":"));
      mn = str.substring(str.indexOf(":") + 1);
    } else if (str.indexOf(":") == 0) {
      hr = "0";
      mn = str;
    } else {
      hr = str;
      mn = "0";
    }
    int[] time = new int[2];
    try {
      time[0] = new Integer(hr).intValue();
      if ((time[0] < 0) || (time[0] > 23)) {
        time[0] = 0;
      }
    } catch (NumberFormatException nfe) {
      return null;
    }
    try {
      time[1] = new Integer(mn).intValue();
      if ((time[1] < 0) || (time[1] > 59)) {
        time[1] = 0;
      }
    } catch (NumberFormatException nfe) {
      return null;
    }
    return time;
  }

}

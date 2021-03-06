 package lottery.utils.date;

 import java.io.PrintStream;
 import java.text.DateFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;



 public class DateUtil
 {
   public static String getCurrentTime()
   {
     GregorianCalendar g = new GregorianCalendar();
     return dateToString(g);
   }




   public static String getCurrentDate()
   {
     GregorianCalendar g = new GregorianCalendar();
     return dateToStringSim(g);
   }




   public static String getYesterday()
   {
     GregorianCalendar g = new GregorianCalendar();
     g.set(5, g.get(5) - 1);
     return dateToStringSim(g);
   }




   public static String getTomorrow()
   {
     GregorianCalendar g = new GregorianCalendar();
     g.set(5, g.get(5) + 1);
     return dateToStringSim(g);
   }





   public static String getTime(long ms)
   {
     GregorianCalendar g = new GregorianCalendar();
     g.setTimeInMillis(ms);
     return dateToString(g);
   }





   private static GregorianCalendar getCalendarByTime(String time, String format)
   {
     GregorianCalendar g = new GregorianCalendar();
     try {
       g.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
     } catch (ParseException e) {
       g = null;
     }
     return g;
   }






   private static String calcDate(GregorianCalendar g, int seconds)
   {
     if (g == null) {
       g = new GregorianCalendar();
     }
     g.add(13, seconds);
     return dateToString(g);
   }




   public static String dateToString(Date date)
   {
     try
     {
       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       return formatter.format(date);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return null;
   }




   public static Date stringToDate(String date)
   {
     try
     {
       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       return formatter.parse(date);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return null;
   }





   public static Date stringToDate(String date, String format)
   {
     try
     {
       DateFormat formatter = new SimpleDateFormat(format);
       return formatter.parse(date);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return null;
   }






   public static long calcDate(String subDate, String minDate)
   {
     long lSubDate = getCalendarByTime(subDate, "yyyy-MM-dd HH:mm:ss").getTimeInMillis();
     long lMinDate = getCalendarByTime(minDate, "yyyy-MM-dd HH:mm:ss").getTimeInMillis();
     return lSubDate - lMinDate;
   }






   public static String calcDateByTime(String time, int seconds)
   {
     GregorianCalendar g = getCalendarByTime(time, "yyyy-MM-dd HH:mm:ss");
     return calcDate(g, seconds);
   }






   public static String calcNewDay(String date, int days)
   {
     GregorianCalendar g = new GregorianCalendar();
     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
     g.set(5, g.get(5) + days);
     return dateToStringSim(g);
   }





   public static String calcNextDay(String date)
   {
     GregorianCalendar g = new GregorianCalendar();
     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
     g.set(5, g.get(5) + 1);
     return dateToStringSim(g);
   }





   public static String calcLastDay(String date)
   {
     GregorianCalendar g = new GregorianCalendar();
     g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
     g.set(5, g.get(5) - 1);
     return dateToStringSim(g);
   }

   private static Calendar getDateOfMonth(Calendar date, int num, boolean flag) {
     Calendar lastDate = (Calendar)date.clone();
     if (flag) {
       lastDate.add(2, num);
     } else {
       lastDate.add(2, -num);
     }

     return lastDate;
   }

   private static Calendar getDateOfLastMonth(String dateStr, int num, boolean flag) {
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     try {
       Date date = sdf.parse(dateStr);
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       return getDateOfMonth(c, num, flag);
     } catch (Exception e) {
       throw new IllegalArgumentException("Invalid date format(yyyyMMdd): " + dateStr);
     }
   }








   public static String getSameDateOfLastMonth(String date, int num, boolean flag)
   {
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     String lastDate = sdf.format(getDateOfLastMonth(date, num, flag).getTime());
     return lastDate;
   }





   private static String dateToString(GregorianCalendar g)
   {
     String year = String.valueOf(g.get(1));
     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
     String hours = String.format("%02d", new Object[] { Integer.valueOf(g.get(11)) });
     String minutes = String.format("%02d", new Object[] { Integer.valueOf(g.get(12)) });
     String seconds = String.format("%02d", new Object[] { Integer.valueOf(g.get(13)) });
     return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
   }






   public static long stringToLong(String time, String format)
   {
     return stringToDate(time, format).getTime();
   }





   public static String dateToStringSim(GregorianCalendar g)
   {
     String year = String.valueOf(g.get(1));
     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
     return year + "-" + month + "-" + day;
   }




   public static String getCurTimeStr()
   {
     GregorianCalendar g = new GregorianCalendar();
     String year = String.valueOf(g.get(1));
     String month = String.format("%02d", new Object[] { Integer.valueOf(g.get(2) + 1) });
     String day = String.format("%02d", new Object[] { Integer.valueOf(g.get(5)) });
     String hours = String.format("%02d", new Object[] { Integer.valueOf(g.get(11)) });
     String minutes = String.format("%02d", new Object[] { Integer.valueOf(g.get(12)) });
     String seconds = String.format("%02d", new Object[] { Integer.valueOf(g.get(13)) });

     return year + month + day + hours + minutes + seconds;
   }








   public static String formatTime(String time, String oldFormat, String newFormat)
   {
     return new SimpleDateFormat(newFormat).format(stringToDate(time, oldFormat));
   }







   public static String formatTime(Date date, String format)
   {
     return new SimpleDateFormat(format).format(date);
   }






   public static String formatTime(long time, String format)
   {
     return formatTime(getTime(time), "yyyy-MM-dd HH:mm:ss", format);
   }






   public static long formatTime(String time, String format)
   {
     return stringToDate(time, format).getTime();
   }

   public static String dateForm(String date, String config) {
     if ("MM/dd/yyyy".equals(config)) {
       String[] dateStrs = date.split("/");
       return dateStrs[2] + "-" + dateStrs[0] + "-" + dateStrs[1];
     }
     if ("MM-dd-yyyy".equals(config)) {
       String[] dateStrs = date.split("-");
       return dateStrs[2] + "-" + dateStrs[0] + "-" + dateStrs[1];
     }
     return null;
   }

   public static int getYear(String time) {
     return Integer.parseInt(time.substring(0, 4));
   }

   public static int getMonth(String time) {
     return Integer.parseInt(time.substring(5, 7));
   }

   public static int getDay(String time) {
     return Integer.parseInt(time.substring(8, 10));
   }

   public static int getHours(String time) {
     return Integer.parseInt(time.substring(11, 13));
   }

   public static int getMinutes(String time) {
     return Integer.parseInt(time.substring(14, 16));
   }

   public static int getSeconds(String time) {
     return Integer.parseInt(time.substring(17));
   }

   public static int getYear() {
     GregorianCalendar g = new GregorianCalendar();
     return g.get(1);
   }

   public static int getMonth() {
     GregorianCalendar g = new GregorianCalendar();
     return g.get(2) + 1;
   }

   public static int getDay() {
     GregorianCalendar g = new GregorianCalendar();
     return g.get(5);
   }

   public static int getHours() {
     GregorianCalendar g = new GregorianCalendar();
     return g.get(11);
   }

   public static int getMinutes() {
     GregorianCalendar g = new GregorianCalendar();
     return g.get(12);
   }

   public static int getSeconds() {
     GregorianCalendar g = new GregorianCalendar();
     return g.get(13);
   }

   public static void main(String[] args) {
     System.out.println("年:" + getYear());
     System.out.println("月:" + getMonth());
     System.out.println("日:" + getDay());
     System.out.println("时:" + getHours());
     System.out.println("分:" + getMinutes());
     System.out.println("秒:" + getSeconds());
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/date/DateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
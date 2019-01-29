 package lottery.utils.date;

 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;




 public class WeekUtil
 {
   public static int getWeekOfYear(Date date)
   {
     Calendar c = new GregorianCalendar();
     c.setFirstDayOfWeek(2);
     c.setMinimalDaysInFirstWeek(7);
     c.setTime(date);
     return c.get(3);
   }





   public static int getMaxWeekNumOfYear(int year)
   {
     Calendar c = new GregorianCalendar();
     c.set(year, 11, 31, 23, 59, 59);
     return getWeekOfYear(c.getTime());
   }






   public static Date getFirstDayOfWeek(int year, int week)
   {
     Calendar c = new GregorianCalendar();
     c.set(1, year);
     c.set(2, 0);
     c.set(5, 1);

     Calendar cal = (GregorianCalendar)c.clone();
     cal.add(5, week * 7);
     return getFirstDayOfWeek(cal.getTime(), 2);
   }






   public static Date getLastDayOfWeek(int year, int week)
   {
     Calendar c = new GregorianCalendar();
     c.set(1, year);
     c.set(2, 0);
     c.set(5, 1);

     Calendar cal = (GregorianCalendar)c.clone();
     cal.add(5, week * 7);

     return getLastDayOfWeek(cal.getTime(), 2);
   }





   public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek)
   {
     Calendar c = new GregorianCalendar();
     c.setFirstDayOfWeek(firstDayOfWeek);
     c.setTime(date);
     c.set(7, c.getFirstDayOfWeek());
     return c.getTime();
   }





   public static Date getLastDayOfWeek(Date date, int firstDayOfWeek)
   {
     Calendar c = new GregorianCalendar();
     c.setFirstDayOfWeek(firstDayOfWeek);
     c.setTime(date);
     c.set(7, c.getFirstDayOfWeek() + 6);
     return c.getTime();
   }





   public static String getFirstDayOfWeek(String time)
   {
     long date = DateUtil.formatTime(time, "yyyy-MM-dd");
     Calendar c = new GregorianCalendar();
     c.setFirstDayOfWeek(1);
     c.setTimeInMillis(date);
     c.set(7, c.getFirstDayOfWeek());
     String firstDayOfWeek = DateUtil.formatTime(c.getTime(), "yyyy-MM-dd");
     return firstDayOfWeek;
   }





   public static String getLastDayOfWeek(String time)
   {
     long date = DateUtil.formatTime(time, "yyyy-MM-dd");
     Calendar c = new GregorianCalendar();
     c.setFirstDayOfWeek(1);
     c.setTimeInMillis(date);
     c.set(7, c.getFirstDayOfWeek() + 6);
     String lastDayOfWeek = DateUtil.formatTime(c.getTime(), "yyyy-MM-dd");
     return lastDayOfWeek;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/date/WeekUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
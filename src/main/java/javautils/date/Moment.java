 package javautils.date;

 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.GregorianCalendar;

 public class Moment
 {
   public GregorianCalendar gc;

   public static void main(String[] args)
   {
     Moment moment = new Moment();
     System.out.println(moment.difference(new Moment()
       .fromDate("2015-01-18"), "hour"));
   }

   static {
     java.util.TimeZone china = java.util.TimeZone.getTimeZone("Asia/Shanghai");
     java.util.TimeZone.setDefault(china);
   }


   public Moment()
   {
     this.gc = new GregorianCalendar();
     this.gc.setFirstDayOfWeek(2);
   }

   public Moment(GregorianCalendar gc) {
     this.gc = gc;
   }





   public Moment fromDate(String date)
   {
     try
     {
       DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       java.util.Date d = format.parse(date);
       this.gc.setTime(d);
     }
     catch (Exception localException) {}
     return this;
   }





   public Moment fromTime(String time)
   {
     try
     {
       DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       java.util.Date d = format.parse(time);
       this.gc.setTime(d);
     }
     catch (Exception localException) {}
     return this;
   }






   public Moment fromTime(String time, String formatStr)
   {
     try
     {
       DateFormat format = new SimpleDateFormat(formatStr);
       java.util.Date d = format.parse(time);
       this.gc.setTime(d);
     }
     catch (Exception localException) {}
     return this;
   }





   public String toSimpleTime()
   {
     return
       year() + "-" + months() + "-" + days() + " " + hours() + ":" + minutes() + ":" + seconds();
   }

   public String toCurTime() {
     return hours() + ":" + minutes() + ":" + seconds();
   }





   public String toFullTime()
   {
     return toSimpleTime() + "." + milliseconds();
   }





   public String toSimpleDate()
   {
     return year() + "-" + months() + "-" + days();
   }





   public java.util.Date toDate()
   {
     return this.gc.getTime();
   }






   public String format(String format)
   {
     return new SimpleDateFormat(format).format(toDate());
   }






   public int year()
   {
     return this.gc.get(1);
   }





   public Moment year(int year)
   {
     this.gc.set(1, year);
     return this;
   }





   public int month()
   {
     return this.gc.get(2) + 1;
   }





   public String months()
   {
     return String.format("%02d", new Object[] { Integer.valueOf(month()) });
   }






   public Moment month(int month)
   {
     if ((month > 0) && (month <= 12)) {
       this.gc.set(2, month - 1);
     }
     return this;
   }






   public Moment month(String month)
   {
     month(Integer.parseInt(month));
     return this;
   }





   public int day()
   {
     return this.gc.get(5);
   }





   public String days()
   {
     return String.format("%02d", new Object[] { Integer.valueOf(day()) });
   }






   public Moment day(int day)
   {
     if ((day > 0) && (day <= 31)) {
       this.gc.set(5, day);
     }
     return this;
   }






   public Moment day(String day)
   {
     day(Integer.parseInt(day));
     return this;
   }





   public int hour()
   {
     return this.gc.get(11);
   }





   public String hours()
   {
     return String.format("%02d", new Object[] { Integer.valueOf(hour()) });
   }






   public Moment hour(int hour)
   {
     if ((hour >= 0) && (hour < 24)) {
       this.gc.set(11, hour);
     }
     return this;
   }






   public Moment hour(String hour)
   {
     hour(Integer.parseInt(hour));
     return this;
   }





   public int minute()
   {
     return this.gc.get(12);
   }





   public String minutes()
   {
     return String.format("%02d", new Object[] { Integer.valueOf(minute()) });
   }






   public Moment minute(int minute)
   {
     if ((minute >= 0) && (minute < 60)) {
       this.gc.set(12, minute);
     }
     return this;
   }






   public Moment minute(String minute)
   {
     minute(Integer.parseInt(minute));
     return this;
   }





   public int second()
   {
     return this.gc.get(13);
   }





   public String seconds()
   {
     return String.format("%02d", new Object[] { Integer.valueOf(second()) });
   }






   public Moment second(int second)
   {
     if ((second >= 0) && (second < 60)) {
       this.gc.set(13, second);
     }
     return this;
   }






   public Moment second(String second)
   {
     second(Integer.parseInt(second));
     return this;
   }





   public int millisecond()
   {
     return this.gc.get(14);
   }





   public String milliseconds()
   {
     return String.format("%03d", new Object[] { Integer.valueOf(millisecond()) });
   }






   public Moment millisecond(int millisecond)
   {
     if ((millisecond >= 0) && (millisecond < 1000)) {
       this.gc.set(14, millisecond);
     }
     return this;
   }






   public Moment millisecond(String millisecond)
   {
     millisecond(Integer.parseInt(millisecond));
     return this;
   }





   public int weekOfMonth()
   {
     return this.gc.get(4);
   }





   public int weekOfYear()
   {
     return this.gc.get(3);
   }





   public int dayOfWeek()
   {
     int field = this.gc.get(7);
     switch (field) {
     case 1:
       return 7;
     case 2:
       return 1;
     case 3:
       return 2;
     case 4:
       return 3;
     case 5:
       return 4;
     case 6:
       return 5;
     case 7:
       return 6;
     }


     return this.gc.getFirstDayOfWeek();
   }






   public Moment dayOfWeek(int week)
   {
     switch (week) {
     case 7:
       this.gc.set(7, 1);
       break;
     case 1:
       this.gc.set(7, 2);
       break;
     case 2:
       this.gc.set(7, 3);
       break;
     case 3:
       this.gc.set(7, 4);
       break;
     case 4:
       this.gc.set(7, 5);
       break;
     case 5:
       this.gc.set(7, 6);
       break;
     case 6:
       this.gc.set(7, 7);
       break;
     }


     return this;
   }





   public int dayOfYear()
   {
     return this.gc.get(6);
   }






   public Moment dayOfYear(int day)
   {
     this.gc.set(6, day);
     return this;
   }





   public int quarter()
   {
     int month = month();
     switch (month) {
     case 1:
     case 2:
     case 3:
       return 1;
     case 4:
     case 5:
     case 6:
       return 2;
     case 7:
     case 8:
     case 9:
       return 3;
     case 10:
     case 11:
     case 12:
       return 4;
     }
     return 0;
   }







   public int get(String key)
   {
     switch (key) {
     case "year":
       return year();
     case "month":
       return month();
     case "day":
     case "date":
       return day();
     case "hour":
       return hour();
     case "minute":
       return minute();
     case "second":
       return second();
     case "millisecond":
       return millisecond();
     }
     return 0;
   }








   public Moment set(String key, int value)
   {
     switch (key) {
     case "year":
       year(value);
       break;
     case "month":
       month(value);
       break;
     case "day":
     case "date":
       day(value);
       break;
     case "hour":
       hour(value);
       break;
     case "minute":
       minute(value);
       break;
     case "second":
       second(value);
       break;
     case "millisecond":
       millisecond(value);
       break;
     }


     return this;
   }





   public long millis()
   {
     return this.gc.getTimeInMillis();
   }






   public static Moment max(Moment... moment)
   {
     Moment max = null;
     if ((moment != null) && (moment.length > 0)) {
       for (Moment tmp : moment) {
         if (max == null) {
           max = tmp;
         }
         if (max.millis() < tmp.millis()) {
           max = tmp;
         }
       }
     }
     return max;
   }






   public static Moment min(Moment... moment)
   {
     Moment min = null;
     if ((moment != null) && (moment.length > 0)) {
       for (Moment tmp : moment) {
         if (min == null) {
           min = tmp;
         }
         if (min.millis() > tmp.millis()) {
           min = tmp;
         }
       }
     }
     return min;
   }







   public Moment add(int amount, String key)
   {
     switch (key) {
     case "years":
     case "y":
       this.gc.add(1, amount);
       break;
     case "quarters":
     case "Q":
       this.gc.add(2, amount * 3);
       break;
     case "months":
     case "M":
       this.gc.add(2, amount);
       break;
     case "weeks":
     case "w":
       this.gc.add(3, amount);
       break;
     case "days":
     case "d":
       this.gc.add(5, amount);
       break;
     case "hours":
     case "h":
       this.gc.add(11, amount);
       break;
     case "minutes":
     case "m":
       this.gc.add(12, amount);
       break;
     case "seconds":
     case "s":
       this.gc.add(13, amount);
       break;
     case "milliseconds":
     case "ms":
       this.gc.add(14, amount);
       break;
     }


     return this;
   }







   public Moment subtract(int amount, String key)
   {
     return add(-amount, key);
   }






   public Moment startOf(String key)
   {
     Moment moment = new Moment((GregorianCalendar)this.gc.clone());
     switch (key) {
     case "year":
       moment.dayOfYear(1).hour(0).minute(0).second(0).millisecond(0);
       break;
     case "month":
       moment.day(1).hour(0).minute(0).second(0).millisecond(0);
       break;
     case "week":
       moment.dayOfWeek(1).hour(0).minute(0).second(0).millisecond(0);
       break;
     case "day":
       moment.hour(0).minute(0).second(0).millisecond(0);
       break;
     case "hour":
       moment.minute(0).second(0).millisecond(0);
     case "minute":
       moment.second(0).millisecond(0);
       break;
     case "second":
       moment.millisecond(0);
       break;
     }


     return moment;
   }






   public Moment endOf(String key)
   {
     Moment moment = new Moment();
     switch (key) {
     case "year":
       return
         moment.startOf(key).add(1, "years").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
     case "month":
       return
         moment.startOf(key).add(1, "months").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
     case "week":
       return
         moment.startOf(key).add(1, "weeks").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
     case "day":
       return
         moment.startOf(key).hour(23).minute(59).second(59).millisecond(999);
     case "hour":
       return moment.startOf(key).minute(59).second(59).millisecond(999);
     case "minute":
       return moment.startOf(key).second(59).millisecond(999);
     case "second":
       return moment.startOf(key).millisecond(999);
     }


     return moment;
   }







   public int difference(Moment moment, String key)
   {
     switch (key) {
     case "year":
       return year() - moment.year();
     case "month":
       return
         difference(moment, "year") * 12 + (month() - moment.month());
     case "day":
       return (int)((millis() - moment.millis()) / 86400000L);
     case "hour":
       return (int)((millis() - moment.millis()) / 3600000L);
     case "minute":
       return (int)((millis() - moment.millis()) / 60000L);
     case "second":
       return (int)((millis() - moment.millis()) / 1000L);
     }


     return 0;
   }






   public boolean gt(Moment moment)
   {
     return millis() > moment.millis();
   }






   public boolean lt(Moment moment)
   {
     return millis() < moment.millis();
   }






   public boolean ge(Moment moment)
   {
     return millis() >= moment.millis();
   }






   public boolean le(Moment moment)
   {
     return millis() <= moment.millis();
   }







   public boolean between(Moment sMoment, Moment eMoment)
   {
     return (ge(sMoment)) && (le(eMoment));
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/date/Moment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
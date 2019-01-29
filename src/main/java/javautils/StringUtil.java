 package javautils;

 import java.io.InputStream;
 import java.text.DecimalFormat;
 import java.text.SimpleDateFormat;
 import javautils.date.Moment;
 import javautils.math.MathUtil;
 import javautils.regex.RegexUtil;






 public class StringUtil
 {
   public static boolean isNotNull(String s)
   {
     if (s == null) return false;
     if (s.trim().length() == 0) return false;
     return true;
   }




   public static boolean isInteger(String s)
   {
     try
     {
       Integer.parseInt(s);
     } catch (Exception e) {
       return false;
     }
     return true;
   }




   public static boolean isDouble(String s)
   {
     try
     {
       Double.parseDouble(s);
     } catch (Exception e) {
       return false;
     }
     return true;
   }




   public static boolean isFloat(String s)
   {
     try
     {
       Float.parseFloat(s);
     } catch (Exception e) {
       return false;
     }
     return true;
   }




   public static boolean isBoolean(String s)
   {
     try
     {
       Boolean.parseBoolean(s);
     } catch (Exception e) {
       return false;
     }
     return true;
   }




   public static boolean isShort(String s)
   {
     try
     {
       Short.parseShort(s);
     } catch (Exception e) {
       return false;
     }
     return true;
   }




   public static boolean isLong(String s)
   {
     try
     {
       Long.parseLong(s);
     } catch (Exception e) {
       return false;
     }
     return true;
   }






   public static Object transObject(String s, Class<?> clazz)
   {
     if (clazz != null) {
       if (clazz == Integer.class) {
         if (isInteger(s)) {
           return Integer.valueOf(Integer.parseInt(s));
         }
         return Integer.valueOf(0);
       }
       if (clazz == Double.class) {
         if (isDouble(s)) {
           return Double.valueOf(Double.parseDouble(s));
         }
         return Integer.valueOf(0);
       }
       if (clazz == Float.class) {
         if (isFloat(s)) {
           return Float.valueOf(Float.parseFloat(s));
         }
         return Integer.valueOf(0);
       }
       if (clazz == Boolean.class) {
         if (isBoolean(s)) {
           return Boolean.valueOf(Boolean.parseBoolean(s));
         }
         return Boolean.valueOf(true);
       }
       if (clazz == Short.class) {
         if (isShort(s)) {
           return Short.valueOf(Short.parseShort(s));
         }
         return Integer.valueOf(0);
       }
       if (clazz == Long.class) {
         if (isLong(s)) {
           return Long.valueOf(Long.parseLong(s));
         }
         return Integer.valueOf(0);
       }
     }
     return s;
   }

   public static String transArrayToString(Object[] array) {
     StringBuffer sb = new StringBuffer();
     int i = 0; for (int j = array.length; i < j; i++) {
       sb.append(array[i].toString());
       if (i < j - 1) {
         sb.append(",");
       }
     }
     return sb.toString();
   }

   public static String transArrayToString(Object[] array, String p) {
     String tempStr = new String();
     for (Object value : array) {
       tempStr = tempStr + p + value.toString() + p + ",";
     }
     tempStr = tempStr.substring(0, tempStr.length() - 2);
     return tempStr;
   }

   public static int[] transStringToIntArray(String string, String regex) {
     if (isNotNull(string)) {
       String[] sArray = string.split(regex);
       int[] intArray = new int[sArray.length];
       for (int i = 0; i < sArray.length; i++) {
         if (isIntegerString(sArray[i])) {
           intArray[i] = Integer.parseInt(sArray[i]);
         }
       }
       return intArray;
     }
     return null;
   }





   public static String transDataLong(long b)
   {
     StringBuffer sb = new StringBuffer();
     long KB = 1024L;
     long MB = KB * 1024L;
     long GB = MB * 1024L;
     long TB = GB * 1024L;
     if (b >= TB) {
       sb.append(MathUtil.doubleFormat(b / TB, 2) + "TB");
     } else if (b >= GB) {
       sb.append(MathUtil.doubleFormat(b / GB, 2) + "GB");
     } else if (b >= MB) {
       sb.append(MathUtil.doubleFormat(b / MB, 2) + "MB");
     } else if (b >= KB) {
       sb.append(MathUtil.doubleFormat(b / KB, 2) + "KB");
     } else {
       sb.append(b + "B");
     }
     return sb.toString();
   }






   public static boolean isIntegerString(String str)
   {
     boolean flag = false;
     if (RegexUtil.isMatcher(str, "^-?\\d+$")) {
       flag = true;
     }
     return flag;
   }

   public static boolean isDoubleString(String str) {
     boolean flag = false;
     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
       flag = true;
     }
     return flag;
   }

   public static boolean isFloatString(String str) {
     boolean flag = false;
     if (RegexUtil.isMatcher(str, "^(-?\\d+)(\\.\\d+)?$")) {
       flag = true;
     }
     return flag;
   }




   public static boolean isDateString(String str)
   {
     try
     {
       if (str.length() != 10) {
         return false;
       }
       new SimpleDateFormat("yyyy-MM-dd").parse(str);
     } catch (Exception e) {
       return false;
     }
     return true;
   }

   public static String markWithSymbol(Object obj, String symbol) {
     return symbol + obj.toString() + symbol;
   }

   public static Object[] split(String s)
   {
     char[] carr = s.toCharArray();
     Object[] arr = new Object[carr.length];
     for (int i = 0; i < carr.length; i++) {
       arr[i] = Character.valueOf(carr[i]);
     }
     return arr;
   }

   public static String substring(String s, String start, String end, boolean isInSub) {
     int idx = s.indexOf(start);
     int edx = s.indexOf(end);
     idx = idx == -1 ? 0 : idx + (isInSub ? 0 : start.length());
     edx = edx == -1 ? s.length() : edx + (isInSub ? end.length() : 0);
     return s.substring(idx, edx);
   }

   public static String doubleFormat(double d) {
     DecimalFormat decimalformat = new DecimalFormat("#0.00");
     return decimalformat.format(d);
   }

   public static String fromInputStream(InputStream inputStream) {
     try {
       StringBuffer sb = new StringBuffer();
       byte[] bytes = new byte['Ѐ'];
       int len;
       while ((len = inputStream.read(bytes)) != -1) {
         sb.append(new String(bytes, 0, len));
       }
       inputStream.close();
       return sb.toString();
     } catch (Exception localException) {}
     return null;
   }






   public static boolean isServiceTime(Moment moment, String times)
   {
     int thisMins = moment.hour() * 60 + moment.minute();
     String[] timeArr = times.split("~");
     int startMins = Integer.parseInt(timeArr[0].split(":")[0]) * 60 + Integer.parseInt(timeArr[0].split(":")[1]);
     int endMins = Integer.parseInt(timeArr[1].split(":")[0]) * 60 + Integer.parseInt(timeArr[1].split(":")[1]);
     if (startMins < endMins)
     {
       if (((thisMins > 0) && (thisMins < startMins)) || ((thisMins > endMins) && (thisMins < 1440))) {
         return false;
       }

     }
     else if ((thisMins > endMins) && (thisMins < startMins)) {
       return false;
     }

     return true;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/StringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
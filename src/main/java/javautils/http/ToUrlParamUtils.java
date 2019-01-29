 package javautils.http;

 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;




 public class ToUrlParamUtils
 {
   private static final String EMPTY = "";
   private static final String EQUALS = "=";
   private static final String DEFAULT_SEPARATOR = "&";

   public static String toUrlParam(Map<String, String> params)
   {
     return toUrlParam(params, "&", true);
   }

   public static String toUrlParam(Map<String, String> params, String separator) {
     return toUrlParam(params, separator, true);
   }

   public static String toUrlParam(Map<String, String> params, String separator, boolean ignoreEmpty) {
     if ((params == null) || (params.isEmpty())) {
       return "";
     }

     StringBuffer url = new StringBuffer();

     Iterator<Entry<String, String>> it = params.entrySet().iterator();
     while (it.hasNext()) {
       Entry<String, String> entry = (Entry)it.next();
       String value = (String)entry.getValue();
       boolean valueIsEmpty = isEmpty(value);
       if ((!ignoreEmpty) || (!valueIsEmpty))
       {


         url.append((String)entry.getKey()).append("=").append(value);
         if (it.hasNext()) {
           url.append(separator);
         }
       }
     }
     return url.toString();
   }

   private static boolean isEmpty(String value) {
     return (value == null) || ("".equals(value));
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/http/ToUrlParamUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
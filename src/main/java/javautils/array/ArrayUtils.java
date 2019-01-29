 package javautils.array;

 import java.util.List;





 public class ArrayUtils
 {
   public static String transInIds(int[] ids)
   {
     StringBuffer sb = new StringBuffer();
     int i = 0; for (int j = ids.length; i < j; i++) {
       sb.append(ids[i]);
       if (i < j - 1) {
         sb.append(",");
       }
     }
     return sb.toString();
   }

   public static String transInIds(Integer[] ids) {
     StringBuffer sb = new StringBuffer();
     int i = 0; for (int j = ids.length; i < j; i++) {
       sb.append(ids[i].intValue());
       if (i < j - 1) {
         sb.append(",");
       }
     }
     return sb.toString();
   }





   public static String transInIds(List<Integer> ids)
   {
     StringBuffer sb = new StringBuffer();
     int i = 0; for (int j = ids.size(); i < j; i++) {
       sb.append(((Integer)ids.get(i)).intValue());
       if (i < j - 1) {
         sb.append(",");
       }
     }
     return sb.toString();
   }





   public static String transInsertIds(int[] ids)
   {
     StringBuffer sb = new StringBuffer();
     int i = 0; for (int j = ids.length; i < j; i++) {
       sb.append("[" + ids[i] + "]");
       if (i < j - 1) {
         sb.append(",");
       }
     }
     return sb.toString();
   }





   public static int[] transGetIds(String ids)
   {
     String[] tmp = ids.replaceAll("\\[|\\]", "").split(",");
     int[] arr = new int[tmp.length];
     for (int i = 0; i < arr.length; i++) {
       arr[i] = Integer.parseInt(tmp[i]);
     }
     return arr;
   }





   public static String toString(List<Integer> list)
   {
     StringBuffer sb = new StringBuffer();
     int i = 0; for (int j = list.size(); i < j; i++) {
       sb.append(list.get(i));
       if (i < j - 1) {
         sb.append(",");
       }
     }
     return sb.toString();
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/array/ArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
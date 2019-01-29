 package javautils.math;

 import java.math.BigDecimal;

 public class MathUtil
 {
   public static double decimalFormat(BigDecimal bd, int point) {
     return bd.setScale(point, 5).doubleValue();
   }

   public static double doubleFormat(double d, int point) {
     try {
       BigDecimal bd = new BigDecimal(d);
       return bd.setScale(point, 5).doubleValue();
     } catch (Exception e) {
       e.printStackTrace();
     }
     return d;
   }

   public static float floatFormat(float f, int point) {
     try {
       BigDecimal bd = new BigDecimal(f);
       return bd.setScale(point, 5).floatValue();
     } catch (Exception e) {
       e.printStackTrace();
     }
     return f;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/math/MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
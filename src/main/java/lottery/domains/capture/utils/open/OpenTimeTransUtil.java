 package lottery.domains.capture.utils.open;

 import java.util.Date;
 import javautils.date.DateUtil;



















 public class OpenTimeTransUtil
 {
   public static String trans(String expect, String refDate, int refExpect, int times)
   {
     String calcExpect = expect;


     Date currentDate = DateUtil.stringToDate(calcExpect.substring(0, 8), "yyyyMMdd");

     int currentTimes = Integer.valueOf(calcExpect.substring(9)).intValue();


     Date refDateDate = DateUtil.stringToDate(refDate, "yyyy-MM-dd");


     int disDays = DateUtil.calcDays(currentDate, refDateDate);


     int disTimes = disDays * times + currentTimes;


     int finalExpect = refExpect + disTimes;

     return String.valueOf(finalExpect);
   }










   public static String trans(int realExpect, String refDate, int refExpect, int times)
   {
     int disTimes = realExpect - refExpect;


     int disDays = disTimes / times;


     int remainTimes = disTimes % times;


     String currentDate = DateUtil.calcNewDay(refDate, disDays);

     return currentDate.replace("-", "") + "-" + String.format("%03d", new Object[] { Integer.valueOf(remainTimes) });
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/OpenTimeTransUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
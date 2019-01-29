 package lottery.domains.capture.utils.open;

 import java.util.List;
 import lottery.domains.content.entity.LotteryOpenTime;
 import lottery.domains.content.pool.LotteryDataFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;






 @Component
 public class LowOpenTimeUtil
   implements OpenTimeUtil
 {
   @Autowired
   private LotteryDataFactory df;

   public String getCurrExpect(String lotteryName, String currTime)
   {
     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lotteryName);
     int i = 0; for (int j = list.size(); i < j; i++) {
       LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
       String startTime = tmpBean.getStartTime();
       String stopTime = tmpBean.getStopTime();
       if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
         if (i == 0) {
           return null;
         }
         tmpBean = (LotteryOpenTime)list.get(i - 1);
         return tmpBean.getExpect();
       }
     }
     return null;
   }

   public String getNextExpect(String lotteryName, String lastExpect)
   {
     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lotteryName);
     int i = 0; for (int j = list.size(); i < j; i++) {
       LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
       String expect = tmpBean.getExpect();
       if (lastExpect.compareTo(expect) < 0) {
         return expect;
       }
     }
     return null;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/LowOpenTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
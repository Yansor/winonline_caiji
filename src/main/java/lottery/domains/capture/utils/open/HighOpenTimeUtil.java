 package lottery.domains.capture.utils.open;

 import java.util.List;
 import javautils.date.DateUtil;
 import lottery.domains.content.dao.LotteryCrawlerStatusDao;
 import lottery.domains.content.entity.LotteryCrawlerStatus;
 import lottery.domains.content.entity.LotteryOpenTime;
 import lottery.domains.content.pool.LotteryDataFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;






 @Component
 public class HighOpenTimeUtil
   implements OpenTimeUtil
 {
   @Autowired
   private LotteryDataFactory df;
   @Autowired
   private LotteryCrawlerStatusDao lotteryCrawlerStatusDao;

   public String getExpect(String lotteryName, String currTime)
   {
     List<LotteryOpenTime> list = this.df.listLotteryOpenTime(lotteryName);
     String currDate = currTime.substring(0, 10);
     String nextDate = DateUtil.calcNextDay(currDate);
     String lastDate = DateUtil.calcLastDay(currDate);
     int i = 0; for (int j = list.size(); i < j; i++) {
       LotteryOpenTime tmpBean = (LotteryOpenTime)list.get(i);
       String startDate = currDate;
       String stopDate = currDate;
       String openDate = currDate;
       String expectDate = currDate;
       String startTime = tmpBean.getStartTime();
       String stopTime = tmpBean.getStopTime();
       String openTime = tmpBean.getOpenTime();
       String expect = tmpBean.getExpect();
       if (i == 0) {
         if (startTime.compareTo(stopTime) > 0) {
           startDate = lastDate;
         }
       } else if (i == j - 1) {
         if (startTime.compareTo(stopTime) > 0) {
           stopDate = nextDate;
         }
         if (startTime.compareTo(openTime) > 0) {
           openDate = nextDate;
         }
         if (currTime.compareTo(stopDate + " " + stopTime) >= 0) {
           tmpBean = (LotteryOpenTime)list.get(0);
           startDate = nextDate;
           stopDate = nextDate;
           openDate = nextDate;
           expectDate = nextDate;
           startTime = tmpBean.getStartTime();
           stopTime = tmpBean.getStopTime();
           openTime = tmpBean.getOpenTime();
           expect = tmpBean.getExpect();
           if (startTime.compareTo(stopTime) > 0) {
             startDate = currDate;
           }
         }
       } else {
         if (startTime.compareTo(stopTime) > 0) {
           stopDate = nextDate;
         }
         if (startTime.compareTo(openTime) > 0) {
           openDate = nextDate;
         }
       }
       if (!tmpBean.getIsTodayExpect().booleanValue()) {
         expectDate = lastDate;
       }
       startTime = startDate + " " + startTime;
       stopTime = stopDate + " " + stopTime;
       openTime = openDate + " " + openTime;
       expect = expectDate.replace("-", "") + "-" + expect;
       if ((currTime.compareTo(startTime) >= 0) && (currTime.compareTo(stopTime) < 0)) {
         return expect;
       }
     }
     return null;
   }

   public String getCurrExpect(String lotteryName, String currTime)
   {
     LotteryCrawlerStatus lotteryCrawlerStatus = this.lotteryCrawlerStatusDao.get(lotteryName);

     String tmpExpect = getExpect(lotteryName, currTime);
     String tmpDate = tmpExpect.substring(0, 8);
     String currDate = DateUtil.formatTime(tmpDate, "yyyyMMdd", "yyyy-MM-dd");
     String currExpect = tmpExpect.substring(9);
     String lastDate = currDate;
     int lastExpect = Integer.parseInt(currExpect);
     int times = lotteryCrawlerStatus.getTimes().intValue();

     if (lastExpect == 1) {
       lastDate = DateUtil.calcLastDay(currDate);
       lastExpect = times;
     } else {
       lastExpect--;
     }

     int formatCount = 3;
     if ("fgffc".equals(lotteryName)) {
       formatCount = 4;
     }

     String expect = lastDate.replaceAll("-", "") + "-" + String.format(new StringBuilder().append("%0").append(formatCount).append("d").toString(), new Object[] { Integer.valueOf(lastExpect) });
     return expect;
   }

   public String getNextExpect(String lotteryName, String lastExpect)
   {
     return null;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/HighOpenTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
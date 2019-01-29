 package lottery.domains.capture.utils.open;

 import javautils.date.Moment;
 import lottery.domains.content.entity.Lottery;
 import lottery.domains.content.pool.LotteryDataFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;






 @Component
 public class LotteryOpenUtil
 {
   @Autowired
   private LotteryDataFactory dataFactory;

   public String subtractExpect(String lotteryShortName, String expect)
   {
     Lottery lottery = this.dataFactory.getLottery(lotteryShortName);
     if (lottery == null) return null;
     String subExpect;
//     String subExpect;
     if (expect.indexOf("-") <= -1) {
       Integer integerExpect = Integer.valueOf(expect);
       integerExpect = Integer.valueOf(integerExpect.intValue() - 1);
//       String subExpect;
       if (integerExpect.toString().length() >= expect.length()) {
         subExpect = integerExpect.toString();
       }
       else {
         subExpect = String.format("%0" + expect.length() + "d", new Object[] { integerExpect });
       }
     }
     else {
       String[] split = expect.split("-");
       int formatCount = split[1].length();
       String date = split[0];
       String currExpect = split[1];
//       String subExpect;
       if ((currExpect.equals("001")) || (currExpect.equals("0001"))) {
         date = new Moment().fromDate(date).subtract(1, "days").format("yyyyMMdd");
         subExpect = String.format("%0" + formatCount + "d", new Object[] { Integer.valueOf(lottery.getTimes()) });
       }
       else {
         Integer integer = Integer.valueOf(currExpect);
         integer = Integer.valueOf(integer.intValue() - 1);
//         String subExpect;
if (integer.toString().length() >= formatCount) {
           subExpect = integer.toString();
         }
         else {
           subExpect = String.format("%0" + formatCount + "d", new Object[] { integer });
         }
       }

       subExpect = date + "-" + subExpect;
     }

     return subExpect;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/open/LotteryOpenUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
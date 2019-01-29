 package lottery.domains.capture.sites.pcqq;

 import com.alibaba.fastjson.annotation.JSONField;
 import javautils.date.Moment;


 public class PCQQBean
 {
   @JSONField(deserialize=false)
   private String onlinetime = new Moment()
     .toSimpleTime();
   @JSONField(name="c")
   private int onlinenumber;
   @JSONField(deserialize=false)
   private int onlinechange;

   public String getOnlinetime() {
     return this.onlinetime;
   }

   public void setOnlinetime(String onlinetime) {
     this.onlinetime = onlinetime;
   }

   public int getOnlinenumber() {
     return this.onlinenumber;
   }

   public void setOnlinenumber(int onlinenumber) {
     this.onlinenumber = onlinenumber;
   }

   public int getOnlinechange() {
     return this.onlinechange;
   }

   public void setOnlinechange(int onlinechange) {
     this.onlinechange = onlinechange;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/sites/pcqq/PCQQBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
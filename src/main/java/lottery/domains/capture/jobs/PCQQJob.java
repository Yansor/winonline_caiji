 package lottery.domains.capture.jobs;

 import com.alibaba.fastjson.JSON;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.concurrent.ConcurrentHashMap;
 import javautils.date.Moment;
 import javautils.http.HttpClientUtil;
 import lottery.domains.capture.sites.pcqq.PCQQBean;
 import lottery.domains.capture.utils.CodeValidate;
 import lottery.domains.capture.utils.ExpectValidate;
 import lottery.domains.content.biz.LotteryOpenCodeService;
 import lottery.domains.content.entity.LotteryOpenCode;
 import org.apache.commons.lang.StringUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.stereotype.Component;






 @Component
 public class PCQQJob
 {
   private static final String ONLINE_URL = "https://mma.qq.com/cgi-bin/im/online";
   private static final String CGI_SVRTIME_URL = "http://cgi.im.qq.com/cgi-bin/cgi_svrtime";
   private static final Logger logger = LoggerFactory.getLogger(PCQQJob.class);

   private static final String NAME = "txffc";
   private static ConcurrentHashMap<String, String> HIS_OPEN_CODES = new ConcurrentHashMap();

   @Autowired
   private LotteryOpenCodeService lotteryOpenCodeService;

   private static boolean isRuning = false;


   @Scheduled(cron="8,15,20,25,30,35 * * * * *")
   public void schedule()
   {
     synchronized (PCQQJob.class) {
       if (isRuning == true) {
         return;
       }
       isRuning = true;
     }

     try
     {
       logger.debug("开始抓取PCQQ官网在线人数数据>>>>>>>>>>>>>>>>");

       long start = System.currentTimeMillis();

       start();
       long spend = System.currentTimeMillis() - start;

       logger.debug("完成抓取PCQQ官网在线人数数据>>>>>>>>>>>>>>>>耗时{}", Long.valueOf(spend));
     } catch (Exception e) {
       logger.error("抓取PCQQ官网在线人数数据出错", e);
     } finally {
       isRuning = false;
     }
   }

   private void start() {
     int startMinute = new Moment().get("minute");







     String result = getOnlineNum();

     int endMinute = new Moment().get("minute");
     if (startMinute != endMinute) {
       logger.error("PCQQ官网在线人数抓取超时，开始分钟{}，结束分钟{}", Integer.valueOf(startMinute), Integer.valueOf(endMinute));
       return;
     }

     int endSecond = new Moment().get("second");
     if (endSecond >= 40) {
       logger.error("PCQQ官网在线人数抓取不处理，因为处理完成的秒数{}>=40秒，可能是下一期的数据", Integer.valueOf(endSecond));
       return;
     }

     handleData(result);
   }

   private boolean validateTencentServerTime() {
     Moment tencentServerTime = getTencentServerTime();
     if (tencentServerTime == null) {
       return false;
     }
     Moment now = new Moment();

     int diffSeconds = now.difference(tencentServerTime, "second");

     if ((diffSeconds >= 5) || (diffSeconds <= -5)) {
       logger.error("当前服务器时间超过腾讯服务器时间太多，抓取服务器时间{}，PCQQ官网时间{}，本次不处理", now.toSimpleTime(), tencentServerTime.toSimpleTime());
       return false;
     }

     logger.error("抓取服务器时间{}，PCQQ官网时间{}", now.toSimpleTime(), tencentServerTime.toSimpleTime());
     return true;
   }

   private void handleData(String result) {
     if (StringUtils.isEmpty(result)) {
       return;
     }

     PCQQBean pcqqBean = (PCQQBean)JSON.parseObject(result, PCQQBean.class);
     if (pcqqBean == null) {
       logger.error("解析PCQQ返回数据出错，返回数据" + result);
       return;
     }


     handleBean(pcqqBean);
   }

   private boolean handleBean(PCQQBean bean) {
     boolean valid = checkData(bean);
     if (!valid) {
       return false;
     }


     String currentExpect = getExpectByTime(bean.getOnlinetime());
     if (!ExpectValidate.validate("txffc", currentExpect)) {
       logger.error("PCQQ官网腾讯分分彩抓取期数" + currentExpect + "错误");
       return false;
     }


     if (HIS_OPEN_CODES.containsKey(currentExpect)) {
       return false;
     }


     String currentCode = convertCode(bean.getOnlinenumber());
     if (!CodeValidate.validate("txffc", currentCode)) {
       logger.error("PCQQ官网腾讯分分彩抓取号码" + currentCode + "错误");
       return false;
     }


     LotteryOpenCode dbData = this.lotteryOpenCodeService.get("txffc", currentExpect);
     if (dbData != null) {
       HIS_OPEN_CODES.put(currentExpect, dbData.getCode());

       if (!dbData.getCode().equals(currentCode)) {
         logger.error("PCQQ官网抓取时遇到错误：抓取{}期开奖号码{}与数据库已有开奖号码{}不符", new Object[] { currentExpect, currentCode, dbData.getCode() });
         return false;
       }

       return true;
     }


     HIS_OPEN_CODES.put(currentExpect, currentCode);


     String lastExpectTime = new Moment().fromTime(bean.getOnlinetime()).subtract(1, "minutes").toSimpleTime();
     String lastExpect = getExpectByTime(lastExpectTime);

     if (!HIS_OPEN_CODES.containsKey(lastExpect)) {
       LotteryOpenCode lastExpectCode = this.lotteryOpenCodeService.get("txffc", lastExpect);
       if (lastExpectCode == null) {
         logger.warn("PCQQ官网抓取时没有获取到上期" + lastExpect + "的开奖数据，本次不处理，本期：" + currentExpect);
         return false;
       }


       HIS_OPEN_CODES.put(lastExpect, lastExpectCode.getCode());
     }


     String lastExpectCode = (String)HIS_OPEN_CODES.get(lastExpect);
     if (StringUtils.isEmpty(lastExpectCode)) {
       return false;
     }


     int status = 0;
     if (lastExpectCode.equals(currentCode))
     {
       status = 2;
     }

     LotteryOpenCode lotteryOpenCode = new LotteryOpenCode("txffc", currentExpect, currentCode, new Moment().toSimpleTime(), Integer.valueOf(status), null, "PCQQ");
     lotteryOpenCode.setInterfaceTime(bean.getOnlinetime());

     boolean added = this.lotteryOpenCodeService.add(lotteryOpenCode, false);

     if (added) {
       logger.info("PCQQ官网成功抓取腾讯分分彩{}期开奖号码{}，上期{}开奖号码{}，是否自动撤单：{}", new Object[] { currentExpect, currentCode, lastExpect, lastExpectCode, status == 2 ? "是" : "否" });


       if ("txffc".equals("txffc")) {
         LotteryOpenCode txlhdCode = new LotteryOpenCode("txlhd", lotteryOpenCode.getExpect(), lotteryOpenCode.getCode(), lotteryOpenCode.getTime(), lotteryOpenCode.getOpenStatus(), null, lotteryOpenCode.getRemarks());
         txlhdCode.setInterfaceTime(lotteryOpenCode.getInterfaceTime());
         this.lotteryOpenCodeService.add(txlhdCode, false);
       }
     }

     return added;
   }


   public static Moment getTencentServerTime()
   {
     try
     {
       String url = "http://cgi.im.qq.com/cgi-bin/cgi_svrtime?_=" + System.currentTimeMillis();

       String data = getHttpResult(url);
       if (data != null) {
         return new Moment().fromTime(data);
       }

       return null;
     } catch (Exception e) {
       logger.error("获取PCQQ官网服务器时间出错", e); }
     return null;
   }



   public static String getOnlineNum()
   {
     try
     {
       String url = "https://mma.qq.com/cgi-bin/im/online?_=" + System.currentTimeMillis();

       String data = getHttpResult(url);
       if ((data != null) && (data.indexOf("online_resp") > -1)) {
         data = data.substring(12);
         return data.substring(0, data.length() - 1);
       }

       return null;
     } catch (Exception e) {
       logger.error("获取PCQQ官网在线人数出错", e); }
     return null;
   }




   public static String getHttpResult(String url)
   {
     try
     {
       Map<String, String> header = new HashMap();
       header.put("referer", "http://im.qq.com/pcqq/");
       header.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");

       return HttpClientUtil.post(url, null, header, 10000);
     }
     catch (Exception e) {
       logger.error("请求PCQQ官网出错", e); }
     return null;
   }


   private boolean checkData(PCQQBean bean)
   {
     if (bean == null) {
       logger.error("PCQQ官网数据非法，空数据");
       return false;
     }
     if ((StringUtils.isEmpty(bean.getOnlinetime())) || (bean.getOnlinenumber() <= 0)) {
       logger.error("PCQQ官网数据非法:" + JSON.toJSONString(bean));
       return false;
     }


     if (bean.getOnlinenumber() < 1000) {
       logger.error("PCQQ官网数据非法:" + JSON.toJSONString(bean));
       return false;
     }

     return true;
   }








   private String convertCode(int onlinenumber)
   {
     String[] chars = (onlinenumber + "").split("");
     int sum = 0;
     for (String aChar : chars) {
       if ((aChar != null) && (!"".equals(aChar))) {
         sum += Integer.valueOf(aChar).intValue();
       }
     }


     String wan = sum + "";
     wan = wan.substring(wan.length() - 1);

     String qian = chars[(chars.length - 4)] + "";
     String bai = chars[(chars.length - 3)] + "";
     String shi = chars[(chars.length - 2)] + "";
     String ge = chars[(chars.length - 1)] + "";

     return wan + "," + qian + "," + bai + "," + shi + "," + ge;
   }

   private static String getExpectByTime(String time) {
     Moment moment = new Moment().fromTime(time);
     int hour = moment.get("hour");
     int minute = moment.get("minute");

     if ((hour == 0) && (minute == 0))
     {
       moment = moment.add(-1, "minutes");
       hour = 24;
     }
     String date = moment.format("yyyyMMdd");
     int dayExpect = hour * 60 + minute;
     String expect = date + "-" + String.format("%04d", new Object[] { Integer.valueOf(dayExpect) });
     return expect;
   }

   public static void main(String[] args) {
     String time = "2017-09-18 00:01:59";
     String curExpect = getExpectByTime(time);
     System.out.println(curExpect);

     String lastExpectTime = new Moment().fromTime(time).subtract(1, "minutes").toSimpleTime();
     String lastExpect = getExpectByTime(lastExpectTime);

     System.out.println(lastExpect);
   }



























   public static String convertCode2(int onlinenumber)
   {
     String[] chars = (onlinenumber + "").split("");
     int sum = 0;
     for (String aChar : chars) {
       if ((aChar != null) && (!"".equals(aChar))) {
         sum += Integer.valueOf(aChar).intValue();
       }
     }


     String wan = sum + "";
     wan = wan.substring(wan.length() - 1);

     String qian = chars[(chars.length - 4)] + "";
     String bai = chars[(chars.length - 3)] + "";
     String shi = chars[(chars.length - 2)] + "";
     String ge = chars[(chars.length - 1)] + "";

     return wan + "," + qian + "," + bai + "," + shi + "," + ge;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/jobs/PCQQJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
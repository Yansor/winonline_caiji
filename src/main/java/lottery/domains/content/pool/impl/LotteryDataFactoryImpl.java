 package lottery.domains.content.pool.impl;

 import java.util.LinkedHashMap;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import lottery.domains.content.biz.LotteryOpenCodeService;
 import lottery.domains.content.dao.LotteryDao;
 import lottery.domains.content.dao.LotteryOpenTimeDao;
 import lottery.domains.content.entity.Lottery;
 import lottery.domains.content.entity.LotteryOpenTime;
 import lottery.domains.content.pool.LotteryDataFactory;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.InitializingBean;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;

 @Component
 public class LotteryDataFactoryImpl implements LotteryDataFactory, InitializingBean
 {
   private static final Logger logger = LoggerFactory.getLogger(LotteryDataFactoryImpl.class);
   @Autowired
   private LotteryOpenTimeDao lotteryOpenTimeDao;

   public void init() { logger.info("init LotteryDataFactory....start");
     initLottery();
     initLotteryOpenTime();
     initLotteryOpenCode();
     logger.info("init LotteryDataFactory....done");
   }

   public void afterPropertiesSet() throws Exception
   {
     init();
   }





   @Autowired
   private LotteryOpenCodeService lotteryOpenCodeService;



   private List<LotteryOpenTime> lotteryOpenTimeList = new LinkedList();
   @Autowired
   private LotteryDao lotteryDao;

   public void initLotteryOpenTime() {
     try { List<LotteryOpenTime> list = this.lotteryOpenTimeDao.listAll();
       if (this.lotteryOpenTimeList != null) {
         this.lotteryOpenTimeList.clear();
       }
       this.lotteryOpenTimeList.addAll(list);
       logger.info("初始化彩票开奖时间信息完成！");
     } catch (Exception e) {
       logger.error("初始化彩票开奖时间信息失败！");
     }
   }

   public List<LotteryOpenTime> listLotteryOpenTime(String lottery)
   {
     List<LotteryOpenTime> list = new LinkedList();
     for (LotteryOpenTime tmpBean : this.lotteryOpenTimeList) {
       if (tmpBean.getLottery().equals(lottery)) {
         list.add(tmpBean);
       }
     }
     return list;
   }

   public void initLotteryOpenCode()
   {
     this.lotteryOpenCodeService.initLotteryOpenCode();
   }




   private Map<Integer, Lottery> lotteryMap = new LinkedHashMap();

   public void initLottery()
   {
     try {
       List<Lottery> list = this.lotteryDao.listAll();
       Map<Integer, Lottery> tmpMap = new LinkedHashMap();
       for (Lottery lottery : list) {
         tmpMap.put(Integer.valueOf(lottery.getId()), lottery);
       }
       this.lotteryMap = tmpMap;
       logger.info("初始化彩票信息完成！");
     } catch (Exception e) {
       logger.error("初始化彩票信息失败！");
     }
   }

   public Lottery getLottery(int id)
   {
     if (this.lotteryMap.containsKey(Integer.valueOf(id))) {
       return (Lottery)this.lotteryMap.get(Integer.valueOf(id));
     }
     return null;
   }

   public Lottery getLottery(String shortName)
   {
     Object[] keys = this.lotteryMap.keySet().toArray();
     for (Object o : keys) {
       Lottery lottery = (Lottery)this.lotteryMap.get(o);
       if (lottery.getShortName().equals(shortName)) {
         return lottery;
       }
     }
     return null;
   }

   public List<Lottery> listLottery()
   {
     List<Lottery> list = new LinkedList();
     Object[] keys = this.lotteryMap.keySet().toArray();
     for (Object o : keys) {
       list.add(this.lotteryMap.get(o));
     }
     return list;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/pool/impl/LotteryDataFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
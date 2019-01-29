 package lottery.domains.content.biz.impl;

 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 import java.util.TreeMap;
 import java.util.TreeSet;
 import java.util.concurrent.ConcurrentHashMap;
 import javautils.redis.JedisTemplate;
 import javautils.redis.JedisTemplate.JedisActionNoResult;
 import lottery.domains.capture.utils.open.LotteryOpenUtil;
 import lottery.domains.content.biz.LotteryOpenCodeService;
 import lottery.domains.content.dao.LotteryDao;
 import lottery.domains.content.dao.LotteryOpenCodeDao;
 import lottery.domains.content.entity.Lottery;
 import lottery.domains.content.entity.LotteryOpenCode;
 import org.apache.commons.collections.CollectionUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import redis.clients.jedis.Jedis;
 import redis.clients.jedis.Pipeline;



 @Service
 public class LotteryOpenCodeServiceImpl
   implements LotteryOpenCodeService
 {
   private static final Logger log = LoggerFactory.getLogger(LotteryOpenCodeServiceImpl.class);

   private static final String OPEN_CODE_KEY = "OPEN_CODE:%s";

   private static final int OPEN_CODE_MOST_EXPECT = 50;
   @Autowired
   private LotteryOpenCodeDao lotteryOpenCodeDao;
   @Autowired
   private LotteryDao lotteryDao;
   @Autowired
   private JedisTemplate jedisTemplate;
   @Autowired
   private LotteryOpenUtil lotteryOpenUtil;
   private static ConcurrentHashMap<String, Boolean> CODE_CAPTURE_CACHE = new ConcurrentHashMap();



   public void initLotteryOpenCode()
   {
     List<Lottery> lotteries = this.lotteryDao.listAll();
     for (Lottery lottery : lotteries) {
       if (lottery.getId() != 117)
       {
         initLotteryOpenCodeByLottery(lottery); }
     }
   }

   private void initLotteryOpenCodeByLottery(Lottery lottery) {
     List<LotteryOpenCode> openCodes = this.lotteryOpenCodeDao.getLatest(lottery.getShortName(), 50);

     final String key = String.format("OPEN_CODE:%s", new Object[] { lottery.getShortName() });


     final TreeMap<String, String> values = new TreeMap();
     for (LotteryOpenCode openCode : openCodes) {
       values.put(openCode.getExpect(), openCode.getCode());
     }





     this.jedisTemplate.execute(new JedisTemplate.JedisActionNoResult()
     {
       public void action(Jedis jedis) {
         Pipeline pipelined = jedis.pipelined();


         pipelined.del(key);


         for (String expect : values.keySet()) {
           pipelined.hset(key, expect, (String)values.get(expect));
         }


         pipelined.sync();
       }
     });
   }

   public boolean hasCaptured(String lotteryName, String expect)
   {
     LotteryOpenCode openCode = this.lotteryOpenCodeDao.get(lotteryName, expect);
     return openCode != null;
   }

   public synchronized boolean add(LotteryOpenCode entity, boolean txffcAssistLast)
   {
     String cacheKey = entity.getLottery() + "_" + entity.getExpect();
     if (CODE_CAPTURE_CACHE.containsKey(cacheKey)) {
       return false;
     }

     boolean hasCaptured = hasCaptured(entity.getLottery(), entity.getExpect());
     if (hasCaptured) {
       CODE_CAPTURE_CACHE.put(cacheKey, Boolean.valueOf(true));
       return false;
     }

     if ((txffcAssistLast) && ("txffc".equals(entity.getLottery()))) {
       String lastExpect = this.lotteryOpenUtil.subtractExpect(entity.getLottery(), entity.getExpect());
       LotteryOpenCode lastExpectCode = this.lotteryOpenCodeDao.get(entity.getLottery(), lastExpect);
       if (lastExpectCode == null) {
         return false;
       }

       if (entity.getCode().equals(lastExpectCode.getCode())) {
         entity.setOpenStatus(Integer.valueOf(2));
       }
     }

     boolean added = this.lotteryOpenCodeDao.add(entity);
     if (!added) {
       return false;
     }

     addedToRedis(entity);
     CODE_CAPTURE_CACHE.put(cacheKey, Boolean.valueOf(true));

     return added;
   }

   private void addedToRedis(LotteryOpenCode entity)
   {
     String key = String.format("OPEN_CODE:%s", new Object[] { entity.getLottery() });


     Set<String> hkeys = this.jedisTemplate.hkeys(key);
     if (CollectionUtils.isEmpty(hkeys))
     {
       this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
     }
     else {
       TreeSet<String> sortHKeys = new TreeSet(hkeys);


       String[] expects = (String[])sortHKeys.toArray(new String[0]);
       String firstExpect = expects[0];
       if (entity.getExpect().compareTo(firstExpect) > 0) {
         this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
         sortHKeys.add(entity.getExpect());
       }

       if ((CollectionUtils.isNotEmpty(sortHKeys)) && (sortHKeys.size() > 50)) {
         int exceedSize = sortHKeys.size() - 50;

         Iterator<String> iterator = sortHKeys.iterator();
         int count = 0;
         List<String> delFields = new ArrayList();
         while ((iterator.hasNext()) &&
           (count < exceedSize))
         {


           delFields.add(iterator.next());
           iterator.remove();
           count++;
         }


         this.jedisTemplate.hdel(key, (String[])delFields.toArray(new String[0]));
       }
     }
   }



   public LotteryOpenCode get(String lottery, String expect)
   {
     return this.lotteryOpenCodeDao.get(lottery, expect);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/biz/impl/LotteryOpenCodeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
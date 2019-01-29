 package lottery.domains.content.dao.impl;

 import java.util.List;
 import javautils.jdbc.hibernate.HibernateSuperDao;
 import lottery.domains.content.dao.LotteryOpenCodeDao;
 import lottery.domains.content.entity.LotteryOpenCode;
 import org.apache.commons.collections.CollectionUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Repository;



 @Repository
 public class LotteryOpenCodeDaoImpl
   implements LotteryOpenCodeDao
 {
   private final String tab = LotteryOpenCode.class.getSimpleName();

   @Autowired
   private HibernateSuperDao<LotteryOpenCode> superDao;

   public LotteryOpenCode get(String lottery, String expect)
   {
     String hql = "from " + this.tab + " where lottery = ?0 and expect = ?1 and userId is null";
     Object[] values = { lottery, expect };
     List<LotteryOpenCode> list = this.superDao.list(hql, values);
     if (CollectionUtils.isNotEmpty(list)) {
       return (LotteryOpenCode)list.get(0);
     }

     return null;
   }









   public boolean add(LotteryOpenCode entity)
   {
     return this.superDao.save(entity);
   }

   public List<LotteryOpenCode> getLatest(String lotteryName, int count)
   {
     String hql = "from " + this.tab + " where lottery = ?0 and userId is null order by expect desc";
     Object[] values = { lotteryName };
     return this.superDao.list(hql, values, 0, count);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/impl/LotteryOpenCodeDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
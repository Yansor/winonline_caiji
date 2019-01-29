 package lottery.domains.content.dao.impl;

 import javautils.jdbc.hibernate.HibernateSuperDao;
 import lottery.domains.content.dao.LotteryCrawlerStatusDao;
 import lottery.domains.content.entity.LotteryCrawlerStatus;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Repository;


 @Repository
 public class LotteryCrawlerStatusDaoImpl
   implements LotteryCrawlerStatusDao
 {
   private final String tab = LotteryCrawlerStatus.class.getSimpleName();

   @Autowired
   private HibernateSuperDao<LotteryCrawlerStatus> superDao;

   public LotteryCrawlerStatus get(String shortName)
   {
     String hql = "from " + this.tab + " where shortName = ?0";
     Object[] values = { shortName };
     return (LotteryCrawlerStatus)this.superDao.unique(hql, values);
   }

   public boolean update(String shortName, String lastExpect, String lastUpdate)
   {
     String hql = "update " + this.tab + " set lastExpect = ?1, lastUpdate = ?2 where shortName = ?0";
     Object[] values = { shortName, lastExpect, lastUpdate };
     return this.superDao.update(hql, values);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/impl/LotteryCrawlerStatusDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
 package lottery.domains.content.dao.impl;

 import java.util.List;
 import javautils.jdbc.hibernate.HibernateSuperDao;
 import lottery.domains.content.dao.LotteryOpenTimeDao;
 import lottery.domains.content.entity.LotteryOpenTime;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Repository;
 import org.springframework.transaction.annotation.Transactional;



 @Repository
 public class LotteryOpenTimeDaoImpl
   implements LotteryOpenTimeDao
 {
   private final String tab = LotteryOpenTime.class.getSimpleName();

   @Autowired
   private HibernateSuperDao<LotteryOpenTime> superDao;

   @Transactional(readOnly=true)
   public List<LotteryOpenTime> listAll()
   {
     String hql = "from " + this.tab + " order by expect asc";
     return this.superDao.list(hql);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/impl/LotteryOpenTimeDaoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
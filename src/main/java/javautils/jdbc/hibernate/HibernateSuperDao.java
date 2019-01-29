 package javautils.jdbc.hibernate;

 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.List;
 import javautils.jdbc.PageList;
 import javautils.jdbc.util.BatchSQLUtil;
 import org.hibernate.Criteria;
 import org.hibernate.Query;
 import org.hibernate.SQLQuery;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projection;
 import org.hibernate.criterion.Projections;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.transaction.annotation.Transactional;

 @org.springframework.stereotype.Component
 public class HibernateSuperDao<T>
 {
   private static final Logger logger = LoggerFactory.getLogger(HibernateSuperDao.class);
   @Autowired
   private SessionFactory sessionFactory;

   public Session getCurrentSession()
   {
     return this.sessionFactory.getCurrentSession();
   }





   @Transactional
   public boolean save(Object entity)
   {
     boolean flag = false;
     try {
       getCurrentSession().save(entity);
       flag = true;
     } catch (Exception e) {
       logger.error("保存实体类异常", e);
     }
     return flag;
   }





   @Transactional
   public boolean update(Object entity)
   {
     boolean flag = false;
     try {
       getCurrentSession().update(entity);
       flag = true;
     } catch (Exception e) {
       logger.error("更新实体类异常", e);
     }
     return flag;
   }






   @Transactional
   public boolean update(String hql, Object[] values)
   {
     boolean flag = false;
     try {
       Query query = getCurrentSession().createQuery(hql);
       for (int i = 0; i < values.length; i++) {
         query.setParameter(String.valueOf(i), values[i]);
       }
       int result = query.executeUpdate();
       flag = result > 0;
     } catch (Exception e) {
       logger.error("更新实体类异常", e);
     }
     return flag;
   }





   public boolean delete(Object entity)
   {
     boolean flag = false;
     try {
       getCurrentSession().delete(entity);
       flag = true;
     } catch (Exception e) {
       logger.error("删除实体类异常", e);
     }
     return flag;
   }






   @Transactional
   public boolean delete(String hql, Object[] values)
   {
     boolean flag = false;
     try {
       Query query = getCurrentSession().createQuery(hql);
       for (int i = 0; i < values.length; i++) {
         query.setParameter(String.valueOf(i), values[i]);
       }
       int result = query.executeUpdate();
       flag = result > 0;
     } catch (Exception e) {
       logger.error("删除实体类异常", e);
     }
     return flag;
   }




   @Transactional(readOnly=true)
   public List<T> list(String hql)
   {
     try
     {
       Query query = getCurrentSession().createQuery(hql);
       return query.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }






   @Transactional(readOnly=true)
   public List<T> list(String hql, int start, int limit)
   {
     try
     {
       Query query = getCurrentSession().createQuery(hql);
       if ((start >= 0) && (limit > 0)) {
         query.setFirstResult(start);
         query.setMaxResults(limit);
       }
       return query.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }




   @Transactional(readOnly=true)
   public Object unique(String hql)
   {
     try
     {
       Query query = getCurrentSession().createQuery(hql);
       return query.uniqueResult();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }





   @Transactional(readOnly=true)
   public Object unique(String hql, Object[] values)
   {
     try
     {
       Query query = getCurrentSession().createQuery(hql);
       for (int i = 0; i < values.length; i++) {
         query.setParameter(String.valueOf(i), values[i]);
       }
       return query.uniqueResult();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }





   @Transactional(readOnly=true)
   public List<T> list(String hql, Object[] values)
   {
     try
     {
       Query query = getCurrentSession().createQuery(hql);
       for (int i = 0; i < values.length; i++) {
         query.setParameter(String.valueOf(i), values[i]);
       }
       return query.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }





   @Transactional(readOnly=true)
   public List<?> listObject(String hql, Object[] values)
   {
     try
     {
       Query query = getCurrentSession().createQuery(hql);
       for (int i = 0; i < values.length; i++) {
         query.setParameter(String.valueOf(i), values[i]);
       }
       return query.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }







   @Transactional(readOnly=true)
   public List<T> list(String hql, Object[] values, int start, int limit)
   {
     try
     {
       Query query = getCurrentSession().createQuery(hql);
       for (int i = 0; i < values.length; i++) {
         query.setParameter(String.valueOf(i), values[i]);
       }
       if ((start >= 0) && (limit > 0)) {
         query.setFirstResult(start);
         query.setMaxResults(limit);
       }
       return query.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }




   @Transactional(readOnly=true)
   public List<?> findWithSql(String sql)
   {
     try
     {
       SQLQuery query = getCurrentSession().createSQLQuery(sql);
       return query.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }




   @Transactional(readOnly=true)
   public Object uniqueWithSqlCount(String sql)
   {
     try
     {
       SQLQuery query = getCurrentSession().createSQLQuery(sql);
       return query.uniqueResult();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }







   @Transactional(readOnly=true)
   public PageList findPageList(String querySql, String countSql, int start, int limit)
   {
     try
     {
       PageList pList = new PageList();

       SQLQuery countQuery = getCurrentSession().createSQLQuery(countSql);
       Number result = (Number)countQuery.uniqueResult();
       int count = result.intValue();
       pList.setCount(count);

       SQLQuery listQuery = getCurrentSession().createSQLQuery(querySql);
       if ((start >= 0) && (limit > 0)) {
         listQuery.setFirstResult(start);
         listQuery.setMaxResults(limit);
       }
       List<T> list = listQuery.list();
       pList.setList(list);
       return pList;
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }








   @Transactional(readOnly=true)
   public PageList findPageList(Class<T> clazz, List<Criterion> criterions, List<Order> orders, int start, int limit)
   {
     try
     {
       PageList pList = new PageList();
       Criteria criteria = getCurrentSession().createCriteria(clazz);
       for (Criterion criterion : criterions) {
         criteria.add(criterion);
       }
       for (Order order : orders) {
         criteria.addOrder(order);
       }

       criteria.setProjection(Projections.rowCount());
       Number result = (Number)criteria.uniqueResult();
       int count = result.intValue();
       pList.setCount(count);
       criteria.setProjection(null);

       if ((start >= 0) && (limit > 0)) {
         criteria.setFirstResult(start);
         criteria.setMaxResults(limit);
       }
       List<T> list = criteria.list();
       pList.setList(list);
       return pList;
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }






   @Transactional(readOnly=true)
   public List<T> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders)
   {
     try
     {
       Criteria criteria = getCurrentSession().createCriteria(clazz);
       for (Criterion criterion : criterions) {
         criteria.add(criterion);
       }
       for (Order order : orders) {
         criteria.addOrder(order);
       }
       return criteria.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }







   @Transactional(readOnly=true)
   public List<T> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders, int start, int limit)
   {
     try
     {
       Criteria criteria = getCurrentSession().createCriteria(clazz);
       for (Criterion criterion : criterions) {
         criteria.add(criterion);
       }
       for (Order order : orders) {
         criteria.addOrder(order);
       }
       if ((start >= 0) && (limit > 0)) {
         criteria.setFirstResult(start);
         criteria.setMaxResults(limit);
       }
       return criteria.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }









   @Transactional(readOnly=true)
   public List<?> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders, Projection projection, int start, int limit)
   {
     try
     {
       Criteria criteria = getCurrentSession().createCriteria(clazz);
       for (Criterion criterion : criterions) {
         criteria.add(criterion);
       }
       for (Order order : orders) {
         criteria.addOrder(order);
       }
       criteria.setProjection(projection);
       if ((start >= 0) && (limit > 0)) {
         criteria.setFirstResult(start);
         criteria.setMaxResults(limit);
       }
       return criteria.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }









   @Transactional(readOnly=true)
   public List<?> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders, Projection projection)
   {
     try
     {
       Criteria criteria = getCurrentSession().createCriteria(clazz);
       for (Criterion criterion : criterions) {
         criteria.add(criterion);
       }
       for (Order order : orders) {
         criteria.addOrder(order);
       }
       criteria.setProjection(projection);
       return criteria.list();
     } catch (Exception e) {
       logger.error("查询出错", e);
     }
     return null;
   }

   @Transactional
   public boolean doWork(final String sql, final List<Object[]> params) {
     boolean flag = false;
     try {
       getCurrentSession().doWork(new org.hibernate.jdbc.Work()
       {
         public void execute(Connection connection) throws SQLException {
           BatchSQLUtil sqlUtil = new BatchSQLUtil(connection, sql);
           for (Object[] param : params) {
             sqlUtil.addCount(param);
           }
           sqlUtil.commit();
         }
       });
       flag = true;
     } catch (Exception e) {
       logger.error("doWork出错", e);
     }
     return flag;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/jdbc/hibernate/HibernateSuperDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
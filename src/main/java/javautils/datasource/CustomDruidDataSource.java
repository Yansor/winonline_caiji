 package javautils.datasource;

 import com.alibaba.druid.filter.Filter;
 import com.alibaba.druid.pool.DruidDataSource;
 import java.util.ArrayList;
 import java.util.List;



 public class CustomDruidDataSource
   extends DruidDataSource
 {
   public List<Filter> getProxyFilters()
   {
     List<Filter> proxyFilters = new ArrayList();
     proxyFilters.add(ConfigDruidDataSource.configLog4jFilter());
     proxyFilters.add(ConfigDruidDataSource.configStatFilter());
     return proxyFilters;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/datasource/CustomDruidDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
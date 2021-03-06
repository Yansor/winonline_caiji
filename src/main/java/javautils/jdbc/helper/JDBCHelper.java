 package javautils.jdbc.helper;

 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
import java.util.List;
 import javax.naming.Context;
 import javax.naming.InitialContext;
 import javax.sql.DataSource;
 import net.sf.json.JSONArray;
 import net.sf.json.JSONObject;



 public class JDBCHelper
 {
   public static final String MYSQL = "com.mysql.jdbc.Driver";

   public static Connection openConnection(String url, String username, String password, String type)
   {
     Connection conn = null;
     try {
       Class.forName(type);
       conn = DriverManager.getConnection(url, username, password);
     } catch (Exception localException) {}
     return conn;
   }

   public static boolean executeUpdate(String sql, Connection conn) {
     boolean flag = false;
     if (conn != null) {
       PreparedStatement pst = null;
       try {
         pst = conn.prepareStatement(sql);
         int count = pst.executeUpdate();
         return count > 0;
       }
       catch (Exception localException) {}finally {
         try {
           if (pst != null) pst.close();
           if (conn != null) conn.close();
         }
         catch (SQLException localSQLException2) {}
       }
     }
return false;

   }

   public static JSONArray executeQuery(String sql, Connection conn) {
     List list = null;
     if (conn != null) {
       PreparedStatement pst = null;
       try {
         pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery();
         return toJsonArray(rs);
       } catch (Exception localException) {}finally {
         try {
           if (pst != null) pst.close();
           if (conn != null) conn.close();
         }
         catch (SQLException localSQLException2) {}
       }
     }
            return null;
   }

   public static JSONArray toJsonArray(ResultSet rs) throws Exception {
     JSONArray list = new JSONArray();
     if (rs != null) {
       ResultSetMetaData metaData = rs.getMetaData();
       int columnCount = metaData.getColumnCount();
       while (rs.next()) {
         JSONObject obj = new JSONObject();
         for (int i = 0; i < columnCount; i++) {
           String columnName = metaData.getColumnLabel(i + 1);
           String value = rs.getString(columnName);
           obj.put(columnName, value);
         }
         list.add(obj);
       }
     }
     return list;
   }





   public static Connection openConnection(String jndi)
   {
     Connection conn = null;
     try {
       Context context = new InitialContext();
       DataSource source = (DataSource)context.lookup(jndi);
       conn = source.getConnection();
     } catch (Exception localException) {}
     return conn;
   }

   public static void main(String[] args) {
     String url = "jdbc:mysql://192.168.1.111:3306/test?useUnicode=true&characterEncoding=utf-8";
     String username = "root";
     String password = "root";
     Connection conn = openConnection(url, username, password, "com.mysql.jdbc.Driver");
     String sql = "select * from user";
     executeQuery(sql, conn);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/jdbc/helper/JDBCHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
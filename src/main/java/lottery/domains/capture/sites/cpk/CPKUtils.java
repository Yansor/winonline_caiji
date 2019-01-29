 package lottery.domains.capture.sites.cpk;

 import java.io.BufferedReader;
 import java.io.InputStream;
 import java.io.PrintStream;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.util.Iterator;
 import net.sf.json.JSONException;
 import net.sf.json.JSONObject;

 public class CPKUtils
 {
   public static void main(String[] args)
   {
     String name = "";
     String uid = "";
     String token = "";

     String url = "http://api.caipiaokong.com/lottery/";
     url = url + "?name=" + name;
     url = url + "&format=json";
     url = url + "&uid=" + uid;
     url = url + "&token=" + token;

     String urlAll = url;
     String charset = "UTF-8";
     String jsonResult = get(urlAll, charset);
     JSONObject object = JSONObject.fromObject(jsonResult);
     try {
       Iterator it = object.keys();
       while (it.hasNext()) {
         String key = (String)it.next();
         String value = object.getString(key);
         JSONObject object1 = JSONObject.fromObject(value);
         String outputStr = "id:" + key;
         outputStr = outputStr + " number:" + object1.getString("number");
         outputStr = outputStr + " dateline:" + object1.getString("dateline");
         System.out.println(outputStr);
       }
     } catch (JSONException e) {
       e.printStackTrace();
     }
   }







   public static String get(String urlAll, String charset)
   {
     BufferedReader reader = null;
     String result = null;
     StringBuffer sbf = new StringBuffer();
     String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
     try {
       URL url = new URL(urlAll);

       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
       connection.setRequestMethod("GET");
       connection.setReadTimeout(30000);
       connection.setConnectTimeout(30000);
       connection.setRequestProperty("User-agent", userAgent);
       connection.connect();
       InputStream is = connection.getInputStream();
       reader = new BufferedReader(new java.io.InputStreamReader(is, charset));
       String strRead = null;
       while ((strRead = reader.readLine()) != null) {
         sbf.append(strRead);
         sbf.append("\r\n");
       }
       reader.close();
       result = sbf.toString();
     } catch (Exception e) {
       e.printStackTrace();
     }
     return result;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/sites/cpk/CPKUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
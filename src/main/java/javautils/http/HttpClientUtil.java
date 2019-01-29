 package javautils.http;

 import java.io.ByteArrayInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.URLDecoder;
 import java.security.KeyManagementException;
 import java.security.NoSuchAlgorithmException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.http.StatusLine;
 import org.apache.http.client.config.RequestConfig;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.client.methods.CloseableHttpResponse;
 import org.apache.http.client.methods.HttpGet;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.conn.ConnectionKeepAliveStrategy;
 import org.apache.http.entity.InputStreamEntity;
 import org.apache.http.impl.client.CloseableHttpClient;
 import org.apache.http.impl.client.HttpClientBuilder;
 import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
 import org.apache.http.message.BasicNameValuePair;
 import org.apache.http.util.EntityUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;



 public class HttpClientUtil
 {
   private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

   private static CloseableHttpClient getHttpClient(int timeout) {
     HttpClientBuilder clientBuilder = HttpClientBuilder.create();


     ConnectionKeepAliveStrategy keepAliveStrategy = HttpConfigBuilder.getInstance().buildKeepAliveStrategy(60000L);
     clientBuilder.setKeepAliveStrategy(keepAliveStrategy);

     RequestConfig requestConfig = HttpConfigBuilder.getInstance().buildRequestConfig(timeout);
     clientBuilder.setDefaultRequestConfig(requestConfig);





     CloseableHttpClient httpClient = clientBuilder.build();

     return httpClient;
   }

   private static CloseableHttpClient getHttpsClient(int timeout, String sslVersion) throws NoSuchAlgorithmException, KeyManagementException {
     HttpClientBuilder clientBuilder = HttpClientBuilder.create();


     ConnectionKeepAliveStrategy keepAliveStrategy = HttpConfigBuilder.getInstance().buildKeepAliveStrategy(60000L);
     clientBuilder.setKeepAliveStrategy(keepAliveStrategy);

     RequestConfig requestConfig = HttpConfigBuilder.getInstance().buildRequestConfig(timeout);
     clientBuilder.setDefaultRequestConfig(requestConfig);

     BasicHttpClientConnectionManager connMgr;
//     BasicHttpClientConnectionManager connMgr;
     if (sslVersion == null) {
       connMgr = HttpConfigBuilder.getInstance().buildBasicHttpsClientConnectionManager();
     }
     else {
       connMgr = HttpConfigBuilder.getInstance().buildBasicHttpsClientConnectionManager(sslVersion);
     }
     clientBuilder.setConnectionManager(connMgr);

     CloseableHttpClient httpClient = clientBuilder.build();

     return httpClient;
   }

   public static String get(String url, Map<String, String> httpHeader, int timeout) throws Exception { CloseableHttpClient httpClient;
//     CloseableHttpClient httpClient;
     if (url.startsWith("https://")) {
       httpClient = getHttpsClient(timeout, null);
     }
     else {
       httpClient = getHttpClient(timeout);
     }

     return get(httpClient, url, httpHeader);
   }

   public static String get(CloseableHttpClient httpClient, String url, Map<String, String> httpHeader) throws IOException {
     String strResult = null;
     try
     {
       HttpGet request = new HttpGet(url);

       Iterator<Entry<String, String>> ies;
       if ((httpHeader != null) && (httpHeader.size() > 0)) {
         for (ies = httpHeader.entrySet().iterator(); ies.hasNext();) {
           Entry<String, String> entry = (Entry)ies.next();
           String key = (String)entry.getKey();
           String value = (String)entry.getValue();
           request.addHeader(key, value);
         }
       }
       CloseableHttpResponse response = httpClient.execute(request);


       if (response.getStatusLine().getStatusCode() == 200) {
         try
         {
           strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
           url = URLDecoder.decode(url, "UTF-8");
         } catch (Exception e) {
           logger.error("get请求提交失败:" + url);
         } finally {
           response.close();
         }
       }
     }
     catch (IOException e) {
       logger.error("get请求提交失败:" + url, e);
     } finally {
       httpClient.close();
     }
     return strResult;
   }

   public static String post(String url, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception { CloseableHttpClient httpClient;
//     CloseableHttpClient httpClient;
     if (url.startsWith("https://")) {
       httpClient = getHttpsClient(timeout, null);
     }
     else {
       httpClient = getHttpClient(timeout);
     }

     return post(httpClient, url, params, headers);
   }

   public static String postAsStream(String url, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception { CloseableHttpClient httpClient;
//     CloseableHttpClient httpClient;
     if (url.startsWith("https://")) {
       httpClient = getHttpsClient(timeout, null);
     }
     else {
       httpClient = getHttpClient(timeout);
     }

     return postAsStream(httpClient, url, params, headers);
   }

   public static String postSSL(String url, Map<String, String> params, Map<String, String> headers, int timeout, String sslVersion) throws Exception { CloseableHttpClient httpClient;
//     CloseableHttpClient httpClient;
     if (url.startsWith("https://")) {
       httpClient = getHttpsClient(timeout, sslVersion);
     }
     else {
       httpClient = getHttpClient(timeout);
     }

     return post(httpClient, url, params, headers);
   }

   public static String post(CloseableHttpClient httpClient, String url, Map<String, String> params, Map<String, String> headers) throws IOException {
     String result = null;
     try {
       HttpPost request = new HttpPost(url);

       Iterator<Entry<String, String>> ies;
       if ((headers != null) && (headers.size() > 0)) {
         for (ies = headers.entrySet().iterator(); ies.hasNext();) {
           Entry<String, String> entry = (Entry)ies.next();

           String key = (String)entry.getKey();
           String value = (String)entry.getValue();

           request.addHeader(key, value);
         }
       }

       if ((params != null) && (params.size() > 0)) {
         List list = new ArrayList();
         Iterator iterator = params.entrySet().iterator();
         while (iterator.hasNext()) {
           Entry elem = (Entry)iterator.next();
           list.add(new BasicNameValuePair((String)elem.getKey(), (String)elem.getValue()));
         }
         if (list.size() > 0) {
           UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
           request.setEntity(entity);
         }
       }

       CloseableHttpResponse response = httpClient.execute(request);
       url = URLDecoder.decode(url, "UTF-8");


       if (response.getStatusLine().getStatusCode() == 200) {
         try
         {
           result = EntityUtils.toString(response.getEntity(), "UTF-8");
         } catch (Exception e) {
           logger.error("post请求提交失败:" + url, e);
         } finally {
           response.close();
         }
       } else {
         result = response.getStatusLine().getStatusCode() + "-" + response.getStatusLine().getReasonPhrase();
       }
     } catch (Exception e) {
       logger.error("post请求提交失败:" + url, e);
     } finally {
       httpClient.close();
     }
     return result;
   }

   public static String postAsStream(CloseableHttpClient httpClient, String url, Map<String, String> params, Map<String, String> headers) throws IOException {
     String result = null;
     try {
       HttpPost request = new HttpPost(url);

       Iterator<Entry<String, String>> ies;
       if ((headers != null) && (headers.size() > 0)) {
         for (ies = headers.entrySet().iterator(); ies.hasNext();) {
           Entry<String, String> entry = (Entry)ies.next();

           String key = (String)entry.getKey();
           String value = (String)entry.getValue();

           request.addHeader(key, value);
         }
       }

       if ((params != null) && (params.size() > 0)) {
         String paramUrl = ToUrlParamUtils.toUrlParam(params);
         InputStream is = new ByteArrayInputStream(paramUrl.getBytes());
         InputStreamEntity inputEntry = new InputStreamEntity(is);
         request.setEntity(inputEntry);
       }

       CloseableHttpResponse response = httpClient.execute(request);
       url = URLDecoder.decode(url, "UTF-8");


       if (response.getStatusLine().getStatusCode() == 200) {
         try
         {
           result = EntityUtils.toString(response.getEntity(), "UTF-8");
         } catch (Exception e) {
           logger.error("post请求提交失败:" + url, e);
         } finally {
           response.close();
         }
       } else {
         result = response.getStatusLine().getStatusCode() + "-" + response.getStatusLine().getReasonPhrase();
       }
     } catch (Exception e) {
       logger.error("post请求提交失败:" + url, e);
     } finally {
       httpClient.close();
     }
     return result;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/http/HttpClientUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
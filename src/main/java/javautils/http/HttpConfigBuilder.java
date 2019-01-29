 package javautils.http;

 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.security.KeyManagementException;
 import java.security.KeyStore;
 import java.security.KeyStoreException;
 import java.security.NoSuchAlgorithmException;
 import java.security.SecureRandom;
 import java.security.UnrecoverableKeyException;
 import java.security.cert.CertificateException;
 import java.security.cert.X509Certificate;
 import javax.net.ssl.HostnameVerifier;
 import javax.net.ssl.KeyManagerFactory;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.SSLSession;
 import javax.net.ssl.TrustManager;
 import javax.net.ssl.X509TrustManager;
 import org.apache.http.client.config.RequestConfig.Builder;
 import org.apache.http.config.Registry;
 import org.apache.http.config.RegistryBuilder;
 import org.apache.http.conn.socket.ConnectionSocketFactory;
 import org.apache.http.conn.socket.PlainConnectionSocketFactory;
 import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
 import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
 import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

 public class HttpConfigBuilder
 {
   private static final HttpConfigBuilder INSTANCE = new HttpConfigBuilder();


   public static HttpConfigBuilder getInstance()
   {
     return INSTANCE;
   }

   public org.apache.http.conn.ConnectionKeepAliveStrategy buildKeepAliveStrategy(final long duration) {
     return new org.apache.http.conn.ConnectionKeepAliveStrategy()
     {
       public long getKeepAliveDuration(org.apache.http.HttpResponse response, org.apache.http.protocol.HttpContext context) {
         return duration;
       }
     };
   }

   public org.apache.http.client.HttpRequestRetryHandler buildRetryHandler(int retryCount) {
     return new org.apache.http.impl.client.DefaultHttpRequestRetryHandler(retryCount, false);
   }

   public org.apache.http.client.config.RequestConfig buildRequestConfig(int timeout) {
     return org.apache.http.client.config.RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
   }



   public BasicHttpClientConnectionManager buildBasicHttpClientConnectionManager()
   {
     BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager();
     return connectionManager;
   }


   public BasicHttpClientConnectionManager buildBasicHttpsClientConnectionManager()
   {
     try
     {
       TrustManager[] trustAllCerts = { new X509TrustManager()
       {
         public X509Certificate[] getAcceptedIssuers() {
           return null;
         }



         public void checkClientTrusted(X509Certificate[] certs, String authType) {}


         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
       } };
       SSLContext sslContext = SSLContext.getInstance("TLS");
       sslContext.init(null, trustAllCerts, new SecureRandom());

       HostnameVerifier allHostsValid = new HostnameVerifier() {
         public boolean verify(String hostname, SSLSession session) {
           return true;
         }
       };
       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);



       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
       return new BasicHttpClientConnectionManager(socketFactoryRegistry);
     }
     catch (KeyManagementException e) {
       e.printStackTrace();
       System.out.println("初始化https连接出错");
     } catch (NoSuchAlgorithmException e) {
       e.printStackTrace();
       System.out.println("初始化https连接出错");
     }

     return null;
   }


   public BasicHttpClientConnectionManager buildBasicHttpsClientConnectionManager(String sslVersion)
   {
     try
     {
       TrustManager[] trustAllCerts = { new X509TrustManager()
       {
         public X509Certificate[] getAcceptedIssuers() {
           return null;
         }



         public void checkClientTrusted(X509Certificate[] certs, String authType) {}


         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
       } };
       SSLContext sslContext = SSLContext.getInstance("TLS");
       sslContext.init(null, trustAllCerts, new SecureRandom());

       HostnameVerifier allHostsValid = new HostnameVerifier() {
         public boolean verify(String hostname, SSLSession session) {
           return true;
         }
       };
       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[] { sslVersion }, null, allHostsValid);



       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
       return new BasicHttpClientConnectionManager(socketFactoryRegistry);
     }
     catch (KeyManagementException e) {
       e.printStackTrace();
       System.out.println("初始化https连接出错");
     } catch (NoSuchAlgorithmException e) {
       e.printStackTrace();
       System.out.println("初始化https连接出错");
     }

     return null;
   }


   public PoolingHttpClientConnectionManager buildPoolingHttpsClientConnectionManager(int maxTotal, int defaultMaxPerRoute)
   {
     try
     {
       TrustManager[] trustAllCerts = { new X509TrustManager()
       {
         public X509Certificate[] getAcceptedIssuers() {
           return null;
         }



         public void checkClientTrusted(X509Certificate[] certs, String authType) {}


         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
       } };
       SSLContext sslContext = SSLContext.getInstance("TLS");
       sslContext.init(null, trustAllCerts, new SecureRandom());

       HostnameVerifier allHostsValid = new HostnameVerifier() {
         public boolean verify(String hostname, SSLSession session) {
           return true;
         }
       };
       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);



       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
       PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
       connectionManager.setMaxTotal(maxTotal);
       connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
       return connectionManager;
     } catch (KeyManagementException e) {
       e.printStackTrace();
       System.out.println("初始化https连接出错");
     } catch (NoSuchAlgorithmException e) {
       e.printStackTrace();
       System.out.println("初始化https连接出错");
     }

     return null;
   }



   public PoolingHttpClientConnectionManager buildPoolingHttpClientConnectionManager(int maxTotal, int defaultMaxPerRoute)
   {
     PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
     connectionManager.setMaxTotal(maxTotal);
     connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
     return connectionManager;
   }


   public PoolingHttpClientConnectionManager buildP12PoolingHttpClientConnectionManager(int maxTotal, int defaultMaxPerRoute, java.io.File p12File, String pwd)
   {
     try
     {
       KeyStore ks = KeyStore.getInstance("PKCS12");
       FileInputStream fis = new FileInputStream(p12File);
       ks.load(fis, pwd.toCharArray());
       KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
       kmf.init(ks, pwd.toCharArray());
       javax.net.ssl.KeyManager[] kms = kmf.getKeyManagers();


       TrustManager[] trustAllCerts = { new X509TrustManager()
       {
         public X509Certificate[] getAcceptedIssuers() {
           return null;
         }



         public void checkClientTrusted(X509Certificate[] certs, String authType) {}


         public void checkServerTrusted(X509Certificate[] certs, String authType) {}
       } };
       SSLContext sslContext = SSLContext.getInstance("TLS");
       sslContext.init(kms, trustAllCerts, new SecureRandom());

       HostnameVerifier allHostsValid = new HostnameVerifier() {
         public boolean verify(String hostname, SSLSession session) {
           return true;
         }
       };
       SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, allHostsValid);



       Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();
       PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
       connMgr.setMaxTotal(maxTotal);
       connMgr.setDefaultMaxPerRoute(defaultMaxPerRoute);

       return connMgr;
     } catch (KeyStoreException e) {
       e.printStackTrace();
       System.out.println("初始化P12连接出错");
     } catch (CertificateException e) {
       e.printStackTrace();
       System.out.println("初始化P12连接出错");
     } catch (NoSuchAlgorithmException e) {
       e.printStackTrace();
       System.out.println("初始化P12连接出错");
     } catch (FileNotFoundException e) {
       e.printStackTrace();
       System.out.println("初始化P12连接出错");
     } catch (IOException e) {
       e.printStackTrace();
       System.out.println("初始化P12连接出错");
     } catch (UnrecoverableKeyException e) {
       e.printStackTrace();
       System.out.println("初始化P12连接出错");
     } catch (KeyManagementException e) {
       e.printStackTrace();
       System.out.println("初始化P12连接出错");
     }

     return null;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/http/HttpConfigBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
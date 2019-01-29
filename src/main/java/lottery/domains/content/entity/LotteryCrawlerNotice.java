 package lottery.domains.content.entity;

 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
















 @Entity
 @Table(name="lottery_crawler_notice", catalog="ecai")
 public class LotteryCrawlerNotice
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String type;
   private String host;
   private Integer port;
   private String username;
   private String password;
   private String method;
   private String url;
   private Integer status;

   public LotteryCrawlerNotice() {}

   public LotteryCrawlerNotice(String name, String type, String host, Integer port, String method, Integer status)
   {
     this.name = name;
     this.type = type;
     this.host = host;
     this.port = port;
     this.method = method;
     this.status = status;
   }



   public LotteryCrawlerNotice(String name, String type, String host, Integer port, String username, String password, String method, String url, Integer status)
   {
     this.name = name;
     this.type = type;
     this.host = host;
     this.port = port;
     this.username = username;
     this.password = password;
     this.method = method;
     this.url = url;
     this.status = status;
   }

   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="id", unique=true, nullable=false)
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   @Column(name="name", nullable=false)
   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   @Column(name="type", nullable=false, length=32)
   public String getType() {
     return this.type;
   }

   public void setType(String type) {
     this.type = type;
   }

   @Column(name="host", nullable=false, length=256)
   public String getHost() {
     return this.host;
   }

   public void setHost(String host) {
     this.host = host;
   }

   @Column(name="port", nullable=false)
   public Integer getPort() {
     return this.port;
   }

   public void setPort(Integer port) {
     this.port = port;
   }

   @Column(name="username")
   public String getUsername() {
     return this.username;
   }

   public void setUsername(String username) {
     this.username = username;
   }

   @Column(name="password")
   public String getPassword() {
     return this.password;
   }

   public void setPassword(String password) {
     this.password = password;
   }

   @Column(name="method", nullable=false, length=32)
   public String getMethod() {
     return this.method;
   }

   public void setMethod(String method) {
     this.method = method;
   }

   @Column(name="url")
   public String getUrl() {
     return this.url;
   }

   public void setUrl(String url) {
     this.url = url;
   }

   @Column(name="status", nullable=false)
   public Integer getStatus() {
     return this.status;
   }

   public void setStatus(Integer status) {
     this.status = status;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/LotteryCrawlerNotice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
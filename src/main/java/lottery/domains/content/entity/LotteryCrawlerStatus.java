 package lottery.domains.content.entity;

 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
















 @Entity
 @Table(name="lottery_crawler_status", catalog="ecai")
 public class LotteryCrawlerStatus
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String showName;
   private String shortName;
   private Integer times;
   private String lastUpdate;
   private String lastExpect;

   public LotteryCrawlerStatus() {}

   public LotteryCrawlerStatus(String showName, String shortName, Integer times, String lastUpdate, String lastExpect)
   {
     this.showName = showName;
     this.shortName = shortName;
     this.times = times;
     this.lastUpdate = lastUpdate;
     this.lastExpect = lastExpect;
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

   @Column(name="show_name", nullable=false, length=128)
   public String getShowName() {
     return this.showName;
   }

   public void setShowName(String showName) {
     this.showName = showName;
   }

   @Column(name="short_name", nullable=false, length=32)
   public String getShortName() {
     return this.shortName;
   }

   public void setShortName(String shortName) {
     this.shortName = shortName;
   }

   @Column(name="times", nullable=false)
   public Integer getTimes() {
     return this.times;
   }

   public void setTimes(Integer times) {
     this.times = times;
   }

   @Column(name="last_update", nullable=false, length=19)
   public String getLastUpdate() {
     return this.lastUpdate;
   }

   public void setLastUpdate(String lastUpdate) {
     this.lastUpdate = lastUpdate;
   }

   @Column(name="last_expect", nullable=false, length=32)
   public String getLastExpect() {
     return this.lastExpect;
   }

   public void setLastExpect(String lastExpect) {
     this.lastExpect = lastExpect;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/LotteryCrawlerStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
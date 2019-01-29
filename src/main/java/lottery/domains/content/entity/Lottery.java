 package lottery.domains.content.entity;

 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;











 @Entity
 @Table(name="lottery", catalog="ecai")
 public class Lottery
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int id;
   private String showName;
   private String shortName;
   private int type;
   private int times;
   private int sort;
   private int status;
   private int self;

   public Lottery() {}

   public Lottery(String showName, int type, int times, int sort, int status, int self)
   {
     this.showName = showName;
     this.type = type;
     this.times = times;
     this.sort = sort;
     this.status = status;
     this.self = self;
   }


   public Lottery(String showName, String shortName, int type, int times, int sort, int status, int self)
   {
     this.showName = showName;
     this.shortName = shortName;
     this.type = type;
     this.times = times;
     this.sort = sort;
     this.status = status;
     this.self = self;
   }

   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="id", unique=true, nullable=false)
   public int getId()
   {
     return this.id;
   }

   public void setId(int id) {
     this.id = id;
   }

   @Column(name="show_name", nullable=false, length=32)
   public String getShowName() {
     return this.showName;
   }

   public void setShowName(String showName) {
     this.showName = showName;
   }

   @Column(name="short_name", length=32, nullable=false)
   public String getShortName() {
     return this.shortName;
   }

   public void setShortName(String shortName) {
     this.shortName = shortName;
   }

   @Column(name="type", nullable=false)
   public int getType() {
     return this.type;
   }

   public void setType(int type) {
     this.type = type;
   }

   @Column(name="times", nullable=false)
   public int getTimes() {
     return this.times;
   }

   public void setTimes(int times) {
     this.times = times;
   }

   @Column(name="`sort`", nullable=false)
   public int getSort() {
     return this.sort;
   }

   public void setSort(int sort) {
     this.sort = sort;
   }

   @Column(name="status", nullable=false)
   public int getStatus() {
     return this.status;
   }

   public void setStatus(int status) {
     this.status = status;
   }

   @Column(name="self", nullable=false)
   public int getSelf() {
     return this.self;
   }

   public void setSelf(int self) {
     this.self = self;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/entity/Lottery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
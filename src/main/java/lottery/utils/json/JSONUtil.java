 package lottery.utils.json;

 import net.sf.json.xml.XMLSerializer;

 public class JSONUtil
 {
   public static net.sf.json.JSON toJSONString(String xml)
   {
     XMLSerializer xmlSerializer = new XMLSerializer();
     net.sf.json.JSON json = xmlSerializer.read(xml);
     return json;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/json/JSONUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
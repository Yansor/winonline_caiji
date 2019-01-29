 package javautils.json;

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



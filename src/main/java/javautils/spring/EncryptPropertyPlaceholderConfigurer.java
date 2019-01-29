 package javautils.spring;

 import javautils.encrypt.DESUtil;
         import javautils.StringUtil;
 import javautils.encrypt.DESUtil;
 import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


 public class EncryptPropertyPlaceholderConfigurer
   extends PropertyPlaceholderConfigurer
 {
   private static final String KEY = "#$ddw134R$#G#DSFW@#?!@#!@#$CCCREW1";
   private static final DESUtil DES_UTIL = new DESUtil();

   protected String convertProperty(String propertyName, String propertyValue)
   {
     if ((StringUtil.isNotNull(propertyValue)) && (propertyValue.endsWith("|e"))) {
       String tempValue = propertyValue.substring(0, propertyValue.length() - 2);
       String decryptValue = DES_UTIL.decryptStr(tempValue, "#$ddw134R$#G#DSFW@#?!@#!@#$CCCREW1");
       return decryptValue;
     }
     return propertyValue;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/spring/EncryptPropertyPlaceholderConfigurer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
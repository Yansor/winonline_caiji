 package javautils.encrypt;

 import java.security.Key;
 import javax.crypto.Cipher;
 import javax.crypto.SecretKeyFactory;
 import javax.crypto.spec.DESKeySpec;
 import sun.misc.BASE64Decoder;
 import sun.misc.BASE64Encoder;








 public class DESUtil
 {
   public static DESUtil getInstance()
   {
     return new DESUtil();
   }



   public Key getKey(String strKey)
   {
     Key key = null;
     try {
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
       DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
       keyFactory.generateSecret(keySpec);
       key = keyFactory.generateSecret(keySpec);
     } catch (Exception e) {
       e.printStackTrace();
     }
     return key;
   }



   public String encryptStr(String strMing, String keyStr)
   {
     byte[] byteMi = null;
     byte[] byteMing = null;
     String strMi = "";
     BASE64Encoder base64en = new BASE64Encoder();
     try {
       byteMing = strMing.getBytes("UTF8");
       byteMi = encryptByte(byteMing, getKey(keyStr));
       strMi = base64en.encode(byteMi);
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       base64en = null;
       byteMing = null;
       byteMi = null;
     }
     return strMi;
   }






   public String decryptStr(String strMi, String keyStr)
   {
     byte[] byteMing = null;
     byte[] byteMi = null;
     String strMing = "";
     BASE64Decoder base64De = new BASE64Decoder();
     try {
       byteMi = base64De.decodeBuffer(strMi);
       byteMing = decryptByte(byteMi, getKey(keyStr));
       strMing = new String(byteMing, "UTF8");
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       base64De = null;
       byteMing = null;
       byteMi = null;
     }
     return strMing;
   }






   private byte[] encryptByte(byte[] byteS, Key key)
   {
     byte[] byteFina = null;
     Cipher cipher;
     try {
       cipher = Cipher.getInstance("DES");
       cipher.init(1, key);
       byteFina = cipher.doFinal(byteS);
     } catch (Exception e) {
//    Cipher cipher;
       e.printStackTrace();
     } finally {
//    Cipher cipher;
       cipher = null;
     }
     return byteFina;
   }






   private byte[] decryptByte(byte[] byteD, Key key)
   {
     byte[] byteFina = null;
     Cipher cipher;
     try {
       cipher = Cipher.getInstance("DES");
       cipher.init(2, key);
       byteFina = cipher.doFinal(byteD);
     } catch (Exception e) {
//    Cipher cipher;
       e.printStackTrace();
     } finally {
//    Cipher cipher;
       cipher = null;
     }
     return byteFina;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/encrypt/DESUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
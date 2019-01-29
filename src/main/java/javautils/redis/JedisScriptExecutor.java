 package javautils.redis;

 import java.io.IOException;
 import java.util.Arrays;
 import java.util.List;
 import javautils.redis.pool.JedisPool;
 import org.apache.commons.io.FileUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.core.io.DefaultResourceLoader;
 import org.springframework.core.io.Resource;
 import org.springframework.util.Assert;
 import redis.clients.jedis.Jedis;
 import redis.clients.jedis.exceptions.JedisDataException;













 public class JedisScriptExecutor
 {
   private static Logger logger = LoggerFactory.getLogger(JedisScriptExecutor.class);

   private JedisTemplate jedisTemplate;
   private String script;
   private String sha1;

   public JedisScriptExecutor(JedisPool jedisPool)
   {
     this.jedisTemplate = new JedisTemplate(jedisPool);
   }

   public JedisScriptExecutor(JedisTemplate jedisTemplate) {
     this.jedisTemplate = jedisTemplate;
   }



   public void load(final String scriptContent)
     throws JedisDataException
   {
     this.sha1 = ((String)this.jedisTemplate.execute(new JedisTemplate.JedisAction() {
       public String action(Jedis jedis) {
         return jedis.scriptLoad(scriptContent);
       }
     }));
     this.script = scriptContent;

     logger.debug("Script \"{}\" had been loaded as {}", scriptContent, this.sha1);
   }


   public void loadFromFile(String scriptPath)
     throws JedisDataException
   {
    String  scriptContent = null;
     try
     {
       Resource resource = new DefaultResourceLoader().getResource(scriptPath);
       scriptContent = FileUtils.readFileToString(resource.getFile(), "UTF-8");
     } catch (IOException e) {
//    String scriptContent;
       throw new IllegalArgumentException(scriptPath + " is not exist.", e);
     }
//     String scriptContent;
     load(scriptContent);
   }



   public Object execute(String[] keys, String[] args)
     throws IllegalArgumentException
   {
     Assert.notNull(keys, "keys can't be null.");
     Assert.notNull(args, "args can't be null.");
     return execute(Arrays.asList(keys), Arrays.asList(args));
   }



   public Object execute(final List<String> keys, final List<String> args)
     throws IllegalArgumentException
   {
     Assert.notNull(keys, "keys can't be null.");
     Assert.notNull(args, "args can't be null.");

     return this.jedisTemplate.execute(new JedisTemplate.JedisAction() {
       public Object action(Jedis jedis) {
         try {
           return jedis.evalsha(JedisScriptExecutor.this.sha1, keys, args);
         } catch (JedisDataException e) {
           JedisScriptExecutor.logger.warn("Script {} is not loaded in server yet or the script is wrong, try to reload and run it again.",

             JedisScriptExecutor.this.script, e); }
         return jedis.eval(JedisScriptExecutor.this.script, keys, args);
       }
     });
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/JedisScriptExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
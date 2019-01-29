 package javautils.redis.pool;

 import redis.clients.jedis.HostAndPort;
 import redis.clients.jedis.JedisPoolConfig;







 public class JedisDirectPool
   extends JedisPool
 {
   public JedisDirectPool(HostAndPort address, JedisPoolConfig config)
   {
     initInternalPool(address, new ConnectionInfo(), config);
   }

   public JedisDirectPool(HostAndPort address, ConnectionInfo connectionInfo, JedisPoolConfig config) {
     initInternalPool(address, connectionInfo, config);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/pool/JedisDirectPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
package com.lifuz.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.lifuz.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * redis 缓存
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/21 20:56
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {

        logger.error("ip:"+ip + "       port:" + port );

        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(Long seckillId) {

        try {

            Jedis jedis = jedisPool.getResource();

            try {

                String key = "seckill:" + seckillId;

                //并没有实现内部序列化操作
                //get->byte[] ->反序列化 -> Object(Seckill)
                //采用自定义序列化

                //protostuff :pojo

                byte[] bytes = jedis.get(key.getBytes());

                if (bytes != null) {

                    Seckill seckill = schema.newMessage();

                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //被反序列化
                    return seckill;
                }

            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;

    }

    public String putSeckill(Seckill seckill) {

        //set Object(Seckill) -> 序列化 -> byte[]

        try {

            Jedis jedis = jedisPool.getResource();

            try {

                String key = "seckill:" + seckill.getSeckillId();

                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                int timeout = 60 * 60;

                String result = jedis.setex(key.getBytes(), timeout, bytes);

                return result;

            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

}

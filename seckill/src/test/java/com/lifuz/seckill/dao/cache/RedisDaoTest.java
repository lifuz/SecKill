package com.lifuz.seckill.dao.cache;

import com.lifuz.seckill.dao.SeckillDao;
import com.lifuz.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/21 21:21
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private Long id = 1001L;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void TestSeckill() throws Exception {

        Seckill seckill = redisDao.getSeckill(id);

        if (seckill == null) {
            seckill = seckillDao.queryById(id);

            if ( seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);


                seckill = redisDao.getSeckill(id);

                System.out.println(seckill);
            }
        }

    }

}
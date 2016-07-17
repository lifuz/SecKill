package com.lifuz.seckill.dao;

import com.lifuz.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 9:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉juni spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {

        int updateCount = seckillDao.reduceNumber(1000L,new Date());
        System.out.println(updateCount);

    }

    @Test
    public void queryById() throws Exception {
        Long id = 1000L;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());

        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0,100);

        for (Seckill seckill:seckills) {

            System.out.println(seckill);

        }

    }

}
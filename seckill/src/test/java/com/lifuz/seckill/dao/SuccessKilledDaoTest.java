package com.lifuz.seckill.dao;

import com.lifuz.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 9:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {

        SuccessKilled successKilled = new SuccessKilled();
        successKilled.setSeckillId(1001L);
        successKilled.setUserPhone(13013873964L);

        int insertCount = successKilledDao.insertSuccessKilled(successKilled);
        System.out.println(insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {

        long id = 1001;
        long phone = 13013873964L;

        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);

    }

}
package com.lifuz.seckill.service;

import com.lifuz.seckill.dto.Exposer;
import com.lifuz.seckill.dto.SeckillExecution;
import com.lifuz.seckill.entity.Seckill;
import com.lifuz.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 15:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"}
)
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {

        List<Seckill> seckills = seckillService.getSeckillList();
        logger.info("list={}", seckills);
    }

    @Test
    public void getById() throws Exception {

        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);

    }

    @Test
    public void exportSeckillUrl() throws Exception {

        long id = 1000;

        Exposer exposer = seckillService.exportSeckillUrl(id);

        logger.info("exposer={}", exposer);

    }

    @Test
    public void testSeckillLogic() throws Exception {

        long id = 1000;

        Exposer exposer = seckillService.exportSeckillUrl(id);

        if (exposer.isExposed()) {

            logger.info("exposer={}", exposer);

            long phone = 13013874965L;
            String md5 = exposer.getMd5();

            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);

                logger.info("SeckillExecution = {}", execution);
            } catch (SeckillException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }


    }

    @Test
    public void executeSeckill() throws Exception {

        long id = 1000;
        long phone = 13013874964L;
        String md5 = "57ce6eed9ab837e938f7a10a97b488fe";

        SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);

        logger.info("SeckillExecution = {}", execution);

    }

}
package com.lifuz.seckill.service.impl;

import com.lifuz.seckill.dao.SeckillDao;
import com.lifuz.seckill.dao.SuccessKilledDao;
import com.lifuz.seckill.dto.Exposer;
import com.lifuz.seckill.dto.SeckillExecution;
import com.lifuz.seckill.entity.Seckill;
import com.lifuz.seckill.entity.SuccessKilled;
import com.lifuz.seckill.enums.SeckillStatEnum;
import com.lifuz.seckill.exception.RepeatKillException;
import com.lifuz.seckill.exception.SeckillCloseException;
import com.lifuz.seckill.exception.SeckillException;
import com.lifuz.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * service层实现类
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:26
 */
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SeckillDao seckillDao;

    private SuccessKilledDao successKilledDao;


    private final String slat = "dkld##$3^5$^5%565#dkdff";


    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 5);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill = seckillDao.queryById(seckillId);

        if (seckill == null) {
            return new Exposer(false, seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        Date nowTime = new Date();

        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }


        String md5 = getMD5(seckillId);

        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
            throws RepeatKillException, SeckillCloseException, SeckillException {

        if(md5 == null || md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }

        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);

            if (updateCount <= 0) {
                throw new SeckillCloseException("sekill is closed");
            } else {

                SuccessKilled successKilled  = new SuccessKilled();
                successKilled.setSeckillId(seckillId);
                successKilled.setUserPhone(userPhone);

                int insertCount = successKilledDao.insertSuccessKilled(successKilled);

                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }

            }
        } catch (SeckillCloseException e1) {

            throw e1;

        } catch (RepeatKillException e2) {

            throw e2;

        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error：" + e.getMessage());
        }

    }
}

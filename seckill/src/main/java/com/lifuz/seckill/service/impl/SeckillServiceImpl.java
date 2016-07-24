package com.lifuz.seckill.service.impl;

import com.lifuz.seckill.dao.SeckillDao;
import com.lifuz.seckill.dao.SuccessKilledDao;
import com.lifuz.seckill.dao.cache.RedisDao;
import com.lifuz.seckill.dto.Exposer;
import com.lifuz.seckill.dto.SeckillExecution;
import com.lifuz.seckill.entity.Seckill;
import com.lifuz.seckill.entity.SuccessKilled;
import com.lifuz.seckill.enums.SeckillStatEnum;
import com.lifuz.seckill.exception.RepeatKillException;
import com.lifuz.seckill.exception.SeckillCloseException;
import com.lifuz.seckill.exception.SeckillException;
import com.lifuz.seckill.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.javassist.scopedpool.SoftValueHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * service层实现类
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:26
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;


    private final String slat = "dkld##$3^5$^5%565#dkdff";


    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 5);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {

        //优化点：缓存优化
        Seckill seckill = redisDao.getSeckill(seckillId);

        if (seckill == null) {

            seckill = seckillDao.queryById(seckillId);

            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                redisDao.putSeckill(seckill);
            }

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

    /**
     * 使用注解控制事务方法的优点：
     * 1：开发团队达成一致的约定，明确标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可能短不要穿插其他的网络操作Rpc/HTTP 请求或者剥离到事务方法外部
     * 3：不是所有的方法都需要事务
     */
    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
            throws RepeatKillException, SeckillCloseException, SeckillException {

        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        Date nowTime = new Date();
        try {

            SuccessKilled successKilled = new SuccessKilled();
            successKilled.setSeckillId(seckillId);
            successKilled.setUserPhone(userPhone);

            int insertCount = successKilledDao.insertSuccessKilled(successKilled);

            if (insertCount <= 0) {
                throw new RepeatKillException("seckill repeated");
            } else {
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

                if (updateCount <= 0) {
                    throw new SeckillCloseException("sekill is closed");
                } else {

                    successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }

            }


        } catch (SeckillCloseException e1) {

            throw e1;

        } catch (RepeatKillException e2) {

            throw e2;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error：" + e.getMessage());
        }


    }

    public SeckillExecution executeSeckillProcedure(Long seckillId, Long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillException {

        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        Date killTime = new Date();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);

        try {

            seckillDao.killByProcedure(map);

            //获取result
            int result = MapUtils.getInteger(map, "result", -2);



            if (result == 1) {
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);

                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
            } else {
//                logger.info(result + "   " +SeckillStatEnum.stateOf(-1).getState() + "  " );
                return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }

    }
}

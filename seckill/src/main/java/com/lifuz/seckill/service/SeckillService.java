package com.lifuz.seckill.service;

import com.lifuz.seckill.dto.Exposer;
import com.lifuz.seckill.dto.SeckillExecution;
import com.lifuz.seckill.entity.Seckill;
import com.lifuz.seckill.exception.RepeatKillException;
import com.lifuz.seckill.exception.SeckillCloseException;
import com.lifuz.seckill.exception.SeckillException;

import java.util.List;

/**
 *
 * 业务接口：站在“使用者”角度设计接口
 * 三个方面：方法定义力度，参数，返回类型
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 11:54
 */
public interface SeckillService {

    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
        throws RepeatKillException,SeckillCloseException,SeckillException;

}

package com.lifuz.seckill.dao;

import com.lifuz.seckill.entity.SuccessKilled;

/**
 * Created by lifuz on 2016/7/17.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param successKilled
     * @return
     */
    int insertSuccessKilled(SuccessKilled successKilled);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(Long seckillId);



}

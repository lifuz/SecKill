package com.lifuz.seckill.dto;

import com.lifuz.seckill.entity.SuccessKilled;
import com.lifuz.seckill.enums.SeckillStatEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 封装秒杀执行后结果
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillExecution {

    public SeckillExecution (Long seckillId, SeckillStatEnum statEnum){
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public SeckillExecution (Long seckillId, SeckillStatEnum statEnum,SuccessKilled successKilled){
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    private Long seckillId;

    //秒杀执行结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;
}

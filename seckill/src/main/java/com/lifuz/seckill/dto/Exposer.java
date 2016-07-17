package com.lifuz.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 暴露秒杀地址DTO
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exposer {

    //是否开启秒杀
    private boolean exposed;

    //一种加密措施
    private String md5;

    //id
    private Long seckillId;

    //系统当前时间
    private Long now;

    //开始时间
    private Long start;

    //结束时间
    private Long end;

    public Exposer(boolean exposed,String md5,long seckillId){
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed,Long seckillId,Long now,Long start,Long end) {

        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;

    }

    public Exposer(boolean exposed,Long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}

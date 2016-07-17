package com.lifuz.seckill.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by lifuz on 2016/7/17.
 */
@Data
@NoArgsConstructor
public class SuccessKilled {

    private Long seckillId;
    private Long userPhone;
    private short state;
    private Date createTime;

    private Seckill seckill;

}

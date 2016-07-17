package com.lifuz.seckill.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by lifuz on 2016/7/17.
 */
@Data
@NoArgsConstructor
public class Seckill {

    private Long seckillId;
    private String name;
    private int number;
    private Date startTime;
    private Date endTime;
    private Date createTime;

}

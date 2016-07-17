package com.lifuz.seckill.enums;

/**
 *
 * 使用枚举表述常量数据字段
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:59
 */
public enum SeckillStatEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEATE_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");


    private int state;

    private String stateInfo;

    SeckillStatEnum(int state,String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStatEnum stateOf(int index){
        for (SeckillStatEnum state :values()) {
            return state;
        }

        return null;
    }
}

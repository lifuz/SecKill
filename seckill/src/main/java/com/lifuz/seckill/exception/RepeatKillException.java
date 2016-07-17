package com.lifuz.seckill.exception;

/**
 *
 * 重复秒杀异常
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:16
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}

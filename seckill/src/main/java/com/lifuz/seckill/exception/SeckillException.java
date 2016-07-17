package com.lifuz.seckill.exception;

/**
 *
 * 秒杀相关异常
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:19
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}

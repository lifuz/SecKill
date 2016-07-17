package com.lifuz.seckill.exception;

import com.lifuz.seckill.dto.SeckillExecution;

/**
 *
 * 秒杀关闭异常
 *
 * @author: 李富
 * @email: lifuzz@163.com
 * @time: 2016/7/17 12:18
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}

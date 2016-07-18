package com.lifuz.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者：李富
 * 邮箱：lifuzz@163.com
 * 时间：2016/7/18 15:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillResult<T> {

    private boolean success;

    private T data;

    private String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}

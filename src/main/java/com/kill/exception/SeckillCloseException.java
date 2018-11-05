package com.kill.exception;

/**
 * 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException {
    // 构造方法：这个异常抛出的信息由我们定义
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}

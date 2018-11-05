package com.kill.exception;

/**
 * 秒杀相关业务异常
 * 因为所有异常都是它
 * 所以重复秒杀和秒杀关闭都应该是它的集成
 */
public class SeckillException  extends RuntimeException {
    //构造方法：这个异常抛出的信息由我们定义
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}

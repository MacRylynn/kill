package com.kill.exception;

/**
 * 重复秒杀异常（运行期异常）
 */
public class RepeatKillException extends SeckillException {
    //构造方法：这个异常抛出的信息由我们定义
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

}

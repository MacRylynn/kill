package com.kill.dto;


//封装json结果  是一个泛型
//所有的ajax请求放回类型
public class SeckillResult<T>  {
    //秒杀执行是否成功
    private boolean success;
    //秒杀执行的数据
    private T data;
    //秒杀执行错误时的错误信息
    private String error;

    //构造器：秒杀执行成功
    public SeckillResult(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    //构造器：秒杀执行失败
    public SeckillResult(boolean success, String error) {
        super();
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }



}

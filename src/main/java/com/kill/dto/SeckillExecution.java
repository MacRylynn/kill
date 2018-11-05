package com.kill.dto;

import com.kill.entity.SuccessKilled;
import com.kill.enums.SeckillStateEnum;

/**
 * 封装秒杀执行后结果
 */
public class SeckillExecution {
    //秒杀商品ID 标识哪一个秒杀单
    private long seckillId;

    // 秒杀执行结果的状态
    private int state;

    // 状态的明文标识
    private String stateInfo;

    // 当秒杀成功时，需要传递秒杀成功的对象回去
    private SuccessKilled successKilled;

    //构造方法：秒杀成功返回的信息
    public SeckillExecution(long seckillId, SeckillStateEnum state, SuccessKilled successKilled) {
        super();
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getInfo();
        this.successKilled = successKilled;
    }

    //构造方法：秒杀失败返回的信息
    public SeckillExecution(long seckillId, SeckillStateEnum state) {
        super();
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getInfo();
    }

    public SeckillExecution(long seckillId, int i, String 秒杀成功, SuccessKilled successKilled) {
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
                + ", successKilled=" + successKilled + "]";
    }


}

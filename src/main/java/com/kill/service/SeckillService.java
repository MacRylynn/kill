package com.kill.service;

import com.kill.dto.Exposer;
import com.kill.dto.SeckillExecution;
import com.kill.entity.Seckill;
import com.kill.exception.RepeatKillException;
import com.kill.exception.SeckillCloseException;
import com.kill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”而不是实现的角度去设计接口。
 * 三个方面：方法定义粒度，应该是很明确的，比如暴露接口、秒杀、减库存应该分成很多部分，而不是写在一个函数里面。再比如，秒杀行为有成功、失败和异常分开写。
 *           参数，应该是越简练越直接越好，不要封装很庞大的参数列表去传输。
 *           返回类型，一定要友好，不要返回Map等等（return 类型/异常）。
 */
public interface SeckillService {
    /**
     * 查询全部的秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 在秒杀开启时输出秒杀接口的地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
     * 传进来的是商品的ID、用户电话号码、MD5，和系统里面的做比较，如果相同则开启秒杀，不同则拒绝秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}

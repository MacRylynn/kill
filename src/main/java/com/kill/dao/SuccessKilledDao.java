package com.kill.dao;

import com.kill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 关注的是对数据库的操作
 */
@Mapper
public interface SuccessKilledDao {
    /**
     * 插入购买明细,可过滤重复，联合主键
     *
     * @param seckillId
     * @param userPhone
     * @return 插入的结果行数，有主键冲突（重复秒杀）时返回0
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据秒杀商品的id查询明细SuccessKilled对象(该对象携带了Seckill秒杀产品对象)
     *
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}

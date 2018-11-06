package com.kill.dao;

import com.kill.entity.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SeckillDao {


    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新的记录行数
     */

    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     *根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId );

    /**
     *根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit   在offset之后去多少条  比如offect=10 limit = 5   就是取 10-14这5条数据
     * @return
     */
    //MyBatis自动把多个参数的函数转化成形参的情况  比如 List<Seckill> queryAll(int offset,  int limit);会被转化成List<Seckill> queryAll(arg0,  arg1)
    //在这里加上@Param("limit")是为了在SeckillDao.xml文件里面的limit #{offset},#{limit}能够识别为参数

    List<Seckill> queryAll(@Param("offset") int offset,  @Param("limit")int limit);
    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}

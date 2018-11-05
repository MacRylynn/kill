package com.kill.dao;

import com.kill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * SpringBoot中进行单元测试
 * 需要添加以下两行注释
 * @RunWith(SpringRunner.class)  ：告诉Junit运行使用Spring 的单元测试支持，SpringRunner是SpringJunit4ClassRunner新的名称，只是视觉上看起来更简单了
 * @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)	：该注解可以在一个测试类指定运行Spring Boot为基础的测试
 *
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillDaoTest {

    @Autowired
    //注入DAO实现类依赖
    private SeckillDao seckillDAO;

    @Test
    public void testReduceNumber() {
        long seckillId=1000;
        Date killTime=new Date();
        int updateCount=seckillDAO.reduceNumber(seckillId,killTime);
        System.out.println(updateCount);
    }

    @Test
    public void testQueryById() {
        long seckillId=1000;
        Seckill seckill=seckillDAO.queryById(seckillId);
        System.out.println(seckill);

    }

    @Test
    public void testQueryAll() {
        int offset = 0;
        int limit = 2;

        List<Seckill> seckills=seckillDAO.queryAll(offset,limit);
        for (Seckill seckill : seckills)
        {
            System.out.println(seckill);
        }
    }

}

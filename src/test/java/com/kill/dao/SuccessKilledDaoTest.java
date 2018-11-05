package com.kill.dao;

import com.kill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessKilledDaoTest {
    @Autowired
    private SuccessKilledDao successKilledDAO;
    @Test
    public void insertSuccessKilled() {
        long seckillId=1000L;
        long userPhone=13476191873L;
        /**
         * 第一次运行：insertCount = 1
         * 第一次运行：insertCount = 0
         * 表示不能重复秒杀
         * 但是相同的收集号码再次秒杀不会报错，是因为在SQL语句中被igonre掉了
         */
        int insertCount=successKilledDAO.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long seckillId=1000L;
        long userPhone=13476191873L;
        SuccessKilled successKilled=successKilledDAO.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
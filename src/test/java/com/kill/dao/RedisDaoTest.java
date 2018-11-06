//package com.kill.dao;
//
//import com.kill.entity.Seckill;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//
//public class RedisDaoTest {
//    private long id = 1001;
//    @Autowired
//    private RedisDao redisDao;
//    @Autowired
//    private SeckillDao seckillDao;
//
//    @Test
//    public void getSeckill() {
//        Seckill seckill= redisDao.getSeckill(id);
//        if(seckill ==null){
//            seckill = seckillDao.queryById(id);
//            if (seckill!=null){
//              String result=  redisDao.putSeckill(seckill);
//                System.out.println(result);
//                seckill = redisDao.getSeckill(id);
//                System.out.println(seckill);
//            }
//
//        }
//    }
//}
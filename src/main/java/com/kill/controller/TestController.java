package com.kill.controller;

import com.kill.dao.SeckillDao;
import com.kill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private SeckillDao seckillDAO;

    @GetMapping(value = "/test")
    public String myTest() {
        Seckill seckill = seckillDAO.queryById(1000);
        return seckill.toString();
    }
}

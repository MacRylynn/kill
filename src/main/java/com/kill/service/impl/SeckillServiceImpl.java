package com.kill.service.impl;


import com.kill.dao.RedisDao;
import com.kill.dao.SeckillDao;
import com.kill.dao.SuccessKilledDao;
import com.kill.dto.Exposer;
import com.kill.dto.SeckillExecution;
import com.kill.entity.Seckill;
import com.kill.entity.SuccessKilled;
import com.kill.enums.SeckillStateEnum;
import com.kill.exception.RepeatKillException;
import com.kill.exception.SeckillCloseException;
import com.kill.exception.SeckillException;
import com.kill.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {
    //统一的日志API
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入Service依赖  不需要再 private SeckillDao seckillDao = new SeckillDao();
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
   private RedisDao redisDao;

    //md5盐值字符串，用于混淆md5
    private final String salt = "asda!@#@#$@#asdas$@1555213123#asdasdaasd$@#asd$@as2#$#!!!";


    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    //暴露秒杀接口  输出秒杀接口地址（仔细体会这个暴露接口的业务的过程）
    @Override
    public Exposer exportSeckillUrl(long seckillId) {

        //方式一  用Redis优化访问
        /**
         * 优化点：缓存优化
         */
//         1:访问Redis
        Seckill seckill = redisDao.getSeckill(seckillId);
     if (seckill == null) {
         // 2：如果缓存没有的话则访问数据库
         seckill = seckillDao.queryById(seckillId);//拿到这一条秒杀
        }
        //如果数据库也没有直接返回错误信息
        if (seckill == null) {
            //构造方法：秒杀商品不存在，不暴露接口
            return new Exposer(false, seckillId);
        }
        else {
            //3：放入Redis
            String result = redisDao.putSeckill(seckill);
            System.out.println(result);
        }

//     方式二  直接用数据库的秒杀
//        Seckill seckill = seckillDao.queryById(seckillId);//拿到这一条秒杀
//        //如果数据库也没有直接返回错误信息
//        if (seckill == null) {
//            //构造方法：秒杀商品不存在，不暴露接口
//            return new Exposer(false, seckillId);
//        }

        Date startTime = seckill.getStartTime();//拿到这条秒杀开始的时间
        Date endTime = seckill.getEndTime();//拿到这条秒杀结束的时间
        Date nowTime = new Date();//拿到系统当前时间
        //判断时间，来判断是否开启接口暴露
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId);
        //条件都满足的话，暴露秒杀接口
        return new Exposer(true, md5, seckillId);
    }

    // seckillId进行MD5加密
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String seckillIdMD5 = DigestUtils.md5DigestAsHex(base.getBytes());//Spring的工具类，生成MD5
        return seckillIdMD5;
    }

    /**
     * 执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
     * <p>
     * 秒杀是否成功，成功:减库存，增加明细；失败:抛出异常，事务回滚
     * <p>
     * 使用注解@Transactional控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务(相对于xml配置而言)，如只有一条修改操作、只读操作不要事务控制
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    @Override
    @Transactional
    //执行秒杀的操作
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        //判断用户输入的MD5是否为空，或者输入的MD5是否和这个秒杀单的MD5相同
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date();//用户执行秒杀的时间

        try {
            //减库存成功了，记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            //唯一：seckillId, userPhone
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                //减库存,热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //库存没有了，抛出秒杀结束了，rollback
                    throw new SeckillCloseException("seckill is closed");
                 } else {
                    //秒杀成功，commit返回成功执行的这个 seckillId，秒杀状态，SuccessKilled
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;//代表秒杀关闭
        } catch (RepeatKillException e2) {
            throw e2;//代表重复秒杀
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期的异常，转化为运行期异常
            throw new SeckillCloseException("seckill inner error" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            return new SeckillExecution(seckillId, SeckillStateEnum.DATE_REWRITE);
        }
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        //执行存储过程,result被复制
        try {
            seckillDao.killByProcedure(map);
            //获取result
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, sk);
            } else {
                return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);

        }

    }
}

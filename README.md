# kill（高并发秒杀系统API）
 


# 主要流程
列表页-->用户登录-->列表详情页-->秒杀倒计时-->秒杀接口地址暴露（防止用户用第三方软件刷单使用了随机salt+userPhone的MD5明文加密）-->执行秒杀。

# 后端使用SpringBoot+MyBatis实现 

# 列表页（前端使用Bootstrap+Thymeleaf实现）
![Image text](https://raw.githubusercontent.com/MacRylynn/kill/master/images/1.png)

# 登录失败
![Image text](https://raw.githubusercontent.com/MacRylynn/kill/master/images/2.png)

# 秒杀结束
![Image text](https://github.com/MacRylynn/kill/blob/master/images/3.png)

# 秒杀倒计时（使用jQuery插件实现）
![Image text](https://raw.githubusercontent.com/MacRylynn/kill/master/images/4.png)

# 开启秒杀
![Image text](https://raw.githubusercontent.com/MacRylynn/kill/master/images/5.png)

# 秒杀成功
![Image text](https://raw.githubusercontent.com/MacRylynn/kill/master/images/6.png)

# 重复秒杀
![Image text](https://raw.githubusercontent.com/MacRylynn/kill/master/images/7.png)

# 高并发可能存在的地方
① 获取系统时间（经过验证不是关键的点）；② 秒杀接口地址暴露（主要是MySQL的物理速度比较慢）③ 执行秒杀操作（主要是MySQL的执行比较慢）。

# 针对于高并发的优化
简单优化：适当交换SQL语句的执行顺序，使行级锁(UPDATE)的持有时间减少，从而减少网络延迟和GC带来的时间消耗。
深度优化：① 针对秒杀接口地址暴露，使用Redis做缓存，在秒杀之前把MySQL数据库中的数据序列化到Redis中，秒杀的时候直接从Redis中取数据；② 使用SQL的存储过程，把SQL语句从客户端转而放入MySQL端执行，降低SQL操作所需要的时间。（后期考虑使用分布式，消息队列等深度优化）

# 体验（使用tomcat热部署）http://47.106.157.157:8080/list

参考
https://github.com/geekyijun/seckill



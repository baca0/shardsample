package com.skplanet.sqe.config;

import com.skplanet.sqe.datasource.ShardAop;
import com.skplanet.sqe.datasource.annotation.Insert;
import com.skplanet.sqe.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오후 3:24
 * To change this template use File | Settings | File Templates.
 */

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());



    @Pointcut("execution(* com.skplanet.sqe.user.UserRepository.createUser*(..))")
    public void test() {
    }

    @Before("test()")
    public void shardInsertBefore(JoinPoint joinPoint) {
        for (Object o : joinPoint.getArgs()) {
              if (o instanceof User ) {
                   User user = (User)o;
                  if (logger.isDebugEnabled() ){
                      logger.debug(user.toString());
                  }
              }
        }
    }
}

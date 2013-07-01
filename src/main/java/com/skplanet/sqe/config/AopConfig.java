package com.skplanet.sqe.config;

import com.skplanet.sqe.datasource.ShardAop;
import com.skplanet.sqe.datasource.annotation.Insert;
import com.skplanet.sqe.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오후 3:24
 */

@Aspect
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());



    @Pointcut("execution(* com.skplanet.sqe..*(..))")
    public void allOperation() {
        if (logger.isDebugEnabled()) {
            logger.debug("allOperation");
        }
    }
}

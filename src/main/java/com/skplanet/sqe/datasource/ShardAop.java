package com.skplanet.sqe.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오후 3:57
 */
//@Aspect
public class ShardAop {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // || @annotation(org.springframework.transaction.annotation.Transactional)
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void shartInsert(JoinPoint joinPoint) {
        logger.debug("=================");

        logger.debug("=================");
    }
}

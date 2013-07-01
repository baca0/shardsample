package com.skplanet.sqe.datasource;

import com.skplanet.sqe.user.UserMeta;
import com.skplanet.sqe.user.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오후 3:57
 */
@Aspect
@Component
public class ShardAop {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShardKey shardKey;


    private interface Executable<T> {
        public T execute();
    }

    public Object transaction(Executable<UserMeta> executable,final ProceedingJoinPoint joinPoint) {
        Object retVal = null;
        ShardContextHolder.setShardDbType(ShardDbType.MASTER);
        UserMeta userMeta = executable.execute();
        if(logger.isDebugEnabled()) {
            logger.debug(userMeta.toString());
        }

        ShardContextHolder.setShardDbType(ShardDbType.values()[userMeta.getUserServerId()]);
        DefaultTransactionDefinition  defaultTransactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager,defaultTransactionDefinition);

        retVal = transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                Object result = null;
                try {
                    result = joinPoint.proceed();
                } catch (Throwable e)  {
                    throw new RuntimeException(e);
                }
                return result;
            }
        });

        ShardContextHolder.setShardDbType(ShardDbType.MASTER);
        return  retVal;
    }

    @Around("com.skplanet.sqe.config.AopConfig.allOperation() && @annotation(com.skplanet.sqe.datasource.annotation.Insert) " )
    public Object shardInsertAround(ProceedingJoinPoint joinPoint) throws Throwable {
         return transaction(new Executable<UserMeta>() {
             @Override
             public UserMeta execute() {
                 return userRepository.createUserMeta();
             }
         }, joinPoint);
    }

    @Around("com.skplanet.sqe.config.AopConfig.allOperation() && @annotation(com.skplanet.sqe.datasource.annotation.Select) " )
    public Object shardSelectAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return transaction(new Executable<UserMeta>() {
            @Override
            public UserMeta execute() {
                return userRepository.selectUserMeta();
            }
        }, joinPoint);
    }

}




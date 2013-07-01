package com.skplanet.sqe.datasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: jaejin
 *
 *  Sharding된 mysql에 user meta 정보와 추가적인 정보를 저장을 할 경우에 메소드위에 선언을 한다.
 *
 *  <code>
 *       @Insert
         @Transactional
         public User createUser(User user) {
             userMapper.insertUser(user);
             return user;
         }
 *  </code>
 *
 *  선언시 transaction는 TransactionDefinition.PROPAGATION_REQUIRES_NEW로 선언되고 개별적인 transaction으로 묶인다.
 *  user_id 정보를 기반으로 해서 저장한 디비를 선정해서 하도록 해준다.
 *
 *  자세한 사항은
 *  @see com.skplanet.sqe.datasource.ShardAop 참조
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Insert {
}

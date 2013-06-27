package com.skplanet.sqe.user;

import com.skplanet.sqe.datasource.ShardContextHolder;
import com.skplanet.sqe.datasource.ShardDbType;
import com.skplanet.sqe.datasource.ShardKey;
import com.skplanet.sqe.datasource.annotation.Insert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오전 11:17
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMetaMapper userMetaMapper;

    @Autowired
    private ShardKey shardKey;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Insert
    public User createUser(User user) {
        ShardContextHolder.setShardDbType(ShardDbType.MASTER);
        UserMeta userMeta= new UserMeta();
        userMeta.setUserId(user.getUserId());
        userMeta.setUserServerId(shardKey.getShardKey(userMetaMapper.createUserSeq()));
        userMetaMapper.insertUserMeta(userMeta);

        if(logger.isDebugEnabled()) {
            logger.debug(userMeta.toString());
        }
        ShardContextHolder.setShardDbType(ShardDbType.SLAVE2);

        if(logger.isDebugEnabled()) {
            logger.debug(ShardContextHolder.getShardDbType().toString());
        }

        userMapper.insertUser(user);
        return user;
    }

    public User selectUser(User user) {
        ShardContextHolder.setShardDbType(ShardDbType.MASTER);
        UserMeta userMeta = userMetaMapper.selectUserMeta(user.getUserId());
        if(userMeta != null ) {
            ShardContextHolder.setShardDbType(ShardDbType.values()[userMeta.getUserServerId()]);
            userMapper.selectUser(user);
        }
        return user;
    }
}

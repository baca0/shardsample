package com.skplanet.sqe.user;

import com.skplanet.sqe.datasource.ShardContextHolder;
import com.skplanet.sqe.datasource.ShardDbType;
import com.skplanet.sqe.datasource.ShardKey;
import com.skplanet.sqe.datasource.annotation.Insert;
import com.skplanet.sqe.datasource.annotation.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오전 11:17
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


    public UserMeta createUserMeta() {
        UserMeta userMeta= new UserMeta();
        userMeta.setUserId(ShardContextHolder.getUserId());
        userMetaMapper.createUserSeq();
        userMeta.setUserServerId(shardKey.getShardKey(userMetaMapper.createUserSeqResult()));
        userMetaMapper.insertUserMeta(userMeta);
        return userMeta;
    }

    public UserMeta selectUserMeta() {
        return userMetaMapper.selectUserMeta(ShardContextHolder.getUserId());
    }

    @Insert
    @Transactional
    public User createUser(User user) {
        userMapper.insertUser(user);
        return user;
    }


    @Select
    public User selectUser(User user) {
        userMapper.selectUser(user);
        return user;
    }
}

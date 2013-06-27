package com.skplanet.sqe.config;

import com.skplanet.sqe.datasource.ShardContextHolder;
import com.skplanet.sqe.datasource.ShardDbType;
import com.skplanet.sqe.user.User;
import com.skplanet.sqe.user.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 25.
 * Time: 오후 3:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PropertiesConfig.class,PersistenceIbatisConfig.class})
public class PersistenceConfigTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {



        User user = new User();
        user.setUserId("");

        ShardContextHolder.setShardDbType(ShardDbType.SLAVE2);
        userMapper.selectUser(user);


        ShardContextHolder.setShardDbType(ShardDbType.SLAVE1);
        userMapper.selectUser(user);


        ShardContextHolder.setShardDbType(ShardDbType.MASTER);
        userMapper.selectUser(user);


    }
}

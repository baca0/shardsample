package com.skplanet.sqe.user;

import com.skplanet.sqe.config.*;
import com.skplanet.sqe.datasource.ShardContextHolder;
import com.skplanet.sqe.datasource.ShardKey;
import kr.pe.kwonnam.reflectioninjector.ReflectionInjectorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.MD5;

import java.text.SimpleDateFormat;
import java.util.*;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오후 1:50
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
@ContextConfiguration(classes = {PersistenceIbatisConfig.class,WebMvcConfig.class,RootConfig.class,PropertiesConfig.class,AopConfig.class} )
public class UserRepositoryTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMetaMapper userMetaMapper;

    private ShardKey shardKey;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

    }

    public List<User> makeUser() {
        List<User> users = new ArrayList<>();

        SimpleDateFormat simpleFormatter = new SimpleDateFormat("YYMMDDHHmmssSS");
        for(int i = 1;i<5;i++) {
            User user = new User();
            user.setUserId(i+simpleFormatter.format(new Date()));
            user.setUserName("test"+i);
            users.add(user);
        }

        return users;
    }


    @Test
    public void testCreateUser() throws Exception {

        List<User> users = makeUser();

        for (User user : users) {
            ShardContextHolder.setUserId(user.getUserId());
            //when
            userRepository.createUser(user);

            //then
            User result = userRepository.selectUser(user);
            assertEquals(user,result);
        }

    }

    @Test
    public void testSelectUser() throws Exception {

        User user  = new User();
        userRepository.selectUser(user);


    }
}

package com.skplanet.sqe.user;

import com.skplanet.sqe.config.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 28.
 * Time: 오후 3:58
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
@ContextConfiguration(classes = {PersistenceIbatisConfig.class,WebMvcConfig.class,RootConfig.class,PropertiesConfig.class,AopConfig.class} )

public class UserMetaMapperTest {

    @Autowired
    private UserMetaMapper userMetaMapper;

    @Test
    public void testCreateUserSeq() throws Exception {

        //when
        userMetaMapper.createUserSeq();
        int one = userMetaMapper.createUserSeqResult();
        userMetaMapper.createUserSeq();
        int two  = userMetaMapper.createUserSeqResult();

        //
        assertTrue(one != two);
    }
}

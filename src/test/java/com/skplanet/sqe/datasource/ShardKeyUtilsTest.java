package com.skplanet.sqe.datasource;

import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오전 11:26
 */

public class ShardKeyUtilsTest {


    private ShardKey shardKey = new ShardKey();

    @Test
    public void testGetShardKey() throws Exception {
        assertEquals(1,shardKey.getShardKey(2));
    }

    @Test
    public void testGetShardKey2() throws Exception {
        assertEquals(2,shardKey.getShardKey(3));
    }

    @Test
    public void testGetShardKey3() throws Exception {
        assertEquals(2,shardKey.getShardKey(1));
    }

    @Test
    public void testGetShardKey4() throws Exception {
        //when
        shardKey = new ShardKey(3);

        //then
        assertEquals(1,shardKey.getShardKey(3));
    }
}

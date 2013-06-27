package com.skplanet.sqe.datasource;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 27.
 * Time: 오후 1:37
 * To change this template use File | Settings | File Templates.
 */
public class ShardDbTypeTest {

    @Test
    public void testEnum() throws Exception {
        assertEquals(ShardDbType.SLAVE1,ShardDbType.values()[0]);
    }
}

package com.skplanet.sqe.datasource;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 26.
 * Time: 오전 11:29
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ShardKey {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final int DEFAULT_DIVIDER = ShardDbType.values().length;

    @Getter private int divider;

    public ShardKey() {
        this.divider = DEFAULT_DIVIDER-1;
    }

    public ShardKey(int divider) {
        this.divider = divider;
    }

    public int getShardKey(int key) {
        if(log.isDebugEnabled()) {
            log.debug("key = " + key);
        }

        return key % getDivider() + 1  ;
    }
}

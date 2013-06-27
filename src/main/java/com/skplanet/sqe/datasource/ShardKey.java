package com.skplanet.sqe.datasource;

import lombok.Getter;
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

    private static final int DEFAULT_DIVIDER = ShardDbType.values().length;

    @Getter private int divider;

    public ShardKey() {
        this.divider = DEFAULT_DIVIDER-1;
    }

    public ShardKey(int divider) {
        this.divider = divider;
    }

    public int getShardKey(int key) {
        return key % getDivider() + 1  ;
    }
}

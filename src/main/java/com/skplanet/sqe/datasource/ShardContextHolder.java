package com.skplanet.sqe.datasource;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 25.
 * Time: 오후 4:24
 */
public class ShardContextHolder {

    public static final ThreadLocal<ShardDbType> contextHolder = new ThreadLocal<>();

    public static  void setShardDbType(ShardDbType shardDbType) {
        contextHolder.set(shardDbType);
    }

    public static ShardDbType getShardDbType() {
        return (ShardDbType) contextHolder.get();
    }

    public static void clearShardDbType() {
        contextHolder.remove();
    }

}

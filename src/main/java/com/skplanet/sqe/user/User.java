package com.skplanet.sqe.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 25.
 * Time: 오후 6:37
 * To change this template use File | Settings | File Templates.
 */
@ToString
@EqualsAndHashCode
public class User {
    @Getter @Setter private String userId;
    @Getter @Setter private String userName;
}

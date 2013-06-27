package com.skplanet.sqe.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 26.
 * Time: 오전 11:25
 * To change this template use File | Settings | File Templates.
 */
@ToString
@EqualsAndHashCode
public class UserMeta {

    @Getter @Setter private String userId;
    @Getter @Setter private int userServerId;

}

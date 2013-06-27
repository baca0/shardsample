package com.skplanet.sqe.user;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 25.
 * Time: 오후 5:17
 * To change this template use File | Settings | File Templates.
 */
public interface UserMapper {

    public Map<String,Object> selectUser(User user);

    public void insertUser(User user);

    public void updateUser(User user);

    public void deleteUser(User user);

}

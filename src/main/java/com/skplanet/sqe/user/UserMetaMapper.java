package com.skplanet.sqe.user;

/**
 * Created with IntelliJ IDEA.
 * User: skplanet
 * Date: 13. 6. 26.
 * Time: 오전 11:27
 */
public interface UserMetaMapper {

    public int createUserSeq();

    public int createUserSeqResult();

    public void insertUserMeta(UserMeta user);

    public void deleteUserMeta(UserMeta user);

    public UserMeta selectUserMeta(String userId);

}

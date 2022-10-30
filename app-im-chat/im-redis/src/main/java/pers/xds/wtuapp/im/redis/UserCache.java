package pers.xds.wtuapp.im.redis;

/**
 * @author HuPeng
 * @date 2022-10-30 15:09
 */
public interface UserCache {

    /**
     * 用户是否存在
     * @param uid 用户id
     * @return 是否存在
     */
    boolean isUserNotExist(int uid);

}

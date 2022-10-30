package pers.xds.wtuapp.web.redis;

/**
 * @author DeSen Xu
 * @date 2022-09-11 22:57
 */
public interface CdnCache {


    /**
     * 获取某个用户获取临时密匙的次数
     * @param userId 用户id
     * @return 获取次数
     */
    int getKeyGenerateTimes(int userId);


}

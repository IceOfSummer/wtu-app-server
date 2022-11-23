package pers.xds.wtuapp.web.redis;

/**
 * @author DeSen Xu
 * @date 2022-09-11 22:57
 */
public interface CosCache {


    /**
     * 获取某个用户获取临时密匙的次数，<b>每次获取后都会将对应的值加一</b>
     * @param userId 用户id
     * @return 获取次数
     */
    int getKeyGenerateTimes(int userId);


}

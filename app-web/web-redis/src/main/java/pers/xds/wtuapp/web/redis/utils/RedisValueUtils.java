package pers.xds.wtuapp.web.redis.utils;

/**
 * @author DeSen Xu
 * @date 2022-12-14 16:14
 */
public class RedisValueUtils {

    public static final String TRUE = "1";

    public static final String FALSE = "0";

    private RedisValueUtils() {}

    public static String castBoolean(boolean b) {
        return b ? TRUE: FALSE;
    }


}

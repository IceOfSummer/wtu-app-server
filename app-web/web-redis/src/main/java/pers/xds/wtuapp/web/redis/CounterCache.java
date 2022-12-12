package pers.xds.wtuapp.web.redis;


import pers.xds.wtuapp.web.redis.common.Duration;


/**
 * 使用redis做计数器
 * @author DeSen Xu
 * @date 2022-12-04 21:21
 */
public interface CounterCache {

    /**
     * 获取参数为某个key时，该方法的调用次数。统计次数每日0点清空
     * @param key 唯一的key
     * @return 调用次数(<b>包括</b>该次调用)
     */
    default int getInvokeCount(String key) {
        return getInvokeCount(key, Duration.DAY);
    }

    /**
     * 获取参数为某个key时，该方法的调用次数。
     * @param key 唯一的key
     * @param duration 清零时间
     * @return 调用次数(<b>包括</b>该次调用)
     */
    int getInvokeCount(String key, Duration duration);

    /**
     * 为某个key的值加一，一定要先调用getInvokeCount后再调用该方法，以防key不能及时过期
     * @param currentVal 当前的值
     * @param key 唯一的key
     */
    void increaseInvokeCount(int currentVal, String key);

    /**
     * 异步的为某个key加一，一定要先调用getInvokeCount后再调用该方法，以防key不能及时过期
     * @param currentVal 当前的值
     * @param key 唯一的key
     */
    void increaseInvokeCountAsync(int currentVal, String key);



}

package pers.xds.wtuapp.web.redis;


import pers.xds.wtuapp.web.redis.common.Duration;


/**
 * 使用redis做计数器
 * @author DeSen Xu
 * @date 2022-12-04 21:21
 */
public interface CounterCache {

    /**
     * 获取参数为某个key时，对应key的{@link CounterCache#increaseInvokeCount}方法的调用次数。。统计次数每日0点清空
     * @param key 唯一的key
     * @return 调用次数, 若之前未曾调用，返回0
     */
    default int getInvokeCount(String key) {
        return getInvokeCount(key, Duration.DAY);
    }

    /**
     * 获取参数为某个key时，对应key的{@link CounterCache#increaseInvokeCount}方法的调用次数。
     * @param key 唯一的key
     * @param duration 清零时间
     * @return 调用次数, 若之前未曾调用，返回0
     */
    int getInvokeCount(String key, Duration duration);

    /**
     * 为某个key的值加一
     * @param key 唯一的key
     */
    void increaseInvokeCount(String key);

}

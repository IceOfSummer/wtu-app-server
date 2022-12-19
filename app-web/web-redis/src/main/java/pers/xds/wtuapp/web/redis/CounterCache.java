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
     * 为某个key的值加一，一定要先调用getInvokeCount后再调用该方法，以防key不能及时过期
     * @param currentVal 当前的值
     * @param key 唯一的key
     * @deprecated
     * @see CounterCache#increaseInvokeCount(String)
     */
    void increaseInvokeCount(int currentVal, String key);

    /**
     * 为某个key的值加一
     * @param key 唯一的key
     */
    void increaseInvokeCount(String key);

    /**
     * 异步的为某个key加一，一定要先调用getInvokeCount后再调用该方法，以防key不能及时过期
     * @param currentVal 当前的值
     * @param key 唯一的key
     * @deprecated
     * @see CounterCache#increaseInvokeCountIgnoreException(String)
     */
    void increaseInvokeCountAsync(int currentVal, String key);


    /**
     * 为某个key对应的值加一，一定要先调用getInvokeCount后再调用该方法。
     * <p>
     * 该方法会在内部捕获所有异常并且打印，<b>但是不会抛出</b>
     * @param key  唯一的key
     */
    void increaseInvokeCountIgnoreException(String key);

}

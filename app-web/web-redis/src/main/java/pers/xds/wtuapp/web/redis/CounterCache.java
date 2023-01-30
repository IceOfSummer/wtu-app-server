package pers.xds.wtuapp.web.redis;


import pers.xds.wtuapp.web.redis.common.Duration;
import java.util.concurrent.TimeUnit;


/**
 * 使用redis做计数器.
 * <p>
 * 对于一个唯一的key，都应该先调用{@link CounterCache#getInvokeCount}，之后再调用{@link CounterCache#increaseInvokeCount}。
 * @author DeSen Xu
 * @date 2022-12-04 21:21
 */
public interface CounterCache {

    /**
     * 获取参数为某个key时，对应key的{@link CounterCache#increaseInvokeCount}方法的调用次数。
     * @param key 唯一的key
     * @param duration 若该key不存在，则新建key的过期时间
     * @return 调用次数, 若之前未曾调用，返回0
     */
    int getInvokeCount(String key, Duration duration);

    /**
     * 获取参数为某个key时，对应key的{@link CounterCache#increaseInvokeCount}方法的调用次数。
     * @param key 唯一的key
     * @param timeout 若该key不存在，则新建key的过期时间
     * @param timeUnit 时间单位
     * @return 调用次数, 若之前未曾调用，返回0
     */
    int getInvokeCount(String key, long timeout, TimeUnit timeUnit);

    /**
     * 为某个key的值加一
     * @param key 唯一的key
     */
    void increaseInvokeCount(String key);

}

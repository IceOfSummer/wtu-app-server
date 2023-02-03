package pers.xds.wtuapp.web.service;

/**
 * @author DeSen Xu
 * @date 2022-11-26 19:44
 * @deprecated
 * @see pers.xds.wtuapp.web.service.exception.ServiceException ServiceException
 */
public enum ServiceCode {

    /**
     * 请求成功
     */
    SUCCESS,
    /**
     * 当前目标不可用
     */
    NOT_AVAILABLE,
    /**
     * 当前目标不存在
     */
    NOT_EXIST,
    /**
     * 参数有误
     */
    BAD_REQUEST,
    /**
     * 由于并发导致乐观锁更新失败，需要重试
     */
    CONCURRENT_ERROR,
    /**
     * 达到使用频率上限
     */
    RATE_LIMIT,
    /**
     * 未知错误，特指在逻辑上不会发生的事发生了，一般解决方法可能为重试。
     */
    UNKNOWN_ERROR,
    /**
     * 表示出现了重复的元素
     */
    DUPLICATE_KEY,
}

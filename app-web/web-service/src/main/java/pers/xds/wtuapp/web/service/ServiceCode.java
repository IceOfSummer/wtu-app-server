package pers.xds.wtuapp.web.service;

/**
 * @author DeSen Xu
 * @date 2022-11-26 19:44
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
    CONCURRENT_ERROR
}

package pers.xds.wtuapp.web.common;

import org.springframework.http.HttpStatus;
import pers.xds.wtuapp.errorcode.ResponseWrapper;

/**
 * 响应状态码
 * @author DeSen Xu
 * @date 2022-08-31 17:52
 * @deprecated
 * @see pers.xds.wtuapp.errorcode.ResponseCode
 * @see ResponseWrapper
 */
public enum ResponseCode {

    /**
     * 请求成功
     */
    SUCCESS(0, "success"),
    /**
     * 用户未登录
     */
    FORBIDDEN(1, "请先登录", HttpStatus.FORBIDDEN),
    /**
     * 用户名或密码错误，或其它身份信息错误
     */
    WRONG_CREDENTIALS(2, "用户名或密码错误", HttpStatus.FORBIDDEN),
    /**
     * 目标不可用
     */
    NOT_AVAILABLE(3, "目标不可用"),
    /**
     * 参数有误
     */
    BAD_REQUEST(4, "参数有误", HttpStatus.BAD_REQUEST),
    /**
     * 想要找的字段不存在
     */
    ELEMENT_NOT_EXIST(5, "id不存在"),
    /**
     * 流量限制
     */
    RATE_LIMIT(6, "您的请求次数过于频繁"),
    /**
     * 数量限制
     * 例如添加某个记录到达了上限
     */
    COUNT_LIMIT(7, "数量限制"),
    /**
     * 服务器繁忙，请稍后再试
     */
    SERVER_BUSY(8, "服务器繁忙，请稍后再试", HttpStatus.SERVICE_UNAVAILABLE),
    /**
     * AccessDenied
     */
    ACCESS_DENIED(9, "拒绝访问", HttpStatus.FORBIDDEN),
    /**
     * 服务器出错
     */
    INTERNAL_SERVER_ERROR(10, "服务器出现问题，请等待修复!", HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * 发生未知错误
     */
    UNKNOWN_ERROR(11, "发生未知错误，请稍后再试!"),
    /**
     * 400 Bad Request
     */
    METHOD_NOT_ALLOWED(12, "请求方法不允许!", HttpStatus.METHOD_NOT_ALLOWED),
    /**
     * 账户被临时冻结
     */
    ACCOUNT_FREEZE_TEMPORARILY(13, "您的账户已被临时冻结，请稍后再试");

    public final int code;

    public final String message;

    public final HttpStatus httpStatus;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = HttpStatus.OK;
    }

    ResponseCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

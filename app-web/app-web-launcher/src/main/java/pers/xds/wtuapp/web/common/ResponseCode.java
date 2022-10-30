package pers.xds.wtuapp.web.common;

import org.springframework.http.HttpStatus;

/**
 * 响应状态码
 * @author DeSen Xu
 * @date 2022-08-31 17:52
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
     * 用户名或密码错误
     */
    WRONG_CREDENTIALS(2, "用户名或密码错误"),
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
    RATE_LIMIT(6, "您的请求次数过于频繁，请稍后再试");

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

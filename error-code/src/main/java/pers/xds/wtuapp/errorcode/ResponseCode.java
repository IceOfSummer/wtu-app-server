package pers.xds.wtuapp.errorcode;

/**
 * 响应码
 * @author DeSen Xu
 * @date 2023-02-03 15:21
 */
public enum ResponseCode {
    /**
     * 请求成功
     */
    SUCCESS(ResponseCodeConstant.SUCCESS, "success"),
    /**
     * 用户端错误
     */
    CLIENT_ERROR(ResponseCodeConstant.CLIENT_ERROR, "用户端错误"),
    /**
     * 用户注册错误
     */
    REGISTER_ERROR(ResponseCodeConstant.REGISTER_ERROR, "注册错误"),
    /**
     * 请求参数有误
     */
    REQUEST_PARAMETERS_ERROR(ResponseCodeConstant.REQUEST_PARAMETERS_ERROR, "请求参数有误"),
    /**
     * 用户资源异常
     */
    USER_RESOURCES_ERROR(ResponseCodeConstant.USER_RESOURCES_ERROR, "用户资源异常"),
    /**
     * 系统资源异常
     */
    SYSTEM_RESOURCES_ERROR(ResponseCodeConstant.SYSTEM_RESOURCE_ERROR, "系统资源异常"),
    /**
     * 并发异常
     */
    CONCURRENT_ERROR(ResponseCodeConstant.CONCURRENT_ERROR, "服务器繁忙，请稍后再试");


    /**
     * 状态码
     */
    private final String code;

    /**
     * 默认信息
     */
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

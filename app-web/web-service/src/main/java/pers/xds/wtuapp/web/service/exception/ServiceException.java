package pers.xds.wtuapp.web.service.exception;

import pers.xds.wtuapp.errorcode.ResponseCode;

/**
 * 业务异常. 该异常不会生成堆栈信息.
 * @author DeSen Xu
 * @date 2023-02-03 14:34
 */
public abstract class ServiceException extends RuntimeException {

    /**
     * 错误码
     * @see pers.xds.wtuapp.errorcode.ResponseCodeConstant ResponseCodeConstant
     */
    private final String code;

    /**
     * @param code 错误码
     * @param message 错误消息
     * @see pers.xds.wtuapp.errorcode.ResponseCodeConstant ResponseCodeConstant
     */
    public ServiceException(String code, String message) {
        super(message, null, false, false);
        this.code = code;
    }

    public ServiceException(ResponseCode responseCode) {
        super(responseCode.getMessage(), null, false, false);
        this.code = responseCode.getCode();
    }

    public String getCode() {
        return code;
    }
}

package pers.xds.wtuapp.web.service.exception;

import pers.xds.wtuapp.errorcode.ResponseCode;
import pers.xds.wtuapp.errorcode.ResponseCodeConstant;

/**
 * @author DeSen Xu
 * @date 2023-02-11 20:56
 */
public class ConcurrentException extends ServiceException{

    public ConcurrentException(String message) {
        super(ResponseCodeConstant.CONCURRENT_ERROR, message);
    }

    public ConcurrentException() {
        super(ResponseCode.CONCURRENT_ERROR);
    }

}

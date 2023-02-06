package pers.xds.wtuapp.web.service.exception;

import pers.xds.wtuapp.errorcode.ResponseCode;
import pers.xds.wtuapp.errorcode.ResponseCodeConstant;

/**
 * @author DeSen Xu
 * @date 2023-02-06 12:31
 */
public class BadArgumentException extends ServiceException {
    public BadArgumentException(String message) {
        super(ResponseCodeConstant.REQUEST_PARAMETERS_ERROR, message);
    }

    public BadArgumentException() {
        super(ResponseCode.REQUEST_PARAMETERS_ERROR);
    }

}

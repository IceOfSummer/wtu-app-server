package pers.xds.wtuapp.web.service.exception;

import pers.xds.wtuapp.errorcode.ResponseCode;
import pers.xds.wtuapp.errorcode.ResponseCodeConstant;

/**
 * 指定的资源不存在
 * @author DeSen Xu
 * @date 2023-02-03 15:47
 */
public class NoSuchElementException extends ServiceException{

    public NoSuchElementException(String message) {
        super(ResponseCodeConstant.USER_RESOURCES_ERROR, message);
    }

    public NoSuchElementException() {
        super(ResponseCode.USER_RESOURCES_ERROR);
    }

}

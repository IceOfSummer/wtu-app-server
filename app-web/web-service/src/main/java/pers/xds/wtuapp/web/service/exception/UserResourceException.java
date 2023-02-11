package pers.xds.wtuapp.web.service.exception;

import pers.xds.wtuapp.errorcode.ResponseCode;
import pers.xds.wtuapp.errorcode.ResponseCodeConstant;

/**
 * 用户资源异常
 * @author DeSen Xu
 * @date 2023-02-11 20:51
 */
public class UserResourceException extends ServiceException{

    public UserResourceException(String message) {
        super(ResponseCodeConstant.USER_RESOURCES_ERROR, message);
    }

    public UserResourceException() {
        super(ResponseCode.USER_RESOURCES_ERROR);
    }

}

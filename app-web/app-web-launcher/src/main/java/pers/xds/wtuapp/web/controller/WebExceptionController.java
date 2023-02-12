package pers.xds.wtuapp.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.errorcode.ResponseWrapper;
import pers.xds.wtuapp.web.service.exception.ServiceException;

/**
 * 全局异常拦截器
 * @author DeSen Xu
 * @date 2023-02-03 15:15
 */
@ControllerAdvice
@RestController
public class WebExceptionController {

    /**
     * 拦截服务异常
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseWrapper<Void> handleHttpMessageNotReadableException(ServiceException e) {
        return ResponseWrapper.fail(e.getCode(), e.getMessage());
    }

}

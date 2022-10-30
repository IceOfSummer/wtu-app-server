package pers.xds.wtuapp.im.exception;

import java.io.IOException;

/**
 * 未知协议异常，一般由于魔数不能对上而造成的错误
 * @author DeSen Xu
 * @date 2022-09-02 12:07
 */
public class UnknownProtocolException extends IOException {

    public UnknownProtocolException() {
    }

    public UnknownProtocolException(String message) {
        super(message);
    }
}

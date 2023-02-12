package pers.xds.wtuapp.errorcode;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应模板
 * @author DeSen Xu
 * @date 2023-02-03 15:20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private String code;

    private String message;

    private T data;

    public ResponseWrapper(ResponseCode responseCode, String message, T data) {
        this(responseCode.getCode(), message, data);
    }

    public ResponseWrapper(ResponseCode responseCode, T data) {
        this(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public ResponseWrapper(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public ResponseWrapper<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseWrapper<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseWrapper<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <D> ResponseWrapper<D> success(D data) {
        return new ResponseWrapper<D>(ResponseCode.SUCCESS, data);
    }

    public static ResponseWrapper<Void> success() {
        return new ResponseWrapper<>(ResponseCode.SUCCESS, null);
    }

    public static <D> ResponseWrapper<D> fail(ResponseCode responseCode) {
        return new ResponseWrapper<D>(responseCode, null);
    }

    public static <D> ResponseWrapper<D> fail(ResponseCode responseCode, String customMessage) {
        return new ResponseWrapper<D>(responseCode, customMessage, null);
    }

    public static <D> ResponseWrapper<D> fail(String code, String message) {
        return new ResponseWrapper<D>(code, message, null);
    }

}

package pers.xds.wtuapp.errorcode;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应模板
 * @author DeSen Xu
 * @date 2023-02-03 15:20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTemplate<T> {

    private String code;

    private String message;

    private T data;

    public ResponseTemplate(ResponseCode responseCode, String message, T data) {
        this(responseCode.getCode(), message, data);
    }

    public ResponseTemplate(ResponseCode responseCode, T data) {
        this(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public ResponseTemplate(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public ResponseTemplate<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseTemplate<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseTemplate<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <D> ResponseTemplate<D> success(D data) {
        return new ResponseTemplate<D>(ResponseCode.SUCCESS, data);
    }

    public static ResponseTemplate<Void> success() {
        return new ResponseTemplate<>(ResponseCode.SUCCESS, null);
    }

    public static <D> ResponseTemplate<D> fail(ResponseCode responseCode) {
        return new ResponseTemplate<D>(responseCode, null);
    }

    public static <D> ResponseTemplate<D> fail(ResponseCode responseCode, String customMessage) {
        return new ResponseTemplate<D>(responseCode, customMessage, null);
    }

    public static <D> ResponseTemplate<D> fail(String code, String message) {
        return new ResponseTemplate<D>(code, message, null);
    }

}

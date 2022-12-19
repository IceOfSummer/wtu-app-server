package pers.xds.wtuapp.web.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.xds.wtuapp.web.service.util.Jackson;

import javax.servlet.http.HttpServletResponse;

/**
 * 响应体模板<p/>
 * @author DeSen Xu
 * @date 2022-08-31 17:47
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTemplate<D> {

    /**
     * 响应状态码
     */
    public int code;

    /**
     * 响应消息
     */
    public String message;

    /**
     * 响应数据
     */
    @Nullable
    public D data;

    public ResponseTemplate(ResponseCode responseCode) {
        this(responseCode, null);
    }

    public ResponseTemplate(ResponseCode responseCode, @Nullable D data) {
        this(responseCode.code, responseCode.message, data);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletResponse response = requestAttributes.getResponse();
            if (response != null) {
                response.setStatus(responseCode.httpStatus.value());
            }
        }
    }

    public ResponseTemplate(int code, String message, @Nullable D data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseTemplate<Void> success() {
        return new ResponseTemplate<>(ResponseCode.SUCCESS);
    }

    public static <D> ResponseTemplate<D> success(D data) {
        return new ResponseTemplate<>(ResponseCode.SUCCESS, data);
    }

    public static <D> ResponseTemplate<D> fail(ResponseCode code) {
        return new ResponseTemplate<>(code);
    }

    public static <D> ResponseTemplate<D> fail(ResponseCode code, String customMessage) {
        return new ResponseTemplate<>(code.code, customMessage, null);
    }

    public String toJson() {
        return Jackson.DEFAULT.toJsonStr(this);
    }

}

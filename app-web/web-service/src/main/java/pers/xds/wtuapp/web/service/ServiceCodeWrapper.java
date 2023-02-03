package pers.xds.wtuapp.web.service;

/**
 * @author DeSen Xu
 * @date 2022-11-27 16:45
 * @deprecated
 * @see pers.xds.wtuapp.web.service.exception.ServiceException ServiceException
 */
public class ServiceCodeWrapper<T> {

    public ServiceCode code;

    public T data;

    public ServiceCodeWrapper(ServiceCode code, T data) {
        this.code = code;
        this.data = data;
    }

    public boolean isSuccess() {
        return code == ServiceCode.SUCCESS;
    }

    public ServiceCodeWrapper(ServiceCode code) {
        this(code, null);
    }

    public static <T> ServiceCodeWrapper<T> success(T data) {
        return new ServiceCodeWrapper<>(ServiceCode.SUCCESS, data);
    }

    public static <T> ServiceCodeWrapper<T> fail(ServiceCode code) {
        return new ServiceCodeWrapper<>(code);
    }

}

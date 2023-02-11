package pers.xds.wtuapp.errorcode;

/**
 * 存放错误码常量
 * @author DeSen Xu
 * @date 2023-02-03 15:39
 */
public interface ResponseCodeConstant {

    /**
     * 请求成功
     */
    String SUCCESS = "00000";


    /**
     * 用户端错误.(二级宏观)
     */
    String CLIENT_ERROR = "A0001";

    /**
     * 用户注册错误.
     */
    String REGISTER_ERROR = "A0100";

    /**
     * 用户请求参数错误
     */
    String REQUEST_PARAMETERS_ERROR = "A0400";

    /**
     * 用户资源异常.(二级宏观)
     */
    String USER_RESOURCES_ERROR = "A0600";

    /**
     * 系统资源异常
     */
    String SYSTEM_RESOURCE_ERROR = "B0300";

    /**
     * 并发异常. 通常指乐观锁更新失败
     */
    String CONCURRENT_ERROR = "B0316";


}

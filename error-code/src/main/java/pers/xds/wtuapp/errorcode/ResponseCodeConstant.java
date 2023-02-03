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
     * 用户资源异常.(二级宏观)
     */
    String USER_RESOURCES_ERROR = "A0600";

}

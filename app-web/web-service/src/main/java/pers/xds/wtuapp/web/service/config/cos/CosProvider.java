package pers.xds.wtuapp.web.service.config.cos;



/**
 * @author DeSen Xu
 * @date 2022-11-22 21:15
 */
public interface CosProvider {

    /**
     * 对用户空间上传进行签名
     * @param uid 用户id
     * @param filenames 要上传的文件名称
     * @return 签名，保证长度与`filenames`相等
     * @throws Exception 生成临时密匙错误
     */
    SignInfo[] signUserspaceUpload(int uid, String[] filenames) throws Exception;

    /**
     * 获取token
     * @return token
     * @throws Exception 生成临时密匙错误
     */
    String getToken() throws Exception;

}

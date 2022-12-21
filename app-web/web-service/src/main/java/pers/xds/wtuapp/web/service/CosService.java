package pers.xds.wtuapp.web.service;

import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.service.config.cos.SignInfo;

/**
 * 对象存储服务
 * @author DeSen Xu
 * @date 2022-11-20 16:32
 */
public interface CosService {

    /**
     * 生成对象存储上传签名，位置为用户专属存储空间
     * @param uid 用户id
     * @param filenames 要上传的文件名称
     * @return 生成的签名，返回null表示生成失败，一般可能为网络原因或者每天的上传次数达到限制
     */
    @Nullable
    SignInfo[] requireUserspaceUploadSign(int uid, String[] filenames);

    /**
     * 生成头像上传签名
     * @param uid 用户id
     * @param type 文件类型描述符, 如'.png'
     * @return 签名，返回null表示生成失败，一般可能为网络原因或者每天的上传次数达到限制
     */
    @Nullable
    SignInfo requireAvatarUploadSign(int uid, String type);
}

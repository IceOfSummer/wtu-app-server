package pers.xds.wtuapp.web.service.config.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;

import java.util.*;

/**
 * 腾讯云Cos
 * <p>
 * 这里直接用子账号的永久密匙来申请临时密匙
 *
 * @author DeSen Xu
 * @date 2022-11-22 21:18
 */
public class TencentCloudCosProviderImpl implements CosProvider {

    /**
     * 永久密匙对
     */
    private final COSCredentials permanentCred;

    private static final COSSigner SIGNER = new COSSigner();

    public TencentCloudCosProviderImpl(String secretId, String secretKey) {
        this.permanentCred = new BasicCOSCredentials(secretId, secretKey);
    }

    /**
     * 对put请求进行签名。
     */
    private SignInfo signPutRequest(String path, Map<String, String> headerMap) {
        Date expirationDate = new Date(System.currentTimeMillis() + 10L * 60L * 1000L);
        return new SignInfo(path, SIGNER.buildAuthorizationStr(
                HttpMethodName.PUT,
                path,
                headerMap,
                Collections.emptyMap(),
                permanentCred,
                expirationDate,
                false)
        );
    }

    @Override
    public SignInfo[] signUserspaceUpload(int uid, String[] filenames) {
        SignInfo[] signInfos = new SignInfo[filenames.length];
        HashMap<String, String> headerMap = new HashMap<>(1);
        headerMap.put("x-cos-meta-uid", String.valueOf(uid));
        for (int i = 0; i < filenames.length; i++) {
            signInfos[i] = signPutRequest("/image/todo/" + filenames[i], headerMap);
        }
        return signInfos;
    }

    @Override
    public SignInfo signAvatarUpload(int uid, String type) {
        HashMap<String, String> headerMap = new HashMap<>(1);
        headerMap.put("x-cos-meta-uid", String.valueOf(uid));
        return signPutRequest("/avatar/todo/" + uid + type, headerMap);
    }


}

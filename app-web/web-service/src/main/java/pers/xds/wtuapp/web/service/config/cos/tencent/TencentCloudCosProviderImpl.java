package pers.xds.wtuapp.web.service.config.cos.tencent;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import com.tencent.cloud.cos.util.Jackson;
import pers.xds.wtuapp.web.service.config.cos.CosProvider;
import pers.xds.wtuapp.web.service.config.cos.SignInfo;

import java.io.IOException;
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
     * 申请密匙时的参数
     */
    private final TreeMap<String, Object> KEY_REQ_PARAM;

    private static final COSSigner SIGNER = new COSSigner();

    /**
     * 保存的Response
     * <p>
     * 对其进行初始化是为了省去{@link TencentCloudCosProviderImpl#ensureKeyNotExpired()}的判空操作
     */
    private Response savedResponse = new Response();


    public TencentCloudCosProviderImpl(String secretKey, String secretId, int duration, String bucket, String region) {
        String appId = bucket.substring(bucket.lastIndexOf("-") + 1);
        this.KEY_REQ_PARAM = new TreeMap<>();
        this.KEY_REQ_PARAM.put("secretId", secretId);
        this.KEY_REQ_PARAM.put("secretKey", secretKey);
        this.KEY_REQ_PARAM.put("durationSeconds", duration);
        this.KEY_REQ_PARAM.put("bucket", bucket);
        this.KEY_REQ_PARAM.put("region", region);
        this.KEY_REQ_PARAM.put("allowPrefixes", new String[]{ "*" });
        this.KEY_REQ_PARAM.put("allowActions", new String[]{ "name/cos:PutObject" });
        this.KEY_REQ_PARAM.put("policy",
                Jackson.toJsonString(
                        new TencentCosPolicy(new CosStatement("cos:PutObject", "qcs::cos:" + region + ":uid/" + appId + ":" + bucket + "/" + "*", 1024 * 1024 * 5))
                )
        );

    }

    @Override
    public SignInfo[] signUserspaceUpload(int uid, String[] filenames) throws IOException {
        ensureKeyNotExpired();
        SignInfo[] signInfos = new SignInfo[filenames.length];
        BasicCOSCredentials credentials = new BasicCOSCredentials(savedResponse.credentials.tmpSecretId, savedResponse.credentials.tmpSecretKey);
        for (int i = 0; i < filenames.length; i++) {
            signInfos[i] = signPutRequest(credentials, "/image/" + uid + "/" + filenames[i]);
        }
        return signInfos;
    }

    @Override
    public String getToken() throws Exception {
        ensureKeyNotExpired();
        return savedResponse.credentials.sessionToken;
    }


    /**
     * 确保密匙没有过期。任何需要使用密匙的方法都需要提前调用该函数！
     * @throws IOException 获取密匙失败
     */
    private void ensureKeyNotExpired() throws IOException {
        // 提前两分钟判断
        final long forward = 120;
        // 毫秒级时间戳转秒级
        final long divide = 1000;
        if (System.currentTimeMillis() / divide >= savedResponse.expiredTime - forward) {
            this.savedResponse = CosStsClient.getCredential(KEY_REQ_PARAM);
        }
    }

    private SignInfo signPutRequest(COSCredentials cred, String path) {
        Date expirationDate = new Date(System.currentTimeMillis() + 10L * 60L * 1000L);
        return new SignInfo(path, SIGNER.buildAuthorizationStr(
                HttpMethodName.PUT,
                path,
                Collections.emptyMap(),
                Collections.emptyMap(),
                cred,
                expirationDate,
                false)
        );
    }


}

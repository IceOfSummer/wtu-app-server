package pers.xds.wtuapp.web.service.config.cos.tencent;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


class TencentCloudCosProviderImplTest {

    @Test
    public void genSign() {
        // 设置用户身份信息。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "SECRETID";
        String secretKey = "SECRETKEY";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        String key = "exampleobject";
        //若key不是以“/”开头，则需要在key的开头加上“/”，否则直接resource_path=key
        String resource_path="/" + key;

        // 用来生成签名
        COSSigner signer = new COSSigner();
        // 设置签名过期时间(可选)，若未进行设置，则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60L * 1000L);

        // 填写本次请求的参数
        Map<String, String> params = new HashMap<>();

        // 填写本次请求的头部
        // host 必填

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.PUT;

        String sign = signer.buildAuthorizationStr(method, resource_path, new HashMap<>(0), params, cred, expirationDate, false);
        System.out.println(sign);
    }

}
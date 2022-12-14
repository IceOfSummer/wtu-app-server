package pers.xds.wtuapp.web.service.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * cos桶配置，目前只允许用户写，读则通过CDN读
 * @see <a href="https://cloud.tencent.com/document/product/436/14048#cos-sts-sdk">临时密钥生成及使用指引</a>
 * @author DeSen Xu
 * @date 2022-09-11 17:04
 */
@ConfigurationProperties(prefix = "app.cos")
public class CosConfigurationProperties {

    /**
     * 过期时间, 单位为秒
     */
    private Integer durationSeconds = 1800;

    /**
     * bucket
     */
    private String bucket;

    /**
     * 地域
     */
    private String region;

    private String secretId;

    private String secretKey;

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

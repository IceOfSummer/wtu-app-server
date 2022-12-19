package pers.xds.wtuapp.web.service.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯云: <a href="https://cloud.tencent.com/document/product/1288/51034">发送邮件</a>
 * @author DeSen Xu
 * @date 2022-12-04 22:34
 */
@ConfigurationProperties(prefix = "app.email")
public class EmailConfigurationProperties {

    /**
     * 商品锁定提示模板
     */
    private Long commodityLockTipTemplate;

    private Long emailCaptchaTemplate;

    /**
     * 地域
     */
    private String region;

    private String secretId;

    private String secretKey;

    private String senderEmail;

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

    public Long getCommodityLockTipTemplate() {
        return commodityLockTipTemplate;
    }

    public void setCommodityLockTipTemplate(Long commodityLockTipTemplate) {
        this.commodityLockTipTemplate = commodityLockTipTemplate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Long getEmailCaptchaTemplate() {
        return emailCaptchaTemplate;
    }

    public void setEmailCaptchaTemplate(Long emailCaptchaTemplate) {
        this.emailCaptchaTemplate = emailCaptchaTemplate;
    }
}

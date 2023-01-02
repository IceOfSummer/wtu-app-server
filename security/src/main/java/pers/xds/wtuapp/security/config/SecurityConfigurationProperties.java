package pers.xds.wtuapp.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DeSen Xu
 * @date 2022-12-31 20:20
 */
@ConfigurationProperties(prefix = "app.security")
public class SecurityConfigurationProperties {

    /**
     * jwt密匙
     */
    private String secret;

    /**
     * jwt密匙有效时间
     */
    private int expireMinute = 30;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getExpireMinute() {
        return expireMinute;
    }

    public void setExpireMinute(int expireMinute) {
        this.expireMinute = expireMinute;
    }
}

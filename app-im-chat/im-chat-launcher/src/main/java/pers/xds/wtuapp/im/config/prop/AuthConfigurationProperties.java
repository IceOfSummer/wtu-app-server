package pers.xds.wtuapp.im.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DeSen Xu
 * @date 2022-11-17 16:00
 */
@ConfigurationProperties(prefix = "im.auth")
public class AuthConfigurationProperties {

    /**
     * 若用户在该时间内未能完成认证，则关闭连接
     * <p>
     * 单位为秒
     */
    private int maxAuthTime = 10;

    /**
     * 当用户尝试连接次数超过该次数后，禁止用户继续连接
     */
    private int maxRetryTimes = 3;

    public int getMaxAuthTime() {
        return maxAuthTime;
    }

    public void setMaxAuthTime(int maxAuthTime) {
        this.maxAuthTime = maxAuthTime;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }
}

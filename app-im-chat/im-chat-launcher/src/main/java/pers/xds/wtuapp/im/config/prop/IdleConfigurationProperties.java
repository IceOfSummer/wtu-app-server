package pers.xds.wtuapp.im.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DeSen Xu
 * @date 2022-11-17 15:57
 */
@ConfigurationProperties(prefix = "im.idle")
public class IdleConfigurationProperties {

    /**
     * 若channel在该时间内没有进行任何读操作，则断开连接
     */
    private int readerIdleTimeSeconds = 1000;

    public int getReaderIdleTimeSeconds() {
        return readerIdleTimeSeconds;
    }

    public void setReaderIdleTimeSeconds(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
    }
}

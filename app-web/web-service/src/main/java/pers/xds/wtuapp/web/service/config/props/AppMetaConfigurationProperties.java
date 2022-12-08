package pers.xds.wtuapp.web.service.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DeSen Xu
 * @date 2022-12-08 20:21
 */
@ConfigurationProperties(prefix = "app.meta")
public class AppMetaConfigurationProperties {

    /**
     * cdn的url，结尾必须带`/`
     * <p>
     * 如: <a href="">https://xxx.xxx.xxx/</a>
     * <p>
     * 而不是:
     * <a href="">https://xxx.xxx.xxx</a>
     * <p>
     * http和https均可
     */
    private String cdnUrl;

    public String getCdnUrl() {
        return cdnUrl;
    }

    public void setCdnUrl(String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }
}

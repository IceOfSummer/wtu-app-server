package pers.xds.wtuapp.im.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author HuPeng
 * @date 2022-10-25 22:02
 */
@ConfigurationProperties(prefix = "chat.ssl")
public class SslConfigurationProperties {

    private String keyPath;

    private String certificatePath;

    private String password;

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

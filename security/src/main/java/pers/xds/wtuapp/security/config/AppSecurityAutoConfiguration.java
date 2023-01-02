package pers.xds.wtuapp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.impl.SecurityJwtProviderImpl;

/**
 * @author DeSen Xu
 * @date 2022-12-31 20:20
 */
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class AppSecurityAutoConfiguration {

    private SecurityConfigurationProperties securityProp;

    @Autowired
    public void setSecurityProp(SecurityConfigurationProperties securityProp) {
        this.securityProp = securityProp;
    }

    @Bean
    public SecurityJwtProvider securityJwtProvider() {
        return new SecurityJwtProviderImpl(
                securityProp.getSecret(),
                securityProp.getExpireMinute()
        );
    }


}

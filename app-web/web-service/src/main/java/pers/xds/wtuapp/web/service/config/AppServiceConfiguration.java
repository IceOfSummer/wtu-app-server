package pers.xds.wtuapp.web.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xds.wtuapp.web.service.config.cos.CosProvider;
import pers.xds.wtuapp.web.service.config.cos.TencentCloudCosProviderImpl;
import pers.xds.wtuapp.web.service.config.props.AppMetaConfigurationProperties;
import pers.xds.wtuapp.web.service.config.props.CosConfigurationProperties;
import pers.xds.wtuapp.web.service.config.props.EmailConfigurationProperties;

/**
 * @author DeSen Xu
 * @date 2022-12-08 20:22
 */
@EnableConfigurationProperties({
        CosConfigurationProperties.class,
        EmailConfigurationProperties.class,
        AppMetaConfigurationProperties.class
})
@Configuration
public class AppServiceConfiguration {

    private CosConfigurationProperties cosConfigurationProperties;

    @Autowired
    public void setCdnConfigurationProperties(CosConfigurationProperties cosConfigurationProperties) {
        this.cosConfigurationProperties = cosConfigurationProperties;
    }

    @Bean
    CosProvider cosProvider() {
        return new TencentCloudCosProviderImpl(cosConfigurationProperties.getSecretId(), cosConfigurationProperties.getSecretKey());
    }


}

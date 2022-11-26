package pers.xds.wtuapp.web.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xds.wtuapp.web.service.config.cos.CosProvider;
import pers.xds.wtuapp.web.service.config.cos.tencent.TencentCloudCosProviderImpl;

/**
 * @author DeSen Xu
 * @date 2022-09-11 17:06
 */
@EnableConfigurationProperties(CosConfigurationProperties.class)
@Configuration
public class CosConfiguration {

    private CosConfigurationProperties cosConfigurationProperties;

    @Autowired
    public void setCdnConfigurationProperties(CosConfigurationProperties cosConfigurationProperties) {
        this.cosConfigurationProperties = cosConfigurationProperties;
    }

    @Bean
    CosProvider cosProvider() {
        return new TencentCloudCosProviderImpl(cosConfigurationProperties);
    }
}

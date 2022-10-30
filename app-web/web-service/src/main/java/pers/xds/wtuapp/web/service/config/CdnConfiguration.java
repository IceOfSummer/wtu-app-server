package pers.xds.wtuapp.web.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xds.wtuapp.web.service.common.CdnManager;

/**
 * @author DeSen Xu
 * @date 2022-09-11 17:06
 */
@EnableConfigurationProperties(CdnConfigurationProperties.class)
@Configuration
public class CdnConfiguration {

    private CdnConfigurationProperties cdnConfigurationProperties;

    @Autowired
    public void setCdnConfigurationProperties(CdnConfigurationProperties cdnConfigurationProperties) {
        this.cdnConfigurationProperties = cdnConfigurationProperties;
    }

    @Bean
    CdnManager cdnManager() {
        return new CdnManager(cdnConfigurationProperties.getSecretKey(),
                cdnConfigurationProperties.getSecretId(),
                cdnConfigurationProperties.getDurationSeconds(),
                cdnConfigurationProperties.getBucket(),
                cdnConfigurationProperties.getRegion(),
                cdnConfigurationProperties.getAllowActions());
    }
}

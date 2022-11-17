package pers.xds.wtuapp.im.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import pers.xds.wtuapp.im.config.prop.AuthConfigurationProperties;
import pers.xds.wtuapp.im.config.prop.IdleConfigurationProperties;
import pers.xds.wtuapp.im.config.prop.SslConfigurationProperties;
import pers.xds.wtuapp.im.handler.ConnectActiveHandler;

import javax.net.ssl.SSLException;
import java.io.FileNotFoundException;

/**
 * @author DeSen Xu
 * @date 2022-11-17 15:52
 */
@Configuration
@EnableConfigurationProperties({
        SslConfigurationProperties.class,
        IdleConfigurationProperties.class,
        AuthConfigurationProperties.class
})
public class ImConfiguration {

    private SslConfigurationProperties sslProp;

    private IdleConfigurationProperties idleConfigurationProperties;

    private AuthConfigurationProperties authConfigurationProperties;

    @Autowired
    public void setAuthConfigurationProperties(AuthConfigurationProperties authConfigurationProperties) {
        this.authConfigurationProperties = authConfigurationProperties;
    }

    @Autowired
    public void setIdleConfigurationProperties(IdleConfigurationProperties idleConfigurationProperties) {
        this.idleConfigurationProperties = idleConfigurationProperties;
    }

    @Autowired
    public void setSslProp(SslConfigurationProperties sslProp) {
        this.sslProp = sslProp;
    }

    @Bean
    public SslContext sslContext() throws FileNotFoundException, SSLException {
        return SslContextBuilder
                .forServer(ResourceUtils.getFile(sslProp.getCertificatePath()), ResourceUtils.getFile(sslProp.getKeyPath()), sslProp.getPassword())
                .build();
    }

    @Bean
    public IdleHandlerFactory idleHandlerFactory() {
        return new IdleHandlerFactory(idleConfigurationProperties.getReaderIdleTimeSeconds());
    }

    @Bean
    public ConnectActiveHandler connectActiveHandler() {
        return new ConnectActiveHandler(authConfigurationProperties.getMaxAuthTime());
    }

}

package pers.xds.wtuapp.im.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import pers.xds.wtuapp.im.config.prop.SslConfigurationProperties;

import javax.net.ssl.SSLException;
import java.io.FileNotFoundException;

/**
 * @author HuPeng
 * @date 2022-10-25 22:01
 */
@EnableConfigurationProperties(SslConfigurationProperties.class)
@Configuration
public class SslConfiguration {

    private SslConfigurationProperties sslProp;

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
}

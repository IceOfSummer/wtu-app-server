package pers.xds.wtuapp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author DeSen Xu
 * @date 2022-09-02 11:04
 */
@SpringBootApplication
@EnableWebSecurity
@EnableCaching
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WtuAppWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtuAppWebApplication.class, args);
    }
}

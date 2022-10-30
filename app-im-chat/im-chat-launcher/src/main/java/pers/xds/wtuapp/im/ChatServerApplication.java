package pers.xds.wtuapp.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author DeSen Xu
 * @date 2022-09-02 11:12
 */
@SpringBootApplication
@EnableCaching
public class ChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }
}

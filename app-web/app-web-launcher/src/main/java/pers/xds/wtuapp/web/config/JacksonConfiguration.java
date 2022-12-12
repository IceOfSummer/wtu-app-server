package pers.xds.wtuapp.web.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;

import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2022-12-10 22:19
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public Module timestampSerializerModule() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(Timestamp.class, new TimestampSerializer());
        return timeModule;
    }

}

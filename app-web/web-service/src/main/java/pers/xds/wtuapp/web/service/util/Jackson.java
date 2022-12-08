package pers.xds.wtuapp.web.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author DeSen Xu
 * @date 2022-12-08 16:28
 */
public enum Jackson {

    DEFAULT(new ObjectMapper());

    private final ObjectMapper objectMapper;

    Jackson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJsonStr(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonString(Object object) {
        return DEFAULT.toJsonStr(object);
    }

}

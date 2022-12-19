package pers.xds.wtuapp.web.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author DeSen Xu
 * @date 2022-12-18 21:52
 */
public enum Jackson {
    /**
     * 默认
     */
    DEFAULT(new ObjectMapper());

    private final ObjectMapper objectMapper;

    Jackson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String writeAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

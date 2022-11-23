package pers.xds.wtuapp.web.service.config.cos.tencent;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2022-11-22 22:17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CosStatement {

    public String effect = "allow";

    public List<String> action;

    public List<String> resource;

    public Map<String, Object> condition;

    /**
     * @param action 允许的操作
     * @param resource 允许的资源
     * @param maxByte 最大上传大小(byte)
     */
    public CosStatement(String action, String resource, Integer maxByte) {
        this.action = new ArrayList<>(1);
        this.action.add(action);
        this.resource = new ArrayList<>(1);
        this.resource.add(resource);
        this.condition = new HashMap<>(2);
        // @see https://cloud.tencent.com/document/product/436/54370
        HashMap<String, Integer> maxSizeLimit = new HashMap<>(1);
        maxSizeLimit.put("cos:content-length", maxByte);

        condition.put("numeric_less_than", maxSizeLimit);
    }

}

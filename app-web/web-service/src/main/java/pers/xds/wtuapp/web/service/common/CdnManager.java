package pers.xds.wtuapp.web.service.common;

import java.util.TreeMap;

/**
 * @author DeSen Xu
 * @date 2022-09-13 17:02
 */
public class CdnManager {
    
    private final String secretKey;
    
    private final String secretId;
    
    private final int duration;
    
    private final String bucket;
    
    private final String region;
    
    private final String[] allowActions;

    public CdnManager(String secretKey, String secretId, int duration, String bucket, String region, String[] allowActions) {
        this.secretKey = secretKey;
        this.secretId = secretId;
        this.duration = duration;
        this.bucket = bucket;
        this.region = region;
        this.allowActions = allowActions;
    }
    
    public TreeMap<String, Object> generateRequestTreeMap(int userId) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("secretId", secretId);
        treeMap.put("secretKey", secretKey);
        treeMap.put("durationSeconds", duration);
        treeMap.put("bucket", bucket);
        treeMap.put("region", region);
        treeMap.put("allowActions", allowActions);
        treeMap.put("allowPrefixes", new String[]{"/images/" + userId + "/*"});
        return treeMap;
    }
}

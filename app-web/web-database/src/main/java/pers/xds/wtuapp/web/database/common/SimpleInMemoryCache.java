package pers.xds.wtuapp.web.database.common;

import org.apache.ibatis.cache.Cache;

import java.util.*;

/**
 * 简单地利用HashMap进行缓存
 * @author DeSen Xu
 * @date 2022-09-29 23:02
 */
public class SimpleInMemoryCache implements Cache {

    private final String id;

    private final Map<Object, Object> cache = new HashMap<>();

    public SimpleInMemoryCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }
}

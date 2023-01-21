package pers.xds.wtuapp.web.redis;

import java.util.Collection;

/**
 * 消息置顶缓存
 * @author DeSen Xu
 * @date 2023-01-20 15:40
 */
public interface TopMessageCache {


    /**
     * 查询所有被置顶的消息
     * @return 所有被置顶的消息
     */
    Collection<Object> queryAllTopMessage();

    /**
     * 取消某条消息的置顶
     * @param id 该消息的id
     */
    void removeTop(int id);

    /**
     * 添加置顶消息
     * @param id 该消息的id
     * @param message 消息内容
     */
    void addTopMessage(int id, Object message);

}

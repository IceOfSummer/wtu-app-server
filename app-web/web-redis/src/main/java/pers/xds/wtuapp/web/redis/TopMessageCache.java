package pers.xds.wtuapp.web.redis;

import java.util.Set;

/**
 * 消息置顶缓存
 * @author DeSen Xu
 * @date 2023-01-20 15:40
 */
public interface TopMessageCache {


    /**
     * 查询所有被置顶的消息id
     * @return 所有被置顶的消息id
     */
    Set<Integer> queryAllTopMessage();

    /**
     * 取消某条消息的置顶
     * @param id 该消息的id
     */
    void removeTop(int id);

    /**
     * 添加置顶消息
     * @param id 该消息的id
     */
    void addTopMessage(int id);

}

package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.EventRemind;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2023-01-14 13:49
 */
@Mapper
public interface EventRemindMapper {

    /**
     * 插入一条消息提醒
     * @param eventRemind 消息提醒
     */
    void insert(@Param("er") EventRemind eventRemind);


    /**
     * 根据接收者id获取提醒. 该方法是为了进行消息聚合而提供。
     * @param receiverId 接收者id
     * @return 消息提醒，只会获取未读的消息(最多选取300条). <p>
     *     仅会返回{@link EventRemind#sourceId}, {@link EventRemind#sourceType}, {@link EventRemind#eventRemindId}
     *     {@link EventRemind#senderId}, {@link EventRemind#sourceContent}, {@link EventRemind#createTime}
     */
    List<EventRemind> selectRemindsForCombine(@Param("uid") int receiverId);


    /**
     * 获取用户未读消息数量
     * @param uid 用户id
     * @return 未读消息数量。<b>若大于100，则会直接返回100</b>
     */
    Integer selectUnreadMessageCount(@Param("uid") int uid);

    /**
     * 标记所有消息已读
     * @param uid 用户id
     */
    void markAllRead(@Param("uid") int uid);
}

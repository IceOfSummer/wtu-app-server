package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.MessageTip;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2023-01-10 22:39
 */
@Mapper
public interface MessageTipMapper {

    /**
     * 插入一条消息提醒
     * @param messageTip 消息提醒
     */
    void insert(@Param("mt") MessageTip messageTip);


    /**
     * 获取消息提醒
     * @param uid 用户id
     * @param minReceiveId 最小接收id(不包括)
     * @param size 最大长度
     * @return 消息提醒。不包括{@link MessageTip#receiver}字段
     */
    List<MessageTip> selectMessageTips(@Param("uid") int uid, @Param("mi") int minReceiveId, @Param("s") int size);


}

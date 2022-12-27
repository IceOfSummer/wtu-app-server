package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.CommunityTip;
import pers.xds.wtuapp.web.database.view.CommunityTipQueryType;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-12-24 20:39
 */
@Mapper
public interface CommunityTipMapper {


    /**
     * 获取某条消息的回复数
     * @param messageId 消息id
     * @return 回复数，可能为空
     */
    Integer selectReplyCount(@Param("mid") int messageId);

    /**
     * 插入一条消息提醒
     * @param communityTip 消息提醒
     */
    void insertOrUpdate(@Param("c") CommunityTip communityTip);

    /**
     * 查询提醒
     * @param uid 用户id
     * @return 最新提醒
     */
    List<CommunityTipQueryType> selectTips(@Param("uid") int uid);

    /**
     * 清空消息提醒(将count字段置0)
     * @param uid 用户id
     */
    void clearTip(@Param("uid") int uid);
}

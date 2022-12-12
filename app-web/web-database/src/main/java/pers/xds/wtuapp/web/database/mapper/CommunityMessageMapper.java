package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;

import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2022-12-10 14:13
 */
@Mapper
public interface CommunityMessageMapper extends BaseMapper<CommunityMessage> {


    /**
     * 删除社区消息
     * @param uid 用户id
     * @param messageId 消息id
     */
    @Delete("DELETE FROM community_message WHERE id = #{messageId} AND author = #{uid}")
    void deleteCommunityMessage(@Param("uid") int uid, @Param("messageId") int messageId);

    /**
     * 根据pid删除社区消息
     * @param pid pid
     */
    @Delete("DELETE FROM community_message WHERE pid = #{pid}")
    void deleteCommunityMessageByPid(@Param("pid") int pid);


    /**
     * 根据最大消息id获取消息
     * @param maxId 最大id，可以为空
     * @param size 每页最多显示几个
     * @return 消息
     */
    List<Map<String, String>> selectMessageByMaxId(@Param("maxId") Integer maxId, @Param("size") int size);

    /**
     *
     * 根据最小消息id获取消息
     * @param minId 最小id
     * @param size 每页最多显示几个
     * @return 消息
     */
    List<Map<String, String>> selectMessageByMinId(@Param("minId") int minId, @Param("size") int size);

    /**
     * 根据pid获取消息
     * @param pid pid
     * @param page 分页
     * @return 评论消息
     */
    IPage<Map<String, String>> selectMessageByPid(@Param("pid") int pid, IPage<Map<String, String>> page);

    /**
     * 根据一组pid获取相关评论预览
     * @param pids 要查询的一组pid
     * @param eachSize 每个pid下最多选择几条
     * @return 相关评论
     */
    List<Map<String, String>> selectMessageReplyPreview(@Param("pids")List<Integer> pids, @Param("size") int eachSize);

    /**
     * 添加某条消息的评论数
     * @param messageId 消息id
     */
    @Update("UPDATE community_message SET reply_count = reply_count + 1 WHERE id = #{id}")
    void increaseReplyCount(@Param("id") int messageId);

    /**
     * 相对的修改点赞数量
     * @param messageId 消息id
     * @param likeAdd 点赞数量相等变化量
     * @param dislikeAdd 点踩数量相对变化量
     */
    void modifyFeedbackAbsolutely(@Param("id") int messageId, @Param("up") int likeAdd, @Param("down") int dislikeAdd);

}

package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.bean.FeedbackRecord;

/**
 * @author DeSen Xu
 * @date 2022-12-12 22:16
 */
@Mapper
public interface FeedbackRecordMapper extends BaseMapper<FeedbackRecord> {

    /**
     * 查询反馈记录
     * @param uid 用户id
     * @param messageId 消息id
     * @return 反馈记录
     */
    @Nullable
    @Select("SELECT uid,message_id,`like` FROM feedback_record WHERE uid = #{uid} AND message_id = #{msgId}")
    FeedbackRecord selectFeedbackRecord(@Param("uid") int uid, @Param("msgId") int messageId);

    /**
     * 更新评价记录
     * @param feedbackRecord 评价记录
     */
    @Update("UPDATE feedback_record SET `like` = #{fb.like} WHERE uid = #{fb.uid} AND message_id = #{fb.messageId}")
    void updateAttitude(@Param("fb") FeedbackRecord feedbackRecord);

}

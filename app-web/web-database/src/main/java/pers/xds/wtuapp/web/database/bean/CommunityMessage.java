package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pers.xds.wtuapp.web.database.common.TimestampSerializer;
import pers.xds.wtuapp.web.database.group.InsertGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2022-12-10 13:59
 */
@TableName("community_message")
public class CommunityMessage {


    /**
     * 消息id
     */
    @Null(groups = {InsertGroup.class})
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父评论的id，为0时表示没有父消息
     */
    @NotNull(groups = {InsertGroup.class})
    private Integer pid;

    /**
     * 作者id
     */
    @Null(groups = {InsertGroup.class})
    private Integer author;

    @Size(max = 30, groups = {InsertGroup.class})
    private String title;

    /**
     * 消息内容
     */
    @NotEmpty(groups = {InsertGroup.class})
    @Size(max = 500, groups = {InsertGroup.class})
    private String content;

    @Null(groups = {InsertGroup.class})
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createTime;

    @TableField("`like`")
    @Null(groups = {InsertGroup.class})
    private int like;

    @Null(groups = {InsertGroup.class})
    private int dislike;

    private Integer replyTo;

    @Null(groups = {InsertGroup.class})
    private Integer replyCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
}

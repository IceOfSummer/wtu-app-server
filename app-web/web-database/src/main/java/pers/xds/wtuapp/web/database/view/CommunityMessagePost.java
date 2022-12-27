package pers.xds.wtuapp.web.database.view;


import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2022-12-27 14:29
 */
public class CommunityMessagePost {

    /**
     * 消息id
     */
    private Integer id;

    /**
     * 消息作者昵称
     */
    private String nickname;

    /**
     * 该消息父消息id
     */
    private Integer pid;

    /**
     * 消息作者id
     */
    private Integer author;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 点赞数量
     */
    private Integer like;

    /**
     * 点踩数量
     */
    private Integer dislike;

    /**
     * 回复数量
     */
    private Integer replyCount;

    /**
     * 该消息是回复给谁的(指向消息id，而非用户id)
     */
    private Integer replyTo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }
}

package pers.xds.wtuapp.web.service.bean;

import pers.xds.wtuapp.web.database.view.CommunityMessagePost;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-12-27 14:53
 */
public class PostReply {

    /**
     * 一级评论
     */
    private List<CommunityMessagePost> reply;


    /**
     * 二级评论, 客户端需要自己和一级评论映射
     */
    private List<CommunityMessagePost> subReply;


    public List<CommunityMessagePost> getReply() {
        return reply;
    }

    public void setReply(List<CommunityMessagePost> reply) {
        this.reply = reply;
    }

    public List<CommunityMessagePost> getSubReply() {
        return subReply;
    }

    public void setSubReply(List<CommunityMessagePost> subReply) {
        this.subReply = subReply;
    }
}

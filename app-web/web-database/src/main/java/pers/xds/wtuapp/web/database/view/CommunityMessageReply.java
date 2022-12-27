package pers.xds.wtuapp.web.database.view;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-12-27 14:30
 */
public class CommunityMessageReply {

    /**
     * 根消息
     */
    private CommunityMessagePost root;

    /**
     * 二级评论
     */
    private List<CommunityMessagePost> subReply;


    public CommunityMessagePost getRoot() {
        return root;
    }

    public void setRoot(CommunityMessagePost root) {
        this.root = root;
    }

    public List<CommunityMessagePost> getSubReply() {
        return subReply;
    }

    public void setSubReply(List<CommunityMessagePost> subReply) {
        this.subReply = subReply;
    }
}

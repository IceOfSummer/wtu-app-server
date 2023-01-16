package pers.xds.wtuapp.web.database.bean;

import kotlin.time.TimeSource;

/**
 * @author DeSen Xu
 * @date 2023-01-10 22:37
 */
public class MessageTip {

    /**
     * 消息接收者
     */
    private Integer receiver;

    /**
     * 消息id
     */
    private Integer id;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 创建时间
     */
    private TimeSource createTime;

    /**
     * 社区回复
     */
    public static final int TYPE_COMMUNITY_REPLY = 1;

    /**
     * 商品交易
     */
    public static final int TYPE_TRADE_ACTION = 2;



    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 额外负载
     */
    private String payload;

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TimeSource getCreateTime() {
        return createTime;
    }

    public void setCreateTime(TimeSource createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}

package pers.xds.wtuapp.web.database.common;

/**
 * 消息提醒类型。确保每个大种类的开头之间，typeId相隔50.<p>
 *     - 0 ~ 49: 广场相关通知<p>
 *     - 50 ~ 99: 商品相关通知<p>
 * 因为枚举类不好使用switch，所以直接用静态常量定义了
 * @author DeSen Xu
 * @date 2023-01-14 14:49
 */
public class EventRemindType {
    /**
     * 对评论点赞
     */
    public static final int LIKE_COMMENT = 0;

    /**
     * 对帖子点赞
     */
    public static final int LIKE_POST = 1;

    /**
     * 自己的帖子被评论
     */
    public static final int REPLY_POST = 2;

    /**
     * 自动的评论被评论
     */
    public static final int REPLY_SUB = 3;

    /**
     * 有新交易
     */
    public static final int TRADE_STARTED = 50;
    /**
     * 交易完成
     */
    public static final int TRADE_DONE = 51;

    private EventRemindType() {}

    /**
     * 是否为广场消息相关通知
     * @param typeId 类型id
     */
    public static boolean isCommunityEvent(int typeId) {
        return typeId >= 0 && typeId < 50;
    }

    /**
     * 是否为交易类型的通知
     * @param typeId 类型id
     */
    public static boolean isTradeEvent(int typeId) {
        return typeId >= 50 && typeId < 100;
    }

}

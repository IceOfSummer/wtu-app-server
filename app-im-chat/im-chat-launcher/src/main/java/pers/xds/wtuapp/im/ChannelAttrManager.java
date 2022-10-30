package pers.xds.wtuapp.im;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * 用于管理每个channel的用户凭据
 * @author DeSen Xu
 * @date 2022-09-02 21:29
 */
public class ChannelAttrManager {

    public static final String CREDENTIALS_SAVE_KEY = "principal";

    public static void saveToken(ChannelHandlerContext ctx, UsernamePasswordAuthenticationToken credentials) {
        if (credentials == null) {
            return;
        }
        ctx.channel().attr(AttributeKey.valueOf(CREDENTIALS_SAVE_KEY)).set(credentials);
    }

    @Nullable
    public static UsernamePasswordAuthenticationToken getToken(ChannelHandlerContext ctx) {
        return (UsernamePasswordAuthenticationToken) ctx.channel().attr(AttributeKey.valueOf(CREDENTIALS_SAVE_KEY)).get();
    }

    @Nullable
    public static UsernamePasswordAuthenticationToken getToken(Channel channel) {
        return (UsernamePasswordAuthenticationToken) channel.attr(AttributeKey.valueOf(CREDENTIALS_SAVE_KEY)).get();
    }

    @Nullable
    public static UserPrincipal getPrincipal(ChannelHandlerContext ctx) {
        UsernamePasswordAuthenticationToken token = getToken(ctx);
        return token == null ? null : (UserPrincipal) token.getPrincipal();
    }

    @Nullable
    public static UserPrincipal getPrincipal(Channel channel) {
        UsernamePasswordAuthenticationToken token = getToken(channel);
        return token == null ? null : (UserPrincipal) token.getPrincipal();
    }

    public static final AttributeKey<Short> REQUEST_ID_KEY = AttributeKey.valueOf("requestId");

    public static void saveRequestId(ChannelHandlerContext ctx, short id) {
        ctx.channel().attr(REQUEST_ID_KEY).set(id);
    }

    public static Short getRequestId(ChannelHandlerContext ctx) {
        return ctx.channel().attr(REQUEST_ID_KEY).get();
    }

    public static final AttributeKey<Integer> CHANNEL_USER_ID = AttributeKey.valueOf("channelUserId");

    /**
     * 每个用户在登录完成的时候就一定会在channel带上自己的userId，并且在服务中保存自己
     * 而查找一个Channel只能查找到已经登录的用户，未登录的返回null
     */
    @NonNull
    public static Integer getChannelUserId(Channel channel) {
        return channel.attr(CHANNEL_USER_ID).get();
    }

    public static void saveChannelUserId(ChannelHandlerContext ctx, int userId) {
        ctx.channel().attr(CHANNEL_USER_ID).set(userId);
    }

    /**
     * 客户端是否建立安全连接
     */
    public static final AttributeKey<Boolean> IS_SECURE = AttributeKey.valueOf("isSecure");

    public static boolean isChannelInsecure(ChannelHandlerContext ctx) {
        Boolean secure = ctx.channel().attr(IS_SECURE).get();
        return secure == null || !secure;
    }

    public static void setChannelSecure(ChannelHandlerContext ctx, boolean secure) {
        ctx.channel().attr(IS_SECURE).set(secure);
    }

    public static void setChannelSecure(ChannelHandlerContext ctx) {
        setChannelSecure(ctx, true);
    }


}

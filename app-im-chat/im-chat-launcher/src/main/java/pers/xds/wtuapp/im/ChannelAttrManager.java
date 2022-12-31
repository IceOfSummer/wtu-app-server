package pers.xds.wtuapp.im;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.springframework.lang.Nullable;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * 用于管理每个channel的用户凭据
 * @author DeSen Xu
 * @date 2022-09-02 21:29
 */
public class ChannelAttrManager {

    public static final AttributeKey<UserPrincipal> PRINCIPAL_SAVE_KEY = AttributeKey.valueOf("principal");

    public static final AttributeKey<Short> REQUEST_ID_KEY = AttributeKey.valueOf("requestId");

    public static void savePrincipal(ChannelHandlerContext ctx, UserPrincipal credentials) {
        if (credentials == null) {
            return;
        }
        ctx.channel().attr(PRINCIPAL_SAVE_KEY).set(credentials);
    }

    @Nullable
    public static UserPrincipal getPrincipal(ChannelHandlerContext ctx) {
        return ctx.channel().attr(PRINCIPAL_SAVE_KEY).get();
    }

    @Nullable
    public static UserPrincipal getPrincipal(Channel channel) {
        return channel.attr(PRINCIPAL_SAVE_KEY).get();
    }

    public static void saveRequestId(ChannelHandlerContext ctx, short id) {
        ctx.channel().attr(REQUEST_ID_KEY).set(id);
    }

    public static Short getRequestId(ChannelHandlerContext ctx) {
        return ctx.channel().attr(REQUEST_ID_KEY).get();
    }


}

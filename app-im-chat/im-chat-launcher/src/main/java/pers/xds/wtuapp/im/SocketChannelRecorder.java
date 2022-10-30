package pers.xds.wtuapp.im;

import io.netty.channel.Channel;
import org.jetbrains.annotations.Nullable;

/**
 * @author HuPeng
 * @date 2022-10-29 18:54
 */
public interface SocketChannelRecorder {

    /**
     * 保存channel
     * @param uid 哪个用户的
     * @param channel channel
     */
    void saveChannel(int uid, Channel channel);

    /**
     * 获取channel
     * @param uid 哪个用户的
     * @return channel
     */
    @Nullable
    Channel getChannel(int uid);

    /**
     * 移除channel
     * @param uid 用户id
     */
    void removeChannel(int uid);
}

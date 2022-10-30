package pers.xds.wtuapp.im;

import io.netty.channel.Channel;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuPeng
 * @date 2022-10-29 18:56
 */
@Component
public class SocketChannelRecorderImpl implements SocketChannelRecorder{

    private final Map<Integer, Channel> recordMap = new HashMap<>();

    @Override
    public void saveChannel(int uid, Channel channel) {
        recordMap.put(uid, channel);
    }

    @Nullable
    @Override
    public Channel getChannel(int uid) {
        return recordMap.get(uid);
    }

    @Override
    public void removeChannel(int uid) {
        recordMap.remove(uid);
    }
}

package pers.xds.wtuapp.im.config;

import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author DeSen Xu
 * @date 2022-11-17 16:08
 */
public class IdleHandlerProvider {

    private final int readerIdleTimeSeconds;

    public IdleHandlerProvider(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
    }


    public IdleStateHandler idleStateHandler() {
        return new IdleStateHandler(readerIdleTimeSeconds, 0, 0);
    }


}

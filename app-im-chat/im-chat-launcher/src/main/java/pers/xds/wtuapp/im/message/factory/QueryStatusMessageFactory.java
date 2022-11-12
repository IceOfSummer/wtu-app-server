package pers.xds.wtuapp.im.message.factory;

import org.jetbrains.annotations.NotNull;
import pers.xds.wtuapp.im.message.QueryReceiveStatusMessage;
import pers.xds.wtuapp.im.message.common.MessageFactory;

/**
 * @author DeSen Xu
 * @date 2022-11-12 18:28
 */
public class QueryStatusMessageFactory implements MessageFactory<QueryReceiveStatusMessage> {

    @NotNull
    @Override
    public QueryReceiveStatusMessage createInstance(byte[] data) {
        return new QueryReceiveStatusMessage();
    }
}

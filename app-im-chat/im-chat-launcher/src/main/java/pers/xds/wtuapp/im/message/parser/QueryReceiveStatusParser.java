package pers.xds.wtuapp.im.message.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.request.QueryReceiveStatusMessage;

/**
 * @author DeSen Xu
 * @date 2022-11-12 22:03
 */
public class QueryReceiveStatusParser implements MessageParser<QueryReceiveStatusMessage> {

    @Override
    public QueryReceiveStatusMessage parseFrom(byte[] bytes) throws Exception {
        return new QueryReceiveStatusMessage();
    }

    @Override
    public byte getMessageType() {
        return QueryReceiveStatusMessage.MESSAGE_TYPE;
    }
}

package pers.xds.wtuapp.im.factory;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.ServerResponseMessage;
import pers.xds.wtuapp.im.message.common.MessageFactory;
import pers.xds.wtuapp.im.message.common.ResponseCode;

/**
 * @author DeSen Xu
 * @date 2022-10-31 0:10
 */
public class ServerResponseMessageFactory implements MessageFactory<ServerResponseMessage> {

    @Override
    @NonNull
    public ServerResponseMessage createInstance(byte[] data) {
        int res = 0;
        for(int i = 0; i < data.length; i++){
            res += (data[3 - i] & 0xff) << (i * 8);
        }
        return new ServerResponseMessage(ResponseCode.values()[res], (short) 0);
    }
}



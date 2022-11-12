package pers.xds.wtuapp.im.message;

/**
 * @author DeSen Xu
 * @date 2022-11-12 18:26
 */
public class QueryReceiveStatusMessage extends Message{

    public static final byte MESSAGE_TYPE = 7;


    public QueryReceiveStatusMessage() {
        super(MESSAGE_TYPE);
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}

package pers.xds.wtuapp.im.message;



/**
 * @author DeSen Xu
 * @date 2022-09-04 17:30
 */
public interface MessageParser<T extends Message> {


    /**
     * 解析
     * @param bytes bytes
     * @return 解析后的对象
     * @throws Exception ex
     */
    T parseFrom(byte[] bytes) throws Exception;

    /**
     * 获取消息类型
     * @return 消息类型
     */
    byte getMessageType();

}

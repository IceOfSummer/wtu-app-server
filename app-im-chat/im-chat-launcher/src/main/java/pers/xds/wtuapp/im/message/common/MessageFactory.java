package pers.xds.wtuapp.im.message.common;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.Message;

/**
 * @author DeSen Xu
 * @date 2022-09-03 15:13
 */
public interface MessageFactory<T extends Message> {



    /**
     * 创建一个消息实例
     * @param data 数据体内容
     * @return 消息实例
     */
    @NonNull
    T createInstance(byte[] data);


}

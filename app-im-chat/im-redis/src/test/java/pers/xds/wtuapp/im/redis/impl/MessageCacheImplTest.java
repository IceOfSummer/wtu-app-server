package pers.xds.wtuapp.im.redis.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.redis.MessageCache;

import java.util.List;


@SpringBootTest
class MessageCacheImplTest {

    private MessageCache messageCache;

    @Autowired
    public void setMessageCache(MessageCache messageCache) {
        this.messageCache = messageCache;
    }


    @Test
    void testSaveAndQuery() {
        Message hello = new Message(1, 1, "hello");
        hello.setMsgId(1);
        messageCache.saveMessage(hello);
        hello.setMsgId(2);
        messageCache.saveMessage(hello);
        List<String> ids = List.of("1", "2");
        Assertions.assertEquals(messageCache.queryCachedMessage(1, ids).size(), ids.size());
    }


}